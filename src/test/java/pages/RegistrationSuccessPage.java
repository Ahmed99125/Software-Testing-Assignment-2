package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationSuccessPage {
    private final WebDriver driver;

    private final By continueButton = By.cssSelector("a[href*='account/account']");

    public RegistrationSuccessPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }
}
