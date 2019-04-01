package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;

import pl.edu.pjatk.tau.domain.Player;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class PlayerDaoJdbcImpl implements PlayerDao {


    public PreparedStatement addPlayerPreparedStatement;
    public PreparedStatement getAllPlayersPreparedStatement;
    public PreparedStatement getPlayerPreparedStatement;
    public PreparedStatement updatePlayerPreparedStatement;
    public PreparedStatement deletePlayerPreparedStatement;

    Connection connection;

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
        getAllPlayersPreparedStatement = connection.prepareStatement(
                "SELECT id, name, armor, hp FROM Player ORDER BY id");
        getPlayerPreparedStatement = connection.prepareStatement("SELECT id, name, armor, hp FROM Player WHERE id = ?");
        updatePlayerPreparedStatement = connection.prepareStatement("UPDATE Player SET hp = ? WHERE id = ?");
        deletePlayerPreparedStatement = connection.prepareStatement("DELETE FROM Player WHERE id = ?");
    }

    @Override
    public int addPlayer(Player player) throws SQLException {
        int result;
        try {
            addPlayerPreparedStatement.setString(1, player.getName());
            addPlayerPreparedStatement.setInt(2, player.getArmor());
            addPlayerPreparedStatement.setInt(3, player.getHp());
            result = addPlayerPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return result;
    }

    @Override
    public Player getPlayer(long id) throws SQLException {
        try {
            getPlayerPreparedStatement.setLong(1, id);
            ResultSet result = getPlayerPreparedStatement.executeQuery();
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
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return players;
    }

    @Override
    public int updatePlayer(Player player) throws SQLException {
        int result;
        try {
            updatePlayerPreparedStatement.setInt(1, player.getHp());
            if (player.getId() != null) {
                updatePlayerPreparedStatement.setLong(2, player.getId());
            }
            else {
                updatePlayerPreparedStatement.setLong(2, -1);
            }
            result = updatePlayerPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        if (result == 1)
            return result;
        else
            throw new SQLException("Player not found");
    }

    @Override
    public int deletePlayer(Player player) throws SQLException {
        int result;
        try {
            if (player.getId() != null) {
                deletePlayerPreparedStatement.setLong(1, player.getId());
            }
            else {
                deletePlayerPreparedStatement.setLong(1, -1);
            }
            result = deletePlayerPreparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        if (result == 1)
            return result;
        else
            throw new SQLException("Player not found");
    }

}


