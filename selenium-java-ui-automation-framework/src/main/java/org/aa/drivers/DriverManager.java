package org.aa.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aa.config.ConfigurationManager;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * WebDriver Manager for creating and managing driver instances
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ConfigurationManager config;

    public DriverManager() {
        this.config = ConfigurationManager.getInstance();
    }

    public WebDriver initializeDriver() {
        logger.info("Initializing WebDriver for browser: {}", config.getBrowser());

        if (config.isSauceLabsEnabled()) {
            return initializeSauceLabsDriver();
        }

        return initializeLocalDriver();
    }

    private WebDriver initializeLocalDriver() {
        WebDriver webDriver;

        switch (config.getBrowser().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (config.isHeadless()) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (config.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (config.isHeadless()) {
                    edgeOptions.addArguments("--headless");
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + config.getBrowser());
        }

        setImplicitWait(webDriver);
        logger.info("WebDriver initialized successfully");
        return webDriver;
    }

    private WebDriver initializeSauceLabsDriver() {
        try {
            logger.info("Initializing Sauce Labs remote driver");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(config.getBrowser());
            capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
            capabilities.setVersion(config.getProperty("sauce.labs.browser.version", "latest"));
            capabilities.setCapability("screenResolution", "1024x768");

            String sauceLabsUrl = String.format("https://%s:%s@ondemand.saucelabs.com/wd/hub",
                    config.getSauceLabsUsername(),
                    config.getSauceLabsAccessKey());

            URL url = new URL(sauceLabsUrl);
            WebDriver webDriver = new RemoteWebDriver(url, capabilities);
            setImplicitWait(webDriver);
            logger.info("Sauce Labs driver initialized successfully");
            return webDriver;
        } catch (Exception e) {
            logger.error("Failed to initialize Sauce Labs driver", e);
            throw new RuntimeException("Failed to initialize Sauce Labs driver", e);
        }
    }

    private void setImplicitWait(WebDriver webDriver) {
        webDriver.manage().timeouts().implicitlyWait(
                config.getImplicitWait(), TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(
                config.getPageLoadTimeout(), TimeUnit.SECONDS);
    }

    public void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new RuntimeException("WebDriver not initialized. Call initializeDriver() first.");
        }
        return webDriver;
    }

    public void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            logger.info("WebDriver quit successfully");
            driver.remove();
        }
    }

    public void maximizeWindow() {
        getDriver().manage().window().maximize();
    }
}

