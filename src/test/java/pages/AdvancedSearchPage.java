package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class AdvancedSearchPage {
    private final WebDriver driver;

    private final By searchInput = By.id("input-search");
    private final By categoryDropdown = By.name("category_id");
    private final By subCategoryCheckbox = By.name("sub_category");
    private final By searchButton = By.id("button-search");
    private final By noResultsMessage = By.cssSelector("#content p:nth-of-type(2)");

    public AdvancedSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchKeyword(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
    }

    public void selectCategory(String categoryName) {
        new Select(driver.findElement(categoryDropdown)).selectByVisibleText(categoryName);
    }

    public void checkSearchInSubcategories() {
        if (!driver.findElement(subCategoryCheckbox).isSelected()) {
            driver.findElement(subCategoryCheckbox).click();
        }
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public String getNoResultsMessage() {
        return driver.findElement(noResultsMessage).getText();
    }
}
