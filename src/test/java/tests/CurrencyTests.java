package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVUtil;

public class CurrencyTests extends BaseTest {

    @DataProvider(name = "CurrencyData")
    public Object[][] getCurrencyData() throws Exception {
        return CSVUtil.getTestData("CurrencyData.csv");
    }

    @Test(dataProvider = "CurrencyData")
    public void ChangeCurrencyTest(String currency, String symbol) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        CurrencyPage currencyPage = new CurrencyPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login(config.get("username"), config.get("password"));

        // 2. Navigate to Desktops -> Show All Desktops
        topNavPage.clickDesktopsShowAll();

        // 3. Verify that prices are shown in $ by default
        Assert.assertTrue(
                productListPage.allPricesContainSymbol("$"),
                "Prices should be displayed in US Dollar ($) by default."
        );

        // 4. Change currency
        currencyPage.changeCurrency(currency);

        // 5. Verify that prices show in the selected currency
        Assert.assertTrue(
                productListPage.allPricesContainSymbol(symbol),
                "Prices should be displayed in " + currency + " (" + symbol + ") after changing currency."
        );

        // 6. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
