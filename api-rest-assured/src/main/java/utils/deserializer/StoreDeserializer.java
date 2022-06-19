package utils.deserializer;

import io.restassured.path.json.JsonPath;
import models.Store;

import java.util.List;

public class StoreDeserializer {

    public static List<Store> storesDeserializer(JsonPath jsonPath){
        return jsonPath.getList("stores", Store.class);
    }

    public static Store storeDeserializer(JsonPath jsonPath){
        return jsonPath.getObject("store", Store.class);
    }

}
