package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPlacedPage {
    private final WebDriverWait wait;

    private final By successMessage = By.xpath("//h1[normalize-space()='Your order has been placed!']");

    public OrderPlacedPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}
