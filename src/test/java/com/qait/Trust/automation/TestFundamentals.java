/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.Trust.automation;

import static com.qait.Trust.automation.TestSessionInitiator.testMethodName;
import static com.qait.Trust.automation.utils.YamlReader.*;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public class TestFundamentals {

    public TestSessionInitiator test;
    public String CourseKey;
    public String activityName;
    public String base_url;
    public String sso_url;
    public String testCase;

    public void init() {

    }

    @BeforeClass
    public void start_test_session() {
        test = new TestSessionInitiator(this.getClass().getSimpleName());
        base_url = getData("base_url");
        sso_url = getData("sso_url");
        init();
    }

    @BeforeMethod
    public void getMethodName(Method methodName) {
        testCase = methodName.getName();
        testMethodName(methodName.getName());
    }

    @AfterMethod
    public void takeScreenshotonFailure(ITestResult result) {
        test.takescreenshot.takeScreenShotOnException(result, testCase);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        test.closeBrowserSession();
    }

}