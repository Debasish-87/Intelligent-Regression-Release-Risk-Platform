package intelligence;

import io.qameta.allure.Allure;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Generates FINAL RELEASE DECISION SUMMARY
 * and attaches it safely to Allure
 */
public class ReleaseSummaryReporter {

    public static void attachReleaseSummary(
            Map<String, Integer> riskScores,
            ReleaseDecisionEngine.Decision decision) {

        StringBuilder summary = new StringBuilder();

        // ===============================
        // SECTION 1: RELEASE DECISION
        // ===============================
        summary.append("===== RELEASE DECISION SUMMARY =====\n");

        for (Map.Entry<String, Integer> entry : riskScores.entrySet()) {
            summary.append(entry.getKey())
                    .append(" → Risk: ")
                    .append(entry.getValue())
                    .append("\n");
        }

        summary.append("-----------------------------------\n");
        summary.append("FINAL DECISION → ")
                .append(decision)
                .append("\n\n");

        // ===============================
        // SECTION 2: RISK SCORE DETAILS
        // ===============================
        for (Map.Entry<String, Integer> entry : riskScores.entrySet()) {
            summary.append(entry.getKey())
                    .append(" → RISK SCORE: ")
                    .append(entry.getValue())
                    .append("\n");
        }

        // ===============================
        // ALLURE ATTACHMENT (SAFE)
        // ===============================
        Allure.getLifecycle().addAttachment(
                "🚦 Release Decision Summary",
                "text/plain",
                ".txt",
                summary.toString().getBytes(StandardCharsets.UTF_8)
        );
    }
}
