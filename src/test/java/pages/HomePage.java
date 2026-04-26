package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By myAccountLink = By.cssSelector("a[title='My Account']");
    private By registerLink = By.linkText("Register");
    private By loginLink = By.linkText("Login");
    private By logoutLink = By.linkText("Logout");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMyAccountLink(){
        driver.findElement(myAccountLink).click();
    }

    public void clickRegisterLink(){
        driver.findElement(registerLink).click();
    }

    public void clickLoginLink(){
        driver.findElement(loginLink).click();
    }

    public void clickLogoutLink(){
        driver.findElement(logoutLink).click();
    }

    public Boolean checkLogoutLinkExist(){
        return driver.findElement(logoutLink).isDisplayed();
    }
}
