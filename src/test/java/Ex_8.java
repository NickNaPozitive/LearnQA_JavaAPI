import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Ex_8 {

    @Test
    public void testRedirect() throws InterruptedException {
        String Url = "https://playground.learnqa.ru/ajax/api/longtime_job";


        JsonPath result = RestAssured
                .given()
                .get(Url)
                .jsonPath();
        result.prettyPrint();


        String responseCookie = result.get("token");
        int responseSeconds = result.get("seconds");
        String data = "?" + "token" + "=" + responseCookie;


        Response checkBefore = RestAssured
                .given()
                .get(Url + data)
                .andReturn();
        checkBefore.prettyPrint();

        Thread.sleep(responseSeconds * 1000);

        Response checkAfter = RestAssured
                .given()
                .get(Url + data)
                .andReturn();
        checkAfter.prettyPrint();


    }
}

