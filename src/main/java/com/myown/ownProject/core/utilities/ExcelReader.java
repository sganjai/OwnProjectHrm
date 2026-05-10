package com.myown.ownProject.core.utilities;


import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

public class ExcelReader {

    // Workbook = entire Excel file
    private Workbook workbook;

    // Sheet = specific tab inside Excel
    private Sheet sheet;

    // 🔹 Constructor → runs when object is created
    public ExcelReader(String filePath, String sheetName) {

        try {
            // Step 1: Open file as input stream
            FileInputStream fis = new FileInputStream(filePath);

            // Step 2: Create Workbook object from file
            workbook = WorkbookFactory.create(fis);

            // Step 3: Get required sheet by name
            sheet = workbook.getSheet(sheetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Method to get data from specific row & column
    public String getCellData(int rowIndex, int colIndex) {

        // DataFormatter handles all types (String, number, date)
        DataFormatter formatter = new DataFormatter();

        // Get row → then cell → then format value
        return formatter.formatCellValue(
                sheet.getRow(rowIndex).getCell(colIndex)
        );
    }

    // 🔹 Method to get total number of rows
    public int getRowCount() {

        return sheet.getPhysicalNumberOfRows();
    }
    // 🔹 Method to get total number of rows including blanks
    public int getColCountIncludeBlanks() {
        return sheet.getRow(0).getLastCellNum();
    }
    // 🔹 Method to get total number of rows NOT including blanks
    public int getColCountIgnoreBlanks() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }


    // 🔹 Close workbook (good practice to avoid memory issues)
    public void close() {
        try {
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}