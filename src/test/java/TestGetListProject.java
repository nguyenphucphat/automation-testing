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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestGetListProject {
    WebDriver driver;
    String URL = "http://localhost:4000";
    //    String firefoxDriverPath = "/home/nhan/Desktop/KCPM/final-project/geckodriver-v0.35.0-linux64/geckodriver";
    String chromeDriverPath = "/Users/admin/Documents/hcmus/test/automation_testing/chromedriver-mac-arm64/chromedriver";

    public void setup() {
        System.setProperty("chrome.driver", chromeDriverPath);
//        System.setProperty("firefox.driver", firefoxDriverPath);
        driver = new ChromeDriver();

        Utils.login(driver);
    }

    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/list-project.csv", numLinesToSkip = 1)
    public void testGetListProject(String projectName, String sortField, String sortOrder, boolean expectedSuccess) {
        setup();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5, 0));
        String url = "http://localhost:4000/project/list?page=1" +
                "&sortField=" + sortField +
                "&sortOrder=" + sortOrder +
                "&projectKeyword=" + projectName;
        driver.get(url);

        boolean actualSuccess = false;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("html > body > main > div:nth-of-type(3) > div > div:nth-of-type(2) > div > div:nth-of-type(1) > a > div > div > h4")));
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
