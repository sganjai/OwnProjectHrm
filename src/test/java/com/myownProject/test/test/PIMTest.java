package com.myownProject.test.test;

import com.myown.ownProject.core.config.configReader;
import com.myown.ownProject.core.pages.HomePage;
import com.myown.ownProject.core.pages.LoginPage;
import com.myown.ownProject.core.pages.PIMPage;
import com.myown.ownProject.core.utilities.Reporter;
import com.myown.ownProject.core.utilities.WaitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class PIMTest extends BaseTest {
    LoginPage loginPage;
    HomePage homePage;
    PIMPage pimPage;
    private static final Logger log = LogManager.getLogger(PIMTest.class);


    @Test
    public void AddEmployee() throws IOException, InterruptedException {

        loginPage = new LoginPage();
        Reporter.node("Entering Credentials");
        homePage = loginPage.login(configReader.getProperty("username"), configReader.getProperty("password"));
        Assert.assertTrue(homePage.isDashBoardLogoDisplayed());
        Reporter.snap("DashBoard");

        Reporter.node("PIM Employee Creation");
        pimPage = new PIMPage();
        pimPage.addEmployee();
        Assert.assertTrue(pimPage.isSuccessPopupDisplayed(), "Success Popup is not Displayed");
        Reporter.info("Success Popup Displayed");
        Reporter.snap("Success Popup");

        Reporter.node("Logging Out");
        homePage.logOut();
        Reporter.info("LoggedOut");

    }
}
