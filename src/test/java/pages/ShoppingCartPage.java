package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShoppingCartPage {
    private final WebDriver driver;

    // Locators
    private final By cartRows = By.cssSelector("div.table-responsive table tbody tr");
    private final By totalRows = By.cssSelector("div.row .col-sm-offset-8 table tr");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isItemInCart(String itemName) {
        List<WebElement> rows = driver.findElements(cartRows);
        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td.text-left a")).getText().contains(itemName)) {
                return true;
            }
        }
        return false;
    }

    public String getItemPrice(String itemName) {
        List<WebElement> rows = driver.findElements(cartRows);
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
        List<WebElement> rows = driver.findElements(cartRows);
        for (WebElement row : rows) {
            WebElement link = row.findElement(By.cssSelector("td.text-left a"));
            if (link.getText().contains(itemName)) {
                // Find small tag containing delivery date
                return row.findElement(By.cssSelector("td.text-left small")).getText();
            }
        }
        return "";
    }

    public String getTotalPrice() {
        List<WebElement> rows = driver.findElements(totalRows);
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
        List<WebElement> rows = driver.findElements(totalRows);
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector("td"));
            if (cells.size() >= 2 && cells.get(0).getText().contains("Sub-Total")) {
                return cells.get(1).getText();
            }
        }
        return "";
    }

    public void clickCheckoutButton() {
        driver.findElement(By.linkText("Checkout")).click();
    }

    public String getSmallCartItemCount() {
        return driver.findElement(By.id("cart-total")).getText().split(" ")[0];
    }

    public boolean hasStockWarning() {
        List<WebElement> alerts = driver.findElements(By.cssSelector(".alert-danger"));
        for (WebElement alert : alerts) {
            if (alert.getText().contains("***")) {
                return true;
            }
        }
        return false;
    }

    public String getWarningMessage() {
        return driver.findElement(By.cssSelector(".alert-danger")).getText();
    }

    public void clearCart() {
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                java.time.Duration.ofSeconds(10));
        while (true) {
            // Target only remove buttons within the cart table
            List<WebElement> removeButtons = driver
                    .findElements(By.cssSelector("div.table-responsive button.btn-danger"));
            if (removeButtons.isEmpty()) {
                break;
            }

            try {
                WebElement button = removeButtons.get(0);
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(button));
                button.click();

                // Wait for the specific row to disappear or page to refresh
                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf(button));
            } catch (Exception e) {
                // If something goes wrong (e.g. staleness or not interactable),
                // refresh the list in the next iteration
                driver.navigate().refresh();
            }
        }
    }
}
