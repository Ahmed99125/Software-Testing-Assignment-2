package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailsPage {
    private final WebDriverWait wait;

    // Locators
    private final By addToCartButton = By.id("button-cart");
    private final By successAlert = By.cssSelector("div.alert-success");
    private final By dateInput = By.cssSelector("div.date input");

    public ProductDetailsPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterDeliveryDate(String date) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput));
        input.clear();
        input.sendKeys(date);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String getSuccessAlertText() {
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
        return alert.getText().replaceAll("×", "").trim();
    }
}
