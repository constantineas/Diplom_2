package user;

public class Autorization {

    private String email;
    private String password;
    public Autorization(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static Autorization from(User user) {
        return new Autorization(user.getEmail(), user.getPassword());
    }
    public String getLogin() {
        return email;
    }
    public void setLogin(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
