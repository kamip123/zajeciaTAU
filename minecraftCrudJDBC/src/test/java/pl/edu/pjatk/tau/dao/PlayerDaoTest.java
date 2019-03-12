package pl.edu.pjatk.tau.dao;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.pjatk.tau.domain.Player;
import java.sql.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class PlayerDaoTest {
    private static final Logger LOGGER = Logger.getLogger(PlayerDaoTest.class.getCanonicalName());

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    PlayerDao playerManager;
    List<Player> expectedDbState;

    @Test
    public void checkAdding() throws Exception {
        Player player = new Player();
        player.setName("BestPlayer1");
        player.setHp(100);

        assertEquals(1, playerManager.addPlayer(player));

        expectedDbState.add(player);
        assertThat(playerManager.getAllPlayers(), equalTo(expectedDbState));
}
}