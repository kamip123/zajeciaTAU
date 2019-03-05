
package pl.edu.pjatk.tau.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class EquipmentTest {
    @Test
    public void createObjectTest() {
        Equipment e = new Equipment();
        assertNotNull(e);
    }

    @Test
    public void equipmentGettersAndSettersTest() {
        Equipment e = new Equipment();
        e.setId(1);
        e.setOwner("Player 1");
        assertEquals(1, e.getId());
        assertEquals("Player 1", e.getOwner());
    }   

    @Test
    public void equipmentLimitTest() {
        Equipment e = new Equipment();
        assertEquals(64, e.add(65, 1));
        assertEquals(64, e.add(65, 2));
    }
}
