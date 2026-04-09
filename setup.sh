#!/bin/bash
# Setup script for Selenium Java Automation Framework

echo "=========================================="
echo "Selenium Java Automation Framework Setup"
echo "=========================================="

# Check Java installation
echo "Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed. Please install JDK 11 or higher."
    exit 1
fi
echo "✓ Java found: $(java -version 2>&1 | head -1)"

# Check Maven installation
echo "Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed. Please install Maven 3.6.0 or higher."
    exit 1
fi
echo "✓ Maven found: $(mvn -version | head -1)"

# Clean and install dependencies
echo ""
echo "Installing dependencies..."
mvn clean install -DskipTests -q

if [ $? -eq 0 ]; then
    echo "✓ Dependencies installed successfully"
else
    echo "ERROR: Failed to install dependencies"
    exit 1
fi

# Create necessary directories
echo ""
echo "Creating necessary directories..."
mkdir -p target/screenshots
mkdir -p logs
mkdir -p target/allure-results
echo "✓ Directories created"

echo ""
echo "=========================================="
echo "Setup completed successfully!"
echo "=========================================="
echo ""
echo "To run tests:"
echo "  mvn test -Denv=qa"
echo "  mvn test -Denv=dev"
echo "  mvn test -Denv=prod"
echo ""
echo "To generate Allure report:"
echo "  mvn allure:report"
echo "  mvn allure:serve"

