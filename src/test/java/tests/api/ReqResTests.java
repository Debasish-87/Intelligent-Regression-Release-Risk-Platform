package tests.api;

import api.ReqResService;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utils.JsonUtils;
import org.testng.SkipException;

import java.util.Map;

/**
 * API Tests using Service Layer
 * External API (ReqRes) → can return 403
 * 403 treated as SKIP, not failure
 */
@Test(groups = {"API", "Critical"})
public class ReqResTests {

    ReqResService service = new ReqResService();

    // 🔹 Centralized safe assertion
    private void assertOrSkip(Response res, int expectedStatus, String message) {
        int status = res.statusCode();
        if (status == 403) {
            throw new SkipException("ReqRes API blocked (403) – skipping test");
        }
        Assert.assertEquals(status, expectedStatus, message);
    }

    @Test(description = "Create User API Test")
    public void createUserTest() {

        Map<String, Object> requestBody =
                JsonUtils.readJson("src/test/resources/testdata/createUser.json");

        Response res = service.createUser(requestBody);

        assertOrSkip(res, 201, "User creation failed");

        Assert.assertNotNull(
                res.jsonPath().getString("id"),
                "User ID is null"
        );

        Assert.assertEquals(
                res.jsonPath().getString("name"),
                requestBody.get("name"),
                "User name mismatch"
        );
    }

    @Test(description = "Get User API Test")
    public void getUserTest() {

        Response res = service.getUser(2);

        assertOrSkip(res, 200, "Get user failed");

        Assert.assertNotNull(
                res.jsonPath().get("data.id"),
                "User ID missing in response"
        );
    }
}
