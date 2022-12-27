package api;

public class UnSuccessReg {
    private String error;

    public String getError() {
        return error;
    }

    public UnSuccessReg () { // добавил пустой конструктор, для исправления ошибки: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of
        super();
    }

    public UnSuccessReg (String error) {
        this.error = error;
    }
}
