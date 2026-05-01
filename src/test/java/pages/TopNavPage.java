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

    // "Laptops & Notebooks" menu item
    private final By laptopsMenu = By.linkText("Laptops & Notebooks");

    // "MP3 Players" menu item
    private final By mp3PlayersMenu = By.linkText("MP3 Players");

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

    /**
     * Hovers over the "Laptops & Notebooks" menu item,
     * then clicks "Show All Laptops & Notebooks".
     */
    public void clickLaptopsShowAll() {
        WebElement laptops = driver.findElement(laptopsMenu);
        laptops.click();

        // Find the see-all link using CSS
        driver.findElement(By.cssSelector("li.dropdown.open a.see-all")).click();
    }

    /**
     * Hovers over the "MP3 Players" menu item,
     * then clicks "Show All MP3 Players".
     */
    public void clickMP3PlayersShowAll() {
        WebElement mp3Players = driver.findElement(mp3PlayersMenu);
        mp3Players.click();

        // Find the see-all link using CSS
        driver.findElement(By.cssSelector("li.dropdown.open a.see-all")).click();
    }

    /**
     * Clicks a category link by name.
     * If the category has a dropdown, it clicks the "Show All..." link.
     */
    public void navigateToCategory(String categoryName) {
        if (categoryName.equalsIgnoreCase("Tablets") || categoryName.equalsIgnoreCase("Phones & PDAs")
                || categoryName.equalsIgnoreCase("Cameras")) {
            driver.findElement(By.linkText(categoryName)).click();
        } else {
            // For categories with "Show All"
            driver.findElement(By.linkText(categoryName)).click();
            driver.findElement(By.cssSelector("li.dropdown.open a.see-all")).click();
        }
    }
}
