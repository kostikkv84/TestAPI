package api;

import login.SuccessLogin;
import login.UserLoginTest;
import login.UserUnsaccessLogin;
import org.junit.Assert;
import org.junit.Test;
import register.Register;
import register.SuccessReg;
import register.UnSuccessReg;
import spec.Specifications;


import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest () {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given() // создаем список и записываем туда данные, те что указываем ниже. Парсим.
                .when() // когда
         //       .contentType(ContentType.JSON)// вернулся тип ответа Json
          //      .get(URL + "api/users?page=2")// типа запроса get
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data",UserData.class); // Извлекаем не все тело, а часть data и помещаем в класс UserData в те поля

        //Вариант 1.1 Перебираем каждый экземпляр класса из списка, проверяем присутствие Id пользователя (переведя в string) в имени аватара.
     //   users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        // Вариант 2.1 Выбираем из полученного списка email у которых окончание @reqres.in
      //  Assert.assertTrue(users.stream().allMatch(x-> x.getEmail().endsWith("@reqres.in")));

        // Вариант 1.2 Сравниваем Аватары и ID пользователя.
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        for(int i =0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
       // Вариант 2.2 Ищем emails с окончанием @reqres.in
        List<String> emails = users.stream().map(UserData::getEmail).collect(Collectors.toList());
        for (int i =0; i < emails.size(); i++) {
            Assert.assertTrue(emails.get(i).endsWith("@reqres.in"));
        }

    }

    @Test
    public void successRegTest () {
        //1. для начала тестируем, получение 200 ответа
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        //2. создаем 2 Pojo (Register.java и SuccessReg.java) - один с регистрацией, один с ответом, для дальнейшей проверки.
        //3. объявляем значения, которые планируем получить
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        //4. Класс регистрации с данными
        Register user = new Register("eve.holt@reqres.in", "pistol");
        //5. Класс с ожидаемым результатом из переменных (id и token). Сразу запускаем запрос и получаем ответ.
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class); // не указываем данные, а выбираем сразу класс, где хранятся полученные данные!
        //6. Сравниваем через Assert
        Assert.assertNotNull(successReg.getId()); // сначала на пустые значения.
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId()); // затем полученные с ожидаемыми.
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest () {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError400());
        Register user = new Register("sydney@fife", "");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals("Missing password", unSuccessReg.getError()); // сравниваем со спарсенным из response
    }

    @Test
    public void sortListToYear () {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        //2. Получаем список
        List<SortListToYear> yearList = given()
                .when()
                .get("api/unknown")// Запрос типа Get
                .then().log().all()
                .extract().body().jsonPath().getList("data", SortListToYear.class);

        //3. собираем только года в список
        List<Integer> years = yearList.stream().map(SortListToYear::getYear).collect(Collectors.toList()); // Полученный список
              //  years.add(1987); для проверки добавил год, в результате сравнение падает, как и ожидалось.
        //4. сортируем
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList()); // его же сортируем (полуаем Ожидаемый)
        //5. Сравниваем списки.
        Assert.assertEquals(years,sortedYears);
        System.out.println(years);
        System.out.println(sortedYears);
    }

    @Test
    public void deleteRequest () {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecUniq(204));
        given()
                .when()
                .delete("api/users/2") // Запрос типа Delete
                .then().log().all();
    }

    @Test
    public void putTimeTest () {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        //
        UserTime user = new UserTime("morpheus", "zion resident"); // записываем данные для передачи в запрос
        // создаем запрос
        UserTimeResponse response = given()
                .body(user) //указываем тело запроса
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);
        String regex = "(.{5})$"; //https://regex101.com/ - тут создали регулярное выражение удаляющее последние 5 символов строке со временем
        String currentTime = Clock.systemUTC().instant().toString().replaceAll("(.{11})$",""); // получили время в формате - 2022-12-27T11:13:01.811012600Z
        System.out.println(currentTime);
        Assert.assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex, ""));
        System.out.println(response.getUpdatedAt().replaceAll(regex, ""));

    }

    @Test
    public void postLoginSuccess () {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());

        String token = "QpwL5tke4Pnpja7X4";

        UserLoginTest userLogin = new UserLoginTest("eve.holt@reqres.in","cityslicka"); // передаем в запрос эти данные
        //создаем запрос
        SuccessLogin login = given()
                .body(userLogin)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().as(SuccessLogin.class);
        Assert.assertEquals(token,login.getToken());
    }

    @Test
    public void postLoginUnSaccess () {
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError400());

        UserLoginTest loginUser = new UserLoginTest("peter@klaven",""); // в логин передаем только email
        UserUnsaccessLogin unsaccessLogin = given()
                .body(loginUser)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().as(UserUnsaccessLogin.class);
        Assert.assertEquals("Missing password", unsaccessLogin.getError());
    }
}
