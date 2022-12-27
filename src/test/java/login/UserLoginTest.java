package login;

public class UserLoginTest {
    public String email;
    public String password;

    public UserLoginTest () {
        super();
    }

    public UserLoginTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
