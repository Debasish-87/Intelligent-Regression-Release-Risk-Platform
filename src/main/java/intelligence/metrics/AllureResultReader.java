package intelligence.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class AllureResultReader {

    private static final String ALLURE_RESULTS_PATH = "target/allure-results";

    public QualityMetrics calculateMetrics() {

        QualityMetrics metrics = new QualityMetrics();

        int total = 0;
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        File resultsDir = new File(ALLURE_RESULTS_PATH);

        //  SAFETY CHECK 1
        if (!resultsDir.exists() || !resultsDir.isDirectory()) {
            System.out.println("[WARN] Allure results directory not found.");
            return metrics;
        }

        File[] files = resultsDir.listFiles();

        //  SAFETY CHECK 2
        if (files == null || files.length == 0) {
            System.out.println("[WARN] No Allure result files found.");
            return metrics;
        }

        ObjectMapper mapper = new ObjectMapper();

        for (File file : files) {

            if (!file.getName().endsWith("-result.json")) {
                continue;
            }

            try {
                JsonNode root = mapper.readTree(file);

                //  SAFETY CHECK 3
                if (!root.has("status")) {
                    continue;
                }

                String status = root.get("status").asText();
                total++;

                switch (status.toLowerCase()) {
                    case "passed":
                        passed++;
                        break;
                    case "failed":
                        failed++;
                        break;
                    case "skipped":
                        skipped++;
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("[ERROR] Failed parsing: " + file.getName());
            }
        }

        metrics.setTotalTests(total);
        metrics.setPassed(passed);
        metrics.setFailed(failed);
        metrics.setSkipped(skipped);

        //  SAFETY CHECK 4 (divide-by-zero)
        metrics.setPassPercentage(
                total == 0 ? 0.0 : (passed * 100.0) / total
        );

        return metrics;
    }
}
