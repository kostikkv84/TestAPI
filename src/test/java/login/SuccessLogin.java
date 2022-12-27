package login;

public class SuccessLogin {
    public String token;

    public SuccessLogin() {
        super();
    }

    public SuccessLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
