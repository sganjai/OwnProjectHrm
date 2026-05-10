package com.myownProject.test.test;


import com.myown.ownProject.core.driver.DriverManager;
import com.myown.ownProject.core.pages.DashBoardPage;
import com.myown.ownProject.core.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class DashBoardTest extends BaseTest {

	@Test
	public void validateDashBoard() throws IOException {
	
	// DriverManager.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	//	DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.seconds(10));
	
	DriverManager.getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	LoginPage login = new LoginPage();
	login.login("Admin", "admin123");
	
	DashBoardPage dash = new DashBoardPage();
	
    // Validate dashboard is loaded
	Assert.assertTrue(dash.isDashboardLoaded(), "❌ Dashboard is NOT loaded!");
	
	// Step 5: Print dashboard title
	System.out.println("✅ Logged In Successfully – Dashboard Title: " + dash.getDashboardTitle());
	
	dash.logOut();
	}
	
}