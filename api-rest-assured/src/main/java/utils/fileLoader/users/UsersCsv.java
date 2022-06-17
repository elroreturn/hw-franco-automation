package utils.fileLoader.users;

import com.opencsv.bean.CsvBindByName;

public class UsersCsv {

    @CsvBindByName(column = "userType", required = true)
    private String userType;

    @CsvBindByName(column = "email", required = true)
    private String email;

    @CsvBindByName(column = "password", required = true)
    private String password;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
