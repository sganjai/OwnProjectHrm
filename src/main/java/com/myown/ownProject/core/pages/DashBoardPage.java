package com.myown.ownProject.core.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.myown.ownProject.core.driver.DriverManager.SlowMotion;


public class DashBoardPage extends BasePage {

	// ---------- LOCATORS ----------
	private By dashboardHeader = By.xpath("//h6[normalize-space()='Dashboard']");

	private By prfileDropDown = By.xpath("//p[@class='oxd-userdropdown-name']");
	
	private By logOutButton = By.xpath("//a[contains(@href,'/web/index.php/auth/logout')]");

	// ---------- CONSTRUCTOR ----------|// Retrieves the already initialized WebDriver (from DriverFactory/BaseTest-beforeMethod via DriverManager) for use throughout this page class|
//	private WebDriver driver;
//	public DashBoardPage() {
//		this.driver = DriverManager.getDriver();
//	}


	// ---------- PAGE ACTIONS ----------
    // 1) To check if dashboard is loaded
	public boolean isDashboardLoaded() {
        //WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions
		        .visibilityOfElementLocated(dashboardHeader));
		return true;
	}
	
	    // 2) To return the dashboard title text
		public String getDashboardTitle() {
			return driver.findElement(dashboardHeader).getText();
		}
		
		
		// 3) To logout 
		public void logOut() {
//			SlowMotion.pause();
//			
//			DriverManager.getDriver().findElement(prfileDropDown).waitAndClick();
//			SlowMotion.pause();
//			
//			DriverManager.getDriver().findElement(logOutButton).waitAndClick();
//			SlowMotion.pause();

			System.out.println("STEP 1: Starting logout slow-motion");
		    SlowMotion.pause();

		    System.out.println("STEP 2: Clicking profile dropdown");
		    driver.findElement(prfileDropDown).click();
		    SlowMotion.pause();

		    System.out.println("STEP 3: Clicking logout button");
		    waitAndClick(logOutButton);
		    SlowMotion.pause();

		    System.out.println("STEP 4: Logout completed");
			
			
		}
		
		


		
}


