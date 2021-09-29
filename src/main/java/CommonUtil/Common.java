package CommonUtil;

import com.google.common.base.Throwables;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Common {

    public WebDriver driver;
    public ExtentReports report;
    public ExtentTest test;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        report = new ExtentReports("Reports\\ExtentReportResults.html");
        report.loadConfig(new File("Reports\\config.xml"));
        test = report.startTest("OrderTest");
    }

    @BeforeMethod
    public void launchChrome() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(readProp().getProperty("URL"));
    }

    @AfterMethod
    public void close(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            testFail(result);
        } else {
            test.log(LogStatus.PASS, "The test was executed successfully.");
            driver.quit();
        }
    }

    @AfterTest
    public void endReports(){
        report.endTest(test);
        report.flush();
    }

    public Properties readProp() throws IOException {
        Properties prop = new Properties();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/config.txt"));
        prop.load(br);
        return prop;
    }

    public void testFail(ITestResult result) throws IOException {
        File scrShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String now = DateTimeFormatter.ofPattern("dd-MM-yy HH.mm.ss").format(LocalDateTime.now());
        FileUtils.copyFile(scrShot, new File("FailedScr/" + now + ".png"));
        String path = Paths.get("FailedScr/" + now + ".png").toAbsolutePath().toString();
        test.log(LogStatus.FAIL, test.addScreenCapture(path), "The Test \""+result.getName()+ "\" has failed.");
        test.log(LogStatus.INFO,"", Throwables.getStackTraceAsString(result.getThrowable()));
    }
}
