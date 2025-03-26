import org.testng.Assert;
import org.testng.annotations.Test;

public class JenkCalciTest {

    @Test
    public void test1(){
        Assert.assertEquals(JenkCalci.add(3,3),6);
    }

    @Test
    public void test2(){
        Assert.assertEquals(JenkCalci.add(4,3),7);
    }
}
