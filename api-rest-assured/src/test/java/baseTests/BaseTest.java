package baseTests;

import org.testng.annotations.BeforeClass;
import utils.fileLoader.users.UsersCsv;
import utils.fileLoader.users.UsersLoader;

import java.util.List;

import static utils.PropertiesReader.loadProperties;

public class BaseTest {

    public List<UsersCsv> testUsers;

    @BeforeClass
    public void setUp(){
        loadProperties();
        this.testUsers = UsersLoader.usersList();
    }
}
