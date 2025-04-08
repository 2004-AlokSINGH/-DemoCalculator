import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.Assert;
import org.testng.annotations.*;

public class JenkCalciTest {

    private static ExtentReports extent;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReports.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    @Test
    public void testAdd1() {
        ExtentTest test = extent.createTest("Addition Test - Positive");
        try {
            Assert.assertEquals(JenkCalci.add(3, 3), 6);
            test.pass("Test passed");
        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testAdd2() {
        ExtentTest test = extent.createTest("Addition Test - Negative Case");
        try {
            Assert.assertEquals(JenkCalci.add(4, 3), 7); // Intentional fail for report
            test.pass("Test passed");
        } catch (AssertionError e) {
            test.fail("Assertion failed: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void testSubtract() {
        ExtentTest test = extent.createTest("Subtraction Test");
        Assert.assertEquals(JenkCalci.subtract(10, 5), 5);
        test.pass("Test passed");
    }

    @Test
    public void testMultiply() {
        ExtentTest test = extent.createTest("Multiplication Test");
        Assert.assertEquals(JenkCalci.multiply(2, 5), 10);
        test.pass("Test passed");
    }

    @Test
    public void testMultiplyFail() {
        ExtentTest test = extent.createTest("Multiplication Fail Test");
        Assert.assertEquals(JenkCalci.multiply(2, 3), 6); // Intentional fail
        test.pass("Test passed");
    }

    @Test
    public void testDivide() {
        ExtentTest test = extent.createTest("Division Test");
        Assert.assertEquals(JenkCalci.divide(10, 2), 5);
        test.pass("Test passed");
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        ExtentTest test = extent.createTest("Division by Zero Test");
        JenkCalci.divide(5, 0);
        test.pass("Expected exception thrown");
    }

    @Test
    public void testDivideFail() {
        ExtentTest test = extent.createTest("Division Fail Test");
        Assert.assertEquals(JenkCalci.divide(10, 2), 5); // Intentional fail
        test.pass("Test passed");
    }
}
