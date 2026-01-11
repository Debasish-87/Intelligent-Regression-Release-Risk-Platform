package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initialize WebDriver (Suite-safe & Thread-safe)
     */
    public static void initDriver() {

        // ✅ Prevent double initialization (VERY IMPORTANT for suites)
        if (driver.get() != null) {
            return;
        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Disable Chrome popups / password prompts
        options.addArguments("--disable-features=PasswordManagerOnboarding,PasswordManagerUI,CredentialProviderExtension");
        options.addArguments("--password-store=basic");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--no-first-run");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // ✅ HEADLESS Mode for CI/CD
        if (System.getProperty("headless", "false").equalsIgnoreCase("true")) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        driver.set(new ChromeDriver(options));
    }

    /**
     * Get current thread WebDriver
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quit and clean WebDriver
     */
    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}
