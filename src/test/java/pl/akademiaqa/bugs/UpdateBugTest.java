package pl.akademiaqa.bugs;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class UpdateBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";
    private static Faker faker;
    private String randomEmployeeId;
    private String randomDescription;
    private JSONObject bug;

    @BeforeAll
    static void BeforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    void BeforeEach(){
        bug = new JSONObject();
        randomEmployeeId = faker.internet().uuid();
        randomDescription = faker.book().genre();
    }

    @Test
    void updateEmployeePutTest() {
        bug.put("id", 2);
        bug.put("title", "Very slow response after creating new employee");
        bug.put("description", randomDescription);
        bug.put("employeeId", 5);
        bug.put("status", "closed");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .put(BASE_URL + "/2")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(randomDescription, json.getString("description"));
    }

    @Test
    void updateEmployeePatchTest(){

        bug.put("employeeId", randomEmployeeId);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .patch(BASE_URL + "/5")
                .then()
                .extract()
                .response();

        Assertions.assertEquals(200,response.getStatusCode());

        JsonPath json= response.jsonPath();
        Assertions.assertEquals(randomEmployeeId, json.getString("employeeId"));
    }
}
