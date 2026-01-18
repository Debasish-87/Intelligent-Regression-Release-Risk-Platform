package pages;

import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver = DriverManager.getDriver();
    private final WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(5));

    // ================= LOCATORS =================

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton  = By.id("login-button");

    // SauceDemo error container
    private final By errorMessage =
            By.cssSelector("[data-test='error']");

    // ================= ACTIONS =================

    /**
     * Login with null-safety, trimming and wait
     */
    public void login(String username, String password) {

        String safeUsername = username == null ? "" : username.trim();
        String safePassword = password == null ? "" : password.trim();

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput))
                .clear();
        driver.findElement(usernameInput).sendKeys(safeUsername);

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(safePassword);

        driver.findElement(loginButton).click();
    }

    // ================= VALIDATIONS =================

    /**
     * @return true if login error message is visible
     */
    public boolean isErrorDisplayed() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    /**
     * @return true if still on login page
     */
    public boolean isLoginPageDisplayed() {
        return !driver.findElements(loginButton).isEmpty();
    }
}
