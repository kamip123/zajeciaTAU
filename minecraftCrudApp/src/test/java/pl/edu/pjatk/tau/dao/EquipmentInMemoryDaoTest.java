package pl.edu.pjatk.tau.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class EquipmentInMemoryDaoTest {
    EquipmentInMemoryDao equipmentDao;

    @Before
    public void setup() {
        equipmentDao = new EquipmentInMemoryDao();
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(equipmentDao);
    }
}