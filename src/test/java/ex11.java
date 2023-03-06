import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ex11 extends BaseTestCase {
    String url = "https://playground.learnqa.ru/api/homework_cookie";

    @Test
    public void goodTestHeader() {
        Response response = RestAssured
                .get(url)
                .andReturn();
        Map<String, String> cookies = response.getCookies();

        System.out.println(response.getCookies());

        Response response2 = RestAssured
                .get(url)
                .andReturn();

        assertFalse(response2.getCookies().isEmpty(), "Куки пусты");
        assertEquals(cookies, response2.getCookies(), "Куки не совпадают");

        }
}
