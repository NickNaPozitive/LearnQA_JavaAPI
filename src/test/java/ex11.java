import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ex11 {
    String url = "https://playground.learnqa.ru/api/homework_cookie";
    String bad_url = "https://playground.learnqa.ru/api/homework_cookie2";

    @Test
    public void goodTestCookie() {
        Response response = RestAssured
                .get(url)
//                .get(bad_url)
                .andReturn();
        Map<String, String> cookies = response.getCookies();

        System.out.println(response.getCookies());

        assertEquals(cookies, response.getCookies(), "Куки пусты");


        }
}
