package com.myown.ownProject.core.pages;

import com.myown.ownProject.core.constants.Timeout;
import com.myown.ownProject.core.driver.DriverManager;
import com.myown.ownProject.core.utilities.WaitUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BasePage {

    // driver — accessible to all page classes that extend BasePage
    protected WebDriver driver;

    // logger — accessible to all page classes, shows which class logs
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    // constructor — auto runs when any page class is created, fetches driver once
    public BasePage() {
        this.driver = DriverManager.getDriver();
        log.info("Driver fetched for: {}", this.getClass().getSimpleName());
    }

    // wraps WaitUtil click with DEFAULT timeout — use for normal elements
    protected void waitAndClick(By locator) {
        WaitUtil.waitAndClick(locator, Timeout.DEFAULT);
    }

    // wraps WaitUtil type with DEFAULT timeout — use for normal input fields
    protected void waitAndType(By locator, String text) {
        WaitUtil.waitAndType(locator, text, Timeout.DEFAULT);
    }


    protected void waitClearAndType(By locator, String text) {
        // This now uses our new, clearly named method
        WebElement webElement = WaitUtil.waitAndGetElement(locator, Timeout.DEFAULT);
        webElement.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        webElement.sendKeys(text);
    }

    // wraps WaitUtil visibility check with DEFAULT timeout — returns true if visible
    protected boolean waitUntilDisplayed(By locator) {
        return WaitUtil.waitUntilDisplayed(locator, Timeout.DEFAULT);
    }
     // wraps WaitUtil visibility
    protected WebElement waitAndGetElement(By locator) {
        return WaitUtil.waitAndGetElement(locator,Timeout.DEFAULT);
    }
    // wraps WaitUtil visibility check with DEFAULT timeout — returns true if visible
    protected boolean waitUntilInvisible(By locator) {
        return WaitUtil.waitUntilInvisible(locator, Timeout.DEFAULT);
    }


    //driver.findElement(locator).isDisplayed(); if not found on the page → Selenium throws NoSuchElementException
    protected boolean isElementDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }


    // wraps WaitUtil getText with DEFAULT timeout — returns visible text of element
    protected String waitAndGetText(By locator) {
        return WaitUtil.waitAndGetText(locator, Timeout.DEFAULT);
    }

    // wraps WaitUtil visibility check specifically for images
    protected boolean isImageDisplayed(By locator) {
        return WaitUtil.waitUntilDisplayed(locator, Timeout.DEFAULT);
    }



}