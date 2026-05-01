package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By cartRows = By.cssSelector("div.table-responsive table tbody tr");
    private final By totalRows = By.cssSelector("div.row .col-sm-offset-8 table tr");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isItemInCart(String itemName) {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartRows));
        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td.text-left a")).getText().contains(itemName)) {
                return true;
            }
        }
        return false;
    }

    public String getItemPrice(String itemName) {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartRows));
        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td.text-left a")).getText().contains(itemName)) {
                // Return the unit price (usually second to last column in the row)
                List<WebElement> columns = row.findElements(By.cssSelector("td.text-right"));
                return columns.get(columns.size() - 1).getText();
            }
        }
        return "";
    }

    public String getItemDeliveryDate(String itemName) {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartRows));
        for (WebElement row : rows) {
            // In OpenCart, the name and options are in a td.text-left
            List<WebElement> cells = row.findElements(By.cssSelector("td.text-left"));
            for (WebElement cell : cells) {
                if (cell.getText().contains(itemName)) {
                    try {
                        return cell.findElement(By.tagName("small")).getText();
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        // This specific cell doesn't have a small tag, continue searching
                    }
                }
            }
        }
        return "";
    }

    public String getTotalPrice() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(totalRows));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector("td"));
            if (cells.size() >= 2 && cells.get(0).getText().contains("Total")
                    && !cells.get(0).getText().contains("Sub")) {
                return cells.get(1).getText();
            }
        }
        return "";
    }

    public String getSubTotalPrice() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(totalRows));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector("td"));
            if (cells.size() >= 2 && cells.get(0).getText().contains("Sub-Total")) {
                return cells.get(1).getText();
            }
        }
        return "";
    }

    public void clickCheckoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Checkout"))).click();
    }

    public String getSmallCartItemCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-total"))).getText().split(" ")[0];
    }

    public boolean hasStockWarning() {
        try {
            List<WebElement> alerts = driver.findElements(By.cssSelector(".alert-danger"));
            for (WebElement alert : alerts) {
                if (alert.getText().contains("***")) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public String getWarningMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-danger"))).getText();
    }

    public void clearCart() {
        while (true) {
            // Target only remove buttons within the cart table
            List<WebElement> removeButtons = driver
                    .findElements(By.cssSelector("div.table-responsive button.btn-danger"));
            if (removeButtons.isEmpty()) {
                break;
            }

            try {
                WebElement button = removeButtons.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(button));
                button.click();

                // Wait for the specific row to disappear or page to refresh
                wait.until(ExpectedConditions.stalenessOf(button));
            } catch (Exception e) {
                // If something goes wrong (e.g. staleness or not interactable),
                // refresh the list in the next iteration
                driver.navigate().refresh();
            }
        }
    }
}
