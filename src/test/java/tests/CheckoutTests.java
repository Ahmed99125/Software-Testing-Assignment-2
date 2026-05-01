package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVUtil;

public class CheckoutTests extends BaseTest {

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() throws Exception {
        return CSVUtil.getTestData("CheckoutData.csv");
    }

    @Test(dataProvider = "checkoutData")
    public void testNormalCheckoutProcess(
            String category, String productName, String successMessage,
            String firstName, String lastName, String address, String city,
            String postCode, String country, String zone, String comment,
            String isStockAvailableStr) {

        boolean isStockAvailable = Boolean.parseBoolean(isStockAvailableStr);
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        OrderPlacedPage orderPlacedPage = new OrderPlacedPage(driver);

        // 1- Login by any valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login(config.get("username"), config.get("password"));

        // Clear cart to ensure test starts with 0 items
        homePage.clickShoppingCartLink();
        shoppingCartPage.clearCart();

        // 2- Click on Category
        topNavPage.navigateToCategory(category);

        // 3- Add product to the cart
        productListPage.clickAddToCartForProduct(productName);
        System.out.println("added " + productName);

        // Handle products that redirect to details (like HP LP3065)
        // We wait up to 5 seconds for the title to change to the product name
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                java.time.Duration.ofSeconds(5));
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.titleIs(productName));
        } catch (Exception e) {
            // Title didn't change to product name, might still be on category page
        }

        if (driver.getTitle().equalsIgnoreCase(productName)) {
            System.out.println("On details page for: " + productName);
            // For products with options, click add to cart again on the details page
            productDetailsPage.clickAddToCart();
        }

        // 4- Info message verification
        String alertText;
        if (driver.getTitle().equalsIgnoreCase(productName)) {
            alertText = productDetailsPage.getSuccessAlertText();
        } else {
            alertText = productListPage.getSuccessAlertText();
        }
        Assert.assertTrue(alertText.contains(successMessage), "Success message did not match!");

        // 5- Open shopping cart and check on the item added
        homePage.clickShoppingCartLink();
        Assert.assertTrue(shoppingCartPage.isItemInCart(productName), productName + " is not in the shopping cart!");

        // Handle Stock Availability
        if (!isStockAvailable) {
            // If item is not in stock, check for warning message
            Assert.assertTrue(shoppingCartPage.hasStockWarning(),
                    "Stock warning should be displayed for " + productName);
            String warning = shoppingCartPage.getWarningMessage();
            Assert.assertTrue(warning.contains("***"), "Warning message should mention stock issue!");
            System.out.println("Verified: Item " + productName + " is out of stock and prevented from checkout.");

            // Log out and end test for this case
            homePage.clickMyAccountLink();
            homePage.clickLogoutLink();
            return;
        }

        // If stock is available, proceed with checkout
        Assert.assertFalse(shoppingCartPage.hasStockWarning(),
                "Stock warning should NOT be displayed for " + productName);

        String expectedSubTotal = shoppingCartPage.getSubTotalPrice();
        // 7- Click on "Checkout" button
        shoppingCartPage.clickCheckoutButton();

        // 8- Fill billing details by new address
        checkoutPage.selectNewAddress();
        checkoutPage.fillBillingDetails(firstName, lastName, address, city, postCode, country, zone);

        // 9- Check on Address drop down filled by new address
        checkoutPage.clickContinueBilling();

        // 11- Shipping details section
        checkoutPage.clickContinueShipping();

        // 12- Delivery method section
        // 13- Add Comment & Click on "Continue"
        checkoutPage.addComment(comment);
        checkoutPage.clickContinueShippingMethod();

        // 14- Payment method section
        checkoutPage.checkTermsAndConditions();
        checkoutPage.clickContinuePaymentMethod();

        // 15- "Confirm order" section
        String actualSubTotal = checkoutPage.getSubTotalFromConfirm();
        Assert.assertEquals(actualSubTotal, expectedSubTotal,
                "SubTotal price in confirm order does not match expected!");

        // 17- Click on "Confirm Order" button
        checkoutPage.clickConfirmOrder();

        // 18- "Your order has been placed!" message
        String successMsg = orderPlacedPage.getSuccessMessage();
        Assert.assertEquals(successMsg, "Your order has been placed!", "Order success message mismatch!");

        String cartCount = shoppingCartPage.getSmallCartItemCount();
        Assert.assertEquals(cartCount, "0", "Small shopping cart should be empty after order!");

        // 19- Log out
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
