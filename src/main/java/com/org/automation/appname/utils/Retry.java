package com.org.automation.appname.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    int retryCount = 0;
    private static final int maxRetryCount = 1;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(retryCount < maxRetryCount){
            retryCount++;
            return true;
        }
        return false;
    }

    public int getRetryCount() {
        return retryCount;
    }
}
