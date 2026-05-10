package com.myown.ownProject.core.pages;

import com.myown.ownProject.core.utilities.Reporter;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    private By dashBoardHeader = By.xpath("//h6[normalize-space()='Dashboard']");
    private By dashBoardLogo = By.xpath("//img[@alt='client brand banner']");
    private By profileDropDown = By.xpath("//p[@class='oxd-userdropdown-name']");
    private By logOutButton = By.xpath("//a[normalize-space()='Logout']");

        public String getDashBoardHeader() {

            return waitAndGetText(dashBoardHeader);
            }

    public boolean isDashBoardLogoDisplayed() {

            return waitUntilDisplayed(dashBoardLogo);
    }

    public void logOut() throws InterruptedException {
       // waitUntilDisplayed(logOutButton);

        waitAndClick(profileDropDown);
        Reporter.snap("Profile Button is clicked");
        //Thread.sleep(500);
        waitAndClick(logOutButton);
        //Thread.sleep(500);
    }
}



