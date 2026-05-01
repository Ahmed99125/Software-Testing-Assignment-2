package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class TopNavPage {
    private final WebDriver driver;

    // "Desktops" menu item in the top navigation bar
    private final By desktopsMenu = By.linkText("Desktops");

    // "Show All Desktops" link inside the Desktops dropdown
    private final By showAllDesktops = By.cssSelector("a.see-all");

    // "Tablets" menu item
    private final By tabletsMenu = By.linkText("Tablets");

    // "Phones & PDAs" menu item
    private final By phonesPdasMenu = By.linkText("Phones & PDAs");

    public TopNavPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Hovers over the "Desktops" menu item to reveal its dropdown,
     * then clicks "Show All Desktops".
     */
    public void clickDesktopsShowAll() {
        Actions actions = new Actions(driver);
        WebElement desktops = driver.findElement(desktopsMenu);
        actions.moveToElement(desktops).perform();
        driver.findElement(showAllDesktops).click();
    }

    /** Clicks the "Tablets" link in the top navigation bar. */
    public void clickTablets() {
        driver.findElement(tabletsMenu).click();
    }

    /** Clicks the "Phones & PDAs" link in the top navigation bar. */
    public void clickPhonesPdas() {
        driver.findElement(phonesPdasMenu).click();
    }
}
