package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CurrencyPage {
    private final WebDriverWait wait;

    // Currency dropdown toggle button (top-right of the page)
    private final By currencyDropdownToggle = By.cssSelector("#form-currency .dropdown-toggle");

    public CurrencyPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * Opens the currency dropdown and waits for it to be visible.
     */
    private void openCurrencyDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(currencyDropdownToggle)).click();
    }

    /**
     * Changes the store currency to Euro (€).
     */
    public void changeCurrency(String currency) {
        By currencyButton = By.cssSelector("button[name='" + currency + "']");
        openCurrencyDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(currencyButton)).click();
    }
}
