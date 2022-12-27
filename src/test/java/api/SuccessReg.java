package api;



public class SuccessReg {

    public SuccessReg () { // добавил пустой конструктор, для исправления ошибки: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of
        super();
    }

    public SuccessReg(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    private Integer id;
    private String token;

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
