
package pl.edu.pjatk.tau.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import pl.edu.pjatk.tau.domain.Equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RunWith(BlockJUnit4ClassRunner.class)
public class EquipmentInMemoryDaoTest {

    EquipmentInMemoryDao dao;

    @Before
    public void setup() {
        Equipment e1 = new Equipment();
        Equipment e2 = new Equipment();
        e1.setId(1L);
        e1.setOwner("Player 1");
        e2.setId(2L);
        e2.setOwner("Player 2");
        dao = new EquipmentInMemoryDao();
        dao.equipments = new HashMap<Long, Equipment>();
        dao.equipments.put(1L,e1);
        dao.equipments.put(2L,e2);
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(dao);
    }

    @Test
    public void getPEquipmentThatExistsTest() {
        Optional<Equipment> e = dao.get(2L);
        assertThat(e.get().getOwner(), is("Player 2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNotExisitingEquipmentShouldThrowTest() {
        Equipment e1 = new Equipment();
        e1.setId(9);
        e1.setOwner("Player 3");
        dao.update(e1);
    }

    @Test
    public  void updateOneRecordTest() {
        Equipment e1 = new Equipment();
        e1.setId(1);
        e1.setOwner("Player 4");
        Equipment e2 = new Equipment();
        e2.setId(2);
        e2.setOwner("Player 2");

        Collection<Equipment> listExpected = dao.equipments.values();
        for (Equipment e:listExpected) if (e.getId()==1) e.setOwner("Player 4");

        dao.update(e1);

        Collection<Equipment> listAfter = dao.equipments.values();
        assertArrayEquals(listExpected.toArray(), listAfter.toArray());
    }
}