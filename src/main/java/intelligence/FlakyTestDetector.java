package intelligence;

import java.util.*;

public class FlakyTestDetector {

    private static final int MIN_RUNS = 3;
    private static final int FLAKY_FAIL_THRESHOLD = 2;

    public static boolean isFlaky(List<String> history) {

        if (history == null || history.size() < MIN_RUNS) {
            return false;
        }

        int failCount = 0;
        int passCount = 0;

        for (String result : history) {
            if ("FAIL".equalsIgnoreCase(result)) {
                failCount++;
            }
            if ("PASS".equalsIgnoreCase(result)) {
                passCount++;
            }
        }

        return failCount >= FLAKY_FAIL_THRESHOLD && passCount >= 1;
    }

    public static Map<String, Boolean> detectFlakyTests(
            Map<String, List<String>> testHistory) {

        Map<String, Boolean> flakyMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : testHistory.entrySet()) {
            flakyMap.put(
                    entry.getKey(),
                    isFlaky(entry.getValue())
            );
        }
        return flakyMap;
    }


    public static void main(String[] args) {

        Map<String, List<String>> history = new HashMap<>();

        history.put("LoginTest",
                Arrays.asList("PASS", "FAIL", "PASS", "FAIL"));

        history.put("CheckoutTest",
                Arrays.asList("PASS", "PASS", "PASS"));

        history.put("PaymentTest",
                Arrays.asList("FAIL", "FAIL", "FAIL"));

        Map<String, Boolean> flakyTests =
                detectFlakyTests(history);

        flakyTests.forEach((test, isFlaky) ->
                System.out.println(test + " → FLAKY: " + isFlaky)
        );
    }

}
