
package pl.edu.pjatk.tau;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AddTest{
    Add a = new Add();

    @Test
    public void additionTest(){
        Assert.assertEquals(4.0, a.add(2.0, 2.0),0.001);
    }

    @Test
    public void sumTest(){
        Assert.assertEquals(1.0, a.sum(),0.001);
    }
}
