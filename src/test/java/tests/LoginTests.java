package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.Objects;

public class LoginTests extends BaseTest {

    @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() throws Exception {
        return utils.CSVUtil.getTestData("ValidLoginData.csv");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() throws Exception {
        return utils.CSVUtil.getTestData("InvalidLoginData.csv");
    }

    @Test(dataProvider = "validLoginData")
    public void ValidLoginTest(String email, String password) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        AccountPage accountPage = new AccountPage(driver);

        // 1. Navigate to the Login page
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();

        // 2. Enter valid credentials and submit
        loginPage.login(email, password);

        // 3. Verify "My Account" page opened
        Assert.assertTrue(
                accountPage.isOnAccountPage(),
                "Expected to land on the My Account page after valid login."
        );
        Assert.assertEquals(
                accountPage.getPageHeading(),
                "My Account",
                "Page heading should be 'My Account' after successful login."
        );

        // 4. Verify Logout link is visible (user is logged in)
        homePage.clickMyAccountLink();
        Assert.assertTrue(
                homePage.checkLogoutLinkExist(),
                "Logout link should be visible for a logged-in user."
        );

        // 5. Logout
        homePage.clickLogoutLink();
    }

    @Test(dataProvider = "invalidLoginData")
    public void InvalidLoginTest(String email, String password) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // 1. Navigate to the Login page
        homePage.clickMyAccountLink();
        homePage.clickLoginLink();

        // 2. Enter wrong credentials and submit
        loginPage.login(email, password);

        // 3. Verify error alert message
        Assert.assertTrue(
                loginPage.getErrorAlertText()
                        .contains("No match for E-Mail Address and/or Password."),
                "Expected error message about invalid credentials."
        );

        // 4. Verify user is still on the login page (not redirected to account)
        Assert.assertTrue(
                Objects.requireNonNull(driver.getCurrentUrl()).contains("route=account/login"),
                "User should remain on the login page after a failed login attempt."
        );
    }
}
