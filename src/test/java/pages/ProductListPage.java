package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListPage {
    private final WebDriver driver;

    // All price elements on the product listing page
    private final By productPrices = By.cssSelector(".product-thumb .price");

    // Last item in the breadcrumb trail (the current page)
    private final By lastBreadcrumb = By.cssSelector("ul.breadcrumb li:last-child a");

    // Currently highlighted / active item in the left sidebar category menu
    private final By activeSidebarItem = By.cssSelector("#column-left .list-group-item.active");

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns the text content of all price elements on the current page.
     * Each entry may look like "$122.00\n$98.00\nEx Tax: $98.00".
     */
    public List<String> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrices);
        return priceElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if ALL visible prices contain the given currency symbol.
     * Example: allPricesContainSymbol("$") or allPricesContainSymbol("€")
     */
    public boolean allPricesContainSymbol(String symbol) {
        List<String> prices = getProductPrices();
        return !prices.isEmpty() && prices.stream().allMatch(p -> p.contains(symbol));
    }

    /**
     * Returns the text of the last (active) breadcrumb link —
     * e.g. "Tablets" when browsing the Tablets category.
     */
    public String getLastBreadcrumbText() {
        return driver.findElement(lastBreadcrumb).getText().trim();
    }

    /**
     * Returns the text of the currently highlighted item in the left sidebar
     * category menu — the one with the "active" CSS class.
     * The raw text may include a product count like "Tablets (1)";
     * this method strips that suffix so callers can assert plain category names.
     */
    public String getActiveSidebarMenuText() {
        String raw = driver.findElement(activeSidebarItem).getText().trim();
        // Remove trailing " (n)" product count, e.g. "Tablets (1)" -> "Tablets"
        return raw.split(" ")[0];
    }
}

