package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriverWait wait;

    private final By myAccountLink = By.cssSelector("a[title='My Account']");
    private final By registerLink = By.linkText("Register");
    private final By loginLink = By.linkText("Login");
    private final By logoutLink = By.linkText("Logout");
    private final By shoppingCartLink = By.cssSelector("a[title='Shopping Cart']");
    private final By searchInput = By.name("search");
    private final By searchButton = By.cssSelector("#search button");

    public HomePage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickMyAccountLink() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccountLink)).click();
    }

    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public void clickLogoutLink() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public Boolean checkLogoutLinkExist() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void search(String keyword) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(keyword);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickShoppingCartLink() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartLink)).click();
    }
}
