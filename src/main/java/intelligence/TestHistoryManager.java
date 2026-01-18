package intelligence;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

/**
 * Manages execution history of tests
 * Used by FlakyTestDetector & RiskScoreCalculator
 *
 * QE 2.0 – Quality Intelligence Layer
 */
public class TestHistoryManager {

    private static final String HISTORY_PATH = "history/test-history.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Load historical test execution data
     */
    public static Map<String, List<String>> loadHistory() {
        try {
            File file = new File(HISTORY_PATH);

            if (!file.exists()) {
                return new HashMap<>();
            }

            return mapper.readValue(file, Map.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test history", e);
        }
    }

    /**
     * Update execution result for a test
     * IMPORTANT: testName must be CLASS NAME (not method name)
     */
    public static synchronized void updateHistory(String testName, String result) {
        try {
            Map<String, List<String>> history = loadHistory();

            history.putIfAbsent(testName, new ArrayList<>());
            history.get(testName).add(result);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(HISTORY_PATH), history);

        } catch (Exception e) {
            throw new RuntimeException("Failed to update test history", e);
        }
    }

    /**
     * Utility method to normalize test names
     * Ensures only class-level names are used
     */
    public static String normalizeTestName(String rawName) {

        if (rawName == null || rawName.isEmpty()) {
            return "UNKNOWN_TEST";
        }

        // If fully qualified class name is passed
        if (rawName.contains(".")) {
            return rawName.substring(rawName.lastIndexOf('.') + 1);
        }

        return rawName;
    }

    // 🔹 TEMP MAIN (OPTIONAL – FOR LOCAL DEBUG ONLY)
    public static void main(String[] args) {

        updateHistory(normalizeTestName("tests.ui.LoginTest"), "PASS");
        updateHistory(normalizeTestName("tests.ui.CheckoutFlowTests"), "FAIL");

        System.out.println(loadHistory());
    }
}
