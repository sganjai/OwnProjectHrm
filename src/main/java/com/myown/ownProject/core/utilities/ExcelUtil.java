package com.myown.ownProject.core.utilities;

import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    // 🔹 Method to convert Excel → Map (Key-Value)
    public static Map<String, String> getData(String filePath, String sheetName) {

        // Create Reader object (uses low-level class)
        ExcelReader reader = new ExcelReader(filePath, sheetName);

        // Map to store final structured data
        Map<String, String> data = new HashMap<>();

        // Get total number of rows
        int rowCount = reader.getRowCount();

        // 🔁 Loop through each row
        for (int i = 0; i < rowCount; i++) {

            // Column 0 = Key
            String key = reader.getCellData(i, 0);

            // Column 1 = Value
            String value = reader.getCellData(i, 1);

            // Store in Map
            data.put(key, value);
        }

        // Close workbook (important)
        reader.close();

        // Return structured data
        return data;
    }

    public static Object[][] getSheetData(String filePath, String sheetName) {

        ExcelReader reader = new ExcelReader(filePath, sheetName);

        int rowCount = reader.getRowCount();
        int colCount = reader.getColCountIncludeBlanks();

        Object[][] data = new Object[rowCount - 1][1]; // skip header

        for (int i = 1; i < rowCount; i++) {

            Map<String, String> rowData = new HashMap<>();

            for (int j = 0; j < colCount; j++) {

                String key = reader.getCellData(0, j);   // header
                String value = reader.getCellData(i, j); // value

                rowData.put(key, value);
            }

            data[i - 1][0] = rowData;
        }

        reader.close();

        return data;
    }

}