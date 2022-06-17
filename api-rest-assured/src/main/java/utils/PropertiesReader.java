package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

    public static void loadProperties(){
        Properties props = System.getProperties();

        try(FileInputStream file = new FileInputStream(new File("src/test/resources/config.properties"))) {
            props.load(file);
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
