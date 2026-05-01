package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class AccountPage {
    private final WebDriver driver;

    // The "My Account" heading visible after a successful login
    private final By pageHeading = By.cssSelector("#content h2");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns the text of the first h2 heading on the My Account page.
     * Expected value: "My Account"
     */
    public String getPageHeading() {
        return driver.findElement(pageHeading).getText().trim();
    }

    /** Returns true when the current URL points to the account dashboard. */
    public boolean isOnAccountPage() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("route=account/account");
    }
}
