package pl.tau.minecraft.service;

import java.util.List;

import pl.tau.minecraft.domain.Equipment;
import pl.tau.minecraft.domain.Player;

public interface EquipmentManager {
	// player
	Long addPlayer(Player player);
	List<Player> findAllPlayers();
	Player findPlayerById(Long id);
	void deletePlayer(Player player);
	void updatePlayer(Player player);
	List<Equipment> findAllPlayerEquipments(Long id);

	// equipment
	Long addEquipment(Equipment equipment);
	void updateEquipment(Equipment equipment);
	Equipment findEquipmentById(Long id);
	void deleteEquipment(Equipment equipment);
	List<Equipment>  findAllEquipments();
	List<Equipment> findEquipmentsByItemName(String itemName);
	void switchEquipmentOwner(Long idPrev, Long idNext, Equipment equipment);
}
