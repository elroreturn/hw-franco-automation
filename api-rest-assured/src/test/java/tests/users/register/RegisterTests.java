package tests.users.register;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.FakeUser.createFakeUser;
import static utils.deserializer.UserDeserializer.userDeserializer;

public class RegisterTests extends BaseTest {

    @Test
    public void registerTest() {

        // Arrange
        User fakeUser = createFakeUser();

        JSONObject parameters = new JSONObject();
        parameters.put("name", fakeUser.getName());
        parameters.put("surname", fakeUser.getSurname());
        parameters.put("username", fakeUser.getUsername());
        parameters.put("email", fakeUser.getEmail());
        parameters.put("password", "password");

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(parameters.toString())
                .log().all();

        // Act
        Response response = request.when()
                .post(System.getProperty("SERVER_URL") + "/api/register")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/register/userRegistration.json"));

        // Extra: Deserialize
        JsonPath userJPath = response.then().extract().body().jsonPath();
        User newUser = userDeserializer(userJPath);

        System.out.println(newUser.toString());
    }
}
