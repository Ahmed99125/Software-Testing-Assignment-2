package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPlacedPage {
    private final WebDriver driver;

    private final By successMessage = By.cssSelector("#content h1");

    public OrderPlacedPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }
}
