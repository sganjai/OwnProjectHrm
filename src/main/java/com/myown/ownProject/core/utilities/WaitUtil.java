package com.myown.ownProject.core.utilities;

import com.myown.ownProject.core.constants.Timeout;
import com.myown.ownProject.core.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {

    private static final Logger log = LogManager.getLogger(WaitUtil.class);

    // private constructor — blocks creating object of this class, static calls only
    private WaitUtil() {}

    // builds a fresh WebDriverWait with given timeout — used internally by all methods
    private static WebDriverWait getWait(Timeout timeout) {
        WebDriver driver = DriverManager.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeout.getSeconds()));
    }

    /**
     * Waits for element to be clickable then clicks it
     * @param locator  - By locator of element
     * @param timeout  - Timeout enum value eg Timeout.DEFAULT
     */
    public static void waitAndClick(By locator, Timeout timeout) {
        log.info("Waiting {}s to click: {}", timeout.getSeconds(), locator);
        getWait(timeout)
                .until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }

    /**
     * Waits for element to be visible then types text into it
     * @param locator  - By locator of element
     * @param text     - text to type
     * @param timeout  - Timeout enum value
     */
    public static void waitAndType(By locator, String text, Timeout timeout) {
        log.info("Waiting {}s to type '{}' into: {}", timeout.getSeconds(), text, locator);
        getWait(timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(text);
    }

    /**
     * Waits for element to be visible then returns true if displayed
     * @param locator  - By locator of element
     * @param timeout  - Timeout enum value
     */
    public static boolean waitUntilDisplayed(By locator, Timeout timeout) {
        log.info("Waiting {}s for visibility: {}", timeout.getSeconds(), locator);
        return getWait(timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .isDisplayed();
    }

        /**
     * Waits for an element to be visible and returns the WebElement instance.
     * Use this for INTERACTION (e.g., to clear, then type).
     * @param locator  - By locator of element
     * @param timeout  - Timeout enum value
     * @return The found WebElement.
     */
    public static WebElement waitAndGetElement(By locator, Timeout timeout) {
        log.info("Waiting {}s to get element: {}", timeout.getSeconds(), locator);
        return getWait(timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for element to be visible then returns its text
     * @param locator  - By locator of element
     * @param timeout  - Timeout enum value
     */
    public static String waitAndGetText(By locator, Timeout timeout) {
        log.info("Waiting {}s to get text from: {}", timeout.getSeconds(), locator);
        return getWait(timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }

    /**
     * Waits until element disappears from DOM — useful for loaders/spinners
     * @param locator  - By locator of element
     * @param timeout  - Timeout enum value
     */
    public static boolean waitUntilInvisible(By locator, Timeout timeout) {
        log.info("Waiting {}s for invisibility: {}", timeout.getSeconds(), locator);
        return getWait(timeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until browser URL contains given fragment — useful after redirects
     * @param urlFragment - partial URL to check eg "dashboard"
     * @param timeout     - Timeout enum value
     */
    public static boolean waitForUrlToContain(String urlFragment, Timeout timeout) {
        log.info("Waiting {}s for URL to contain: {}", timeout.getSeconds(), urlFragment);
        return getWait(timeout)
                .until(ExpectedConditions.urlContains(urlFragment));
    }
}