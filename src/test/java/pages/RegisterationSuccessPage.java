package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterationSuccessPage {
    private WebDriver driver;

    private By continueButton = By.cssSelector("a[href*='account/account']");

    public RegisterationSuccessPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }
}
