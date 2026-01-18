package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginTest extends BaseTest {

    private static final Logger log =
            LoggerUtil.getLogger(LoginTest.class);

    // ================= DATA PROVIDER =================

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return ExcelUtils.getSheetData(
                "src/test/resources/testdata/login_data_clean.xlsx",
                "LoginData"
        );
    }

    // ================= TEST =================

    @Test(
            dataProvider = "loginData",
            groups = {"Regression", "Critical"},
            description = "Login with multiple datasets from Excel"
    )
    @Severity(SeverityLevel.BLOCKER)
    public void loginTestDDT(
            String username,
            String password,
            String expectedResult) {

        log.info("🔐 Login Test Started | User: {} | Expected: {}",
                username, expectedResult);

        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage();
        boolean isLoggedIn = inventoryPage.isUserLoggedIn();

        boolean shouldLoginSucceed =
                "success".equalsIgnoreCase(expectedResult);

        // ================= ASSERTION =================

        if (shouldLoginSucceed) {

            log.info("✅ Verifying successful login...");
            Assert.assertTrue(
                    isLoggedIn,
                    "Expected login to succeed for user: " + username
            );

        } else {

            log.info("❌ Verifying login failure...");
            Assert.assertFalse(
                    isLoggedIn,
                    "Expected login to fail for user: " + username
            );
        }

        log.info("🏁 Login Test Finished for user: {}", username);
    }
}
