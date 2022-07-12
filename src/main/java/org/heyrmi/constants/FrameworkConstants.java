package org.heyrmi.constants;

import lombok.Getter;

public final class FrameworkConstants {

    //To avoid external instantiation
    private FrameworkConstants(){}


    //TODO: Improve it.
    private static @Getter String extentReportPath = System.getProperty("user.dir")+ "/reports/Report.html";
}
