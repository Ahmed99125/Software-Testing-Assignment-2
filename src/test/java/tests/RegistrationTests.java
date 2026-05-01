package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;
import pages.RegistrationSuccessPage;
import utils.CSVUtil;

public class RegistrationTests extends BaseTest {
    @DataProvider(name = "validRegistrationData")
    public Object[][] getValidRegistrationDataData() throws Exception {
        return CSVUtil.getTestData("ValidRegistrationData.csv");
    }

    @DataProvider(name = "InvalidRegistrationData")
    public Object[][] getInvalidRegistrationDataData() throws Exception {
        return CSVUtil.getTestData("InvalidRegistrationData.csv");
    }

    @Test(dataProvider = "validRegistrationData")
    public void SuccessfulRegistrationTest(
            String firstname, String lastname, String email,
            String telephone, String password, String confirmPassword) {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        RegistrationSuccessPage registrationSuccessPage = new RegistrationSuccessPage(driver);

        // Go to registration page
        homePage.clickMyAccountLink();
        homePage.clickRegisterLink();

        // Fill in user information
        registrationPage.fillRegistrationForm(firstname, lastname, email, telephone, password, confirmPassword);

        // Submit
        registrationPage.clickContinueButton();

        // Check "Your Account Has Been Created!" message
        registrationSuccessPage.clickContinueButton();

        // Check if LogOut exists on My Account menu
        homePage.clickMyAccountLink();

        Assert.assertEquals(homePage.checkLogoutLinkExist(), true);

        // Logout of the system
        homePage.clickLogoutLink();
    }

    @Test(dataProvider = "InvalidRegistrationData")
    public void FailedRegistrationTest(
            String firstname, String lastname, String email,
            String telephone, String password, String confirmPassword) {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        // Go to registration page
        homePage.clickMyAccountLink();
        homePage.clickRegisterLink();

        // Fill in user information
        registrationPage.fillRegistrationForm(firstname, lastname, "", "", "", "");

        registrationPage.clickContinueButton();

        // Check errors
        Assert.assertEquals(
                registrationPage.getEmailErrorText(),
                "E-Mail Address does not appear to be valid!"
        );
        Assert.assertEquals(
                registrationPage.getTelephoneErrorText(),
                "Telephone must be between 3 and 32 characters!"
        );
        Assert.assertEquals(
                registrationPage.getPasswordErrorText(),
                "Password must be between 4 and 20 characters!"
        );

        // Fill data
        registrationPage.fillRegistrationForm(firstname, lastname, email, telephone, password, confirmPassword);

        registrationPage.clickContinueButton();

        // Check password error
        Assert.assertEquals(
                registrationPage.getPasswordErrorText(),
                "Password must be between 4 and 20 characters!"
        );
    }
}
