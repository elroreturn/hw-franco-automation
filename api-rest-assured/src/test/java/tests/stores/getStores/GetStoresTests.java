package tests.stores.getStores;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Store;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.deserializer.StoreDeserializer.storesDeserializer;

public class GetStoresTests extends BaseTest {

    @Test
    public void getStoresTests() {

        // Arrange
        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .log().all();

        // Act
        Response response = request.when()
                .get(System.getProperty("SERVER_URL") + "/api/stores")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/store/stores.json"));

        // Extra
        JsonPath storesPath = response.then().extract().body().jsonPath();
        List<Store> stores = storesDeserializer(storesPath);
        stores.forEach(System.out::println);
    }
}
