
package pl.edu.pjatk.tau.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EquipmentTest {
    @Test
    public void createObjectTest() {
        Equipment e = new Equipment();
        assertNotNull(e);
    }

    @Test
    public void equipmentGettersAndSettersTest() {
        Equipment e = new Equipment();
        e.setId(1L);
        e.setOwner("Player 1");
        assertEquals(1, e.getId());
        assertEquals("Player 1", e.getOwner());
    }
}
