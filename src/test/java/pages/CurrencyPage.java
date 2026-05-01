package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CurrencyPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Currency dropdown toggle button (top-right of the page)
    private final By currencyDropdownToggle = By.cssSelector("#form-currency .dropdown-toggle");

    // Individual currency option buttons
    private final By eurButton = By.cssSelector("button[name='EUR']");
    private final By usdButton = By.cssSelector("button[name='USD']");
    private final By gbpButton = By.cssSelector("button[name='GBP']");

    public CurrencyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /** Opens the currency dropdown and waits for it to be visible. */
    private void openCurrencyDropdown() {
        driver.findElement(currencyDropdownToggle).click();
    }

    /** Changes the store currency to Euro (€). */
    public void changeCurrencyToEuro() {
        openCurrencyDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(eurButton)).click();
    }

    /** Changes the store currency back to US Dollar ($). */
    public void changeCurrencyToDollar() {
        openCurrencyDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(usdButton)).click();
    }

    /** Changes the store currency to British Pound (£). */
    public void changeCurrencyToPound() {
        openCurrencyDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(gbpButton)).click();
    }
}
