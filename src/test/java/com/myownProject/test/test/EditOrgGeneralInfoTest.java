package com.myownProject.test.test;

import com.myown.ownProject.core.config.configReader;
import com.myown.ownProject.core.pages.HomePage;
import com.myown.ownProject.core.pages.LoginPage;
import com.myown.ownProject.core.pages.OrgGeneralInfo;
import com.myown.ownProject.core.utilities.ExcelUtil;
import com.myown.ownProject.core.utilities.Reporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Map;

public class EditOrgGeneralInfoTest extends BaseTest{
    OrgGeneralInfo orgGeneralInfo;
    LoginPage loginPage;
    HomePage homePage;
    @Test
    public void editOrganisation() throws IOException, InterruptedException {
        orgGeneralInfo = new OrgGeneralInfo();

        loginPage = new LoginPage();
        Reporter.node("Entering Credentials");
        homePage = loginPage.login(configReader.getProperty("username"), configReader.getProperty("password"));
        Assert.assertTrue(homePage.isDashBoardLogoDisplayed());
        Reporter.snap("DashBoard");

        orgGeneralInfo.editGenearalInfo();
        Assert.assertTrue(orgGeneralInfo.isSuccessPopupDisplayed(), "New General Info Successfully Saved");
        Reporter.snap("Success Popup");

        // 🔹 Excel Data
        String path = "src/test/resources/TestData/DataViaExcel.xlsx";
        Map<String, String> excelData = ExcelUtil.getData(path, "GeneralInfo");
        // 🔥 Read UI Data
        Map<String, String> uiData = orgGeneralInfo.getFormDataFromUI();


        SoftAssert softAssert = new SoftAssert();
        for (Map.Entry<String, String> entry : excelData.entrySet()) {

            // 🏷️ KEY from Excel (field name)
            // trim() → removes unwanted spaces (" City " → "City")
            String key = entry.getKey().trim();

            // 📘 EXPECTED value from Excel
            // trim() → avoids mismatch due to extra spaces
            String expected = entry.getValue().trim();

            // 🌐 ACTUAL value from UI map (already captured earlier)
            // using key to fetch corresponding UI value
            String actual = uiData.get(key);

            //Below block is for logging helps in debug
            boolean isMatch = actual != null && actual.equals(expected);
            Reporter.info((isMatch ? "Assertion ✅ PASS → " : "Assertion ❌ FAIL → ") + key +
                    " | Expected: " + expected +
                    " | Actual: " + actual);


            // 🧪 Soft assertion (TestNG)
            // Compares actual vs expected
            // If mismatch → logs failure BUT continues execution
            softAssert.assertEquals(
                    actual,     // actual value from UI
                    expected,   // expected value from Excel
                    "Mismatch in field: " + key   // custom message
            );
        }
        softAssert.assertAll(); // 🔥 FINAL TRIGGER


        Reporter.node("Logging Out");
        homePage.logOut();
        Reporter.info("LoggedOut");

    }
}
