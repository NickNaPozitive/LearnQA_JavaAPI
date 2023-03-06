import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ex12 extends BaseTestCase {
    String url = "https://playground.learnqa.ru/api/homework_header2";

    String searchingHeaderValue = "Some secret value";
    String searchingHeader= "x-secret-homework-header";

    @Test
    public void goodTestHeader() {
        Response response = RestAssured
                .get(url)
                .andReturn();

        System.out.println(response.getHeaders());

        assertEquals(searchingHeaderValue, getHeader( response, searchingHeader));


    }
}
