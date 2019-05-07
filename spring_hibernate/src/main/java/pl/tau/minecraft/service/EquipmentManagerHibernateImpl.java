package pl.tau.minecraft.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.tau.minecraft.domain.Player;
import pl.tau.minecraft.domain.Equipment;

@Component
@Transactional
public class EquipmentManagerHibernateImpl implements EquipmentManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	// player
	@Override
	public Long addPlayer(Player player) {
		if (player.getId() != null) throw new IllegalArgumentException("the player ID should be null if added to database");
		for (Equipment equipment : player.getEquipments()) {
			equipment.setPlayer(player);
			sessionFactory.getCurrentSession().update(equipment);
		}
		sessionFactory.getCurrentSession().persist(player);
		sessionFactory.getCurrentSession().flush();
		return player.getId();
	}

	@Override
	public List<Player> findAllPlayers() {
		return sessionFactory.getCurrentSession().getNamedQuery("player.all").list();
	}

	@Override
	public Player findPlayerById(Long id) {
		return (Player) sessionFactory.getCurrentSession().get(Player.class, id);
	}

	@Override
    public void updatePlayer(Player player) {
        sessionFactory.getCurrentSession().update(player);
    }

	@Override
	public void deletePlayer(Player player) {
		player = (Player) sessionFactory.getCurrentSession().get(Player.class, player.getId());
		for (Equipment equipment : player.getEquipments()) {
			equipment.setPlayer(null);
			sessionFactory.getCurrentSession().update(equipment);
		}
		sessionFactory.getCurrentSession().delete(player);
	}

	@Override
	public List<Equipment> findAllPlayerEquipments(Long id) {
		Player player = sessionFactory.getCurrentSession().get(Player.class, id);
		return player.getEquipments();
	}


	// equipment
	@Override
	public Long addEquipment(Equipment equipment) {
		return (Long) sessionFactory.getCurrentSession().save(equipment);
	}

	@Override
	public void updateEquipment(Equipment equipment) {
		sessionFactory.getCurrentSession().update(equipment);
	}

	@Override
	public Equipment findEquipmentById(Long id) {
		return (Equipment) sessionFactory.getCurrentSession().get(Equipment.class, id);
	}

	@Override
	public void deleteEquipment(Equipment equipment) {
        equipment = (Equipment) sessionFactory.getCurrentSession().get(Equipment.class, equipment.getId());
		sessionFactory.getCurrentSession().delete(equipment);
	}

	@Override
	public List<Equipment> findAllEquipments() {
		return sessionFactory.getCurrentSession().getNamedQuery("equipment.all").list();
	}
	
	@Override
	public List<Equipment> findEquipmentsByItemName(String itemName) {
		return (List<Equipment>) sessionFactory.getCurrentSession()
				.getNamedQuery("equipment.findEquipmentsByItemName")
				.setString("itemName", "%"+itemName+"%")
				.list();
	}

}
