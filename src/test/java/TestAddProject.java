import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestAddProject {
    WebDriver driver;
    String URL = "http://localhost:4000";
    String firefoxDriverPath = "/Users/admin/Documents/hcmus/test/automation_testing/firefoxdriver/geckodriver";
    String chromeDriverPath = "/Users/admin/Documents/hcmus/test/automation_testing/chromedriver-mac-arm64/chromedriver";

    public void setup() {
        System.setProperty("chrome.driver", chromeDriverPath);
        System.setProperty("firefox.driver", firefoxDriverPath);
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        driver = new SafariDriver();
        Utils.login(driver);
    }

    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/add-project.csv", numLinesToSkip = 1)
    public void testAddProject(String workspace, String projectName, boolean isSample, boolean expectedSuccess) {
        setup();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8, 0));
        driver.get("http://localhost:4000/project/list?page=1");
        driver.manage().window().setSize(new Dimension(1512, 875));
        driver.findElement(By.cssSelector(".col-md-6 > .btn-outline-danger")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("workspace")));
        driver.findElement(By.name("workspace")).sendKeys(workspace);
        driver.findElement(By.name("projectName")).clear();
        driver.findElement(By.name("projectName")).sendKeys(projectName);

        if (isSample) {
            driver.findElement(By.id("fSampleData")).click();
        }
        driver.findElement(By.cssSelector("#addProject .btn-danger")).click();

        boolean actualSuccess = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#project-info")));
            actualSuccess = true;
        } catch (Exception e) {

        }

        Assertions.assertEquals(expectedSuccess, actualSuccess);
    }

    @AfterEach
    public void close() {
        tearDown();
    }
}
