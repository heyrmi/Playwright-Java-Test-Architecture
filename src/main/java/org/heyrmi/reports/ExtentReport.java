package org.heyrmi.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.heyrmi.constants.FrameworkConstants;

import java.util.Objects;

public final class ExtentReport {

    //To avoid external instantiation
    private ExtentReport(){}

    private static ExtentReports extentReport;

    public static void initReport(){
        if(Objects.isNull(extentReport)){
            extentReport = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(FrameworkConstants.getExtentReportPath());
            extentReport.attachReporter(sparkReporter);

            //Extent report configrations
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Playwright Testing Report");
            sparkReporter.config().setReportName("Test Report");
        }
    }

    public static void flushReports(){
        if(Objects.nonNull(extentReport)){
            extentReport.flush();
        }
        //Clear threadlocal variable
        ExtentManager.unload();
    }

    public static void createTest(String testcasename) {
        ExtentManager.setExtentTest(extentReport.createTest(testcasename));
    }

    public static void addAuthors(String[] authors) {
        for (String temp : authors) {
            ExtentManager.getExtentTest().assignAuthor(temp);
        }
    }
}
