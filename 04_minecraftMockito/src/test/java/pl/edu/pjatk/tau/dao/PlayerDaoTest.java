package pl.edu.pjatk.tau.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.edu.pjatk.tau.domain.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

import org.mockito.InOrder;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class PlayerDaoTest {
    PlayerDaoJdbcImpl playerDao;
    static List<Player> initialDatabaseState;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement selectAllStatementMock;

    @Mock
    PreparedStatement selectByIdStatementMock;

    @Mock
    PreparedStatement addStatementMock;

    abstract class AbstractResultSet implements ResultSet {
        int i;

        @Override
        public int getInt(String columnLabel) throws SQLException {
            if (columnLabel.equals("armor"))
                return initialDatabaseState.get(i-1).getArmor();
            else if (columnLabel.equals("hp"))
                return initialDatabaseState.get(i-1).getHp();
            else
                throw new IllegalArgumentException(columnLabel + " column does not exists or does not store int value");
        }

        @Override
        public long getLong(String columnLabel) throws SQLException {
            if (columnLabel.equals("id"))
                return initialDatabaseState.get(i-1).getId();
            else
                throw new IllegalArgumentException(columnLabel + " column does not exists or does not store long value");
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            if (columnLabel.equals("name"))
                return initialDatabaseState.get(i-1).getName();
            else
                throw new IllegalArgumentException(columnLabel + " column does not exists or does not store string value");
        }

        @Override
        public boolean next() throws SQLException {
            i++;
            return i <= initialDatabaseState.size();
        }
    }

    @Before
    public void setup() throws SQLException {
        initialDatabaseState = new ArrayList<>();
        Collections.addAll(initialDatabaseState,
                new Player(1L, "best player", 50, 10),
                new Player(2L, "worst player", 20, 1000),
                new Player(3L, "sekiro", 200, 200));

        when(connection.prepareStatement("SELECT id, name, armor, hp FROM Player ORDER BY id")).thenReturn(selectAllStatementMock);
        when(connection.prepareStatement("SELECT id, name, armor, hp FROM Player WHERE id = ?")).thenReturn(selectByIdStatementMock);
        when(connection.prepareStatement("INSERT INTO Player (name, armor, hp) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)).thenReturn(addStatementMock);

        playerDao = new PlayerDaoJdbcImpl();
        playerDao.setConnection(connection);
    }

    @Test
    public void setConnectionTest() throws SQLException {
        assertNotNull(playerDao.getConnection());
        assertEquals(playerDao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesTest() throws SQLException {
        assertNotNull(playerDao.getAllPlayersPreparedStatement);
        verify(connection).prepareStatement("SELECT id, name, armor, hp FROM Player ORDER BY id");
    }

    @Test
    public void addingTest() throws SQLException {
        InOrder inOrder = inOrder(addStatementMock);
        when(addStatementMock.executeUpdate()).thenReturn(1);

        Player player = new Player();
        player.setName("zxc123");
        player.setArmor(100);
        player.setHp(100);
        playerDao.addPlayer(player);

        inOrder.verify(addStatementMock, times(1)).setString(1, "zxc123");
        inOrder.verify(addStatementMock, times(1)).setInt(2, 100);
        inOrder.verify(addStatementMock, times(1)).setInt(3, 100);
        inOrder.verify(addStatementMock).executeUpdate();
    }

    @Test
    public void gettingByIdTest() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenReturn(initialDatabaseState.get(2).getId());
        when(mockedResultSet.getString("name")).thenReturn(initialDatabaseState.get(2).getName());
        when(mockedResultSet.getInt("armor")).thenReturn(initialDatabaseState.get(2).getArmor());
        when(mockedResultSet.getInt("hp")).thenReturn(initialDatabaseState.get(2).getHp());
        when(selectByIdStatementMock.executeQuery()).thenReturn(mockedResultSet);

        Player player = playerDao.getPlayer(2L);
        assertEquals(player, initialDatabaseState.get(2));

        verify(selectByIdStatementMock, times(1)).setLong(1, 2);
        verify(selectByIdStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getLong("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(1)).getInt("armor");
        verify(mockedResultSet, times(1)).getInt("hp");
        verify(mockedResultSet, times(1)).next();
    }

    @Test
    public void gettingAllTest() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getInt("armor")).thenCallRealMethod();
        when(mockedResultSet.getInt("hp")).thenCallRealMethod();
        when(selectAllStatementMock.executeQuery()).thenReturn(mockedResultSet);

        List<Player> players = playerDao.getAllPlayers();
        assertThat(players, equalTo(initialDatabaseState));

        verify(selectAllStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("name");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("armor");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("hp");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }
}