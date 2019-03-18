package pl.edu.pjatk.tau.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pl.edu.pjatk.tau.domain.Player;

public interface PlayerDao {
	Connection getConnection();
	void setConnection(Connection connection) throws SQLException;
	List<Player> getAllPlayers();

	int addPlayer(Player player);
	int deletePlayer(Player player);
	int updatePlayer(Player player) throws SQLException;
	Player getPlayer(long id) throws SQLException;
}