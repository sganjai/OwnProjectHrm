package com.myownProject.test.listener;

import com.aventstack.extentreports.ExtentTest;
import com.myown.ownProject.core.driver.DriverManager;
import com.myown.ownProject.core.utilities.ExtentManager;
import com.myown.ownProject.core.utilities.ExtentTestManager;
import com.myown.ownProject.core.utilities.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // Optional
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        ExtentTest test = ExtentManager
                .getInstance()
                .createTest(testName);

        ExtentTestManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTestManager.getTest()
                .pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String base64 = ScreenshotUtil.captureBase64(
                DriverManager.getDriver()
        );

        ExtentTestManager.getTest()
                .fail(result.getThrowable())
                .addScreenCaptureFromBase64String(base64, "Failure Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        Throwable cause = result.getThrowable();
        if (cause != null) {
            ExtentTestManager.getTest().skip(cause);
        } else {
            ExtentTestManager.getTest().skip("Skipped — no reason provided");
        }
    }

 /*   @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }*/
}