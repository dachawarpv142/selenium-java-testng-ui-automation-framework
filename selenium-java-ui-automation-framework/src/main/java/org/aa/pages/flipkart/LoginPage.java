package org.aa.pages.flipkart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aa.pages.base.BasePage;

/**
 * Flipkart Login Page Object
 */
public class LoginPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    // Locators
    @FindBy(xpath = "//a[contains(text(), 'Login')]")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@type='text' and @class='r4vIwf']")
    private WebElement emailPhoneInput;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginSubmitButton;

    @FindBy(xpath = "//button//span[contains(text(), 'Login')]")
    private WebElement loginModalSubmitButton;

    @FindBy(xpath = "//a[contains(@class, 'ExitIcon')]")
    private WebElement closeLoginModal;

    @FindBy(xpath = "//span[contains(text(), 'Account')]")
    private WebElement accountButton;

    private static final By LOGIN_BUTTON_LOCATOR = By.xpath("//a[contains(text(), 'Login')]");
    private static final By EMAIL_PHONE_INPUT_LOCATOR = By.xpath("//input[@type='text' and @class='r4vIwf']");
    private static final By PASSWORD_INPUT_LOCATOR = By.xpath("//input[@type='password']");
    private static final By LOGIN_SUBMIT_BUTTON_LOCATOR = By.xpath("//button[contains(text(), 'Login')]");

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("Initializing LoginPage");
    }

    /**
     * Opens Flipkart login page
     */
    public void openLoginPage() {
        logger.info("Opening Flipkart login page");
        String baseUrl = config.getBaseUrl();
        navigateTo(baseUrl);

        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.warn("Interrupted while waiting for page to load", e);
        }
    }

    /**
     * Clicks on login button
     */
    public void clickLoginButton() {
        logger.info("Clicking on Login button");
        try {
            if (isElementDisplayed(LOGIN_BUTTON_LOCATOR)) {
                click(LOGIN_BUTTON_LOCATOR);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            logger.warn("Error clicking login button", e);
        }
    }

    /**
     * Enters email or phone number
     */
    public void enterEmailOrPhone(String emailOrPhone) {
        logger.info("Entering email or phone: {}", emailOrPhone);
        try {
            sendKeys(EMAIL_PHONE_INPUT_LOCATOR, emailOrPhone);
        } catch (Exception e) {
            logger.error("Error entering email/phone", e);
            throw new RuntimeException("Failed to enter email/phone", e);
        }
    }

    /**
     * Enters password
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        try {
            sendKeys(PASSWORD_INPUT_LOCATOR, password);
        } catch (Exception e) {
            logger.error("Error entering password", e);
            throw new RuntimeException("Failed to enter password", e);
        }
    }

    /**
     * Clicks login submit button
     */
    public void clickLoginSubmit() {
        logger.info("Clicking Login Submit button");
        try {
            click(LOGIN_SUBMIT_BUTTON_LOCATOR);
            Thread.sleep(3000);
        } catch (Exception e) {
            logger.error("Error clicking login submit button", e);
            throw new RuntimeException("Failed to click login submit button", e);
        }
    }

    /**
     * Performs login with email and password
     */
    public void login(String emailOrPhone, String password) {
        logger.info("Performing login");
        openLoginPage();
        clickLoginButton();
        enterEmailOrPhone(emailOrPhone);
        clickLoginSubmit();
        enterPassword(password);
        clickLoginSubmit();
    }

    /**
     * Checks if user is logged in by verifying account button
     */
    public boolean isUserLoggedIn() {
        logger.info("Checking if user is logged in");
        try {
            Thread.sleep(2000);
            return isElementDisplayed(By.xpath("//span[contains(text(), 'Account')]"));
        } catch (Exception e) {
            logger.warn("Error checking login status", e);
            return false;
        }
    }

    /**
     * Gets login error message
     */
    public String getLoginErrorMessage() {
        logger.info("Getting login error message");
        try {
            By errorLocator = By.xpath("//span[contains(text(), 'Please check your Email ID or Mobile number')]");
            if (isElementDisplayed(errorLocator)) {
                return getText(errorLocator);
            }
            return "";
        } catch (Exception e) {
            logger.warn("No error message displayed", e);
            return "";
        }
    }
}

