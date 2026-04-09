package org.aa.tests.base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.aa.config.ConfigurationManager;
import org.aa.drivers.DriverManager;
import org.aa.utils.ReportingUtils;

/**
 * Base Test class for all test classes
 */
public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected DriverManager driverManager;
    protected ConfigurationManager config;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("=============== Test Setup Started ===============");
        config = ConfigurationManager.getInstance();
        driverManager = new DriverManager();

        // Initialize driver
        driver = driverManager.initializeDriver();
        driverManager.setDriver(driver);
        driverManager.maximizeWindow();

        logger.info("Test Setup Completed");
        logger.info("Environment: {}", config.getEnvironment());
        logger.info("Base URL: {}", config.getBaseUrl());
        logger.info("Browser: {}", config.getBrowser());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("=============== Test Teardown Started ===============");

        // Take screenshot on failure if configured
        if (config.isScreenshotOnFailure()) {
            ReportingUtils.attachScreenshotToAllure("test-screenshot");
        }

        // Quit driver
        if (driverManager != null) {
            driverManager.quitDriver();
        }

        logger.info("Test Teardown Completed");
        logger.info("=============== Test Execution Finished ===============");
    }
}

