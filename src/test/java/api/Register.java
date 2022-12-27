package api;

public class Register {

    public Register () { // добавил пустой конструктор, для исправления ошибки: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of
        super();
    }
    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private String email;
    private String password;

    //геттер не создаем так как тут POST

}

