package tests.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.SkipException;

/**
 * API Tests – ReqRes
 * External public API → can return 403
 * 403 is treated as SKIP (not failure)
 */
@Test(groups = {"API", "Critical"})
public class ReqResApiTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    // 🔹 Common safe assertion
    private void assertOrSkip(Response res, int expectedStatus) {
        int status = res.getStatusCode();
        if (status == 403) {
            throw new SkipException("ReqRes API blocked (403) – skipping test");
        }
        Assert.assertEquals(status, expectedStatus);
    }

    // 1️⃣ GET USERS
    @Test(description = "Get users list")
    public void getUsersTest() {
        Response res = RestAssured.get("/api/users?page=2");
        assertOrSkip(res, 200);
        Assert.assertTrue(res.asString().contains("Janet"));
    }

    // 2️⃣ GET SINGLE USER
    @Test(description = "Get single user")
    public void getSingleUserTest() {
        Response res = RestAssured.get("/api/users/2");
        assertOrSkip(res, 200);
        Assert.assertTrue(res.asString().contains("email"));
    }

    // 3️⃣ INVALID PAGE
    @Test(description = "Get users with invalid page")
    public void getUsersInvalidPage() {
        Response res = RestAssured.get("/api/users?page=999");
        assertOrSkip(res, 200);
    }

    // 4️⃣ USER NOT FOUND
    @Test(description = "Get user not found")
    public void getUserNotFound() {
        Response res = RestAssured.get("/api/users/23");
        assertOrSkip(res, 404);
    }

    // 5️⃣ CREATE USER
    @Test(description = "Create new user")
    public void createUserTest() {
        String body = "{ \"name\": \"john\", \"job\": \"tester\" }";

        Response res = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/api/users");

        assertOrSkip(res, 201);
        Assert.assertTrue(res.asString().contains("john"));
    }

    // 6️⃣ CREATE USER NEGATIVE
    @Test(description = "Create user with missing name")
    public void createUserMissingName() {
        String body = "{ \"job\": \"tester\" }";

        Response res = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/api/users");

        assertOrSkip(res, 201);
    }

    // 7️⃣ UPDATE USER (PUT)
    @Test(description = "Update user")
    public void updateUserTest() {
        String body = "{ \"name\": \"john\", \"job\": \"lead tester\" }";

        Response res = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .put("/api/users/2");

        assertOrSkip(res, 200);
        Assert.assertTrue(res.asString().contains("lead tester"));
    }

    // 8️⃣ PATCH USER
    @Test(description = "Patch user")
    public void patchUserTest() {
        String body = "{ \"job\": \"architect\" }";

        Response res = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .patch("/api/users/2");

        assertOrSkip(res, 200);
        Assert.assertTrue(res.asString().contains("architect"));
    }

    // 9️⃣ DELETE USER
    @Test(description = "Delete user")
    public void deleteUserTest() {
        Response res = RestAssured.delete("/api/users/2");
        assertOrSkip(res, 204);
    }

    // 🔟 LOGIN SUCCESS
    @Test(description = "Login successful")
    public void loginSuccessful() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response res = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/api/login");

        assertOrSkip(res, 200);
        Assert.assertTrue(res.asString().contains("token"));
    }

    // 1️⃣1️⃣ LOGIN FAILURE
    @Test(description = "Login unsuccessful")
    public void loginUnsuccessful() {
        String body = "{ \"email\": \"eve.holt@reqres.in\" }";

        Response res = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("/api/login");

        assertOrSkip(res, 400);
    }

    // 1️⃣2️⃣ DELAYED RESPONSE
    @Test(description = "Delayed response test")
    public void delayedResponseTest() {
        Response res = RestAssured.get("/api/users?delay=3");
        assertOrSkip(res, 200);
    }
}
