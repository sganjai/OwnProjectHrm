package com.myown.ownProject.core.pages;

//import com.aventstack.extentreports.util.Assert;
//import org.testng.Assert;
import com.myown.ownProject.core.utilities.Reporter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class PIMPage extends BasePage {
    private static final Logger log = LogManager.getLogger(PIMPage.class);

    private By pimModule = By.xpath("//span[normalize-space()='PIM']");
    private By addEmployeeTab = By.xpath("//a[normalize-space()='Add Employee']");
    private By firstName = By.xpath("//input[@name='firstName']");
    private By lastName = By.xpath("//input[@name='lastName']");
    private By employeeId = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");
    private By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private By successPopup = By.xpath("//p[contains(@class,'toast-message') and contains(normalize-space(),'Successfully')]");
    private By loader = By.xpath("//div[contains(@class,'oxd-form-loader')]");

    public String addEmployee() {
        // Generate a 4-character random string with letters and numbers (e.g., "a1B2")
        String randomLastName = RandomStringUtils.randomAlphanumeric(2,4);
        String randomEmployeeId = RandomStringUtils.randomNumeric(4);

        waitAndClick(pimModule);
        waitUntilInvisible(loader);  // wait for loader to dissapear
        waitAndClick(addEmployeeTab);
        waitUntilDisplayed(firstName);
        Reporter.snap("Clicked on Add Employee");
        waitAndType(firstName, "Akhil");
        waitAndType(lastName, randomLastName);
        Reporter.info("Random last name is = "+ randomLastName);
        waitUntilDisplayed(employeeId);
        Reporter.snap("FirstName & randomLastName Entered");
        waitClearAndType(employeeId, randomEmployeeId);
        Reporter.info("Random Employee Id = " + randomEmployeeId);
        Reporter.snap("randomEmployeeId Entered");
        waitAndClick(saveButton);

        return randomEmployeeId;
    }

    public boolean isSuccessPopupDisplayed() {

        return waitUntilDisplayed(successPopup);
    }




}