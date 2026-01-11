package intelligence.decision;

import intelligence.metrics.AllureResultReader;
import intelligence.metrics.CriticalTestAnalyzer;
import intelligence.metrics.QualityMetrics;

public class ReleaseDecisionEngine {

    private static final double PASS_THRESHOLD = 95.0;

    public void evaluateRelease() {

        AllureResultReader reader = new AllureResultReader();
        QualityMetrics metrics = reader.calculateMetrics();

        CriticalTestAnalyzer criticalAnalyzer = new CriticalTestAnalyzer();
        int criticalFailures = criticalAnalyzer.countCriticalFailures();

        System.out.println("\n===== QUALITY SUMMARY =====");
        System.out.println("Total Tests     : " + metrics.getTotalTests());
        System.out.println("Passed          : " + metrics.getPassed());
        System.out.println("Failed          : " + metrics.getFailed());
        System.out.println("Pass %          : " + metrics.getPassPercentage());
        System.out.println("Critical Fails  : " + criticalFailures);

        boolean releaseAllowed =
                metrics.getPassPercentage() >= PASS_THRESHOLD
                        && criticalFailures == 0;

        System.out.println("\n===== RELEASE DECISION =====");
        if (releaseAllowed) {
            System.out.println("✅ GO – Release Approved");
        } else {
            System.out.println("❌ NO-GO – Release Blocked");
            System.exit(1); //  CI FAIL
        }
    }
}
