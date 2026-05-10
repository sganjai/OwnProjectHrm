package com.myownProject.test.test;

import com.aventstack.extentreports.ExtentTest;
import com.myown.ownProject.core.config.configReader;
import com.myown.ownProject.core.driver.DriverFactory;
import com.myown.ownProject.core.driver.DriverManager;
import com.myown.ownProject.core.utilities.ExtentManager;
import com.myown.ownProject.core.utilities.ExtentTestManager;
import com.myown.ownProject.core.utilities.Reporter;
import com.myown.ownProject.core.utilities.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BaseTest {

    //    protected static final boolean PER_TEST_LOG =
//            Boolean.parseBoolean(System.getProperty("perTestLog", "true"));
    protected WebDriver driver;
    private static final String RUN_ID = String.valueOf(System.currentTimeMillis());
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    private static String RUN_TIME;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    @BeforeSuite
    public void setRunTime() {
        RUN_TIME = LocalDateTime.now().format(FORMATTER);
        
    }

    //Below block of code used in beforemethod
    private void browserLaunch(String zoomFactor) throws IOException {
        log.info("Initiating driver 'before method' base test");
        DriverFactory.initializeDriver(zoomFactor);
        driver = DriverManager.getDriver();   // important line, Getting driver which is initiated in above

/*        //Setting implicit global wait
        int implicitWait = Integer.parseInt(configReader.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        log.info("Implicit wait applied");*/

        //Open website
        driver.get(configReader.getProperty("url"));
        log.info("Page title is: " + driver.getTitle());
        driver.manage().window().maximize();
        log.info("Window maximized");

    }


    @Parameters({"zoomFactor"})
    @BeforeMethod
    public void setup(@Optional("1") String zoomFactor,
                      ITestResult result,
                      ITestContext context) throws IOException {


        String testName = result.getMethod().getMethodName();


        String fileName;

        boolean isParallel =
                context.getSuite().getXmlSuite().getParallel() != org.testng.xml.XmlSuite.ParallelMode.NONE;

        if (isParallel) {
            // 🚀 Parallel → same run time + thread separation
            fileName = RUN_TIME + "_" + testName + "_" + Thread.currentThread().getId();
        } else {
            // 🎯 Sequential → fresh time per test
            String currentTime = LocalDateTime.now().format(FORMATTER);
            fileName = currentTime + "_" + testName;
        }

        // 🔥 ThreadContext
        ThreadContext.put("testName", testName);
        ThreadContext.put("threadId", String.valueOf(Thread.currentThread().getId()));
        ThreadContext.put("fileName", fileName);
        ThreadContext.put("testId", testName + "-" + Thread.currentThread().getId());

     /*   // 🔥 EXTENT ----------- Handled in listener
        ExtentTest extentTest = ExtentManager
                .getInstance()
                .createTest(testName);

        ExtentTestManager.setTest(extentTest);*/

        log.info("browserLaunch called from before method");
        browserLaunch(zoomFactor);

    }




    @AfterMethod
    public void tearDown(ITestResult result) {

     /*   // 🔥 Failure handling  ----------- Handled in listener
        if (result.getStatus() == ITestResult.FAILURE) {

            String base64 = ScreenshotUtil.captureBase64(
                    DriverManager.getDriver()
            );

            ExtentTestManager.getTest()
                    .fail("Test Failed")
                    .addScreenCaptureFromBase64String(base64, "Failure Screenshot");
        }*/

        // 🔥 Quit driver (your existing code)
        if (driver != null) {
            log.info("Starting quit from 'after method' base test");
            driver.quit();
            DriverManager.unload();
            log.info("Completed unload from 'after method' base test");
        }

        // 🔥 Clear node (VERY IMPORTANT)
        Reporter.clearNode();
        // 🔥 Logging cleanup (MANDATORY)
        ThreadContext.clearAll();

//        // 🔥 Logging cleanup (your existing)
//        if (PER_TEST_LOG) {
//            ThreadContext.clearAll();
//        } else {
//            log.info("\n==================== END: " +
//                    result.getMethod().getMethodName() +
//                    " ====================\n");
//        }
    }


    @AfterSuite
    public void flushReport() throws Exception {

    ExtentManager.getInstance().flush();

    String path = System.getProperty("user.dir") +
            "/reports/" + ExtentManager.getTime() + "/Report.html";

    Desktop.getDesktop().browse(new File(path).toURI());
}
}
