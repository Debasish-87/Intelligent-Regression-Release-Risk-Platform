package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutFlowTests extends BaseTest {

    // 1️⃣ HAPPY PATH – JOHN DOE
    @Test(description = "Checkout with valid user John Doe")
    public void checkoutJohnDoe() {
        login();
        checkout("John", "Doe", "12345", true);
    }

    // 2️⃣ HAPPY PATH – AMIT SHARMA
    @Test(description = "Checkout with valid user Amit Sharma")
    public void checkoutAmitSharma() {
        login();
        checkout("Amit", "Sharma", "560001", true);
    }

    // 3️⃣ HAPPY PATH – RAHUL MEHTA
    @Test(description = "Checkout with valid user Rahul Mehta")
    public void checkoutRahulMehta() {
        login();
        checkout("Rahul", "Mehta", "110011", true);
    }

    // 4️⃣ INVALID ZIP – ALPHABETS
    @Test(description = "Checkout with invalid alphabetic zip")
    public void checkoutInvalidZipAlpha() {
        login();
        checkout("Neha", "Verma", "ABCDE", false);
    }

    // 5️⃣ INVALID ZIP – SPECIAL CHAR
    @Test(description = "Checkout with special character zip")
    public void checkoutInvalidZipSpecial() {
        login();
        checkout("Priya", "Singh", "@@@@", false);
    }

    // 6️⃣ EMPTY FIRST NAME
    @Test(description = "Checkout with empty first name")
    public void checkoutEmptyFirstName() {
        login();
        checkout("", "Kapoor", "400001", false);
    }

    // 7️⃣ EMPTY LAST NAME
    @Test(description = "Checkout with empty last name")
    public void checkoutEmptyLastName() {
        login();
        checkout("Rohit", "", "700001", false);
    }

    // 8️⃣ EMPTY ZIP
    @Test(description = "Checkout with empty zip")
    public void checkoutEmptyZip() {
        login();
        checkout("Suresh", "Iyer", "", false);
    }

    // 9️⃣ NUMERIC NAME
    @Test(description = "Checkout with numeric first name")
    public void checkoutNumericFirstName() {
        login();
        checkout("1234", "Test", "500001", false);
    }

    // 🔟 VERY LONG ZIP
    @Test(description = "Checkout with long zip code")
    public void checkoutLongZip() {
        login();
        checkout("Ankit", "Patel", "123456789", false);
    }

    // ================= COMMON METHODS =================

    private void login() {
        new LoginPage().login("standard_user", "secret_sauce");
    }

    private void checkout(
            String firstName,
            String lastName,
            String zip,
            boolean expectSuccess) {

        InventoryPage inv = new InventoryPage();
        inv.addBackpack();
        inv.openCart();

        CartPage cart = new CartPage();
        cart.clickCheckout();

        CheckoutInfoPage info = new CheckoutInfoPage();
        info.fillInfo(firstName, lastName, zip);
        info.continueCheckout();

        if (expectSuccess) {
            CheckoutOverviewPage overview = new CheckoutOverviewPage();
            overview.finishCheckout();

            OrderSuccessPage success = new OrderSuccessPage();
            Assert.assertEquals(
                    success.getSuccessMessage(),
                    "Thank you for your order!"
            );
        }
    }
}
