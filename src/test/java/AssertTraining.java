import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AssertTraining {

    @Test
    public void testBigString() {

        String bigString = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";

        assertTrue(bigString.length()<=15, "Длина строки больше 15");
    }

    @Test
    public void testTinyString() {

        String tinyString = "https://";

        assertTrue(tinyString.length()<=15, "Длина строки меньше 15");
    }
}
