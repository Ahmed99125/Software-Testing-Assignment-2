package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver driver;

    // Form fields
    private final By emailInput = By.id("input-email");
    private final By passwordInput = By.id("input-password");
    private final By loginButton = By.cssSelector("input[type='submit'].btn-primary");

    // Error alert shown on failed login
    private final By errorAlert = By.cssSelector("div.alert-danger");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillEmail(String email) {
        WebElement input = driver.findElement(emailInput);
        input.clear();
        input.sendKeys(email);
    }

    public void fillPassword(String password) {
        WebElement input = driver.findElement(passwordInput);
        input.clear();
        input.sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
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
     * Expected: "Warning: No match for E-Mail Address and/or Password."
     */
    public String getErrorAlertText() {
        return driver.findElement(errorAlert).getText().trim();
    }
}
