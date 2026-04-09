package org.aa.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aa.drivers.DriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for reporting and screenshots
 */
public class ReportingUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReportingUtils.class);
    private static final String SCREENSHOTS_DIR = "target/screenshots";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    static {
        createScreenshotsDirectory();
    }

    /**
     * Creates screenshots directory if it doesn't exist
     */
    private static void createScreenshotsDirectory() {
        File screenshotsDir = new File(SCREENSHOTS_DIR);
        if (!screenshotsDir.exists()) {
            if (screenshotsDir.mkdirs()) {
                logger.info("Screenshots directory created: {}", SCREENSHOTS_DIR);
            }
        }
    }

    /**
     * Takes screenshot and saves to file
     */
    public static String takeScreenshot(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                logger.warn("WebDriver is null, cannot take screenshot");
                return null;
            }

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = String.format("%s/%s_%s.png", SCREENSHOTS_DIR, testName,
                    LocalDateTime.now().format(formatter));

            File destinationFile = new File(fileName);
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, destinationFile);

            logger.info("Screenshot saved: {}", fileName);
            return fileName;
        } catch (IOException e) {
            logger.error("Error taking screenshot", e);
            return null;
        }
    }

    /**
     * Attaches screenshot to Allure report
     */
    public static void attachScreenshotToAllure(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                logger.warn("WebDriver is null, cannot attach screenshot to Allure");
                return;
            }

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Allure.addAttachment("Screenshot", new FileInputStream(screenshotFile));
            logger.info("Screenshot attached to Allure report");
        } catch (IOException e) {
            logger.error("Error attaching screenshot to Allure", e);
        }
    }

    /**
     * Logs test step to Allure report
     */
    public static void logStep(String stepDescription) {
        logger.info("Step: {}", stepDescription);
        Allure.step(stepDescription);
    }

    /**
     * Adds description to Allure report
     */
    public static void addDescription(String description) {
        logger.info("Test description: {}", description);
        Allure.description(description);
    }

    /**
     * Adds label to Allure report
     */
    public static void addLabel(String label) {
        logger.info("Adding label: {}", label);
        Allure.label("Manual", label);
    }


    /**
     * Gets current page title
     */
    public static String getCurrentPageTitle() {
        try {
            return DriverManager.getDriver().getTitle();
        } catch (Exception e) {
            logger.error("Error getting page title", e);
            return "";
        }
    }

    /**
     * Gets current URL
     */
    public static String getCurrentUrl() {
        try {
            return DriverManager.getDriver().getCurrentUrl();
        } catch (Exception e) {
            logger.error("Error getting current URL", e);
            return "";
        }
    }
}

