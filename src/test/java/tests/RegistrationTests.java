package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterationPage;
import pages.RegisterationSuccessPage;

public class RegistrationTests extends BaseTest {
    @Test
    public void SuccessfulRegistrationTest() {
        HomePage homePage = new HomePage(driver);
        RegisterationPage registerationPage = new RegisterationPage(driver);
        RegisterationSuccessPage  registerationSuccessPage = new RegisterationSuccessPage(driver);

        // Go to registeration page
        homePage.clickMyAccountLink();
        homePage.clickRegisterLink();

        // Fill in user information
        registerationPage.fillRegisterationForm(
                "ahmed",
                "tamer",
                "aaa@b.com",
                "123456789",
                "1234",
                "1234");

        // Submit
        registerationPage.clickContinueButton();

        // Check "Your Account Has Been Created!" message
        registerationSuccessPage.clickContinueButton();

        // Check if LogOut exists on My Account menu
        homePage.clickMyAccountLink();

        Assert.assertEquals(true, homePage.checkLogoutLinkExist());

        // Logout of the system
        homePage.clickLogoutLink();
    }

    @Test
    public void FailedRegistrationTest(){
        HomePage homePage = new HomePage(driver);
        RegisterationPage registerationPage = new RegisterationPage(driver);
        RegisterationSuccessPage  registerationSuccessPage = new RegisterationSuccessPage(driver);

        // Go to registeration page
        homePage.clickMyAccountLink();
        homePage.clickRegisterLink();

        // Fill in user information
        registerationPage.fillRegisterationForm("ahmed", "tamer", "", "", "", "");

        registerationPage.clickContinueButton();

        // Check errors
        Assert.assertEquals(
                "E-Mail Address does not appear to be valid!",
                registerationPage.getEmailErrorText()
        );
        Assert.assertEquals(
                "Telephone must be between 3 and 32 characters!",
                registerationPage.getTelephoneErrorText()
        );
        Assert.assertEquals(
                "Password must be between 4 and 20 characters!",
                registerationPage.getPasswordErrorText()
        );

        // Fill data
        registerationPage.fillRegisterationForm(
                "ahmed",
                "tamer",
                "aaa2@b.com",
                "123456789",
                "123",
                "123");

        registerationPage.clickContinueButton();

        // Check password error
        Assert.assertEquals(
                "Password must be between 4 and 20 characters!",
                registerationPage.getPasswordErrorText()
        );
    }
}
