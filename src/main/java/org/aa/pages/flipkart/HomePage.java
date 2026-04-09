package org.aa.pages.flipkart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aa.pages.base.BasePage;

/**
 * Flipkart Home Page Object
 */
public class HomePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // Locators
    @FindBy(xpath = "//input[@name='q']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@type='submit']//span[contains(text(), 'Search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//span[contains(text(), 'Account')]")
    private WebElement accountButton;

    private static final By SEARCH_INPUT_LOCATOR = By.xpath("//input[@name='q']");
    private static final By ACCOUNT_MENU_LOCATOR = By.xpath("//span[contains(text(), 'Account')]");

    public HomePage(WebDriver driver) {
        super(driver);
        logger.info("Initializing HomePage");
    }

    /**
     * Opens Flipkart home page
     */
    public void openHomePage() {
        logger.info("Opening Flipkart home page");
        String baseUrl = config.getBaseUrl();
        navigateTo(baseUrl);
    }

    /**
     * Searches for product
     */
    public void searchProduct(String productName) {
        logger.info("Searching for product: {}", productName);
        try {
            sendKeys(SEARCH_INPUT_LOCATOR, productName);

            // Find and click the search button
            By searchButtonLocator = By.xpath("//button[contains(@class, 'L0Z21f')]");
            click(searchButtonLocator);

            Thread.sleep(3000);
        } catch (Exception e) {
            logger.error("Error searching for product", e);
            throw new RuntimeException("Failed to search for product", e);
        }
    }

    /**
     * Checks if search results are displayed
     */
    public boolean areSearchResultsDisplayed() {
        logger.info("Checking if search results are displayed");
        try {
            Thread.sleep(2000);
            By resultsLocator = By.xpath("//div[contains(@class, 's1Q8_c')]");
            return isElementPresent(resultsLocator);
        } catch (Exception e) {
            logger.warn("Error checking search results", e);
            return false;
        }
    }

    /**
     * Gets first search result product name
     */
    public String getFirstSearchResultProductName() {
        logger.info("Getting first search result product name");
        try {
            By productNameLocator = By.xpath("(//a[contains(@class, 's1Q8_c')]//div[contains(@class, 'KzDlHZ')])[1]");
            return getText(productNameLocator);
        } catch (Exception e) {
            logger.warn("Error getting first search result", e);
            return "";
        }
    }

    /**
     * Clicks on account menu
     */
    public void clickAccountMenu() {
        logger.info("Clicking on Account menu");
        click(ACCOUNT_MENU_LOCATOR);
    }

    /**
     * Logs out user
     */
    public void logout() {
        logger.info("Logging out user");
        try {
            clickAccountMenu();
            Thread.sleep(1000);
            By logoutButtonLocator = By.xpath("//a[contains(text(), 'Logout')]");
            click(logoutButtonLocator);
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.warn("Error during logout", e);
        }
    }

    /**
     * Checks if user is logged in
     */
    public boolean isUserLoggedIn() {
        logger.info("Checking if user is logged in");
        try {
            return isElementPresent(ACCOUNT_MENU_LOCATOR);
        } catch (Exception e) {
            logger.warn("Error checking login status", e);
            return false;
        }
    }
}

