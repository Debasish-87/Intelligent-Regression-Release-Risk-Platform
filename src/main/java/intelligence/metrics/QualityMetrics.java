package intelligence.metrics;

public class QualityMetrics {

    private int totalTests;
    private int passed;
    private int failed;
    private int skipped;
    private double passPercentage;
    private int criticalFailures;


    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public double getPassPercentage() {
        return passPercentage;
    }

    public void setPassPercentage(double passPercentage) {
        this.passPercentage = passPercentage;
    }

    public int getCriticalFailures() {
        return criticalFailures;
    }

    public void setCriticalFailures(int criticalFailures) {
        this.criticalFailures = criticalFailures;
    }

}
