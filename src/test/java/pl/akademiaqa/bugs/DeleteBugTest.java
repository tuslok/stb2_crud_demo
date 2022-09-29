package pl.akademiaqa.bugs;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class DeleteBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void deleteBugTest(){
        Response response = given()
                .when()
                .delete(BASE_URL + "/4")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200,response.getStatusCode());

    }

    @Test
    void deleteBugWithPathVariableTest(){

        Response response = given()
                .pathParams("id", 6)
                .when()
                .delete(BASE_URL + "/{id}");

        Assertions.assertEquals(200,response.getStatusCode());
    }

}
