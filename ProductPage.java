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

public class ProductPage {
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

    @Test(priority = 1)
    public void verifyAllProduct() throws InterruptedException {
        // Wait for the products page to load
        Thread.sleep(2000);

        // Find all product elements (Each product has a class name 'inventory_item')
        List<WebElement> products = driver.findElements(By.className("inventory_item"));

        // Verify that the correct number of products are displayed (in this case, 6 products)
        int expectedNumberOfProducts = 6; // This is based on the number of products on the page, adjust as needed.
        Assert.assertEquals(products.size(), expectedNumberOfProducts, "incorrect number of products displayed!");

        // Loop through each product and verify details
        for (WebElement product : products) {
            // Verify product name is not empty
            WebElement productName = product.findElement(By.className("inventory_item_name"));
            Assert.assertFalse(productName.getText().isEmpty(), "Product name is missing!");

            // Verify product price is not empty
            WebElement productPrice = product.findElement(By.className("inventory_item_price"));
            Assert.assertFalse(productPrice.getText().isEmpty(), "Product price is missing!");

            // Verify product image is displayed
            WebElement productImage = product.findElement(By.className("inventory_item_desc"));
            Assert.assertTrue(productImage.isDisplayed(), "Product description is  displayed!");
        }
    }

    @Test(priority = 2)
    public void Sorting() throws InterruptedException {
        // Wait for the products page to load
        Thread.sleep(2000);
        WebElement sort = driver.findElement(By.xpath("//select[@class='product_sort_container']"));
        sort.click();
        Thread.sleep(2000);

        WebElement SortingByPrice = driver.findElement(By.xpath("//option[@value='lohi']"));
        SortingByPrice.click();
        Thread.sleep(2000);


    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
