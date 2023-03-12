package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    private final String userUrl = "https://playground.learnqa.ru/api/user/";

    @Test
    @Description("Test create user and checks if`s already exists")
    @DisplayName("Negative test for creating a profile")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Test
    @Description("Test create user and checks if`s succeed")
    @DisplayName("Positive test for creating a profile")
    public void testCreateUserSuccessfully() {

        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @Test
    @Description("Test create user with invalid email")
    @DisplayName("Positive test for creating a profile with invalid email")
    public void testCreateUserWithWrongEmail() {
        String email = DataGenerator.getRandomEmail().replace("@", "");

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    @ParameterizedTest
    @CsvSource(value = {
            " :MaxPayne:Max:Payne",
            "312123: :Mass:Effect",
            "999999:JoSh: :Shepard",
            "33333:ResidentEvil:Leon: "
    }, delimiter = ':')
    @Description("This test creates an account without one of the parameters and if the test " +
            "passes without assert - registration is impossible without one of the parameters")
    @DisplayName("Test for creating a profile without some parameter or parameters")
    public void testCreateUserWithoutSomeParameters(String password, String userName, String firstName, String lastName) {
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("username", userName);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);

        Response responseWithOneEmptyParameter = apiCoreRequests.makePostRequest(userUrl, userData);
        Assertions.assertResponseCodeEquals(responseWithOneEmptyParameter, 400);

    }

    @Test
    @Description("This test tries to create a new user with one symbol userName")
    @DisplayName("Test negative create user with one symbol userName")
    public void testCreateUserWithOneSymbolUserName() {
        Map<String, String> userData = new HashMap<>();
        userData.put("username", "N");
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUserWithOneSymbolUserName = apiCoreRequests
                .makePostRequest(userUrl, userData);

        Assertions.assertResponseCodeEquals(responseCreateUserWithOneSymbolUserName, 400);
        Assertions.assertResponseTextEquals(responseCreateUserWithOneSymbolUserName,
                "The value of 'username' field is too short");
    }

    @Test
    @Description("This test tries to create a new user with 251 symbol userName")
    @DisplayName("Test negative create user with 251 symbol userName")
    public void testCreateUserWithLongUserName251Characters() {
        String generatedUserName = RandomStringUtils.random(251, true, true);
        Map<String, String> userData = new HashMap<>();
        userData.put("username", generatedUserName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateUserWith251SymbolUserName = apiCoreRequests
                .makePostRequest(userUrl, userData);

        Assertions.assertResponseCodeEquals(responseCreateUserWith251SymbolUserName, 400);
        Assertions.assertResponseTextEquals(responseCreateUserWith251SymbolUserName,
                "The value of 'username' field is too long");
    }
}