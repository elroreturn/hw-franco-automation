package tests.groups.getGroup;

import baseTests.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.Test;
import utils.GeneralPreconditions;
import utils.fileLoader.users.UsersCsv;
import utils.fileLoader.users.UsersLoader;

import static io.restassured.RestAssured.given;

public class getGroupTests extends BaseTest {

    @Test(testName = "Get group information using groupId")
    public void getGroupInformationTest(){
        UsersCsv testUser = new UsersLoader().getUserByUserType(this.testUsers, "defaultUser");
        User user = new GeneralPreconditions().getUserFromLogin(testUser);

        System.out.println(user.toString());

        String defaultGroupId = "6341a623f3f92f1a88553ba3";

        RequestSpecification request = given()
                .baseUri(System.getProperty("SERVER_URL"))
                .pathParam("groupId", defaultGroupId)
                .contentType(ContentType.JSON)
                .log().all();

    }
}
