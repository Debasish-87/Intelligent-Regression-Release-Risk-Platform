import intelligence.metrics.AllureResultReader;
import intelligence.metrics.QualityMetrics;
import org.testng.annotations.Test;

public class QualityMetricsTest {

    @Test
    public void verifyQualityMetrics() {

        AllureResultReader  reader = new AllureResultReader();
        QualityMetrics metrics = new QualityMetrics();

        System.out.println("TOTAL: " + metrics.getTotalTests());
        System.out.println("PASSED: " + metrics.getPassed());
        System.out.println("FAILED: " + metrics.getFailed());
        System.out.println("PASS %: " + metrics.getPassPercentage());

    }
}
