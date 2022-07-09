package org.heyrmi.utils;

import org.heyrmi.factory.PlaywrightFactory;

import java.util.Base64;

public final class Screenshot {

    //To avoid external instantiation
    private Screenshot(){}


    //TODO: Find way to attach screenshot to extent report

    public static byte[] takeScreenshot(){
        byte[] buffer = PlaywrightFactory.getPage().screenshot();
        return Base64.getEncoder().encode(buffer);
    }
}
