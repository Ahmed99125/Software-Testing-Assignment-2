package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import pages.TopNavPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTests extends BaseTest {

    // -----------------------------------------------------------------------
    // TC-07 : Sort By name
    // Steps:
    //   1. Login by any valid user
    //   2. Click on "Phones & PDAs"
    //   3. Sort by name "A--Z"
    //   4. Verify the items sorted alphabetically ascending
    //   5. Sort by name "Z--A"
    //   6. Verify the items sorted alphabetically descending
    //   7. Log out
    // -----------------------------------------------------------------------
    @Test
    public void SortByNameTest() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login("abc@gc.com", "1234");

        // 2. Click on "Phones & PDAs"
        topNavPage.clickPhonesPdas();

        // 3. Sort by name "Name (A - Z)"
        productListPage.selectSortBy("Name (A - Z)");

        // 4. Verify items sorted ascending
        List<String> actualNamesAsc = productListPage.getProductNames();
        List<String> expectedNamesAsc = new ArrayList<>(actualNamesAsc);
        expectedNamesAsc.sort(String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(actualNamesAsc, expectedNamesAsc, "Products are not sorted by Name (A-Z).");

        // 5. Sort by name "Name (Z - A)"
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
