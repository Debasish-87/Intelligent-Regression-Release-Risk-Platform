package listeners;

import base.DriverManager;
import intelligence.TestHistoryManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener
 * - Updates test execution history (CLASS LEVEL ONLY)
 * - Attaches screenshots & error logs to Allure
 *
 * QE 2.0 – Automation + Intelligence
 */
public class TestListener implements ITestListener {

    // ================= SCREENSHOT =================

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] takeScreenshot() {
        if (DriverManager.getDriver() == null) {
            return new byte[0];
        }
        return ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Error Details", type = "text/plain")
    public static String attachErrorLog(String message) {
        return message;
    }

    // ================= TEST EVENTS =================

    @Override
    public void onTestStart(ITestResult result) {
        Allure.step("Starting Test Method: "
                + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        // ✅ CLASS NAME ONLY (IMPORTANT)
        String testName =
                result.getTestClass()
                        .getRealClass()
                        .getSimpleName();

        // 1️⃣ Update history
        TestHistoryManager.updateHistory(testName, "PASS");

        // 2️⃣ Allure step (method name ok for readability)
        Allure.step("Test Passed: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {

        // ✅ CLASS NAME ONLY (IMPORTANT)
        String testName =
                result.getTestClass()
                        .getRealClass()
                        .getSimpleName();

        // 1️⃣ Update history
        TestHistoryManager.updateHistory(testName, "FAIL");

        Allure.step("Test Failed: " + testName);

        // 2️⃣ Screenshot
        takeScreenshot();

        // 3️⃣ Error attachment
        String errorMsg = result.getThrowable() != null
                ? result.getThrowable().toString()
                : "Unknown Error";
        attachErrorLog(errorMsg);
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        // ✅ CLASS NAME ONLY (IMPORTANT)
        String testName =
                result.getTestClass()
                        .getRealClass()
                        .getSimpleName();

        TestHistoryManager.updateHistory(testName, "SKIPPED");

        Allure.step("Test Skipped: " + testName);
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n===== Test Execution Started =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("===== Test Execution Finished =====\n");
    }
}
