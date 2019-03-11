package pl.edu.pjatk.tau.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.pjatk.tau.domain.Equipment;

import java.util.ArrayList;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EquipmentInMemoryDaoTest {
    EquipmentInMemoryDao equipmentDao;

    @Before
    public void setup() {
        equipmentDao = new EquipmentInMemoryDao();
        equipmentDao.equipments = new ArrayList<>();
        equipmentDao.equipments.add((new Equipment(1L, "Player 1", 30, 30)));
        equipmentDao.equipments.add((new Equipment(2L, "Player 2", 35, 35)));
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(equipmentDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void alreadyExistsTest() {
        Equipment equipment = new Equipment(1L, "Player 1", 30, 30);
        equipmentDao.save(equipment);
    }

    @Test
    public void savingTest() {
        Equipment equipment = new Equipment(3L, "Player 3", 45, 45);
        equipmentDao.save(equipment);
        assertEquals(3L, equipmentDao.equipments.get(2).getId().longValue());
        assertEquals(3, equipmentDao.equipments.size());
    }

    @Test
    public void updateTest() {
        Equipment equipment = equipmentDao.getById(1L).get();
        equipment.setSlotOne(50);
        equipmentDao.update(equipment);
        assertEquals(50, equipmentDao.getById(1L).get().getSlotOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateExistingTest() {
        Equipment equipment = new Equipment(3L, "Player 3", 45, 45);
        equipmentDao.update(equipment);
    }

    @Test
    public void gettingAllEquipmentsTest() {
        assertArrayEquals(equipmentDao.equipments.toArray(), equipmentDao.getAll().toArray());
        assertEquals(equipmentDao.equipments.size(), equipmentDao.getAll().size());
    }

    @Test
    public void gettingByIdTest() {
        Equipment equipment = new Equipment(3L, "Player 4", 55, 55);
        equipmentDao.save(equipment);
        assertEquals(equipment, equipmentDao.getById(3L).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void gettingExistsTest() {
        equipmentDao.getById(4L);
    }

    @Test
    public void deleteTest() {
        Equipment equipment = equipmentDao.getById(1L).get();
        equipmentDao.delete(equipment);
        assertEquals(1, equipmentDao.equipments.size());
    }
}
