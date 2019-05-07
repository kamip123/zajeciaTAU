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
     * Helper method allowing for easier adding players to tests
     * @param name
     * @param hp
     * @param armor
     * @param currentEquipment
     * @return
     */
    private Player addPlayerHelper(String name, int hp, int armor, List<Equipment> currentEquipment) {
        Long playerId;
        Player player;
        player = new Player();

        player.setName(name);
        player.setHp(hp);
        player.setArmor(armor);
        player.setEquipments(currentEquipment);
        player.setId(null);

        playerIds.add(playerId = equipmentManager.addPlayer(player));
        assertNotNull(playerId);
        return player;
    }

    /**
     * Helper function for fast adding clients
     * @param firstPosition
     * @param secondPosition
     * @param firstPositionQuantity
     * @param secondPositionQuantity
     * @param player
     * @return
     */
    private Equipment addEquipmentHelper(String firstPosition, String secondPosition, int firstPositionQuantity, int secondPositionQuantity, Player player) {
        Long equipmentId;
        Equipment equipment;
        equipment = new Equipment();

        equipment.setFirstPosition(firstPosition);
        equipment.setSecondPosition(secondPosition);
        equipment.setFirstPositionQuantity(firstPositionQuantity);
        equipment.setSecondPositionQuantity(secondPositionQuantity);
        equipment.setPlayer(player);
        equipment.setId(null);

        equipmentIds.add(equipmentId = equipmentManager.addEquipment(equipment));
        assertNotNull(equipmentId);
        return equipment;
    }


    @Before
    public void setup() {
        equipmentIds = new LinkedList<>();
        playerIds = new LinkedList<>();

        addEquipmentHelper("sword +1", "shield", 1 , 1, null);
        addEquipmentHelper("spear +1","armor", 1, 1, null);
        addEquipmentHelper("axe +1","shield", 1, 1, null);

        Equipment equipment = addEquipmentHelper("axe +100","shield +60", 10,  10, null);
        Equipment equipment2 = addEquipmentHelper("axe +2","shield +7", 3,  5, null);

        List<Equipment> currentEquipment = new LinkedList<Equipment>();
        currentEquipment.add(equipment);
        addPlayerHelper("witcher", 100, 10, currentEquipment);

        currentEquipment = new LinkedList<Equipment>();
        currentEquipment.add(equipment2);
        Player player = addPlayerHelper("Nameless", 1000, 1000, currentEquipment);
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
    public void getEquipmentByIdTest() {
        Equipment equipment = equipmentManager.findEquipmentById(equipmentIds.get(0));
        assertEquals("sword +1",equipment.getFirstPosition());
    }

    @Test
    public void getPlayerByIdTest() {
        Player player = equipmentManager.findPlayerById(playerIds.get(0));
        assertEquals("witcher",player.getName());
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

    @Test
    public void findByItemNameTest() {
        List<Equipment> equipments = equipmentManager.findEquipmentsByItemName("hield");
        assertEquals("shield", equipments.get(0).getSecondPosition());
    }

    @Test
    public void deletePlayerTest() {
        int prevSize = equipmentManager.findAllPlayers().size();
        Player player = equipmentManager.findPlayerById(playerIds.get(1));
        assertNotNull(player);
        equipmentManager.deletePlayer(player);
        assertNull(equipmentManager.findPlayerById(playerIds.get(1)));
        assertEquals(prevSize-1,equipmentManager.findAllPlayers().size());
    }

    @Test
    public void updatePlayerNameTest() {
        Player player = equipmentManager.findPlayerById(playerIds.get(0));
        assertNotNull(player);
        player.setName("Pokemon Master");
        equipmentManager.updatePlayer(player);
        assertEquals("Pokemon Master", equipmentManager.findPlayerById(playerIds.get(0)).getName());
    }

    @Test
    public void addPlayerEquipmentsTest() {
        Player player = equipmentManager.findPlayerById(playerIds.get(0));
        assertNotNull(player);
        int prevSize = player.getEquipments().size();
        assertEquals(prevSize, 1);

        Equipment equipment = equipmentManager.findEquipmentById(equipmentIds.get(0));
        assertNotNull(equipment);
        equipment.setPlayer(player);
        equipmentManager.updateEquipment(equipment);
        player.getEquipments().add(equipment);
        equipmentManager.updatePlayer(player);

        player = equipmentManager.findPlayerById(playerIds.get(0));
        int newSize = player.getEquipments().size();
        assertEquals(prevSize+1, newSize);
    }

    @Test
    public void switchPlayerEquipmentsTest() {
        Player player = equipmentManager.findPlayerById(playerIds.get(0));
        assertNotNull(player);
        int prevSize = player.getEquipments().size();

        Player player2 = equipmentManager.findPlayerById(playerIds.get(1));
        assertNotNull(player2);
        int prevSize2 = player2.getEquipments().size();

        Equipment equipment = equipmentManager.findEquipmentById(equipmentIds.get(1));
        assertNotNull(equipment);

        equipmentManager.switchEquipmentOwner(player.getId(), player2.getId(), equipment);

        Player player = equipmentManager.findPlayerById(playerIds.get(0));
        Player player2 = equipmentManager.findPlayerById(playerIds.get(1));
        int newSize = player.getEquipments().size();
        int newSize2 = player2.getEquipments().size();

        assertEquals(prevSize-1, newSize);
        assertEquals(prevSize+1, newSize2);
    }
}
