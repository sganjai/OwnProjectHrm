package com.myownProject.test.test;

import com.myown.ownProject.core.pages.LoginPage;
import com.myown.ownProject.core.utilities.Reporter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHrmNewWindowTest extends BaseTest{

    @Test
    public void verifyOrangeHrmNewTab() {

        LoginPage login = new LoginPage();
        Reporter.node("Login Page");
        Reporter.snap("Login Page launched");
        String parentWindow = driver.getWindowHandle();

        login.clickOrangeHrmLink();

        for(String window : driver.getWindowHandles()) {

            if(!window.equals(parentWindow)) {

                driver.switchTo().window(window);
                break;
            }
        }
        Reporter.snap("New Page launched");
        String actualText = login.getHrmHeadingText();

        Assert.assertTrue(
                actualText.contains("Streamline All Your HR Needs")

        );

        driver.close();
        driver.switchTo().window(parentWindow);
        Reporter.snap("Back to Login Page");

    }
}

