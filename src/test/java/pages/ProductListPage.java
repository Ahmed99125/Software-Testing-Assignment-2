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

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns the text content of all price elements on the current page.
     * Each entry may look like "$122.00\n$98.00\nEx Tax: $98.00" — the first
     * token is the relevant price to check.
     */
    public List<String> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrices);
        return priceElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if ALL visible prices contain the given currency symbol.
     * Example: assertPricesContainSymbol("$") or assertPricesContainSymbol("€")
     */
    public boolean allPricesContainSymbol(String symbol) {
        List<String> prices = getProductPrices();
        return !prices.isEmpty() && prices.stream().allMatch(p -> p.contains(symbol));
    }
}
