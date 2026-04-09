# Selenium Java UI Automation Framework

A comprehensive, production-ready Selenium Java automation framework using **Page Object Model (POM)** with **TestNG**, **Allure Reporting**, **GitHub Actions CI/CD**, **Sauce Labs** integration, and support for multiple environments.

## 📋 Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Environment Configuration](#environment-configuration)
- [CI/CD Pipeline](#cicd-pipeline)
- [Sauce Labs Integration](#sauce-labs-integration)
- [Allure Reporting](#allure-reporting)
- [Test Cases](#test-cases)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## ✨ Features

### Core Features
- **Page Object Model (POM)**: Organized and maintainable test automation
- **TestNG Framework**: Powerful test execution with grouping, prioritization, and data-driven testing
- **WebDriver Manager**: Automatic WebDriver binary management
- **Configuration Management**: Environment-specific configurations (dev, qa, prod)
- **Parallel Execution**: Run tests across multiple browsers and environments simultaneously
- **Cross-browser Support**: Chrome, Firefox, Edge

### Reporting & Logging
- **Allure Reporting**: Beautiful, detailed HTML reports with step-by-step execution details
- **SLF4J with Logback**: Comprehensive logging with file rotation
- **Screenshot Capture**: Automatic screenshots on test failure
- **Allure Annotations**: Epic, Feature, Story, Severity for better organization

### CI/CD & Cloud Integration
- **GitHub Actions**: Complete CI/CD pipeline configuration
- **Sauce Labs Integration**: Cross-browser execution on cloud infrastructure
- **Artifact Management**: Automatic uploading of test results and screenshots
- **Report Publishing**: GitHub Pages integration for report hosting

### Test Data & Utilities
- **Data-Driven Testing**: Support for parameterized tests
- **Wait Mechanisms**: Implicit and explicit waits with proper synchronization
- **Retry Logic**: Built-in retry mechanism for flaky tests
- **Base Classes**: Reusable base classes for Pages and Tests

## 📦 Prerequisites

### System Requirements
- **Java**: JDK 11 or higher
- **Maven**: 3.6.0 or higher
- **Git**: For version control
- **Chrome/Firefox/Edge**: Browsers for testing

### Environment Setup

#### Windows
```powershell
# Install Java
choco install jdk11

# Install Maven
choco install maven

# Install Git
choco install git
```

#### macOS
```bash
# Install Java
brew install java11

# Install Maven
brew install maven

# Install Git
brew install git
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install openjdk-11-jdk maven git

# CentOS/RHEL
sudo yum install java-11-openjdk maven git
```

## 📁 Project Structure

```
selenium-java-ui-automation-framework/
├── src/
│   ├── main/
│   │   └── java/org/aa/
│   │       ├── config/
│   │       │   └── ConfigurationManager.java
│   │       ├── drivers/
│   │       │   └── DriverManager.java
│   │       ├── pages/
│   │       │   ├── base/
│   │       │   │   └── BasePage.java
│   │       │   └── flipkart/
│   │       │       ├── LoginPage.java
│   │       │       └── HomePage.java
│   │       └── utils/
│   │           └── ReportingUtils.java
│   └── test/
│       ├── java/org/aa/
│       │   ├── tests/
│       │   │   ├── base/
│       │   │   │   └── BaseTest.java
│       │   │   ├── LoginTest.java
│       │   │   └── SearchTest.java
│       │   └── listeners/
│       │       └── TestListener.java
│       └── resources/
│           ├── config/
│           │   ├── config-dev.properties
│           │   ├── config-qa.properties
│           │   └── config-prod.properties
│           ├── testng.xml
│           └── logback.xml
├── .github/
│   └── workflows/
│       └── automation-tests.yml
├── pom.xml
├── README.md
├── .gitignore
├── target/
│   ├── allure-results/
│   ├── allure-report/
│   └── screenshots/
└── logs/
    └── test.log
```

## 🚀 Setup and Installation

### Step 1: Clone Repository
```bash
git clone https://github.com/yourusername/selenium-java-ui-automation-framework.git
cd selenium-java-ui-automation-framework
```

### Step 2: Install Dependencies
```bash
mvn clean install
```

### Step 3: Verify Installation
```bash
java -version
mvn --version
```

## ⚙️ Configuration

### ConfigurationManager

The framework uses `ConfigurationManager.java` to load environment-specific configurations dynamically.

#### Configuration File Structure
Each environment has its own configuration file:

**config-dev.properties**
```properties
environment=dev
base.url=http://localhost:8080
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20
page.load.timeout=30
screenshot.on.failure=true
video.recording=false
sauce.labs.enabled=false
```

**config-qa.properties**
```properties
environment=qa
base.url=https://www.flipkart.com
browser=chrome
headless=false
implicit.wait=15
explicit.wait=30
page.load.timeout=30
screenshot.on.failure=true
video.recording=false
sauce.labs.enabled=false
```

**config-prod.properties**
```properties
environment=prod
base.url=https://www.flipkart.com
browser=chrome
headless=true
implicit.wait=20
explicit.wait=40
page.load.timeout=30
screenshot.on.failure=true
video.recording=true
sauce.labs.enabled=true
sauce.labs.url=https://ondemand.saucelabs.com/wd/hub
sauce.labs.platform=Windows 10
sauce.labs.browser.version=latest
```

### How to Add New Environments

1. Create new configuration file: `src/test/resources/config/config-{environment}.properties`
2. Define properties specific to that environment
3. Run tests with: `mvn test -Denv={environment}`

## 🧪 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Tests for Specific Environment
```bash
# Dev environment
mvn test -Denv=dev

# QA environment
mvn test -Denv=qa

# Production environment
mvn test -Denv=prod
```

### Run Tests for Specific Browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run Specific Test Class
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=SearchTest
```

### Run Tests in Specific Group
```bash
# Run only smoke tests
mvn test -Dgroups=smoke

# Run only login tests
mvn test -Dgroups=login

# Run tests in regression group
mvn test -Dgroups=regression
```

### Run Tests in Parallel
```bash
mvn test -Dthreads=4 -DthreadCount=4
```

### Run Tests in Headless Mode
```bash
mvn test -Denv=qa -Dheadless=true
```

### Run Tests with Custom TestNG Suite
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 🌍 Environment Configuration

### Dynamic Configuration Loading

The framework automatically loads the appropriate configuration file based on the `env` system property:

```java
// Automatically loads config-qa.properties when -Denv=qa
ConfigurationManager config = ConfigurationManager.getInstance();
String baseUrl = config.getBaseUrl();
```

### Environment Variables

For sensitive data (credentials, API keys), use environment variables:

```bash
# Set environment variables
export SAUCE_USERNAME=your_username
export SAUCE_ACCESS_KEY=your_access_key

# Run tests
mvn test -Denv=prod
```

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

The framework includes a complete GitHub Actions workflow file: `.github/workflows/automation-tests.yml`

#### Features:
- **Scheduled Runs**: Daily execution at 2 AM UTC
- **Multi-OS Support**: Ubuntu, Windows, macOS
- **Multi-Browser**: Chrome, Firefox
- **Multi-Environment**: dev, qa, prod
- **Matrix Builds**: Runs combination of OS, browser, environment
- **Sauce Labs Integration**: Separate job for cloud execution
- **Report Publishing**: Automatic Allure report publishing to GitHub Pages
- **Artifact Management**: Uploads logs, screenshots, and reports

#### Trigger Conditions:
- Push to main or develop branch
- Pull requests to main or develop
- Scheduled daily execution
- Manual trigger (workflow_dispatch)

### Setting Up GitHub Actions

1. **Push code to repository**
   ```bash
   git add .
   git commit -m "Initial commit"
   git push origin main
   ```

2. **Enable GitHub Pages**
   - Go to repository Settings
   - Navigate to Pages section
   - Set source to `gh-pages` branch

3. **Add Secrets (for Sauce Labs)**
   - Go to Settings → Secrets and variables → Actions
   - Add `SAUCE_USERNAME`
   - Add `SAUCE_ACCESS_KEY`

4. **Monitor Execution**
   - Go to Actions tab
   - View workflow runs and logs
   - Check generated reports

## 🚀 Sauce Labs Integration

### Configuration

Add Sauce Labs credentials as GitHub repository secrets:
- `SAUCE_USERNAME`: Your Sauce Labs username
- `SAUCE_ACCESS_KEY`: Your Sauce Labs access key

### Running Tests on Sauce Labs

#### Locally
```bash
export SAUCE_USERNAME=your_username
export SAUCE_ACCESS_KEY=your_access_key
mvn test -Denv=prod -Dsauce.labs.enabled=true
```

#### Via GitHub Actions
Tests automatically run on Sauce Labs when pushing to main branch with prod environment.

### Sauce Labs Configuration

Edit `config-prod.properties`:
```properties
sauce.labs.enabled=true
sauce.labs.url=https://ondemand.saucelabs.com/wd/hub
sauce.labs.platform=Windows 10
sauce.labs.browser.version=latest
```

## 📊 Allure Reporting

### Generate Allure Report

```bash
# Generate report
mvn allure:report

# Open report in browser (if installed locally)
mvn allure:serve
```

### Report Location
- Results: `target/allure-results/`
- HTML Report: `target/site/allure-maven-plugin-report.html`

### GitHub Pages Report
When using GitHub Actions, reports are automatically published to:
```
https://yourusername.github.io/selenium-java-ui-automation-framework
```

### Allure Annotations

The framework supports rich Allure annotations:

```java
@Epic("Flipkart Automation")
@Feature("Authentication")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Description("Verify that user can login with valid credentials")
public void testLogin() {
    // Test code
}
```

#### Test Organization Hierarchy
- **Epic**: High-level feature group
- **Feature**: Functional area (Login, Search, etc.)
- **Story**: Specific test scenario
- **Severity**: BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL

## 📝 Test Cases

### Login Test Cases

Located in: `src/test/java/org/aa/tests/LoginTest.java`

1. **testLoginPageLoads** - Verify login page loads
2. **testLoginWithValidCredentials** - Verify login with valid email and password
3. **testLoginFormValidation** - Verify login form validation
4. **testLoginButtonVisibility** - Verify login button is visible
5. **testNavigateToFlipkart** - Verify navigation to Flipkart

### Search Test Cases

Located in: `src/test/java/org/aa/tests/SearchTest.java`

1. **testSearchProduct** - Verify product search functionality
2. **testSearchMultipleProducts** - Verify search with multiple products
3. **testSearchBarPresence** - Verify search bar is present
4. **testNavigateToHomePage** - Verify homepage navigation
5. **testPageTitle** - Verify page title
6. **testSearchProductDataDriven** - Data-driven search test

### Adding New Test Cases

1. Create new test class extending `BaseTest`:
   ```java
   @Epic("Feature Name")
   @Feature("Functionality")
   public class YourTest extends BaseTest {
       
       @Test
       @Story("Test Story")
       @Severity(SeverityLevel.HIGH)
       public void testYourFeature() {
           YourPage page = new YourPage(driver);
           // Test implementation
       }
   }
   ```

2. Create corresponding Page Object extending `BasePage`:
   ```java
   public class YourPage extends BasePage {
       private By element = By.id("element-id");
       
       public YourPage(WebDriver driver) {
           super(driver);
       }
       
       public void yourAction() {
           click(element);
       }
   }
   ```

## 🎯 Best Practices

### Page Object Model
- Keep page objects focused on a single page/component
- Use meaningful method names that describe actions
- Centralize all locators in page objects
- Avoid test logic in page objects

### Test Writing
- One assertion per test or logically related assertions
- Use descriptive test names
- Add proper logging at each step
- Use `ReportingUtils.logStep()` for Allure reports
- Group related tests using TestNG groups

### Wait Strategies
- Use explicit waits for element interactions
- Set reasonable timeout values
- Avoid Thread.sleep() unless necessary
- Use ExpectedConditions for proper synchronization

### Configuration Management
- Never hardcode URLs or credentials
- Use configuration files for environment-specific data
- Use environment variables for sensitive data
- Load configuration at test startup

### Reporting
- Attach screenshots on failures
- Log meaningful messages at each step
- Use Allure annotations for better organization
- Regularly review reports for trends

## 🔧 Troubleshooting

### Issue: Tests fail with "WebDriver not initialized"
**Solution**: Ensure `@BeforeMethod` in `BaseTest` is being called. Check if test extends `BaseTest`.

### Issue: "WebDriver path does not exist"
**Solution**: WebDriverManager should handle this. Run `mvn clean install` to refresh dependencies.

### Issue: TimeoutException on element interaction
**Solution**: 
- Increase explicit wait in configuration
- Check if element selector is correct
- Verify element is actually present on page

### Issue: Configuration file not found
**Solution**: 
- Verify file exists in `src/test/resources/config/`
- Check file name matches environment parameter
- Ensure -Denv parameter is correct

### Issue: GitHub Actions workflow not triggering
**Solution**:
- Verify workflow file is in `.github/workflows/` directory
- Check branch name matches trigger conditions
- Verify YAML syntax is correct

### Issue: Sauce Labs tests not running
**Solution**:
- Verify credentials are set in GitHub secrets
- Check Sauce Labs account has available concurrency
- Verify -Dsauce.labs.enabled=true is passed

### Issue: Allure report not generating
**Solution**:
- Ensure Allure plugin is configured in pom.xml
- Run: `mvn allure:report`
- Check `target/allure-results/` directory exists

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [Page Object Model](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)
- [Sauce Labs Documentation](https://docs.saucelabs.com/)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add YourFeature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## 📞 Support

For issues, questions, or suggestions, please open an issue in the repository or contact the maintainers.

---

**Last Updated**: April 9, 2026

**Framework Version**: 1.0.0

**Maintained by**: QA Automation Team

