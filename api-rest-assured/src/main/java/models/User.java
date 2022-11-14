package models;

public class User {
    private String _id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private String image;
    private String username;
    private String iat;
    private String exp;
    private String jwt;

    public User() {
    }

    public User (String id, String name, String surname, String email, String role, String image, String username) {
        this._id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.image = image;
        this.username = username;
    }

    public User(String _id, String name, String surname, String email, String role, String image, String username, String iat, String exp) {
        this._id = _id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.image = image;
        this.username = username;
        this.iat = iat;
        this.exp = exp;
    }

    @Override
    public String toString(){
        return "{ \"_id\":\"" + this._id + "\", " +
                "\"name\":\"" + this.name + "\", " +
                "\"surname\":\"" + this.surname + "\", " +
                "\"email\":\"" + this.email + "\", " +
                "\"role\":\"" + this.role + "\", " +
                "\"image\":\"" + this.image + "\", " +
                "\"username\":\"" + this.username + "\", " +
                "\"iat\":\"" + this.iat + "\", " +
                "\"exp\":\"" + this.exp + "\" }";
    }

    @Override
    public boolean equals(Object obj){

        if(!(obj instanceof User)){
            return false;
        }

        User user2 = (User) obj;

        if(this._id.equals(user2.get_id()) &&
           this.name.equals(user2.getName()) &&
           this.surname.equals(user2.getSurname()) &&
           this.email.equals(user2.getEmail()) &&
           this.role.equals(user2.getRole()) &&
           this.image.equals(user2.getImage()) &&
           this.username.equals(user2.getUsername())){
            return true;
        }
        else {
            return false;
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
