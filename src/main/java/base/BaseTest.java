package base;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.io.ByteArrayInputStream;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        // Initialize Driver (suite-safe)
        DriverManager.initDriver();

        WebDriver driver = DriverManager.getDriver();

        // Navigate to URL from config.properties
        String url = ConfigReader.getProperty("url");
        driver.get(url);

        // Maximize Browser
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        WebDriver driver = DriverManager.getDriver();

        // Take screenshot only if test fails
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Failed Test Screenshot",
                    new ByteArrayInputStream(screenshot)
            );
        }

        // Quit & clean ThreadLocal driver
        DriverManager.quitDriver();
    }
}
