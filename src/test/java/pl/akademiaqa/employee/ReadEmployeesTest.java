package pl.akademiaqa.employee;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class ReadEmployeesTest {

    private static final String BASE_URL = "http://localhost:3000/employees";
    @Test
    void readAllEmployeesTest() {

        Response response = given()
                .when()
                .get(BASE_URL);

        /*Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        List<String> firstName = json.getList("firstName");
        Assertions.assertTrue(firstName.size() > 0);*/

        //Jeśli chcesz zobaczć samo body
        //System.out.println(response.asString());
        //response.prettyPrint();

        //Jeśli chcesz zobaczyć całego response z body, nagłówkami itd
        //response.prettyPeek();
    }

    @Test
    void readOneEmployeeTest() {
        Response response = given()
                .when()
                .get(BASE_URL + "/1");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void readOneEmployeeV2Test(){
        given()
                .when()
                .get(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .body("firstName", Matchers.equalTo("Bartek"))
                .body("lastName", Matchers.equalTo("Czarny"))
                .body("username", Matchers.equalTo("bczarny"))
                .body("email", Matchers.equalTo("bczarny@testerprogramuje.pl"));
    }

    @Test
    void readOneUserWithPathVariableTest() {
        Response response = given()
                .pathParams("id", 1)
                .when()
                .get(BASE_URL + "/{id}");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getString("firstName"));
        Assertions.assertEquals("Czarny", json.getString("lastName"));
        Assertions.assertEquals("bczarny", json.getString("username"));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getString("email"));
    }

    @Test
    void readOneEmployeesWithQueryParamsTest(){
        Response response = given()
                .queryParams("firstName", "Bartek")
                .when()
                .get(BASE_URL);

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Bartek", json.getList("firstName").get(0));
        Assertions.assertEquals("Czarny", json.getList("lastName").get(0));
        Assertions.assertEquals("bczarny", json.getList("username").get(0));
        Assertions.assertEquals("bczarny@testerprogramuje.pl", json.getList("email").get(0));
    }
}
