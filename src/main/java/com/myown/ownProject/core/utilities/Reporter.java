package com.myown.ownProject.core.utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.myown.ownProject.core.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reporter {
    private static final Logger log = LogManager.getLogger(Reporter.class);
    private static ThreadLocal<ExtentTest> currentNode = new ThreadLocal<>();

    // 🔥 Create Node
    public static Reporter node(String nodeName) {
        // FIX: Add a null check to prevent NullPointerException
        ExtentTest test = ExtentTestManager.getTest();
        if (test == null) {
            // Log an error to indicate the root cause: the test was not initialized.
            log.error("ExtentTest has not been initialized. Cannot create node: '" + nodeName + "'. Please ensure ExtentTestManager.startTest() is called before the test begins.");
            return new Reporter(); // Return to allow method chaining without crashing.
        }

        ExtentTest node = test.createNode(nodeName);
        currentNode.set(node);

        logAndReport("NODE: " + nodeName);
        return new Reporter();
    }

    // 🔥 Snap method with screenshot
    public static void snap(String message) {
        WebDriver driver = DriverManager.getDriver();
        String base64 = ScreenshotUtil.captureBase64(driver);

        ExtentTest node = currentNode.get();
        if (node != null) {
            // Log message + screenshot in ONE line
            node.info(message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } else {
            // FIX: Also check if the main test object is null here
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.info(message,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
            }
        }

        log.info(message); // still log to logger file separately
    }

    // 🔥 Info method (logs only, no screenshot)
    public static void info(String message) {
        logAndReport(message);
    }

    // 🔥 Optional chaining version
    public Reporter snapChain(String message) {
        snap(message);
        return this;
    }

    // 🔥 Clear node
    public static void clearNode() {
        currentNode.remove();
    }

    // 🔥 Private helper to log to both log file and HTML report
    private static void logAndReport(String message) {
        log.info(message); // log file
        ExtentTest node = currentNode.get();
        if (node != null) {
            node.info(message); // HTML report
        } else {
            // FIX: Also check if the main test object is null here
            ExtentTest test = ExtentTestManager.getTest();
            if (test != null) {
                test.info(message); // HTML report at test level
            }
        }
    }
}