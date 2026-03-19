# 📚 Теория: Продвинутые темы REST Assured

## 1. JSON Path

**JSON Path** — язык запросов для извлечения данных из JSON.

### 1.1. Базовый синтаксис

```java
// Простое поле
response.path("id");
response.path("name");

// Вложенные поля
response.path("user.id");
response.path("user.address.city");

// Массивы
response.path("items[0]");       // Первый элемент
response.path("items[0].name");  // Имя первого элемента
response.path("items[-1]");      // Последний элемент

// Все элементы массива
response.path("items.name");     // Список всех имен
response.path("items.id");       // Список всех ID

// Фильтрация массивов
response.path("items.findAll { it.price > 100 }");
response.path("items.find { it.id == 1 }");
response.path("items.price.sum()");
response.path("items.size()");
```

---

### 1.2. JsonPath класс

```java
import io.restassured.path.json.JsonPath;

// Из response
Response response = get("/users");
JsonPath json = response.jsonPath();

// Получение данных
String name = json.getString("name");
int id = json.getInt("id");
List<Integer> ids = json.getList("data.id");

// Pretty print
System.out.println(json.prettyPrint());

// Прямое создание из строки
String jsonStr = "{\"name\": \"John\", \"age\": 30}";
JsonPath jsonPath = new JsonPath(jsonStr);
String name = jsonPath.getString("name");
```

---

### 1.3. Продвинутые операции JSON Path

```java
// Фильтрация
response.path("books.findAll { book -> book.price < 10 }");
response.path("books.find { book -> book.category == 'fiction' }");

// Агрегация
response.path("books.price.sum()");
response.path("books.price.min()");
response.path("books.price.max()");
response.path("books.price.average()");

// Размер
response.path("books.size()");

// Группировка
response.path("books.category.unique()");

// Сложные условия
response.path("books.findAll { it.price > 10 && it.category == 'fiction' }");
```

---

## 2. XML Path

```java
import io.restassured.path.xml.XmlPath;

// Из response
Response response = get("/xml-endpoint");
XmlPath xml = response.xmlPath();

// Получение данных
String name = xml.getString("user.name");
int id = xml.getInt("user.id");

// Атрибуты
String id = xml.getString("user.@id");

// Список
List<String> names = xml.getList("users.user.name");

// Pretty print
System.out.println(xml.prettyPrint());
```

---

## 3. Serialization / Deserialization

### 3.1. POJO классы

```java
// Request POJO
public class CreateUserRequest {
    private String name;
    private String email;
    
    // Конструкторы, геттеры, сеттеры
    public CreateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
    // getters and setters...
}

// Response POJO
public class UserResponse {
    private int id;
    private String name;
    private String email;
    
    // getters and setters...
}
```

---

### 3.2. Использование POJO

```java
// Отправка POJO
CreateUserRequest request = new CreateUserRequest("John", "john@example.com");

given()
    .contentType(ContentType.JSON)
    .body(request)
.when()
    .post("/users")
.then()
    .statusCode(201);

// Получение POJO
UserResponse user = given()
.when()
    .get("/users/1")
.then()
    .statusCode(200)
    .extract()
    .as(UserResponse.class);

System.out.println(user.getName());
```

---

### 3.3. Jackson аннотации

```java
import com.fasterxml.jackson.annotation.*;

public class User {
    @JsonProperty("user_id")
    private int id;
    
    @JsonProperty("user_name")
    private String name;
    
    @JsonIgnore
    private String password;  // Не будет сериализоваться
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nickname;  // Не включится если null
}
```

---

## 4. Request и Response Spec

### 4.1. RequestSpecBuilder

```java
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

// Создание спецификации
RequestSpecification requestSpec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setBasePath("/api/v1")
    .setContentType(ContentType.JSON)
    .addHeader("Authorization", "Bearer token")
    .addQueryParam("api_key", "key123")
    .addFilter(new AllureRestAssured())
    .build();

// Использование в тестах
given()
    .spec(requestSpec)
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 4.2. ResponseSpecBuilder

```java
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

// Создание спецификации
ResponseSpecification responseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .expectHeader("X-Request-Id", notNullValue())
    .expectHeader("Content-Type", containsString("application"))
    .expectBody("timestamp", notNullValue())
    .build();

// Использование
given()
    .spec(requestSpec)
.when()
    .get("/users")
.then()
    .spec(responseSpec);
```

---

### 4.3. Spec в базовом классе

```java
public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    
    @BeforeClass
    public static void setup() {
        requestSpec = new RequestSpecBuilder()
            .setBaseUri("https://api.example.com")
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer token")
            .build();
        
        responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();
    }
}

public class UserTest extends BaseTest {
    @Test
    public void testGetUsers() {
        given()
            .spec(requestSpec)
        .when()
            .get("/users")
        .then()
            .spec(responseSpec);
    }
}
```

---

## 5. Фильтры

### 5.1. RequestFilter

```java
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CustomRequestFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        // Добавить header ко всем запросам
        requestSpec.header("X-Custom-Header", "value");
        
        // Продолжить выполнение
        return ctx.next(requestSpec, responseSpec);
    }
}

// Использование
given()
    .filter(new CustomRequestFilter())
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 5.2. ResponseFilter

```java
public class LoggingResponseFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        
        // Логирование ответа
        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response time: " + response.getTime() + "ms");
        
        return response;
    }
}
```

---

### 5.3. Встроенные фильтры

```java
// Логирование
given()
    .filter(logRequestAndResponseIfStatusWasStatusCode(400))
    // или
    .filter(logRequestAndResponseIfValidationFails())
.when()
    .get("/users")
.then()
    .statusCode(200);

// Allure
given()
    .filter(new AllureRestAssured())
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

## 6. Таймауты и Retry

### 6.1. Таймауты

```java
// Таймаут запроса
given()
    .timeout(5000)  // 5 секунд
.when()
    .get("/slow-endpoint")
.then()
    .statusCode(200);

// Таймаут подключения
given()
    .config(RestAssured.config()
        .httpClient(HttpClientConfig.httpClientConfig()
            .setConnectionTimeout(5000)
            .setSocketTimeout(5000)))
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 6.2. Retry

```java
import io.restassured.filter.retry.RetryFilter;

// Создание фильтра retry
RetryFilter retryFilter = new RetryFilter(
    3,              // количество попыток
    1000,           // задержка между попытками (мс)
    (req, resp, ctx) -> resp.getStatusCode() >= 500  // условие retry
);

// Использование
given()
    .filter(retryFilter)
.when()
    .get("/flaky-endpoint")
.then()
    .statusCode(200);
```

---

## 7. Multipart / Form Data

### 7.1. Form Data

```java
// application/x-www-form-urlencoded
given()
    .formParam("name", "John")
    .formParam("email", "john@example.com")
    // или:
    // .formParams("name", "John", "email", "john@example.com")
.when()
    .post("/users")
.then()
    .statusCode(201);

// Map параметров
Map<String, String> formData = new HashMap<>();
formData.put("name", "John");
formData.put("email", "john@example.com");

given()
    .formParams(formData)
.when()
    .post("/users")
.then()
    .statusCode(201);
```

---

### 7.2. Multipart

```java
// Загрузка файла
given()
    .multiPart("file", new File("path/to/file.txt"))
    .multiPart("description", "Test file")
.when()
    .post("/upload")
.then()
    .statusCode(200);

// Multipart с именем
given()
    .multiPart("uploaded_file", "filename.txt", new File("path/to/file.txt"))
.when()
    .post("/upload")
.then()
    .statusCode(200);

// Multipart с contentType
given()
    .multiPart("file", "data.json", jsonContent, "application/json")
.when()
    .post("/upload")
.then()
    .statusCode(200);
```

---

## 8. Proxy

```java
// Настройка прокси
given()
    .proxy("proxy.example.com", 8080)
    // или:
    // .proxy("proxy.example.com")
.when()
    .get("/users")
.then()
    .statusCode(200);

// С аутентификацией прокси
given()
    .proxy("proxy.example.com", 8080, "username", "password")
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

## 9. Извлечение данных

### 9.1. Extract

```java
// Извлечение response
Response response = given()
.when()
    .get("/users/1")
.then()
    .statusCode(200)
    .extract()
    .response();

// Извлечение конкретных значений
int id = response.path("id");
String name = response.path("name");

// Извлечение в переменные
Response response = get("/users/1");
int statusCode = response.getStatusCode();
String body = response.getBody().asString();
Headers headers = response.getHeaders();
```

---

### 9.2. Response DTO

```java
// Извлечение в DTO
UserResponse user = given()
.when()
    .get("/users/1")
.then()
    .statusCode(200)
    .extract()
    .as(UserResponse.class);

// Извлечение списка
List<UserResponse> users = given()
.when()
    .get("/users")
.then()
    .statusCode(200)
    .extract()
    .jsonPath()
    .getList("data", UserResponse.class);
```

---

## 10. Шпаргалка

| Тема | Синтаксис | Пример |
|------|-----------|--------|
| JSON Path | `response.path()` | `response.path("user.name")` |
| POJO Request | `.body(object)` | `.body(new User())` |
| POJO Response | `.extract().as()` | `.extract().as(User.class)` |
| RequestSpec | `RequestSpecBuilder` | `.spec(requestSpec)` |
| ResponseSpec | `ResponseSpecBuilder` | `.spec(responseSpec)` |
| Filter | `.filter()` | `.filter(new CustomFilter())` |
| Timeout | `.timeout()` | `.timeout(5000)` |
| Multipart | `.multiPart()` | `.multiPart("file", file)` |
| Form | `.formParam()` | `.formParam("name", "John")` |
| Proxy | `.proxy()` | `.proxy("host", 8080)` |
