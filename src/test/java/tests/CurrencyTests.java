package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CurrencyTests extends BaseTest {

    @Test
    public void ChangeCurrencyTest() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        CurrencyPage currencyPage = new CurrencyPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login("abc@gc.com", "1234");

        // 2. Navigate to Desktops -> Show All Desktops
        topNavPage.clickDesktopsShowAll();

        // 3. Verify that prices are shown in $ by default
        Assert.assertTrue(
                productListPage.allPricesContainSymbol("$"),
                "Prices should be displayed in US Dollar ($) by default."
        );

        // 4. Change currency to Euro
        currencyPage.changeCurrencyToEuro();

        // 5. Verify that prices are now shown in €
        Assert.assertTrue(
                productListPage.allPricesContainSymbol("€"),
                "Prices should be displayed in Euro (€) after currency change."
        );

        // 6. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
