import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;





public class Ex_7 {

    @Test
    public void testRedirect() {

        boolean doRequest = true;
        String Url = "https://playground.learnqa.ru/api/long_redirect";
        int counterOfRedirects = 0;


        while (doRequest) {
            Response result = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(Url)
                    .andReturn();

            if (result.getHeader("Location") != null) {
                Url = result.getHeader("Location");
                System.out.println(Url);
                counterOfRedirects++;
            } else {
                doRequest = false;

            }
        }
        System.out.println(counterOfRedirects);
    }
}