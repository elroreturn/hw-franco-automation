package tests.stores.deleteStore;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Store;
import models.User;
import org.testng.annotations.Test;
import utils.GeneralPreconditions;
import utils.fileLoader.users.UsersCsv;
import utils.fileLoader.users.UsersLoader;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class deleteStoreTests extends BaseTest {

    @Test(testName = "Delete store")
    public void deleteStoreTest(){

        // Arrange
        GeneralPreconditions gp = new GeneralPreconditions();
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");
        User user = gp.getUserFromLogin(testUser);
        Store store = gp.getUserStore(user.get_id());


        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getJwt())
                .log().all();

        // Act
        Response response = request.when()
                .delete(System.getProperty("SERVER_URL") + "/api/store/" + store.get_id())
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/store/deleteStore.json"));

    }
}
