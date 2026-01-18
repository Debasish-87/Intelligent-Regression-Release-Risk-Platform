package intelligence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * Loads test metadata used for
 * risk analysis & release decisions.
 *
 * QE 2.0 – Test Intelligence Layer
 */
public class TestMetadataReader {

    private static final String METADATA_FILE =
            "metadata/test-metadata.json";

    /**
     * Loads metadata JSON from classpath
     */
    public static Map<String, Map<String, Object>> loadMetadata() {

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream stream = TestMetadataReader.class
                    .getClassLoader()
                    .getResourceAsStream(METADATA_FILE);

            if (stream == null) {
                System.err.println(
                        "⚠️ Test metadata file not found: " + METADATA_FILE
                );
                return Collections.emptyMap();
            }

            return mapper.readValue(
                    stream,
                    new TypeReference<Map<String, Map<String, Object>>>() {}
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "❌ Failed to load test metadata", e
            );
        }
    }

    // 🔹 LOCAL DEBUG ONLY
    public static void main(String[] args) {

        Map<String, Map<String, Object>> metadata =
                loadMetadata();

        System.out.println("\n===== TEST METADATA LOADED =====");
        if (metadata.isEmpty()) {
            System.out.println("No metadata found.");
        } else {
            metadata.forEach((test, data) ->
                    System.out.println(test + " → " + data)
            );
        }
    }
}
