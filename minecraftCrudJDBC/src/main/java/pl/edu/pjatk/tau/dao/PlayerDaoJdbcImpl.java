package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class PlayerDaoJdbcImpl implements PlayerDao {

    private Connection connection;

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
    }

}


