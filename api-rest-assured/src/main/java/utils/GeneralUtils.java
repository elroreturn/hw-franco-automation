package utils;

import java.io.File;

public class GeneralUtils {

    public static File getFileFromPath(String imageName){
        return new File("src/test/resources/images/" + imageName);
    }
}
