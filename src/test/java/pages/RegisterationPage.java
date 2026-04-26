package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterationPage {
    private WebDriver driver;

    private By firstName = By.name("firstname");
    private By lastName = By.name("lastname");
    private By email = By.name("email");
    private By telephone = By.name("telephone");
    private By password = By.name("password");
    private By confirmPassword = By.name("confirm");
    private By privacyPolicy = By.name("agree");
    private By continueButton = By.cssSelector("input[type='submit']");

    private By firstNameError = By.cssSelector("input[name='firstname'] + .text-danger");
    private By lastNameError = By.cssSelector("input[name='lastname'] + .text-danger");
    private By emailError = By.cssSelector("input[name='email'] + .text-danger");
    private By telephoneError = By.cssSelector("input[name='telephone'] + .text-danger");
    private By passwordError = By.cssSelector("input[name='password'] + .text-danger");


    public RegisterationPage(WebDriver driver) {
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

    public void fillRegisterationForm(String firstName, String lastName, String email, String telephone, String password, String confirmPassword) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmail(email);
        fillTelephone(telephone);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        fillPrivacyPolicy();
    }

    public void fillPrivacyPolicy() {
        driver.findElement(privacyPolicy).click();
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