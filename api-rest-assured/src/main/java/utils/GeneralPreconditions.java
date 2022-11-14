package utils;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Store;
import models.User;
import org.json.JSONObject;
import utils.fileLoader.users.UsersCsv;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.deserializer.StoreDeserializer.storesDeserializer;
import static utils.deserializer.UserDeserializer.userFromJWT;

public class GeneralPreconditions {

    public User getUserFromLogin(UsersCsv usersCsv){

        JSONObject parameters = new JSONObject();
        parameters.put("email", usersCsv.getEmail());
        parameters.put("password", usersCsv.getPassword());

        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .body(parameters.toString())
                .log().all();

        Response response = request.when()
                .post(System.getProperty("SERVER_URL") + "/api/login/token")
                .prettyPeek();

        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/login/tokenLogin.json"));

        String jwt = response.then().extract().path("token");
        return userFromJWT(jwt);

    }

    public Store getStore(){

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

        return stores.get(0);
    }

    public Store getUserStore(String userId){

        // Arrange
        Store store = new Store();
        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .pathParam("userId", userId)
                .log().all();

        // Act
        Response response = request.when()
                .get(System.getProperty("SERVER_URL") + "/api/stores/{userId}")
                .prettyPeek();

        // Assert
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("jsonSchemas/store/stores.json"));

        // Extra
        JsonPath storesPath = response.then().extract().body().jsonPath();
        List<Store> stores = storesDeserializer(storesPath);

        if(!stores.isEmpty()){
            Optional<Store> opStore = stores.stream()
                    .filter(store1 -> store1.getLabel().contains("Automation Test"))
                    .findFirst();

            if(opStore.isPresent()){
                store = opStore.get();
            }
        }

        return store;
    }
}
