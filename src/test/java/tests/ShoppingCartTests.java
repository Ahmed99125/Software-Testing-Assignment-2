package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVUtil;

public class ShoppingCartTests extends BaseTest {

    @DataProvider(name = "shoppingCartData")
    public Object[][] getShoppingCartData() throws Exception {
        return CSVUtil.getTestData("ShoppingCartData.csv");
    }

    @Test(dataProvider = "shoppingCartData")
    public void testAddItemsToCartAndCompare(
            String email, String password, String tabletName, String tabletSuccessMessage,
            String laptopName, String deliveryDate, String laptopSuccessMessage) {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        // 1- Login by any valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login(email, password);

        // 2- Click on "Tablets"
        topNavPage.clickTablets();

        // 3- Add tablet to the cart
        productListPage.clickAddToCartForProduct(tabletName);

        // 4- Check success message
        String alertText = productListPage.getSuccessAlertText();
        Assert.assertTrue(alertText.contains(tabletSuccessMessage),
                "Success message for adding tablet to cart did not match!");

        // 5- Open shopping cart and check on the item added & it's price
        homePage.clickShoppingCartLink();
        Assert.assertTrue(shoppingCartPage.isItemInCart(tabletName), tabletName + " is not in the shopping cart!");
        String tabletPrice = shoppingCartPage.getItemPrice(tabletName);
        Assert.assertNotNull(tabletPrice, "Could not retrieve price for " + tabletName);

        // 6- Go to "Laptops" & Add laptop
        topNavPage.clickLaptopsShowAll();
        productListPage.clickAddToCartForProduct(laptopName);

        // 7- Change the delivery date & add it to the shopping cart
        productDetailsPage.enterDeliveryDate(deliveryDate);
        productDetailsPage.clickAddToCart();

        // Ensure success message is shown before proceeding
        String laptopAlertText = productDetailsPage.getSuccessAlertText();
        Assert.assertTrue(laptopAlertText.contains(laptopSuccessMessage),
                "Success message for adding laptop to cart did not match!");

        // 8- Open the shopping cart to check on the item and it's details (delivery
        // date)
        homePage.clickShoppingCartLink();
        Assert.assertTrue(shoppingCartPage.isItemInCart(laptopName), laptopName + " is not in the shopping cart!");
        String laptopDeliveryDate = shoppingCartPage.getItemDeliveryDate(laptopName);
        Assert.assertTrue(laptopDeliveryDate.contains(deliveryDate), "Delivery date does not match expected value!");

        // 9- check on the "Total" to be equal the total price of the items
        String laptopPrice = shoppingCartPage.getItemPrice(laptopName);
        String totalPriceStr = shoppingCartPage.getTotalPrice();

        double tPrice = Double.parseDouble(tabletPrice.replaceAll("[^0-9.]", ""));
        double lPrice = Double.parseDouble(laptopPrice.replaceAll("[^0-9.]", ""));
        double totalSum = tPrice + lPrice;

        double actualTotal = Double.parseDouble(totalPriceStr.replaceAll("[^0-9.]", ""));
        Assert.assertEquals(actualTotal, totalSum,
                String.format(
                        "Total price is not equal to sum of item prices! Tablet: %f, Laptop: %f, Total: %f, " +
                                "actualTotal: %f",
                        tPrice, lPrice, totalSum, actualTotal));

        // 10- Log out
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
