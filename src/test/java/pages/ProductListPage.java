package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListPage {
    private final WebDriverWait wait;

    // Success alert shown when item is added to cart
    private final By successAlert = By.cssSelector("div.alert-success");

    // All price elements on the product listing page
    private final By productPrices = By.cssSelector(".product-thumb .price");

    // Sort dropdown
    private final By sortDropdown = By.id("input-sort");

    // Product names
    private final By productNames = By.cssSelector(".product-layout .caption h4 a");

    // Last item in the breadcrumb trail (the current page)
    private final By lastBreadcrumb = By.cssSelector("ul.breadcrumb li:last-child a");

    // Currently highlighted / active item in the left sidebar category menu
    private final By activeSidebarItem = By.cssSelector("#column-left .list-group-item.active");
    private final By addToCartButtons = By.cssSelector("button[onclick*='cart.add']");

    public ProductListPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Returns the text of all product names visible on the page.
     */
    public List<String> getProductNames() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames)).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Selects an option from the 'Sort By' dropdown by its visible text.
     * Example: "Name (A - Z)", "Name (Z - A)"
     */
    public void selectSortBy(String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
        // Capture a list item before sorting to detect when the page updates
        WebElement firstProduct = wait.until(ExpectedConditions.presenceOfElementLocated(productNames));

        new Select(dropdown).selectByVisibleText(visibleText);

        // Wait for the page to refresh (the old product element becomes stale)
        wait.until(ExpectedConditions.stalenessOf(firstProduct));
    }

    /**
     * Returns the text content of all price elements on the current page.
     */
    public List<String> getProductPrices() {
        List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));
        return priceElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if ALL visible prices contain the given currency symbol.
     */
    public boolean allPricesContainSymbol(String symbol) {
        List<String> prices = getProductPrices();
        return !prices.isEmpty() && prices.stream().allMatch(p -> p.contains(symbol));
    }

    /**
     * Returns the text of the last (active) breadcrumb link.
     */
    public String getLastBreadcrumbText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lastBreadcrumb)).getText().trim();
    }

    /**
     * Returns the text of the currently highlighted item in the left sidebar.
     */
    public String getActiveSidebarMenuText() {
        String raw = wait.until(ExpectedConditions.visibilityOfElementLocated(activeSidebarItem)).getText().trim();
        return raw.replaceAll("\\s*\\(\\d+\\)$", "");
    }

    /**
     * Clicks "Add to Cart" button for a specific product by its name.
     */
    public void clickAddToCartForProduct(String productName) {
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(
                ".product-layout")));
        boolean found = false;
        for (WebElement product : products) {
            String name = product.findElement(By.cssSelector("h4 a")).getText();
            if (name.equalsIgnoreCase(productName)) {
                WebElement addButton = product.findElement(By.cssSelector("button[onclick*='cart.add']"));
                wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new org.openqa.selenium.NoSuchElementException("Could not find product with name: " + productName);
        }
    }

    /**
     * Returns the text of the success alert.
     */
    public String getSuccessAlertText() {
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
        return alert.getText().replaceAll("×", "").trim();
    }
}
