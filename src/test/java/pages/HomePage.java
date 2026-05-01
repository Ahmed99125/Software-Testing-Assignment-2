package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private final WebDriver driver;

    private final By myAccountLink = By.cssSelector("a[title='My Account']");
    private final By registerLink = By.linkText("Register");
    private final By loginLink = By.linkText("Login");
    private final By logoutLink = By.linkText("Logout");
    private final By shoppingCartLink = By.cssSelector("a[title='Shopping Cart']");
    private final By searchInput = By.name("search");
    private final By searchButton = By.cssSelector("#search button");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMyAccountLink() {
        driver.findElement(myAccountLink).click();
    }

    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    public void clickLogoutLink() {
        driver.findElement(logoutLink).click();
    }

    public Boolean checkLogoutLinkExist() {
        return driver.findElement(logoutLink).isDisplayed();
    }

    public void search(String keyword) {
        WebElement input = driver.findElement(searchInput);
        input.clear();
        input.sendKeys(keyword);
        driver.findElement(searchButton).click();
    }

    public void clickShoppingCartLink() {
        driver.findElement(shoppingCartLink).click();
    }
}
