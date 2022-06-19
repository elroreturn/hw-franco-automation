package utils.deserializer;

import models.User;
import io.restassured.path.json.JsonPath;

import java.util.Base64;

public class UserDeserializer {

    /**
     * Deserialize users from RestAssure response JsonPath
     * @param jsonPath Object to deserialize
     * @return User class
     */
    public static User userDeserializer(JsonPath jsonPath){
        return jsonPath.getObject("user", User.class);
    }

    /**
     * Deserialize users from JWT
     * @param jwToken JWT
     * @return User class
     */
    public static User userFromJWT(String jwToken){

        String[] parts = jwToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(parts[1]));

        JsonPath jsonPath = JsonPath.given(payload.replace("sub", "_id"));

        User user = jsonPath.getObject("", User.class);
        user.setJwt(jwToken);
        return user;
    }
}
