package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import pages.TopNavPage;

public class BreadcrumbTests extends BaseTest {

    // -----------------------------------------------------------------------
    // TC-06 : Check on Breadcrumb & Left Side Menu
    // Steps:
    //   1. Login by any valid user
    //   2. Click on "Tablets"
    //   3. Verify the last link in the breadcrumb is "Tablets"
    //   4. Verify the highlighted link in the LEFT side menu is "Tablets"
    //      (Note: the test scenario document says "right handside" but the
    //       category menu is physically in the left column on the live site)
    //   5. Log out
    // -----------------------------------------------------------------------
    @Test
    public void BreadcrumbAndSideMenuTest() {
        HomePage        homePage        = new HomePage(driver);
        LoginPage       loginPage       = new LoginPage(driver);
        TopNavPage      topNavPage      = new TopNavPage(driver);
        ProductListPage productListPage = new ProductListPage(driver);

        // 1. Login with a valid user
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();
        loginPage.login("abc@gc.com", "1234");

        // 2. Click on "Tablets" in the top navigation
        topNavPage.clickTablets();

        // 3. Verify the last breadcrumb item is "Tablets"
        Assert.assertEquals(
                productListPage.getLastBreadcrumbText(),
                "Tablets",
                "The last breadcrumb link should be 'Tablets'."
        );

        // 4. Verify the highlighted / active item in the left side menu is "Tablets"
        Assert.assertEquals(
                productListPage.getActiveSidebarMenuText(),
                "Tablets",
                "The active left side menu item should be 'Tablets'."
        );

        // 5. Logout
        homePage.clickMyAccountLink();
        homePage.clickLogoutLink();
    }
}
