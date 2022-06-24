package tests.stores.getUserStores;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Store;
import models.User;
import org.testng.annotations.Test;
import utils.GeneralPreconditions;
import utils.fileLoader.users.UsersCsv;
import utils.fileLoader.users.UsersLoader;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.deserializer.StoreDeserializer.storesDeserializer;

public class GetUserStoresTests extends BaseTest {

    @Test
    public void getUserStoresTest() {

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
                .get("/api/stores/{userId}")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/store/stores.json"));

        // Extra
        JsonPath storesPath = response.then().extract().body().jsonPath();
        List<Store> stores = storesDeserializer(storesPath);
        stores.forEach(store -> store.getUser().equals(user.get_id()));

        stores.forEach(System.out::println);
    }
}
