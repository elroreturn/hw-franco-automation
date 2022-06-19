package tests.users.uploadPhoto;

import baseTests.BaseTest;
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
import static org.testng.Assert.assertNotEquals;
import static utils.GeneralUtils.getFileFromPath;
import static utils.deserializer.UserDeserializer.userDeserializer;

public class UserPhotoUploadTests extends BaseTest {

    @Test(testName = "User is able to update his/her profile photo")
    public void uploadUserPhotoTest() {

        // Arrange
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");
        User user = new GeneralPreconditions().getUserFromLogin(testUser);

        System.out.println(user.toString());

        RequestSpecification request = given()
                .contentType("multipart/form-data")
                .header("Authorization", user.getJwt())
                .multiPart("file0", getFileFromPath("userAvatar.jpg"))
                .log().all();

        // Act
        Response response = request.when()
                .post(System.getProperty("SERVER_URL") + "/api/user/upload")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/login/simpleLogin.json"));

        JsonPath userJPath = response.then().extract().body().jsonPath();
        User newUser = userDeserializer(userJPath);

        assertNotEquals(newUser.getImage(), user.getImage(), "Users are different");
        System.out.println(newUser.toString());
    }
}
