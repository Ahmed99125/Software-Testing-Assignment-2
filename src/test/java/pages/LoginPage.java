package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriverWait wait;

    // Form fields
    private final By emailInput = By.id("input-email");
    private final By passwordInput = By.id("input-password");
    private final By loginButton = By.cssSelector("input[type='submit'].btn-primary");

    // Error alert shown on failed login
    private final By errorAlert = By.cssSelector("div.alert-danger");

    public LoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        input.clear();
        input.sendKeys(email);
    }

    public void fillPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        input.clear();
        input.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /**
     * Fills email + password then clicks Login.
     */
    public void login(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        clickLoginButton();
    }

    /**
     * Returns the text of the danger alert displayed on a failed login attempt.
     */
    public String getErrorAlertText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorAlert)).getText().trim();
    }
}
