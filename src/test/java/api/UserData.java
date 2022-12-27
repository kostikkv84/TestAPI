package api;

public class UserData {
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    public UserData () { // добавил пустой конструктор, для исправления ошибки: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of
        super();
    }
    //создаем конструктор класса UserData
    public UserData(Integer id, String email, String first_name, String last_name, String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }
    //создаем геттеры, которые будем использовать для сверки ожидаемого результата с актуальным.
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }
}
