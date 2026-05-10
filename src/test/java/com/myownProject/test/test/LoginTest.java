package com.myownProject.test.test;
import com.myown.ownProject.core.pages.HomePage;
import com.myown.ownProject.core.utilities.ExcelDataProvider;
import com.myown.ownProject.core.utilities.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.myown.ownProject.core.pages.LoginPage;

import java.io.IOException;
import java.util.Map;

public class LoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @Test(dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
    public void validateLogin(Map<String, String> data) throws InterruptedException, IOException {

        String username = data.get("username");
        String password = data.get("password");
        String expected = data.get("expected");

        LoginPage loginPage = new LoginPage();
        Reporter.node("Entering Credentials");
        String result = loginPage.loginAndGetResult(username, password);
        Reporter.snap(result);

        Assert.assertEquals(result, expected);
        // 🔥 Logout only if success
        if (result.equals("Login Success")) {
            Reporter.node("Logging out");

            HomePage homePage = new HomePage();
            homePage.logOut();

            Reporter.info("Logged out successfully");
        }
    }
}
