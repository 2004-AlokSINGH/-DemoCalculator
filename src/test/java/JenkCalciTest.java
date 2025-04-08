import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.Assert;
import org.testng.annotations.*;

public class JenkCalciTest {

    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }

    @Test
    public void testAdd1() {
        test = extent.createTest("Addition Test - Positive");
        Assert.assertEquals(JenkCalci.add(3, 3), 6);
    }

    @Test
    public void testAdd2() {
        test = extent.createTest("Addition Test - Negative Case");
        Assert.assertEquals(JenkCalci.add(4, 3), 8); // Fail (expected 7)
    }

    @Test
    public void testSubtract() {
        test = extent.createTest("Subtraction Test");
        Assert.assertEquals(JenkCalci.subtract(10, 5), 5);
    }

    @Test
    public void testMultiply() {
        test = extent.createTest("Multiplication Test");
        Assert.assertEquals(JenkCalci.multiply(2, 5), 10);
    }

    @Test
    public void testMultiplyFail() {
        test = extent.createTest("Multiplication Fail Test");
        Assert.assertEquals(JenkCalci.multiply(2, 3), 5); // Fail (expected 6)
    }

    @Test
    public void testDivide() {
        test = extent.createTest("Division Test");
        Assert.assertEquals(JenkCalci.divide(10, 2), 5);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        test = extent.createTest("Division by Zero Test");
        JenkCalci.divide(5, 0); // should throw ArithmeticException
    }

    @Test
    public void testDivideFail() {
        test = extent.createTest("Division Fail Test");
        Assert.assertEquals(JenkCalci.divide(10, 2), 4); // Fail (expected 5)
    }
}
