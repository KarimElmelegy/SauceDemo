import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test(priority = 1)
    public void ValidLoginTest() throws InterruptedException {
        // Wait for 2 seconds
        Thread.sleep(2000);

        // Interact with the username field
        WebElement username = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        username.click();
        username.sendKeys("standard_user");

        // Wait for 2 seconds
        Thread.sleep(2000);

        // Interact with the password field
        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.click();
        password.sendKeys("secret_sauce");

        // Wait for 2 seconds
        Thread.sleep(2000);

        // Click the submit button
        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
        submit.click();

        // Wait for 2 seconds
        Thread.sleep(2000);
        WebElement productsTitle = driver.findElement(By.className("title"));

    }

    @Test(priority = 2)
    public void InvalidLoginTest() throws InterruptedException {
        // Wait for 2 seconds
        Thread.sleep(2000);

        // Interact with the username field
        WebElement username = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        username.click();
        username.sendKeys("InvalidUser");

        // Wait for 2 seconds
        Thread.sleep(2000);

        // Interact with the password field
        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.click();
        password.sendKeys("secret_sauce");

        // Wait for 2 seconds
        Thread.sleep(2000);

        // Click the submit button
        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
        submit.click();

        // Wait for 2 seconds
        Thread.sleep(2000);
        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"));

    }

    @Test(priority = 3)
    public void ErrorMessage()throws InterruptedException  {
        // Use explicit waits
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Interact with the username field
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Username']")));
        username.sendKeys("InvalidUser");

        // Interact with the password field
        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.sendKeys("secret_sauce");
        Thread.sleep(2000);

        // Click the submit button
        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
        submit.click();
        Thread.sleep(2000);

        // Verify error message is displayed for invalid login attempt
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match any user"),
                "Error message not displayed as expected for invalid login.");
        Thread.sleep(2000);
        System.out.println("Username and password do not match any user");
        Thread.sleep(2000);


    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}