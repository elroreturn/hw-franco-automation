package utils.fileLoader.users;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersLoader {

    private static final String CSV_FILE_NAME = System.getProperty("USERS_FILE");

    public static List<UsersCsv> usersList() {
        List<UsersCsv> usersCsvList = new ArrayList<>();

        try(Reader fileReader = Files.newBufferedReader(Paths.get("src/test/resources/" + CSV_FILE_NAME))) {
            usersCsvList = new CsvToBeanBuilder<UsersCsv>(fileReader)
                    .withType(UsersCsv.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersCsvList;
    }

    public UsersCsv getUserByUserType(List<UsersCsv> usersCsvs, String userType){

        UsersCsv user = new UsersCsv();

        Optional<UsersCsv> optionalUser = usersCsvs.stream()
                .filter(usersCsv -> usersCsv.getUserType().equals(userType))
                .findFirst();

        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }

        return user;
    }
}
