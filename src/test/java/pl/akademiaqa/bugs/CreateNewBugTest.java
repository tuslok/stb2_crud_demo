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

class CreateNewBugTest {

    private static final String BASE_URL = "http://localhost:3000/bugs";

    private static Faker faker;
    private String title;
    private String description;
    private String status;

    @BeforeAll
    static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    void beforeEach(){
        title = faker.book().title();
        description = faker.weather().description();
        status = faker.demographic().maritalStatus();
    }

    @Test
    void createNewBugTest(){
        JSONObject bug = new JSONObject();
        bug.put("title", title);
        bug.put("description", description);
        bug.put("employeeId", "2");
        bug.put("status", status);

        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(bug.toString())
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();

        Assertions.assertEquals(201,response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(title, json.getString("title"));
        Assertions.assertEquals(description, json.getString("description"));
        Assertions.assertEquals("2", json.getString("employeeId"));
        Assertions.assertEquals(status, json.getString("status"));
    }
}
