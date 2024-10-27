import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;

import java.util.List;

public class Cart {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        // Perform login first
        WebElement username = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.sendKeys("secret_sauce");

        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
        submit.click();
    }

    @Test(priority = 2)
    public void AddToCart() throws InterruptedException {
        // Wait for the products page to load
        Thread.sleep(2000);
        WebElement AddToCart = driver.findElement(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));
        AddToCart.click();
        Thread.sleep(2000);
        WebElement CheckCart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        CheckCart.click();
        Thread.sleep(2000);
        WebElement cartItem = driver.findElement(By.xpath("//div[@id='page_wrapper']"));
        Assert.assertTrue(cartItem.isDisplayed(), "Item not found in the cart!");
    }

    @Test(priority = 2)
    public void RemoveFromCart() throws InterruptedException {
        // Wait for the products page to load
        Thread.sleep(2000);

        WebElement AddToCart = driver.findElement(By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']"));
        AddToCart.click();
        Thread.sleep(2000);
        WebElement CheckCart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        CheckCart.click();
        Thread.sleep(2000);
        WebElement Remove = driver.findElement(By.xpath("//button[@class='btn btn_secondary btn_small cart_button']"));
        Remove.click();
        Thread.sleep(2000);

        // Verify the cart badge is updated (disappears if no items are left)
        List<WebElement> cartBadge = driver.findElements(By.className("shopping_cart_badge"));
        Assert.assertTrue(cartBadge.isEmpty(), "Cart is not empty after removing item!");

        // Verify that the cart item is no longer displayed
        List<WebElement> cartItem = driver.findElements(By.className("inventory_item_name"));
        Assert.assertTrue(cartItem.isEmpty(), "Item still found in the cart after removal!");
    }

    @Test(priority = 3)
    public void verifyCartBadgeUpdates() throws InterruptedException {
        // Wait for the products page to load
        Thread.sleep(2000);

        // Verify the cart badge is initially empty (no items)
        List<WebElement> initialCartBadge = driver.findElements(By.className("shopping_cart_badge"));
        Assert.assertTrue(initialCartBadge.isEmpty(), "Cart badge should be empty initially!");

        // Add an item to the cart
        WebElement addToCart = driver.findElement(By.xpath("//button[contains(text(),'Add to cart')]"));
        addToCart.click();
        Thread.sleep(2000);

        // Verify the cart badge count increases to 1
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartCountAfterAdd = cartBadge.getText();
        Assert.assertEquals(cartCountAfterAdd, "1", "Cart badge count should be 1 after adding an item!");

        // Remove the item from the cart
        WebElement removeFromCart = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
        removeFromCart.click();
        Thread.sleep(2000);

        // Verify the cart badge is empty again (no items)
        List<WebElement> cartBadgeAfterRemove = driver.findElements(By.className("shopping_cart_badge"));
        Assert.assertTrue(cartBadgeAfterRemove.isEmpty(), "Cart badge should be empty after removing the item!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

