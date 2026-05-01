package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPage {
    private final WebDriver driver;

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
        this.driver = driver;
    }

    public void fillFirstName(String firstNameInput) {
        WebElement input = driver.findElement(firstName);
        input.clear();
        input.sendKeys(firstNameInput);
    }

    public void fillLastName(String lastNameInput) {
        WebElement input = driver.findElement(lastName);
        input.clear();
        input.sendKeys(lastNameInput);
    }

    public void fillEmail(String emailInput) {
        WebElement input = driver.findElement(email);
        input.clear();
        input.sendKeys(emailInput);
    }

    public void fillTelephone(String telephoneInput) {
        WebElement input = driver.findElement(telephone);
        input.clear();
        input.sendKeys(telephoneInput);
    }

    public void fillPassword(String passwordInput) {
        WebElement input = driver.findElement(password);
        input.clear();
        input.sendKeys(passwordInput);
    }

    public void fillConfirmPassword(String confirmPasswordInput) {
        WebElement input = driver.findElement(confirmPassword);
        input.clear();
        input.sendKeys(confirmPasswordInput);
    }

    public void fillRegistrationForm(String firstName, String lastName, String email, String telephone, String password, String confirmPassword) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmail(email);
        fillTelephone(telephone);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        fillPrivacyPolicy();
    }

    public void fillPrivacyPolicy() {
        WebElement element = driver.findElement(privacyPolicy);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void clickContinueButton() {
        driver.findElement(continueButton).click();
    }

    // Action Methods to extract the error text
    public String getFirstNameErrorText() {
        return driver.findElement(firstNameError).getText();
    }

    public String getLastNameErrorText() {
        return driver.findElement(lastNameError).getText();
    }

    public String getEmailErrorText() {
        return driver.findElement(emailError).getText();
    }

    public String getTelephoneErrorText() {
        return driver.findElement(telephoneError).getText();
    }

    public String getPasswordErrorText() {
        return driver.findElement(passwordError).getText();
    }
}