package intelligence;

import java.util.Map;

/**
 * Determines final release decision based on
 * aggregated risk scores.
 *
 * QE 2.0 – Release Governance Engine
 */
public class ReleaseDecisionEngine {

    public enum Decision {
        GO,
        HOLD,
        NO_GO
    }

    /**
     * FINAL DECISION LOGIC
     *
     * Rules:
     * 1️⃣ Any CRITICAL API test risk >= 6        → NO_GO
     * 2️⃣ Any NON-API test risk >= 7            → NO_GO
     * 3️⃣ Average risk >= 5                     → HOLD
     * 4️⃣ Skipped tests present (no failures)   → HOLD
     * 5️⃣ Otherwise                             → GO
     */
    public static Decision decideRelease(
            Map<String, Integer> riskScores,
            int skippedTests) {

        // Rule 1 & 2: Hard blockers
        for (Map.Entry<String, Integer> entry : riskScores.entrySet()) {

            String testName = entry.getKey();
            int risk = entry.getValue();

            // API tests are more critical
            if (testName.toLowerCase().contains("api") && risk >= 6) {
                return Decision.NO_GO;
            }

            // Any very high risk blocks release
            if (risk >= 7) {
                return Decision.NO_GO;
            }
        }

        // Rule 3: Average risk check
        double avgRisk = riskScores.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        if (avgRisk >= 5) {
            return Decision.HOLD;
        }

        // Rule 4: Skipped tests downgrade confidence
        if (skippedTests > 0) {
            System.out.println(
                    "⚠ Skipped tests detected: " + skippedTests +
                            " → Release put on HOLD for review"
            );
            return Decision.HOLD;
        }

        // Rule 5: Safe to release
        return Decision.GO;
    }

    // 🔹 Local validation (manual run only)
    public static void main(String[] args) {

        Map<String, Integer> riskScores = Map.of(
                "LoginTest", 4,
                "CheckoutFlowTests", 1,
                "ReqResApiTests", 3,
                "ReqResTests", 2
        );

        int skippedTests = 5;

        Decision decision = decideRelease(riskScores, skippedTests);

        System.out.println("\n===== RELEASE DECISION SUMMARY =====");
        riskScores.forEach((t, r) ->
                System.out.println(t + " → Risk: " + r));
        System.out.println("Skipped Tests → " + skippedTests);
        System.out.println("-----------------------------------");
        System.out.println("FINAL DECISION → " + decision);
    }
}
