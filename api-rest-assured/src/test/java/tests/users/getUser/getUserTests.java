package tests.users.getUser;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.Test;
import utils.GeneralPreconditions;
import utils.fileLoader.users.UsersCsv;
import utils.fileLoader.users.UsersLoader;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static utils.deserializer.UserDeserializer.userDeserializer;

public class getUserTests extends BaseTest {

    @Test(testName = "Get user information from UserId")
    public void getUserInformationTest() {

        // Arrange
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");
        User user = new GeneralPreconditions().getUserFromLogin(testUser);

        System.out.println(user.toString());

        RequestSpecification request = given()
                .baseUri(System.getProperty("SERVER_URL"))
                .pathParam("userId", user.get_id())
                .contentType(ContentType.JSON)
                .log().all();

        // Act
        Response response = request.when()
                .get("/api/user/{userId}")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/user/userInformation.json"));

        JsonPath userJPath = response.then().extract().body().jsonPath();
        User newUser = userDeserializer(userJPath);

        assertEquals(user, newUser, "Users are different");

    }
}
