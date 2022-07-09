package org.heyrmi.reports;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.heyrmi.utils.Screenshot;


public final class ExtentLogger {
    // to avoid external instantiation
    private ExtentLogger() {
    }

    public static void pass(String message) {
        ExtentManager.getExtentTest().pass(message);
    }

    public static void failWithExtentColor(String message) {
        ExtentManager.getExtentTest().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
        attachScreenshot();
    }

    public static void fail(String message) {
        ExtentManager.getExtentTest().fail(message);
        attachScreenshot();
    }

    public static void info(String message) {
        ExtentManager.getExtentTest().info(message);
    }

    public static void skipWithExtentColor(String message) {
        ExtentManager.getExtentTest().skip(MarkupHelper.createLabel(message, ExtentColor.ORANGE));
    }

    public static void skip(String message) {
        ExtentManager.getExtentTest().skip(message);
    }

    private static void attachScreenshot() {
        //TODO: Test the working of screenshot
        String screenShot = String.valueOf(Screenshot.takeScreenshot());
        MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot).build();
    }
}

