package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AdvancedSearchPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;

import java.util.List;

public class SearchTests extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws Exception {
        return utils.CSVUtil.getTestData("SearchData.csv");
    }

    @org.testng.annotations.DataProvider(name = "subCategorySearchData")
    public Object[][] getSubCategorySearchData() throws Exception {
        return utils.CSVUtil.getTestData("SubCategorySearchData.csv");
    }

    // -----------------------------------------------------------------------
    // TC-08 : Search by name
    // Steps:
    //   1. Login by any valid user
    //   2. Enter any name in "search" input box
    //   3. Click on "Search"
    //   4. Verify all displayed products contain the search keyword
    //   5. Log out
    // -----------------------------------------------------------------------
    @Test(dataProvider = "searchData")
    public void SearchByNameTest(String searchKeyword) {
        HomePage        homePage        = new HomePage(driver);
        LoginPage       loginPage       = new LoginPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login("abc@gc.com", "1234");

        // 2. Enter name in search box and 3. Click Search
        homePage.search(searchKeyword);

        // 4. Verify all displayed products contain the search keyword
        List<String> productNames = productListPage.getProductNames();
        
        Assert.assertFalse(productNames.isEmpty(), "No products were found for the search term: " + searchKeyword);
        
        for (String name : productNames) {
            Assert.assertTrue(name.toLowerCase().contains(searchKeyword.toLowerCase()), 
                "Product name '" + name + "' does not contain the search keyword '" + searchKeyword + "'");
        }

        // 5. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }

    // -----------------------------------------------------------------------
    // TC-09 : Search in subcategories
    // Steps:
    //   1. Login by any valid user
    //   2. Click on "Search" icon
    //   3. Enter "Apple" in Search Keyword
    //   4. Choose "Components"
    //   5. No products found
    //   6. Check on "Search in subcategories"
    //   7. "Apple Cinema 30" displayed
    //   8. Log out
    // -----------------------------------------------------------------------
    @Test(dataProvider = "subCategorySearchData")
    public void SearchInSubcategoriesTest(String keyword, String category, String expectedProduct) {
        HomePage           homePage           = new HomePage(driver);
        LoginPage          loginPage          = new LoginPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver);
        ProductListPage    productListPage    = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login("abc@gc.com", "1234");

        // 2. Click on "Search" icon (empty search)
        homePage.search(keyword);

        // 3. Choose category
        advancedSearchPage.selectCategory(category);
        
        // 4. Search and verify no products found
        advancedSearchPage.clickSearchButton();
        Assert.assertEquals(advancedSearchPage.getNoResultsMessage(), "There is no product that matches the search criteria.");

        // 5. Check search in subcategories and 6. Search again
        advancedSearchPage.checkSearchInSubcategories();
        advancedSearchPage.clickSearchButton();

        List<String> productNames = productListPage.getProductNames();
        Assert.assertTrue(productNames.contains(expectedProduct), "Expected product '" + expectedProduct + "' was not found in results.");

        // 7. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
