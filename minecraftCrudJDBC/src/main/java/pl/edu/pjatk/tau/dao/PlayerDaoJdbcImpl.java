package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;
import pl.edu.pjatk.tau.domain.Player;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class PlayerDaoJdbcImpl implements PlayerDao {

    private Connection connection;
    private PreparedStatement addPlayerPreparedStatement;
    private PreparedStatement getAllPlayersPreparedStatement;
    private PreparedStatement getPlayerByIdPreparedStatement;
    private PreparedStatement updatePlayerPreparedStatement;
    private PreparedStatement deletePlayerPreparedStatement;

    public PlayerDaoJdbcImpl() { }

    public PlayerDaoJdbcImpl(Connection connection) throws SQLException {
        setConnection(connection);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addPlayerPreparedStatement = connection.prepareStatement(
                "INSERT INTO Player (name, armor, hp) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        getAllPlayersPreparedStatement = connection.prepareStatement("SELECT id, name, armor, hp FROM Player ORDER By id");
        getPlayerByIdPreparedStatement = connection.prepareStatement("SELECT id, name, armor, hp FROM Player WHERE id = ?");
        updatePlayerPreparedStatement = connection.prepareStatement("UPDATE Player SET armor = ? WHERE id = ?");
        deletePlayerPreparedStatement = connection.prepareStatement("DELETE FROM Player WHERE id =  ?");
    }

    @Override
    public int addPlayer(Player player) {
        int count;
        try {
            addPlayerPreparedStatement.setString(1, player.getName());
            addPlayerPreparedStatement.setInt(2, player.getArmor());
            addPlayerPreparedStatement.setInt(3, player.getHp());
            count = addPlayerPreparedStatement.executeUpdate();
            ResultSet generatedKeys = addPlayerPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                player.setId(generatedKeys.getLong(1));
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return count;
    }

    @Override
    public int updatePlayer(Player player) throws SQLException {
        int count;
        try {
            updatePlayerPreparedStatement.setInt(1, player.getArmor());
            if (player.getId() != null) {
                updatePlayerPreparedStatement.setLong(2, player.getId());
            }
            else {
                updatePlayerPreparedStatement.setLong(2, -1);
            }
            count = updatePlayerPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        if (count == 1)
            return count;
        else
            throw new SQLException("Player not found");
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            ResultSet result = getAllPlayersPreparedStatement.executeQuery();
            while (result.next()) {
                Player player = new Player();
                player.setId(result.getLong("id"));
                player.setName(result.getString("name"));
                player.setArmor(result.getInt("armor"));
                player.setHp(result.getInt("hp"));
                players.add(player);
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return players;
    }

    @Override
    public Player getPlayer(long id) throws SQLException {
        try {
            getPlayerByIdPreparedStatement.setLong(1, id);
            ResultSet result = getPlayerByIdPreparedStatement.executeQuery();
            if (result.next()) {
                Player player = new Player();
                player.setId(result.getLong("id"));
                player.setName(result.getString("name"));
                player.setArmor(result.getInt("armor"));
                player.setHp(result.getInt("hp"));
                return player;
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        throw new SQLException("Player with id " + id + " does not exists");
    }

    @Override
    public int deletePlayer(Player player) {
        try {
            deletePlayerPreparedStatement.setLong(1, player.getId());
            return deletePlayerPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}


