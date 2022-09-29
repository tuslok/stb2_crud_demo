package pl.akademiaqa.bugs;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class ReadBugsTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    @Test
    void readAllBugsTest(){

        Response response = given()
                .when()
                .get(BASE_URL);

        Assertions.assertEquals(200,response.getStatusCode());
        response.prettyPeek();
    }

    @Test
    void readOneBug(){

        Response response = given()
                .when()
                .get(BASE_URL + "/2");

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Very slow response after creating new employee",json.getString("title"));
        Assertions.assertEquals("It takes 2 minuts to create new emploee via API",json.getString("description"));
        Assertions.assertEquals("5",json.getString("employeeId"));
        Assertions.assertEquals("open",json.getString("status"));
    }

    @Test
    void readOneBugWithPathVariableTest(){

        Response response = given()
                .pathParams("id", 4)
                .when()
                .get(BASE_URL + "/{id}");

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("I don't like to see n/a on as a company details.", json.getString("title"));
        Assertions.assertEquals("I don't see why we have to show empty company details when emploee type is uop. Can we hide company section?", json.getString("description"));
        Assertions.assertEquals("2", json.getString("employeeId"));
        Assertions.assertEquals("open", json.getString("status"));
        response.prettyPrint();
    }

    @Test
    void readOneBugWithQueryParamsTest(){

        Response response = given()
                .queryParams("employeeId", "5")
                .when()
                .get(BASE_URL);

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Very slow response after creating new employee",json.getList("title").get(0));
        Assertions.assertEquals("It takes 2 minuts to create new emploee via API",json.getList("description").get(0));
        Assertions.assertEquals(5,json.getList("employeeId").get(0));
        Assertions.assertEquals("open",json.getList("status").get(0));

        response.prettyPrint();
    }
}
