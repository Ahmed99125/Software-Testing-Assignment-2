package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdvancedSearchPage {
    private final WebDriverWait wait;

    private final By searchInput = By.id("input-search");
    private final By categoryDropdown = By.name("category_id");
    private final By subCategoryCheckbox = By.name("sub_category");
    private final By searchButton = By.id("button-search");
    private final By noResultsMessage = By.cssSelector("#content p:nth-of-type(2)");

    public AdvancedSearchPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterSearchKeyword(String keyword) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        element.clear();
        element.sendKeys(keyword);
    }

    public void selectCategory(String categoryName) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(categoryDropdown))).selectByVisibleText(categoryName);
    }

    public void checkSearchInSubcategories() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(subCategoryCheckbox));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public String getNoResultsMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage)).getText();
    }
}
