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

}


