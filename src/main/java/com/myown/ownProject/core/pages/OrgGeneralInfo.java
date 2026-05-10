package com.myown.ownProject.core.pages;

import com.myown.ownProject.core.utilities.ExcelUtil;
import com.myown.ownProject.core.utilities.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class OrgGeneralInfo extends BasePage{
    private static final Logger log = LogManager.getLogger(OrgGeneralInfo.class);
    // 🔹 Field map
    private Map<String, By> formFields = new HashMap<>();

    // 🔥 Constructor → auto runs when page object is created
    public OrgGeneralInfo() {
        initFormFields();  // ✅ auto-initialize
    }

    private By adminModule = By.xpath("//span[text()='Admin']");
    private By organisation = By.xpath("//span[text()='Organization ']");
    private By getGeneralInformationMenu = By.xpath("//a[normalize-space()='General Information']");
    private By generalInformationText = By.xpath("//h6[text()='General Information']");
    private By editToggle = By.xpath("//label[normalize-space()='Edit']");
    private By organizationName = By.xpath("//label[normalize-space()='Organization Name']/following::input[1]");
    private By registrationNumber = By.xpath("//label[normalize-space()='Registration Number']/following::input[1]");
    private By taxID = By.xpath("//label[normalize-space()='Tax ID']/following::input[1]");
    private By phone = By.xpath("//label[normalize-space()='Phone']/following::input[1]");
    private By fax = By.xpath("//label[normalize-space()='Fax']/following::input[1]");
    private By email = By.xpath("//label[normalize-space()='Email']/following::input[1]");
    private By addressStreet1= By.xpath("//label[normalize-space()='Address Street 1']/following::input[1]");
    private By addressStreet2 = By.xpath("//label[normalize-space()='Address Street 2']/following::input[1]");
    private By city = By.xpath("//label[normalize-space()='City']/following::input[1]");
    private By stateProvince= By.xpath("//label[normalize-space()='State/Province']/following::input[1]");
    private By zipPostalCode= By.xpath("//label[normalize-space()='Zip/Postal Code']/following::input[1]");
    private By notes = By.xpath("//label[normalize-space()='Notes']/following::textarea[1]");
    private By required = By.xpath("//label[normalize-space()='//p[normalize-space()='* Required']");

    private By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private By successPopup = By.xpath("//p[contains(@class,'toast-message') and contains(normalize-space(),'Successfully')]");



    public void editGenearalInfo() throws InterruptedException {

    /*   String path = "src/test/resources/TestData/DataViaExcel.xlsx";
        Map<String, String> data = ExcelUtil.getData(path, "GeneralInfo");
*/
        waitAndClick(adminModule);
        waitAndClick(organisation);
        waitAndClick(getGeneralInformationMenu);
        waitUntilDisplayed(generalInformationText);
        Reporter.node("generalInformation");
        Reporter.snap("Organisation General Information Page Displayed");
        waitAndClick(editToggle);
        Reporter.snap("General Information Edit Enabled");

        String path = "src/test/resources/TestData/DataViaExcel.xlsx";
        Map<String, String> data = ExcelUtil.getData(path, "GeneralInfo");
        fillForm(data);
        Reporter.snap("Details of General Info Form filled");
        waitAndClick(saveButton);

    }



    // 🔹 Initialize mapping (EXACT Excel keys)
    private void initFormFields() {

        formFields.put("Organization Name", organizationName);
        formFields.put("Registration Number", registrationNumber);
        formFields.put("Tax ID", taxID);
        formFields.put("Phone", phone);
        formFields.put("Fax", fax);
        formFields.put("Email", email);
        formFields.put("Address Street 1", addressStreet1);
        formFields.put("Address Street 2", addressStreet2);
        formFields.put("City", city);
        formFields.put("State/Province", stateProvince);
        formFields.put("Zip/Postal Code", zipPostalCode);
        formFields.put("Notes", notes);


    }


    // 🔥 REUSABLE LOOP (space-safe + null-safe)
    public void fillForm(Map<String, String> data) throws InterruptedException {

        for (Map.Entry<String, String> entry : data.entrySet()) {

            String key = entry.getKey().trim();     // ✅ handles extra spaces
            String value = entry.getValue().trim(); // ✅ clean value

            By locator = formFields.get(key);

            if (locator != null && !value.isEmpty()) {

                waitClearAndType(locator, value);
                Thread.sleep(3000);
            } else {
                System.out.println("⚠️ Skipping field: " + key);
            }
        }




    }

    public Map<String, String> getFormDataFromUI() {

        // 🗂️ Create a new Map to store UI data
        // Key   -> Field name (e.g., "City")
        // Value -> Value fetched from UI (e.g., "Hyderabad")
        Map<String, String> uiData = new HashMap<>();


        // 🔁 Enhanced for loop (for-each loop)
        // entrySet() → gives a collection of all key-value pairs from formFields
        // Map.Entry<String, By> → represents ONE pair:
        //      key     = field name (String)
        //      value   = locator (By)
        // entry → current pair in each iteration
        for (Map.Entry<String, By> entry : formFields.entrySet()) {

            // 🏷️ Extract the KEY from the entry
            // Example: "City"
            String key = entry.getKey();

            // 📍 Extract the VALUE from the entry
            // Example: By.id("cityInput")
            By locator = entry.getValue();

            // 🔍 Use Selenium to find the element using locator
            // getAttribute("value") → fetches the text inside input field
            // Example: "Hyderabad"
            String value = driver.findElement(locator).getAttribute("value");

            // 🧩 Store the extracted data into uiData map
            // key   = "City"
            // value = "Hyderabad"
            uiData.put(key, value);
        }

        // 📦 Return the final map containing all UI field data
        return uiData;
    }

    public boolean isSuccessPopupDisplayed() {

        return waitUntilDisplayed(successPopup);
    }

}
