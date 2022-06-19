package tests.stores.createStore;

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

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static utils.deserializer.StoreDeserializer.storeDeserializer;

public class CreateStoreTests extends BaseTest {

    @Test(testName = "Create new Store")
    public void createStoreTest() {

        // Arrange
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");
        User user = new GeneralPreconditions().getUserFromLogin(testUser);
        System.out.println(user.toString());

        Store store = new Store();
        store.setDraggable(false);
        store.setLabel("Automation Test");
        store.setLat(-34.865425);
        store.setLng(-55.024155);
        System.out.println(store.toString());

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getJwt())
                .body(store)
                .log().all();

        // Act
        Response response = request.when()
                .post(System.getProperty("SERVER_URL") + "/api/store")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/store/createStore.json"));

        // Extra
        JsonPath storePath = response.then().extract().body().jsonPath();
        Store newStore = storeDeserializer(storePath);

        assertEquals(newStore.getLabel(), store.getLabel());
        assertEquals(newStore.getLat(), store.getLat());
        assertEquals(newStore.getLng(), store.getLng());
        assertEquals(newStore.isDraggable(), store.isDraggable());
        assertEquals(newStore.getUser(), user.get_id());
        System.out.println(newStore.toString());

    }
}
