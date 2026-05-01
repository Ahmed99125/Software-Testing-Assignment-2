package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriverWait wait;

    private final By firstName = By.name("firstname");
    private final By lastName = By.name("lastname");
    private final By email = By.name("email");
    private final By telephone = By.name("telephone");
    private final By password = By.name("password");
    private final By confirmPassword = By.name("confirm");
    private final By privacyPolicy = By.name("agree");
    private final By continueButton = By.cssSelector("input[type='submit']");

    private final By firstNameError = By.cssSelector("input[name='firstname'] + .text-danger");
    private final By lastNameError = By.cssSelector("input[name='lastname'] + .text-danger");
    private final By emailError = By.cssSelector("input[name='email'] + .text-danger");
    private final By telephoneError = By.cssSelector("input[name='telephone'] + .text-danger");
    private final By passwordError = By.cssSelector("input[name='password'] + .text-danger");


    public RegistrationPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillFirstName(String firstNameInput) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        input.clear();
        input.sendKeys(firstNameInput);
    }

    public void fillLastName(String lastNameInput) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        input.clear();
        input.sendKeys(lastNameInput);
    }

    public void fillEmail(String emailInput) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        input.clear();
        input.sendKeys(emailInput);
    }

    public void fillTelephone(String telephoneInput) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(telephone));
        input.clear();
        input.sendKeys(telephoneInput);
    }

    public void fillPassword(String passwordInput) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(password));
        input.clear();
        input.sendKeys(passwordInput);
    }

    public void fillConfirmPassword(String confirmPasswordInput) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword));
        input.clear();
        input.sendKeys(confirmPasswordInput);
    }

    public void fillRegistrationForm(String firstName, String lastName, String email, String telephone,
                                     String password, String confirmPassword) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmail(email);
        fillTelephone(telephone);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        fillPrivacyPolicy();
    }

    public void fillPrivacyPolicy() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(privacyPolicy));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    // Action Methods to extract the error text
    public String getFirstNameErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameError)).getText();
    }

    public String getLastNameErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameError)).getText();
    }

    public String getEmailErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailError)).getText();
    }

    public String getTelephoneErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(telephoneError)).getText();
    }

    public String getPasswordErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }
}
