package org.aa.pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aa.config.ConfigurationManager;
import org.aa.drivers.DriverManager;

import java.time.Duration;

/**
 * Base Page Object class with common methods
 */
public class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ConfigurationManager config;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.config = ConfigurationManager.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for element to be visible and returns it
     */
    protected WebElement waitForElement(By locator) {
        logger.debug("Waiting for element: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for element to be clickable and returns it
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Finds element by locator
     */
    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Clicks on element
     */
    protected void click(By locator) {
        logger.info("Clicking on element: {}", locator);
        waitForElementToBeClickable(locator).click();
    }

    /**
     * Clicks on web element
     */
    protected void click(WebElement element) {
        logger.info("Clicking on element");
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    /**
     * Enters text in element
     */
    protected void sendKeys(By locator, String text) {
        logger.info("Entering text: {} in element: {}", text, locator);
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Enters text in web element
     */
    protected void sendKeys(WebElement element, String text) {
        logger.info("Entering text: {} in element", text);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets text from element
     */
    protected String getText(By locator) {
        logger.debug("Getting text from element: {}", locator);
        return waitForElement(locator).getText();
    }

    /**
     * Gets text from web element
     */
    protected String getText(WebElement element) {
        logger.debug("Getting text from element");
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    /**
     * Checks if element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            logger.warn("Element not displayed: {}", locator);
            return false;
        }
    }

    /**
     * Waits for element to be present
     */
    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            logger.warn("Element not present: {}", locator);
            return false;
        }
    }

    /**
     * Selects option by visible text
     */
    protected void selectByVisibleText(By locator, String text) {
        logger.info("Selecting option by visible text: {} from dropdown: {}", text, locator);
        WebElement element = waitForElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    /**
     * Selects option by value
     */
    protected void selectByValue(By locator, String value) {
        logger.info("Selecting option by value: {} from dropdown: {}", value, locator);
        WebElement element = waitForElement(locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    /**
     * Navigates to URL
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.navigate().to(url);
    }

    /**
     * Gets current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Refreshes page
     */
    protected void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Waits for URL to contain text
     */
    protected boolean waitForUrlToContain(String text) {
        logger.info("Waiting for URL to contain: {}", text);
        return wait.until(ExpectedConditions.urlContains(text));
    }

    /**
     * Waits for title to be
     */
    protected boolean waitForTitle(String title) {
        logger.info("Waiting for page title: {}", title);
        return wait.until(ExpectedConditions.titleIs(title));
    }

    /**
     * Gets page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
}

