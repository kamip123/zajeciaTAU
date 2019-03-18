package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;
import pl.edu.pjatk.tau.domain.Player;

import java.sql.*;

public class PlayerDaoJdbcImpl implements PlayerDao {

    private Connection connection;
    private PreparedStatement addPlayerPreparedStatement;

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

}


