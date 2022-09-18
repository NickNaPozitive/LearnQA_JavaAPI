import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class exNum5 {

    @Test
    public void testHelloWorld() {

        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        response.prettyPrint();

        ArrayList<Object> data = response.get("messages");
        System.out.println(data.get(1));
    }
}
