//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class NewTest {
//    @Test
//    public void get_secret_password_homework() {
//
//        boolean doRequest = true;
//        String urlAuth = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
//        String urlCheckCookie = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
//
//        int counterOfRedirects = 0;
//
//        String[] pass = {"123456789","password", "password", "123456", "123456", "123456", "123456", "123456", "123456", "123456",
//                "123456", "123456", "password", "password", "password", "password", "password", "password", "123456789"};
//
//        Map<String, String> data = new HashMap<>();
//        data.put("login", "super_admin");
//        data.put("password", pass[0]);
//        System.out.println(data);
//
//
//        Response result = RestAssured
//                .given()
//                .body(data)
//                .when()
//                .post(urlAuth)
//                .andReturn();
//        result.prettyPrint();
//
//        String responseCookie = result.getCookie("auth_cookie");
//        Map<String, String> cookies = new HashMap<>();
//        cookies.put("auth_cookie", responseCookie);
//
//        System.out.println(responseCookie);
//
//        Response authCookie = RestAssured
//                .given()
//                .body(data)
//                .cookies(cookies)
//                .when()
//                .patch(urlCheckCookie)
//                .andReturn();
//        authCookie.print();
//
//
//
//
//    }
//}

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewTest {
    @Test
    public void get_secret_password_homework() {

        boolean doRequest = true;
        String urlAuth = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        String urlCheckCookie = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";


        Map<String, String> data = new HashMap<>();
        data.put("login", "super_admin");
        data.put("password", "welcome");


        Response result = RestAssured
                .given()
                .body(data)
                .when()
                .post(urlAuth)
                .andReturn();
        result.prettyPrint();

        String responseCookie = result.getCookie("auth_cookie");
        Map<String, String> cookies = new HashMap<>();
        cookies.put("auth_cookie", responseCookie);

        System.out.println(responseCookie);

        Response authCookie = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .patch(urlCheckCookie)
                .andReturn();
        authCookie.print();


    }
}



