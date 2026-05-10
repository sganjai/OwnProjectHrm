package com.myown.ownProject.core.pages;

import com.myown.ownProject.core.utilities.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class LoginPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);

    // ===== Locators =====
    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");
    private By usernameRequired = By.xpath("//input[@placeholder='Username']/following:: span[text()='Required'][1]");
    private By passwordRequired = By.xpath("//input[@placeholder='Password']/following:: span[text()='Required'][1]");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");
    private By dashBoardLogo = By.xpath("//img[@alt='client brand banner']");

    // ===== Actions =====
    public void enterUsername(String user) { waitAndType(username,user); }
    public void enterPassword(String pass) {
        waitAndType(password, pass);
    }
    public void clickLogin() {
        waitAndClick(loginBtn);
    }

    // ===== Full Login Flow for Login Success =====
    public HomePage login(String user, String pass) throws IOException {

        enterUsername(user);
        // log.info("username entered");
        Reporter.snap("Entered username");
        enterPassword(pass);
        // log.info("password entered");
        Reporter.snap("Entered password");
        clickLogin();
        //log.info("Login Button is clicked");
        Reporter.info("Login Button is clicked");
        log.info("Returning HomePage");
        return new HomePage(); //homepage object is thrown return as test lands on homepage after login
    }


    public boolean isLoginSuccessful() {
        return waitUntilDisplayed(dashBoardLogo);
    }

    public String loginAndGetResult(String user, String pass) {

        enterUsername(user);
        Reporter.snap("Entered username");
        enterPassword(pass);
        Reporter.snap("Entered password");
        clickLogin();
        Reporter.info("Login Button is clicked");

        // 1️⃣ Success
        /*if (isLoginSuccessful()) {
            return "Login Success";
        }
        // 2️⃣ Field validation (blank case)
        else if (isFieldValidationPresent()) {
            return getFieldValidationMessage();
        }
        else {
            // 3️⃣ Invalid credentials
            return getErrorMessage();
        }*/
        // 1️⃣ Field validation (NO WAIT)
        if (isFieldValidationPresent()) {
            return getFieldValidationMessage();
        }

        // 2️⃣ Invalid credentials (SMALL WAIT)
        if (isDisplayedWithWait(errorMessage, 2)) {
            return getErrorMessage();
        }

        // 3️⃣ SUCCESS (ONLY NOW we check with wait)
        if (isLoginSuccessful()) {
            return "Login Success";
        }

        return "UNKNOWN_STATE";

    }

    public boolean isFieldValidationPresent() {
        return isElementDisplayed(usernameRequired) || isElementDisplayed(passwordRequired);
    }

    public String getFieldValidationMessage() {
        String userError = "";
        String passError = "";
        if (isElementDisplayed(usernameRequired)) {
            userError = "User name is " + waitAndGetText(usernameRequired);
        }
        if (isElementDisplayed(passwordRequired)) {
            passError = "Password is " + waitAndGetText(passwordRequired);
        }
        return userError + ", " + passError;
    }

    protected boolean isDisplayedWithWait(By locator, int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {

        List<WebElement> elements = driver.findElements(errorMessage);

        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
            return elements.get(0).getText().trim();
        }

        return "NO_ERROR_MESSAGE";
    }


}
