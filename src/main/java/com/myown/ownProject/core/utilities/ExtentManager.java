package com.myown.ownProject.core.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

    private static ExtentReports extent;

    // 🔒 Generate once per run (important)
    private static final String TIME = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    public static ExtentReports getInstance() {

        if (extent == null) {

            String folder = System.getProperty("user.dir") +
                    "/reports/" + TIME;

            new File(folder).mkdirs();

            String reportPath = folder + "/Report.html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setReportName("Automation Report");
            spark.config().setDocumentTitle("Test Execution");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }

        return extent;
    }

    public static String getTime() {
        return TIME;
    }
}