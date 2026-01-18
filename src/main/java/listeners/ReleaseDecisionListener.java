package listeners;

import intelligence.FlakyTestDetector;
import intelligence.ReleaseDecisionEngine;
import intelligence.ReleaseSummaryReporter;
import intelligence.RiskScoreCalculator;
import intelligence.TestHistoryManager;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * QE 2.0 – Suite level Release Decision Listener
 * Creates a synthetic Allure test to attach final release summary
 */
public class ReleaseDecisionListener implements ISuiteListener {

    @Override
    public void onFinish(ISuite suite) {

        // ===============================
        // 1️⃣ LOAD TEST HISTORY
        // ===============================
        Map<String, List<String>> history =
                TestHistoryManager.loadHistory();

        // ===============================
        // 2️⃣ DETECT FLAKY TESTS
        // ===============================
        Map<String, Boolean> flakyMap =
                FlakyTestDetector.detectFlakyTests(history);

        // ===============================
        // 3️⃣ CALCULATE RISK SCORES
        // ===============================
        Map<String, Integer> riskScores =
                RiskScoreCalculator.calculateRiskScores(
                        history, flakyMap
                );

        // ===============================
        // 4️⃣ COLLECT SKIPPED TEST COUNT
        // ===============================
        int skippedTests =
                suite.getResults().values().stream()
                        .mapToInt(r -> r.getTestContext()
                                .getSkippedTests().size())
                        .sum();

        // ===============================
        // 5️⃣ FINAL RELEASE DECISION
        // ===============================
        ReleaseDecisionEngine.Decision decision =
                ReleaseDecisionEngine.decideRelease(
                        riskScores, skippedTests
                );

        // ===============================
        // 6️⃣ CREATE SYNTHETIC ALLURE TEST
        // ===============================
        String uuid = UUID.randomUUID().toString();

        Status allureStatus;
        switch (decision) {
            case NO_GO:
                allureStatus = Status.FAILED;
                break;
            case HOLD:
                allureStatus = Status.BROKEN;
                break;
            default:
                allureStatus = Status.PASSED;
        }

        TestResult releaseTest = new TestResult()
                .setUuid(uuid)
                .setName("🚦 Release Decision")
                .setFullName("QE 2.0 – Final Release Decision")
                .setStatus(allureStatus);

        Allure.getLifecycle().scheduleTestCase(releaseTest);
        Allure.getLifecycle().startTestCase(uuid);

        // ===============================
        // 7️⃣ ATTACH RELEASE SUMMARY
        // ===============================
        ReleaseSummaryReporter.attachReleaseSummary(
                riskScores, decision
        );

        // ===============================
        // 8️⃣ FINISH SYNTHETIC TEST
        // ===============================
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);

        // ===============================
        // 9️⃣ CONSOLE VISIBILITY
        // ===============================
        System.out.println("\n===== FINAL RELEASE DECISION =====");
        riskScores.forEach((t, r) ->
                System.out.println(t + " → Risk: " + r));
        System.out.println("Skipped Tests → " + skippedTests);
        System.out.println("Decision → " + decision);
    }
}
