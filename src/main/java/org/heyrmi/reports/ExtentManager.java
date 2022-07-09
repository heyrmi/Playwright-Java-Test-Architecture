package org.heyrmi.reports;

import com.aventstack.extentreports.ExtentTest;

import java.util.Objects;

public final class ExtentManager {

    //To avoid external instantiation
    private ExtentManager(){}

    private static ThreadLocal<ExtentTest> extentTestThread= new ThreadLocal<>();


    public static ExtentTest getExtentTest() {
        return extentTestThread.get();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        //Assign only when value of ExtentTest is not null
        if(Objects.isNull(extentTest)){
            extentTestThread.set(extentTest);
        }
    }

    public static void unload(){
        if(Objects.nonNull(extentTestThread)){
            extentTestThread.remove();
        }
    }
}
