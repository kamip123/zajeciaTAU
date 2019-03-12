package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pl.edu.pjatk.tau.domain.Player;

public interface PlayerDao {
	public Connection getConnection();
	public void setConnection(Connection connection) throws SQLException;
	public List<Player> getAllPlayers();
	public int addPlayer(Player player);
	public int deletePPlayer(Player player);
	public int updatePlayer(Player player) throws SQLException;
	public Player getPlayer(long id) throws SQLException;
}