package tests.users.editUser;

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

public class EditUserTests extends BaseTest {

    @Test(testName = "User is able to update his/her personal information")
    public void updateUserTest() {

        // Arrange
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");
        User user = new GeneralPreconditions().getUserFromLogin(testUser);

        user.setSurname("Antognazza");
        user.setName("Franco");

        System.out.println(user.toString());

        RequestSpecification request = given()
                .baseUri(System.getProperty("SERVER_URL"))
                .contentType(ContentType.JSON)
                .header("Authorization", user.getJwt())
                .body(user)
                .log().all();

        // Act
        Response response = request.when()
                .put("/api/user")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/user/editUser.json"));

        JsonPath userJPath = response.then().extract().body().jsonPath();
        User newUser = userDeserializer(userJPath);

        assertEquals(user, newUser, "Users are different");
    }
}
