package intelligence.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class AllureResultReader {

    private static final String ALLURE_RESULTS_PATH  = "allure_result";

    public QualityMetrics calculateMetrics() {
        QualityMetrics metrics = new QualityMetrics();

        int total = 0;
        int passed = 0;
        int failed = 0;
        int skipped = 0;


        ObjectMapper mapper = new ObjectMapper();
        File resultsDir = new File(ALLURE_RESULTS_PATH);

        for  (File file : resultsDir.listFiles()) {
            if(file.getName().endsWith("-result.json")) {
                try {
                    JsonNode root = mapper.readTree(file);
                    String status = root.get("status").asText();

                    total++;

                    switch(status) {

                        case "passed":
                            passed++;
                            break;
                        case "failed":
                            failed++;
                            break;
                        case "skipped":
                            skipped++;
                            break;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        metrics.setTotalTests(total);
        metrics.setPassed(passed);
        metrics.setFailed(failed);
        metrics.setSkipped(skipped);
        metrics.setPassPercentage((passed * 100.0) / total);

        return metrics;
    }
}
