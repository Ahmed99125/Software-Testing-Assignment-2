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

public class BreadcrumbTests extends BaseTest {

    @DataProvider(name = "breadcrumbData")
    public Object[][] getBreadcrumbData() throws Exception {
        return CSVUtil.getTestData("BreadcrumbData.csv");
    }

    @Test(dataProvider = "breadcrumbData")
    public void BreadcrumbAndSideMenuTest(String category) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        TopNavPage topNavPage = new TopNavPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login(config.get("username"), config.get("password"));

        // 2. Click on category in the top navigation
        topNavPage.navigateToCategory(category);

        // 3. Verify the last breadcrumb item
        Assert.assertEquals(
                productListPage.getLastBreadcrumbText(),
                category,
                "The last breadcrumb link should be '" + category + "'."
        );

        // 4. Verify the highlighted / active item in the left side menu
        Assert.assertEquals(
                productListPage.getActiveSidebarMenuText(),
                category,
                "The active left side menu item should be '" + category + "'."
        );

        // 5. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
