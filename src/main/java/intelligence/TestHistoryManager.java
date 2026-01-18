package intelligence;

import com.fasterxml.jackson.core.type.TypeReference;
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

    private static final String HISTORY_DIR = "history";
    private static final String HISTORY_FILE = "test-history.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Load historical test execution data
     */
    public static Map<String, List<String>> loadHistory() {

        try {
            File file = getHistoryFile();

            if (!file.exists()) {
                return new HashMap<>();
            }

            return mapper.readValue(
                    file,
                    new TypeReference<Map<String, List<String>>>() {}
            );

        } catch (Exception e) {
            // ⚠️ NEVER break test execution due to history issues
            System.err.println("[QE 2.0] Unable to load test history. Skipping history read.");
            return new HashMap<>();
        }
    }

    /**
     * Update execution result for a test
     * IMPORTANT: testName must be CLASS NAME
     */
    public static synchronized void updateHistory(String testName, String result) {

        try {
            Map<String, List<String>> history = loadHistory();

            history.putIfAbsent(testName, new ArrayList<>());
            history.get(testName).add(result);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(getHistoryFile(), history);

        } catch (Exception e) {
            // ⚠️ CI SAFE: Do NOT fail build
            System.err.println(
                    "[QE 2.0] Failed to update test history for "
                            + testName + ". Skipping history write."
            );
        }
    }

    /**
     * Normalize test names to class-level identifiers
     */
    public static String normalizeTestName(String rawName) {

        if (rawName == null || rawName.isEmpty()) {
            return "UNKNOWN_TEST";
        }

        if (rawName.contains(".")) {
            return rawName.substring(rawName.lastIndexOf('.') + 1);
        }

        return rawName;
    }

    /**
     * Ensures history directory & file exist
     */
    private static File getHistoryFile() {

        File dir = new File(HISTORY_DIR);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new File(dir, HISTORY_FILE);
    }

    // 🔹 LOCAL DEBUG ONLY (OPTIONAL)
    public static void main(String[] args) {

        updateHistory(normalizeTestName("tests.ui.LoginTest"), "PASS");
        updateHistory(normalizeTestName("tests.ui.CheckoutFlowTests"), "FAIL");

        System.out.println(loadHistory());
    }
}
