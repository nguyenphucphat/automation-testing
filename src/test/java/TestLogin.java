import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestLogin {
    WebDriver driver;
    String URL = "http://localhost:4000";
    //    String firefoxDriverPath = "/home/nhan/Desktop/KCPM/final-project/geckodriver-v0.35.0-linux64/geckodriver";
    String chromeDriverPath = "/Users/admin/Documents/hcmus/test/automation_testing/chromedriver-mac-arm64/chromedriver";

    public void setup() {
        System.setProperty("chrome.driver", chromeDriverPath);
//        System.setProperty("firefox.driver", firefoxDriverPath);
        driver = new ChromeDriver();
    }

    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/login.csv", numLinesToSkip = 1)
    public void testLogin(String email, String password, boolean expectedHaveError) {
        setup();

        driver.get("http://localhost:4000/login");
        driver.manage().window().setSize(new Dimension(1512, 945));
        driver.findElement(By.id("fEmail")).clear();
        driver.findElement(By.id("fEmail")).sendKeys(email);
        driver.findElement(By.id("fPassword")).clear();
        driver.findElement(By.id("fPassword")).sendKeys(password);
        driver.findElement(By.cssSelector(".w-100")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(0, 5));
        boolean haveError = false;

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("html > body > main > main > div > div:nth-of-type(2) > div > form > div:nth-of-type(2) > div:nth-of-type(1) > div")));
            haveError = true;
        } catch (Exception e) {

        }

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("html > body > main > main > div > div:nth-of-type(2) > div > form > div:nth-of-type(2) > div:nth-of-type(2) > div")));
            haveError = true;
        } catch (Exception e) {
            // Không làm gì nếu không tìm thấy lỗi password
        }

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class^='alert']")));
            haveError = true;
        } catch (Exception e) {
            // Không làm gì nếu không tìm thấy lỗi password
        }

        // Kiểm tra xem có ít nhất một lỗi hay không
        Assertions.assertEquals(expectedHaveError, haveError);
    }

    @AfterEach
    public void close() {
        tearDown();
    }
}
