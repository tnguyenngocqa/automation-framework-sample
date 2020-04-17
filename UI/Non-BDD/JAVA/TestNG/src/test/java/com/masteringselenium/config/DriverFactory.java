package com.masteringselenium.config;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This class holds a reference to a WebDriver object
 * - Ensures that every time you call **getDriver()** you get a valid instance of WebDriver back.
 * If one started up, you will get the existing one. If one hasn't been started up, it will start one for you
 * - Provides **quitDriver()** method that will perform quit()
 */
public class DriverFactory {

    private RemoteWebDriver webDriver;
    private DriverType selectedDriverType;

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");

    public DriverFactory() {
        DriverType driverType = DriverType.FIREFOX;
        String browser = System.getProperty("browser").toUpperCase();
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public RemoteWebDriver getDriver() {
        if (null == webDriver) {
            System.out.println(" ");
            System.out.println("Current Operating System: " + operatingSystem);
            System.out.println("Current Architecture: " + systemArchitecture);
            System.out.println("Current Browser Selection: " + selectedDriverType);
            System.out.println("Current Thread: " + Thread.currentThread().getId());
            System.out.println(" ");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            webDriver = selectedDriverType.getWebDriverObject(capabilities);
        }

        return webDriver;
    }

    public void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
