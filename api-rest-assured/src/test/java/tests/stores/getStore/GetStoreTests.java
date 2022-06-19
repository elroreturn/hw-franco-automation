package tests.stores.getStore;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Store;
import org.testng.annotations.Test;
import utils.GeneralPreconditions;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static utils.deserializer.StoreDeserializer.storeDeserializer;
import static utils.deserializer.StoreDeserializer.storesDeserializer;

public class GetStoreTests extends BaseTest {

    @Test
    public void getStoreTests() {

        // Arrange
        Store store = new GeneralPreconditions().getStore();

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .log().all();

        // Act
        Response response = request.when()
                .get(System.getProperty("SERVER_URL") + "/api/store/" + store.get_id())
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/store/store.json"));

        // Extra
        JsonPath storePath = response.then().extract().body().jsonPath();
        Store newStore = storeDeserializer(storePath);

        assertEquals(newStore, store);
        System.out.println(newStore.toString());
    }
}
