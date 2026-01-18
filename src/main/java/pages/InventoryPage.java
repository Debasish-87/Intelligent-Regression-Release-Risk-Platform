package pages;

import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class InventoryPage {

    private final WebDriver driver = DriverManager.getDriver();

    // ================= LOCATORS =================

    private final By title =
            By.cssSelector(".title");

    private final By addBackpackBtn =
            By.id("add-to-cart-sauce-labs-backpack");

    private final By cartBadge =
            By.className("shopping_cart_badge");

    private final By cartIcon =
            By.className("shopping_cart_link");

    private final By menuButton =
            By.id("react-burger-menu-btn");

    private final By logoutLink =
            By.id("logout_sidebar_link");

    // ================= ACTIONS =================

    public InventoryPage() {
        // Driver handled via DriverManager
    }

    public String getPageTitle() {
        return driver.findElement(title).getText();
    }

    /**
     * Add Backpack item to cart
     */
    public void addBackpack() {
        driver.findElement(addBackpackBtn).click();
    }

    /**
     * Open Cart Page
     */
    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    /**
     * Get Cart Item Count (waits until visible)
     */
    public String getCartCount() {
        return WaitUtils.waitElementPresent(
                driver,
                cartBadge,
                10
        ).getText();
    }

    /**
     * Validate user is logged in and reached inventory
     */
    public boolean isUserLoggedIn() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    /**
     * Explicit Inventory Page check (used by tests)
     */
    public boolean isOnInventoryPage() {
        return isUserLoggedIn();
    }

    /**
     * Logout from application
     */
    public void logout() {
        driver.findElement(menuButton).click();

        try {
            Thread.sleep(500); // menu animation wait
        } catch (InterruptedException ignored) {}

        driver.findElement(logoutLink).click();
    }
}
