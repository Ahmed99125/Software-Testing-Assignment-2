package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class AccountPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // The "My Account" heading visible after a successful login
    private final By pageHeading = By.cssSelector("#content h2");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Returns the text of the first h2 heading on the My Account page.
     * Expected value: "My Account"
     */
    public String getPageHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).getText().trim();
    }

    /**
     * Returns true when the current URL points to the account dashboard.
     */
    public boolean isOnAccountPage() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("route=account/account");
    }
}
