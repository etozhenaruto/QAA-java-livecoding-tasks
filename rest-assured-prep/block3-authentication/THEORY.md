# 📚 Теория: Аутентификация и фильтры REST Assured

## 1. Типы аутентификации

### 1.1. Basic Authentication

```java
// Basic Auth
given()
    .auth()
    .basic("username", "password")
.when()
    .get("/users")
.then()
    .statusCode(200);

// С preemptive (отправка до challenge)
given()
    .auth()
    .preemptive()
    .basic("username", "password")
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 1.2. Bearer Token / OAuth2

```java
// Bearer Token
given()
    .auth()
    .oauth2("your-token-here")
.when()
    .get("/users")
.then()
    .statusCode(200);

// Или через header
given()
    .header("Authorization", "Bearer your-token-here")
.when()
    .get("/users")
.then()
    .statusCode(200);

// OAuth2 с параметрами
given()
    .auth()
    .oauth2("accessToken", OAuth2Parameter.FORM)
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 1.3. API Key

```java
// API Key в header
given()
    .header("X-API-Key", "your-api-key")
.when()
    .get("/users")
.then()
    .statusCode(200);

// API Key в query parameter
given()
    .queryParam("api_key", "your-api-key")
.when()
    .get("/users")
.then()
    .statusCode(200);

// API Key в spec
RequestSpecification spec = new RequestSpecBuilder()
    .addHeader("X-API-Key", "your-api-key")
    .build();

given()
    .spec(spec)
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 1.4. Form Authentication

```java
// Form-based auth
given()
    .auth()
    .form("username", "password", 
          FormAuthConfig.formAuthConfig()
              .withLoggingEnabled())
.when()
    .get("/protected")
.then()
    .statusCode(200);

// С кастомными именами полей
given()
    .auth()
    .form("user", "pass",
          new FormAuthConfig("/login", "username", "password"))
.when()
    .get("/protected")
.then()
    .statusCode(200);
```

---

### 1.5. NTLM Authentication

```java
// NTLM (Windows authentication)
given()
    .auth()
    .ntlm("username", "password", "workstation", "domain")
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

## 2. Фильтры

### 2.1. Создание кастомного фильтра

```java
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class TimingFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        long startTime = System.currentTimeMillis();
        
        Response response = ctx.next(requestSpec, responseSpec);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Request took: " + (endTime - startTime) + "ms");
        
        return response;
    }
}

// Использование
given()
    .filter(new TimingFilter())
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 2.2. RequestFilter для модификации запроса

```java
public class HeaderFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        // Добавить header ко всем запросам
        requestSpec.header("X-Request-ID", UUID.randomUUID().toString());
        requestSpec.header("X-Test-Run", "automation");
        
        return ctx.next(requestSpec, responseSpec);
    }
}
```

---

### 2.3. ResponseFilter для валидации

```java
public class ValidationFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        
        // Валидация ответа
        if (response.getStatusCode() >= 500) {
            throw new AssertionError("Server error: " + response.getStatusCode());
        }
        
        return response;
    }
}
```

---

## 3. Allure интеграция

### 3.1. Настройка

```xml
<!-- pom.xml -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-rest-assured</artifactId>
    <version>2.27.0</version>
</dependency>
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.27.0</version>
</dependency>
```

---

### 3.2. Использование

```java
import io.qameta.allure.restassured.AllureRestAssured;

public class ApiTest {
    
    @Test
    @AllureDescription("Получение списка пользователей")
    @AllureSeverity(SeverityLevel.CRITICAL)
    public void testGetUsers() {
        given()
            .filter(new AllureRestAssured())  // Логирование в Allure
            .baseUri("https://api.example.com")
        .when()
            .get("/users")
        .then()
            .statusCode(200);
    }
    
    @Test
    @AllureDescription("Создание нового пользователя")
    @AllureSeverity(SeverityLevel.BLOCKER)
    public void testCreateUser() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "John");
        body.put("email", "john@example.com");
        
        given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/users")
        .then()
            .statusCode(201);
    }
}
```

---

### 3.3. Allure аннотации

```java
import io.qameta.allure.*;

@Feature("User Management")
@Story("User CRUD operations")
public class UserTests {
    
    @Test
    @Description("Get all users")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("test@example.com")
    @Epic("API Tests")
    public void testGetUsers() { }
    
    @Test
    @Description("Create new user")
    @Severity(SeverityLevel.BLOCKER)
    @Link(name = "JIRA-123", url = "https://jira.example.com/123")
    public void testCreateUser() { }
}
```

---

## 4. TestNG интеграция

### 4.1. Базовый тест

```java
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;

public class ApiTest {
    
    @BeforeClass
    public void setup() {
        baseURI = "https://api.example.com";
        basePath = "/api/v1";
    }
    
    @Test
    public void testGetUsers() {
        given()
        .when()
            .get("/users")
        .then()
            .statusCode(200);
    }
    
    @Test(dependsOnMethods = "testGetUsers")
    public void testGetUserById() {
        // Этот тест выполнится после testGetUsers
    }
}
```

---

### 4.2. DataProvider для параметризации

```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParameterizedTest {
    
    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][] {
            {"John", "john@example.com"},
            {"Jane", "jane@example.com"},
            {"Bob", "bob@example.com"}
        };
    }
    
    @Test(dataProvider = "userData")
    public void testCreateUser(String name, String email) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("email", email);
        
        given()
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("name", equalTo(name));
    }
}
```

---

## 5. Page Object для API

### 5.1. API Page класс

```java
public class UsersApiPage {
    private static final String USERS_ENDPOINT = "/users";
    
    public Response getAllUsers() {
        return given()
            .when()
            .get(USERS_ENDPOINT)
            .then()
            .extract()
            .response();
    }
    
    public Response getUserById(int id) {
        return given()
            .pathParam("id", id)
            .when()
            .get(USERS_ENDPOINT + "/{id}")
            .then()
            .extract()
            .response();
    }
    
    public Response createUser(String name, String email) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("email", email);
        
        return given()
            .contentType(ContentType.JSON)
            .body(body)
            .when()
            .post(USERS_ENDPOINT)
            .then()
            .extract()
            .response();
    }
    
    public Response updateUser(int id, Map<String, String> updates) {
        return given()
            .contentType(ContentType.JSON)
            .body(updates)
            .pathParam("id", id)
            .when()
            .put(USERS_ENDPOINT + "/{id}")
            .then()
            .extract()
            .response();
    }
    
    public Response deleteUser(int id) {
        return given()
            .pathParam("id", id)
            .when()
            .delete(USERS_ENDPOINT + "/{id}")
            .then()
            .extract()
            .response();
    }
}
```

---

### 5.2. Использование в тестах

```java
public class UserTests {
    private UsersApiPage usersApi;
    
    @BeforeClass
    public void setup() {
        usersApi = new UsersApiPage();
        RestAssured.baseURI = "https://api.example.com";
    }
    
    @Test
    public void testGetAllUsers() {
        Response response = usersApi.getAllUsers();
        response.then().statusCode(200);
    }
    
    @Test
    public void testCreateAndGetUser() {
        // Create
        Response createResponse = usersApi.createUser("John", "john@example.com");
        createResponse.then().statusCode(201);
        int userId = createResponse.path("id");
        
        // Get
        Response getResponse = usersApi.getUserById(userId);
        getResponse.then()
            .statusCode(200)
            .body("name", equalTo("John"));
        
        // Delete
        usersApi.deleteUser(userId).then().statusCode(204);
    }
}
```

---

## 6. Best Practices

### 6.1. Структура проекта

```
src/test/java/
├── api/
│   ├── pages/           # API Page Object
│   ├── models/          # POJO классы
│   └── specs/           # Request/Response specs
├── tests/
│   ├── BaseTest.java    # Базовый класс
│   └── UserTests.java   # Тесты
└── utils/
    └── Config.java      # Конфигурация
```

---

### 6.2. Базовый класс для тестов

```java
public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = System.getProperty("base.uri", "https://api.example.com");
        RestAssured.basePath = "/api/v1";
        
        requestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + getToken())
            .addFilter(new AllureRestAssured())
            .build();
        
        responseSpec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectHeader("X-Request-Id", notNullValue())
            .build();
    }
    
    private static String getToken() {
        // Логика получения токена
        return "token123";
    }
}
```

---

### 6.3. Конфигурация через properties

```java
public class Config {
    public static String getBaseUrl() {
        return System.getProperty("base.uri", "https://api.example.com");
    }
    
    public static String getApiKey() {
        return System.getProperty("api.key", "default-key");
    }
    
    public static int getTimeout() {
        return Integer.parseInt(System.getProperty("timeout", "5000"));
    }
}

// Использование в тестах
RestAssured.baseURI = Config.getBaseUrl();
```

---

## 7. Шпаргалка по аутентификации

| Тип | Синтаксис | Пример |
|-----|-----------|--------|
| Basic | `.auth().basic()` | `.auth().basic("user", "pass")` |
| Bearer | `.auth().oauth2()` | `.auth().oauth2("token")` |
| API Key | `.header()` | `.header("X-API-Key", "key")` |
| Form | `.auth().form()` | `.auth().form("u", "p")` |
| NTLM | `.auth().ntlm()` | `.auth().ntlm("u","p","w","d")` |
