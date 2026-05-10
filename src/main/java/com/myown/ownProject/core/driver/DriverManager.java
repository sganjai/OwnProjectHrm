package com.myown.ownProject.core.driver;

import com.myown.ownProject.core.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;



public class DriverManager {
	private static final Logger log = LogManager.getLogger(DriverManager.class);

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        log.info("getDriver() returned: {} :DriverManager", driver.get());
		return driver.get();
	}

	public static void setDriver(WebDriver driverInstance) {
		// TODO Auto-generated method stub
		driver.set(driverInstance);
		log.info("setDriver() has set the {} :DriverManager", driver.get());
	}

	public static void unload() {
		log.info("unload() has been called" + " :DriverManager");
		driver.remove();
	}

	public static class SlowMotion {
	    public static void pause() {
	        try { Thread.sleep(5000); } catch (Exception e) {}
	    }
	}

	
	
}




