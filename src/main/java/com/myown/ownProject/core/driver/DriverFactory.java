package com.myown.ownProject.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//import static com.infor.myown.ownProject.core.driver.DriverManager.driver;

public class DriverFactory {
    protected static Properties prop;
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    // This method creates the WebDriver and applies Chrome UI Zoom
    public static WebDriver initializeDriver(String zoomFactor) throws IOException {
        log.info("Initializing driver : DriverFactory");
        WebDriver driver;
        //Below setup is to load properties file
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        // Create browser instance with respect to browser mentioned in config file
        String browser = prop.getProperty("browser");
        log.info("Setting Browser: {} : DriverFactory", browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions copt = new ChromeOptions();
                copt.setAcceptInsecureCerts(true);
                //Below is codn to check head or headless via property file, if nothing default to headed mode
                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    copt.addArguments("--headless=new");
                }
                copt.addArguments("--force-device-scale-factor=" + zoomFactor);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(copt);
                break;

            case "firefox":
                FirefoxOptions fopt = new FirefoxOptions();
                fopt.setAcceptInsecureCerts(true);
                //Below is codn to check head or headless via property file, if nothing default to headed mode
                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    fopt.addArguments("--headless");
                }
                fopt.addArguments("--force-device-scale-factor=" + zoomFactor);
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(fopt);
                break;


            case "edge":
                EdgeOptions ed = new EdgeOptions();
                ed.setAcceptInsecureCerts(true);
                //Below is codn to check head or headless via property file, if nothing default to headed mode
                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    ed.addArguments("--headless=new");
                }
                ed.addArguments("--force-device-scale-factor=" + zoomFactor);
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(ed);
                break;


            default:
                throw new IllegalArgumentException("Invalid browser" +  browser);



        }


        // Store driver in ThreadLocal (parallel-safe)
        DriverManager.setDriver(driver);
        log.info("Driver initialized set to driver manager  : DriverFactory");
        log.info("Running in thread: " + Thread.currentThread().getId());

        log.info("Returning driver  : DriverFactory");
        return driver; // give driver object to calling test
    }
}
