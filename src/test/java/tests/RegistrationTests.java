package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterationPage;
import pages.RegisterationSuccessPage;
import utils.CSVUtil;

public class RegistrationTests extends BaseTest {
    @DataProvider(name="validRegisterationData")
    public Object[][] getValidRegisterationDataData() throws Exception {
        return CSVUtil.getTestData("ValidRegisterationData.csv");
    }

    @DataProvider(name="InvalidRegisterationData")
    public Object[][] getInvalidRegisterationDataData() throws Exception {
        return CSVUtil.getTestData("InvalidRegisterationData.csv");
    }

    @Test(dataProvider = "validRegisterationData")
    public void SuccessfulRegistrationTest(
            String firstname, String lastname, String email,
            String telephone, String password, String confirmPassword) {
        HomePage homePage = new HomePage(driver);
        RegisterationPage registerationPage = new RegisterationPage(driver);
        RegisterationSuccessPage  registerationSuccessPage = new RegisterationSuccessPage(driver);

        // Go to registeration page
        homePage.clickMyAccountLink();
        homePage.clickRegisterLink();

        // Fill in user information
        registerationPage.fillRegisterationForm(firstname, lastname, email, telephone, password, confirmPassword);

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

    @Test(dataProvider = "InvalidRegisterationData")
    public void FailedRegistrationTest(
            String firstname, String lastname, String email,
            String telephone, String password, String confirmPassword) {
        HomePage homePage = new HomePage(driver);
        RegisterationPage registerationPage = new RegisterationPage(driver);

        // Go to registeration page
        homePage.clickMyAccountLink();
        homePage.clickRegisterLink();

        // Fill in user information
        registerationPage.fillRegisterationForm(firstname, lastname, "", "", "", "");

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
        registerationPage.fillRegisterationForm(firstname, lastname, email, telephone, password, confirmPassword);

        registerationPage.clickContinueButton();

        // Check password error
        Assert.assertEquals(
                "Password must be between 4 and 20 characters!",
                registerationPage.getPasswordErrorText()
        );
    }
}
