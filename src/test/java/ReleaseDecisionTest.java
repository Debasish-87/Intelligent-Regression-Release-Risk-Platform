import intelligence.decision.ReleaseDecisionEngine;
import org.testng.annotations.Test;

public class ReleaseDecisionTest {

    @Test
    public void evaluateReleaseDecision() {
        new ReleaseDecisionEngine().evaluateRelease();
    }
}
