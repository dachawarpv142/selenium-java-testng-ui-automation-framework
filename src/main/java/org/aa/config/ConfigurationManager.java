package org.aa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager to load environment-specific properties
 */
public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
    private static ConfigurationManager instance;
    private Properties properties;
    private String environment;

    private ConfigurationManager() {
        properties = new Properties();
        loadConfiguration();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private void loadConfiguration() {
        environment = System.getProperty("env", "qa");
        logger.info("Loading configuration for environment: {}", environment);

        String configFilePath = String.format("src/test/resources/config/config-%s.properties", environment);
        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            logger.error("Configuration file not found: {}", configFilePath);
            throw new RuntimeException("Configuration file not found: " + configFilePath);
        }

        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully for environment: {}", environment);
        } catch (IOException e) {
            logger.error("Failed to load configuration file", e);
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: {}", key);
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public long getImplicitWait() {
        return Long.parseLong(getProperty("implicit.wait", "10"));
    }

    public long getExplicitWait() {
        return Long.parseLong(getProperty("explicit.wait", "20"));
    }

    public long getPageLoadTimeout() {
        return Long.parseLong(getProperty("page.load.timeout", "30"));
    }

    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public boolean isVideoRecording() {
        return Boolean.parseBoolean(getProperty("video.recording", "false"));
    }

    public boolean isSauceLabsEnabled() {
        return Boolean.parseBoolean(getProperty("sauce.labs.enabled", "false"));
    }

    public String getSauceLabsUrl() {
        return getProperty("sauce.labs.url", "https://ondemand.saucelabs.com/wd/hub");
    }

    public String getSauceLabsUsername() {
        return System.getenv("SAUCE_USERNAME");
    }

    public String getSauceLabsAccessKey() {
        return System.getenv("SAUCE_ACCESS_KEY");
    }

    public String getEnvironment() {
        return environment;
    }
}

