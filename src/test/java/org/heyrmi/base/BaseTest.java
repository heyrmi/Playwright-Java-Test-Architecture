package org.heyrmi.base;

import com.microsoft.playwright.Page;
import org.heyrmi.factory.PlaywrightFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected BaseTest() {}

    @BeforeClass
    protected void setUp() {
        Page page = PlaywrightFactory.getPage();
    }

    @AfterClass
    protected void tearDown() {
        PlaywrightFactory.closePage();
    }
}