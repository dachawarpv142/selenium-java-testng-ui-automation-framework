package org.aa.listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.aa.drivers.DriverManager;
import org.aa.utils.ReportingUtils;

/**
 * TestNG Listener for test execution events
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("========================================");
        logger.info("Test Started: {}", result.getMethod().getMethodName());
        logger.info("========================================");
        Allure.description("Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✓ Test Passed: {}", result.getMethod().getMethodName());
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("✗ Test Failed: {}", result.getMethod().getMethodName());
        logger.error("Failure Message: {}", result.getThrowable());

        // Take screenshot on failure
        try {
            ReportingUtils.attachScreenshotToAllure(result.getMethod().getMethodName());
        } catch (Exception e) {
            logger.warn("Failed to take screenshot", e);
        }

        // Add failure info to Allure report
        Allure.step("Test Failed with error: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⊘ Test Skipped: {}", result.getMethod().getMethodName());
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test Failed but within success percentage: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onStart(org.testng.ITestContext context) {
        logger.info("========================================");
        logger.info("Test Suite Started: {}", context.getSuite().getName());
        logger.info("========================================");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        logger.info("========================================");
        logger.info("Test Suite Finished: {}", context.getSuite().getName());
        logger.info("Total Tests Run: {}", context.getAllTestMethods().length);
        logger.info("Passed: {}", context.getPassedTests().size());
        logger.info("Failed: {}", context.getFailedTests().size());
        logger.info("Skipped: {}", context.getSkippedTests().size());
        logger.info("========================================");
    }
}

