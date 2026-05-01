package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import pages.TopNavPage;
import utils.CSVUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTests extends BaseTest {

    @DataProvider(name = "sortData")
    public Object[][] getSortData() throws Exception {
        return CSVUtil.getTestData("SortData.csv");
    }

    @Test(dataProvider = "sortData")
    public void SortByNameTest(String category) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login(config.get("username"), config.get("password"));

        // 2. Click on Category
        topNavPage.navigateToCategory(category);

        // 3. Sort by name ascending
        productListPage.selectSortBy("Name (A - Z)");

        // 4. Verify items sorted ascending
        List<String> actualNamesAsc = productListPage.getProductNames();
        List<String> expectedNamesAsc = new ArrayList<>(actualNamesAsc);
        expectedNamesAsc.sort(String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(actualNamesAsc, expectedNamesAsc, "Products are not sorted by Name (A-Z).");

        // 5. Sort by name descending
        productListPage.selectSortBy("Name (Z - A)");

        // 6. Verify items sorted descending
        List<String> actualNamesDesc = productListPage.getProductNames();
        List<String> expectedNamesDesc = new ArrayList<>(actualNamesDesc);
        expectedNamesDesc.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        Assert.assertEquals(actualNamesDesc, expectedNamesDesc, "Products are not sorted by Name (Z-A).");

        // 7. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
