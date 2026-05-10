package com.myown.ownProject.core.utilities;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String path = "src/test/resources/TestData/DataViaExcel.xlsx";
        return ExcelUtil.getSheetData(path, "LoginTestData");
    }
}