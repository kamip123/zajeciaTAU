package pl.edu.pjatk.tau.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.edu.pjatk.tau.domain.Equipment;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void savingTest() {
        Equipment equipment = new Equipment(3L, "Player 3", 45, 45);
        equipmentDao.save(equipment);
        assertEquals(3L, equipmentDao.equipments.get(2).getId().longValue());
        assertEquals(3, equipmentDao.equipments.size());
    }
}
