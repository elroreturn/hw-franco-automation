package tests.users.login;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.fileLoader.users.UsersCsv;
import utils.fileLoader.users.UsersLoader;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static utils.deserializer.UserDeserializer.userDeserializer;
import static utils.deserializer.UserDeserializer.userFromJWT;

public class LoginTests extends BaseTest {

    @Test
    public void simpleSuccessfulLoginTest() {

        // Arrange
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");

        JSONObject parameters = new JSONObject();
        parameters.put("email", testUser.getEmail());
        parameters.put("password", testUser.getPassword());

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(parameters.toString())
                .log().all();

        // Act
        Response response = request.when()
                .post(System.getProperty("SERVER_URL") + "/api/login")
                .prettyPeek();

        // Assert
        assertEquals(response.statusCode(), 200);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("jsonSchemas/login/simpleLogin.json"));

        // Extra: Deserialize
        JsonPath userJPath = response.then().extract().body().jsonPath();
        User newUser = userDeserializer(userJPath);

        assertEquals(newUser.getEmail(), testUser.getEmail());
        assertEquals(newUser.getRole(), "ROLE_USER");
        assertEquals(newUser.getName(), "Franco");
        assertEquals(newUser.getSurname(), "Antognazza");
        assertEquals(newUser.getImage(), "lganteke.jpeg");

        System.out.println(newUser.toString());
    }

    @Test
    public void tokenSuccessfulLoginTest() {

        // Arrange
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");

        JSONObject parameters = new JSONObject();
        parameters.put("email", testUser.getEmail());
        parameters.put("password", testUser.getPassword());
        parameters.put("getToken", "true");

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(parameters.toString())
                .log().all();

        // Act
        Response response = request.when()
                .post(System.getProperty("SERVER_URL") + "/api/login")
                .prettyPeek();

        // Assert
        assertEquals(response.statusCode(), 200);

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("jsonSchemas/login/tokenLogin.json"));

        // TODO: Extra: Deserialize From JWT
        String jwt = response.then().extract().path("token");
        User user = userFromJWT(jwt);

        System.out.println(user.toString());

    }
}
