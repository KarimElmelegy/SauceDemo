import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;


public class Logout {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.sendKeys("secret_sauce");

        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
        submit.click();
    }

    @Test(priority = 1)
    public void LogoutTest() throws InterruptedException {
        // Wait for the products page to load
        Thread.sleep(2000);
        WebElement Menubar = driver.findElement(By.xpath("//button[@type='button']"));
        Menubar.click();
        Thread.sleep(2000);
        WebElement LogoutButton = driver.findElement(By.xpath("//a[@id='logout_sidebar_link']"));
        LogoutButton.click();
        Thread.sleep(2000);
        // Verify that the user is redirected to the login page
        String expectedUrl = "https://www.saucedemo.com/"; // Adjust this if the login URL is different
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "User is not redirected to the login page!");
        Thread.sleep(2000);

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}




