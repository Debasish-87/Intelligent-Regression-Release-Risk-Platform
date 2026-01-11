package intelligence.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class CriticalTestAnalyzer {

    private static final String ALLURE_RESULTS_PATH = "allure-results";

    public int countCriticalFailures() {
        int criticalFailures = 0;
        ObjectMapper mapper = new ObjectMapper();

        File dir = new File(ALLURE_RESULTS_PATH);

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith("-result.json")) {
                try {
                    JsonNode root = mapper.readTree(file);

                    if (!root.has("labels")) continue;

                    boolean isCritical = false;
                    for (JsonNode label : root.get("labels")) {
                        if ("tag".equals(label.get("name").asText())
                                && "Critical".equalsIgnoreCase(label.get("value").asText())) {
                            isCritical = true;
                            break;
                        }
                    }

                    String status = root.get("status").asText();
                    if (isCritical && "failed".equalsIgnoreCase(status)) {
                        criticalFailures++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return criticalFailures;
    }
}
