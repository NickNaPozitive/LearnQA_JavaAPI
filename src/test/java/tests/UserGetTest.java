package tests;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Description;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.apache.http.cookie.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testGetUserDataNotAuth() {
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstname");
        Assertions.assertJsonHasNotField(responseUserData, "lastname");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }

    @Test
    @Owner(value = "Дегтярёв Никита Витальевич")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] expectedFields = {"username", "firstName", "lastName", "email"};
        Assertions.assertJsonHasFields(responseUserData, expectedFields);
    }

    @Test
    @Owner(value = "Дегтярёв Никита Витальевич")
    @Severity(value = SeverityLevel.TRIVIAL)
    @Description("This test tries to get user's info with auth as another user")
    @DisplayName("Test positive get user's info with auth as another user")
    public void testGetUserDetailsAuthAsAnotherUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        String header = getHeader(responseGetAuth, "x-csrf-token");
        String cookie = getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/1", header, cookie);

        String[] expectedFields = {"firstName", "lastName", "email"};

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotFields(responseUserData, expectedFields);

        responseUserData.prettyPrint();

    }

}
