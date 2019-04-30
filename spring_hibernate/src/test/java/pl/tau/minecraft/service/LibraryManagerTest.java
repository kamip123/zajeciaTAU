package pl.tau.minecraft.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.tau.minecraft.domain.Player;
import pl.tau.minecraft.domain.Equipment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class EquipmentManagerTest {

	@Autowired
    EquipmentManager equipmentManager;

	List<Long> playerIds;
	List<Long> equipmentIds;

	/**
	 * Helper method allowing for easier adding books to tests
	 * @param name
	 * @param hp
	 * @param armor
	 * @return
	 */
	private Player addPlayerHelper(String name, int hp, int armor) {
		Long playerId;
		Player player;
		player = new Player();

		player.setName(name);
		player.setHp(hp);
		player.setArmor(armor);
		player.setId(null);

		playerIds.add(playerId = equipmentManager.addPlayer(player));
		assertNotNull(playerId);
		return player;
	}

	/**
	 * Helper function for fast adding clients
	 * @param name
	 * @param boorowedBooks
	 * @return
	 */
	private Equipment addEquipmentHelper(String firstPosition, String secondPosition, int firstPositionQuantity, int secondPositionQuantity) {
		Long equipmentId;
		Equipment equipment;
		equipment = new Equipment();

		equipment.setFirstPosition(firstPosition);
		equipment.setSecondPosition(secondPosition);
		equipment.setFirstPositionQuantity(firstPositionQuantity);
		equipment.setSecondPositionQuantity(secondPositionQuantity);
		equipment.setId(null);

		equipmentIds.add(equipmentId = equipmentManager.addEquipment(equipment));
		assertNotNull(equipmentId);
		return equipment;
	}


	@Before
	public void setup() {
		equipmentIds = new LinkedList<>();
		playerIds = new LinkedList<>();

		addEquipmentHelper('sword +1', 1, 'shield', 1);
		addEquipmentHelper('spear +1', 1, 'armor', 1);
		addEquipmentHelper('axe +1', 1, 'shield', 1);
		Equipment equipment = addEquipmentHelper('axe +1', 1, 'shield +6', 1);

		addPlayerHelper('witcher', 100, 10);
		Player player = addPlayerHelper('Nameless', 1000, 1000);
	}

	@Test
	public void addPlayerTest() {
		assertTrue(playerIds.size() > 0);
	}

	@Test
	public void addEquipmentTest() {
		assertTrue(equipmentIds.size() > 0);
	}

	@Test
	public void getAllPlayersTest() {
		List <Long> foundIds = new LinkedList<>();
		for (Player player: equipmentManager.findAllPlayers()) {
			if (playerIds.contains(player.getId())) foundIds.add(player.getId());
		}
		assertEquals(playerIds.size(), playerIds.size());
	}

	@Test
	public void getAllEquipmentsTest() {
		List <Long> foundIds = new LinkedList<>();
		for (Equipment equipment: equipmentManager.findAllEquipments()) {
			if (equipmentIds.contains(equipment.getId())) foundIds.add(equipment.getId());
		}
		assertEquals(equipmentIds.size(), foundIds.size());
	}

	///////////////////////

	@Test
	public void deleteBookNotBorrowedTest() {
		int prevSize = libraryManager.findAllBooks().size();
		Book book = libraryManager.findBookById(bookIds.get(0));
		assertNotNull(book);
		libraryManager.deleteBook(book);
		assertNull(libraryManager.findBookById(bookIds.get(0)));
		assertEquals(prevSize-1,libraryManager.findAllBooks().size());
	}

	@Test
	public void findByItemNameTest() {
		List<Equipment> equipments = equipmentManager.findEquipmentsByItemName("hield");
		assertEquals("shield", equipments.get(0).getSecondPositionQuantity());
	}
}
