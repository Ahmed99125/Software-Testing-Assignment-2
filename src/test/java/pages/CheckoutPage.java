package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators for different sections
    private final By firstNameInput = By.id("input-payment-firstname");
    private final By lastNameInput = By.id("input-payment-lastname");
    private final By address1Input = By.id("input-payment-address-1");
    private final By cityInput = By.id("input-payment-city");
    private final By postCodeInput = By.id("input-payment-postcode");
    private final By countrySelect = By.id("input-payment-country");
    private final By zoneSelect = By.id("input-payment-zone");
    private final By continueBillingButton = By.id("button-payment-address");
    private final By continueShippingButton = By.id("button-shipping-address");
    private final By continueShippingMethodButton = By.id("button-shipping-method");
    private final By continuePaymentMethodButton = By.id("button-payment-method");
    private final By confirmOrderButton = By.id("button-confirm");

    private final By agreeCheckbox = By.name("agree");
    private final By commentTextArea = By.name("comment");

    private final By newAddressRadio = By.cssSelector("input[value='new']");
    private final By existingAddressSelect = By.cssSelector("select[name='address_id']");

    private final By successMessage = By.cssSelector("#content h1");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void selectNewAddress() {
        wait.until(ExpectedConditions.elementToBeClickable(newAddressRadio)).click();
    }

    public void fillBillingDetails(String firstName, String lastName, String address1, String city, String postCode,
            String country, String zone) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(address1Input).sendKeys(address1);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(postCodeInput).sendKeys(postCode);

        Select countryDropDown = new Select(driver.findElement(countrySelect));
        countryDropDown.selectByVisibleText(country);

        // Zone might take a moment to load after country selection
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(zoneSelect, By.tagName("option")));
        Select zoneDropDown = new Select(driver.findElement(zoneSelect));
        zoneDropDown.selectByVisibleText(zone);
    }

    public boolean isAddressInDropDown(String address) {
        Select existingAddress = new Select(
                wait.until(ExpectedConditions.visibilityOfElementLocated(existingAddressSelect)));
        for (WebElement option : existingAddress.getOptions()) {
            if (option.getText().contains(address)) {
                return true;
            }
        }
        return false;
    }

    public void clickContinueBilling() {
        driver.findElement(continueBillingButton).click();
    }

    public void clickContinueShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShippingButton)).click();
    }

    public void addComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextArea)).sendKeys(comment);
    }

    public void clickContinueShippingMethod() {
        driver.findElement(continueShippingMethodButton).click();
    }

    public void checkTermsAndConditions() {
        wait.until(ExpectedConditions.elementToBeClickable(agreeCheckbox)).click();
    }

    public void clickContinuePaymentMethod() {
        driver.findElement(continuePaymentMethodButton).click();
    }

    public String getTotalFromConfirm() {
        List<WebElement> rows = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.cssSelector(".table-responsive table tfoot tr")));
        for (WebElement row : rows) {
            if (row.getText().contains("Total") && !row.getText().contains("Sub")) {
                return row.findElement(By.cssSelector("td:last-child")).getText();
            }
        }
        return "";
    }

    public String getSubTotalFromConfirm() {
        List<WebElement> rows = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.cssSelector(".table-responsive table tfoot tr")));
        for (WebElement row : rows) {
            if (row.getText().contains("Sub-Total")) {
                return row.findElement(By.cssSelector("td:last-child")).getText();
            }
        }
        return "";
    }

    public void clickConfirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}
