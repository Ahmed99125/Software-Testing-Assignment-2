package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductDetailsPage {
    private final WebDriver driver;

    // Locators
    private final By addToCartButton = By.id("button-cart");
    private final By successAlert = By.cssSelector("div.alert-success");
    private final By dateInput = By.cssSelector("div.date input");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterDeliveryDate(String date) {
        WebElement input = driver.findElement(dateInput);
        input.clear();
        input.sendKeys(date);
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public String getSuccessAlertText() {
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                java.time.Duration.ofSeconds(10));
        WebElement alert =
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(successAlert));
        return alert.getText().replaceAll("×", "").trim();
    }
}
