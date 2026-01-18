package intelligence;

import java.util.*;

/**
 * Calculates risk score for each test based on:
 * 1. Historical failures
 * 2. Flakiness
 * 3. Business criticality (base risk)
 *
 * QE 2.0 – Quality Intelligence Engine
 */
public class RiskScoreCalculator {

    /**
     * BUSINESS BASE RISK (static for now – interview friendly)
     * Can be replaced by metadata.json later
     */
    private static final Map<String, Integer> BASE_RISK = Map.of(
            "LoginTest", 7,
            "CheckoutFlowTests", 1,
            "CheckoutTest", 1,
            "PaymentTest", 10
    );

    /**
     * Calculate risk for a single test
     */
    public static int calculateRisk(
            String testName,
            List<String> history,
            boolean isFlaky) {

        // 1️⃣ Start with business base risk
        int risk = BASE_RISK.getOrDefault(testName, 1);

        if (history == null || history.isEmpty()) {
            return risk;
        }

        long failCount = history.stream()
                .filter(r -> "FAIL".equalsIgnoreCase(r))
                .count();

        // 2️⃣ All runs failed → MAX RISK
        if (failCount == history.size()) {
            return 10;
        }

        // 3️⃣ Flaky tests → elevate risk
        if (isFlaky && risk < 7) {
            risk = 7;
        }

        // 4️⃣ Mostly failing → medium-high risk
        if (failCount > 0 && failCount >= history.size() / 2) {
            risk = Math.max(risk, 5);
        }

        return risk;
    }

    /**
     * Calculate risk scores for all tests
     */
    public static Map<String, Integer> calculateRiskScores(
            Map<String, List<String>> history,
            Map<String, Boolean> flakyMap) {

        Map<String, Integer> riskScores = new LinkedHashMap<>();

        for (String testName : history.keySet()) {

            int risk = calculateRisk(
                    testName,
                    history.get(testName),
                    flakyMap.getOrDefault(testName, false)
            );

            riskScores.put(testName, risk);
        }

        return riskScores;
    }

    // 🔹 LOCAL VALIDATION (OPTIONAL)
    public static void main(String[] args) {

        Map<String, List<String>> history = new HashMap<>();

        history.put("LoginTest",
                Arrays.asList("PASS", "FAIL", "PASS", "FAIL"));

        history.put("CheckoutTest",
                Arrays.asList("PASS", "PASS", "PASS"));

        history.put("PaymentTest",
                Arrays.asList("FAIL", "FAIL", "FAIL"));

        Map<String, Boolean> flakyMap =
                FlakyTestDetector.detectFlakyTests(history);

        Map<String, Integer> riskScores =
                calculateRiskScores(history, flakyMap);

        System.out.println("===== RISK SCORE OUTPUT =====");
        riskScores.forEach((test, score) ->
                System.out.println(test + " → RISK SCORE: " + score)
        );
    }
}
