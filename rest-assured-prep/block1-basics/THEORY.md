# 📚 Теория: Основы REST Assured

## 1. Что такое REST Assured?

**REST Assured** — Java библиотека для тестирования REST API.

**Преимущества:**
- Простой DSL синтаксис
- Интеграция с JUnit/TestNG
- Поддержка JSON и XML
- Hamcrest матчеры для проверок
- Встроенная поддержка аутентификации

---

## 2. Maven зависимости

```xml
<dependencies>
    <!-- REST Assured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Jackson для JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.0</version>
    </dependency>
    
    <!-- Hamcrest матчеры -->
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 3. Базовый синтаксис

### 3.1. Given-When-Then

```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    
    @Test
    public void testGetUser() {
        given()                          // Предусловия
            .baseUri("https://api.example.com")
            .basePath("/users/1")
            .header("Authorization", "Bearer token")
        .when()                          // Действие
            .get()
        .then()                          // Проверки
            .statusCode(200)
            .body("name", equalTo("John"))
            .body("email", containsString("@"));
    }
}
```

---

### 3.2. Статические импорты

```java
// Обязательно добавьте:
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
```

---

## 4. HTTP методы

### 4.1. GET запрос

```java
// Простой GET
given()
    .baseUri("https://api.example.com")
.when()
    .get("/users")
.then()
    .statusCode(200);

// GET с path parameter
given()
    .baseUri("https://api.example.com")
.when()
    .get("/users/{id}", 1)
.then()
    .statusCode(200);

// GET с query parameters
given()
    .baseUri("https://api.example.com")
    .queryParams("page", 1, "limit", 10)
    // или:
    // .queryParam("page", 1)
    // .queryParam("limit", 10)
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 4.2. POST запрос

```java
// POST с Map
Map<String, String> body = new HashMap<>();
body.put("name", "John");
body.put("email", "john@example.com");

given()
    .contentType(ContentType.JSON)
    .body(body)
.when()
    .post("/users")
.then()
    .statusCode(201);

// POST с JSON строкой
String jsonBody = "{\"name\": \"John\", \"email\": \"john@example.com\"}";

given()
    .contentType(ContentType.JSON)
    .body(jsonBody)
.when()
    .post("/users")
.then()
    .statusCode(201);

// POST с POJO
User user = new User("John", "john@example.com");

given()
    .contentType(ContentType.JSON)
    .body(user)
.when()
    .post("/users")
.then()
    .statusCode(201);
```

---

### 4.3. PUT запрос

```java
// PUT для обновления
Map<String, String> body = new HashMap<>();
body.put("name", "John Updated");

given()
    .contentType(ContentType.JSON)
    .body(body)
.when()
    .put("/users/1")
.then()
    .statusCode(200);

// PATCH (частичное обновление)
given()
    .contentType(ContentType.JSON)
    .body("{\"name\": \"Updated\"}")
.when()
    .patch("/users/1")
.then()
    .statusCode(200);
```

---

### 4.4. DELETE запрос

```java
// Простой DELETE
given()
    .baseUri("https://api.example.com")
.when()
    .delete("/users/1")
.then()
    .statusCode(204);

// DELETE с path parameter
given()
    .baseUri("https://api.example.com")
.when()
    .delete("/users/{id}", 1)
.then()
    .statusCode(204);
```

---

## 5. Проверка статус кодов

```java
// Конкретный статус код
.then().statusCode(200);
.then().statusCode(201);
.then().statusCode(204);
.then().statusCode(400);
.then().statusCode(404);
.then().statusCode(500);

// С матчером
.then().statusCode(equalTo(200));
.then().statusCode(lessThan(300));
.then().statusCode(is(200));

// Предикаты
.then().statusLine(equalTo("HTTP/1.1 200 OK"));
.then().contentType(ContentType.JSON);
```

---

## 6. Проверка Response Body

### 6.1. Проверка JSON полей

```java
// Простые проверки
.then()
    .body("id", equalTo(1))
    .body("name", equalTo("John"))
    .body("email", containsString("@"))
    .body("isActive", equalTo(true));

// Проверка вложенных полей
.then()
    .body("user.id", equalTo(1))
    .body("user.name", equalTo("John"))
    .body("user.address.city", equalTo("Moscow"));

// Проверка массивов
.then()
    .body("items[0].id", equalTo(1))
    .body("items[0].name", equalTo("Item 1"))
    .body("items.size()", equalTo(10));
```

---

### 6.2. Hamcrest матчеры

```java
import static org.hamcrest.Matchers.*;

// Равенство
.body("id", equalTo(1))
.body("name", is("John"))

// Сравнение
.body("age", greaterThan(18))
.body("price", lessThan(1000))
.body("count", greaterThanOrEqualTo(0))

// Строки
.body("email", containsString("@"))
.body("name", startsWith("J"))
.body("name", endsWith("n"))
.body("name", equalToIgnoringCase("john"))

// Null проверки
.body("deletedAt", nullValue())
.body("createdAt", notNullValue())

// Коллекции
.body("items.id", hasItem(1))
.body("items.id", hasItems(1, 2, 3))
.body("items", hasSize(10))
.body("items.name", contains("A", "B", "C"))
.body("items.id", containsInAnyOrder(3, 1, 2))

// Логические
.body("isActive", is(true))
.body("isDeleted", is(false))
```

---

## 7. Path Parameters и Query Parameters

### 7.1. Path Parameters

```java
// Один параметр
given()
    .pathParam("id", 1)
.when()
    .get("/users/{id}")
.then()
    .statusCode(200);

// Несколько параметров
given()
    .pathParam("userId", 1)
    .pathParam("postId", 10)
.when()
    .get("/users/{userId}/posts/{postId}")
.then()
    .statusCode(200);

// Inline (без явного указания)
given()
.when()
    .get("/users/{id}", 1)  // {id} = 1
.then()
    .statusCode(200);
```

---

### 7.2. Query Parameters

```java
// Один параметр
given()
    .queryParam("page", 1)
.when()
    .get("/users")
.then()
    .statusCode(200);

// Несколько параметров
given()
    .queryParam("page", 1)
    .queryParam("limit", 10)
    .queryParam("sort", "name")
.when()
    .get("/users")
.then()
    .statusCode(200);

// Map параметров
Map<String, String> params = new HashMap<>();
params.put("page", 1);
params.put("limit", 10);

given()
    .queryParams(params)
.when()
    .get("/users")
.then()
    .statusCode(200);

// queryParams (varargs)
given()
    .queryParams("page", 1, "limit", 10)
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

## 8. Headers и Cookies

### 8.1. Headers

```java
// Один header
given()
    .header("Authorization", "Bearer token123")
.when()
    .get("/users")
.then()
    .statusCode(200);

// Несколько headers
given()
    .header("Authorization", "Bearer token123")
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")
.when()
    .get("/users")
.then()
    .statusCode(200);

// Map headers
Map<String, String> headers = new HashMap<>();
headers.put("Authorization", "Bearer token123");
headers.put("Content-Type", "application/json");

given()
    .headers(headers)
.when()
    .get("/users")
.then()
    .statusCode(200);

// Проверка response headers
.then()
    .header("Content-Type", "application/json")
    .header("X-RateLimit-Remaining", notNullValue());
```

---

### 8.2. Cookies

```java
// Отправка cookie
given()
    .cookie("sessionId", "abc123")
.when()
    .get("/profile")
.then()
    .statusCode(200);

// Несколько cookies
given()
    .cookie("sessionId", "abc123")
    .cookie("userId", "123")
.when()
    .get("/profile")
.then()
    .statusCode(200);

// Проверка response cookies
.then()
    .cookie("sessionId", notNullValue())
    .cookie("rememberMe", equalTo("true"));
```

---

## 9. Конфигурация RestAssured

### 9.1. Базовая конфигурация

```java
// В базовом классе или @BeforeMethod
RestAssured.baseURI = "https://api.example.com";
RestAssured.basePath = "/api/v1";
RestAssured.port = 8080;

// Сброс к значениям по умолчанию
RestAssured.reset();
```

---

### 9.2. RequestSpecification

```java
// Создание спецификации
RequestSpecification requestSpec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setBasePath("/api/v1")
    .setContentType(ContentType.JSON)
    .addHeader("Authorization", "Bearer token")
    .addQueryParam("api_key", "key123")
    .build();

// Использование
given()
    .spec(requestSpec)
.when()
    .get("/users")
.then()
    .statusCode(200);
```

---

### 9.3. ResponseSpecification

```java
// Создание спецификации ответа
ResponseSpecification responseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .expectHeader("X-Request-Id", notNullValue())
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

## 10. Логирование

```java
// Логирование запроса
given()
    .log().all()  // Всё
    // .log().method()  // Метод
    // .log().uri()     // URI
    // .log().headers() // Headers
    // .log().body()    // Body
.when()
    .get("/users")
.then()
    .statusCode(200);

// Логирование ответа
given()
.when()
    .get("/users")
.then()
    .log().all()  // Всё
    // .log().status()   // Статус
    // .log().headers()  // Headers
    // .log().body()     // Body
    .statusCode(200);

// Логирование только при ошибке
given()
.when()
    .get("/users")
.then()
    .log().ifValidationFails()
    .statusCode(200);

// Логирование в файл
given()
    .log().all().toFile("request.log")
.when()
    .get("/users")
.then()
    .log().all().toFile("response.log")
    .statusCode(200);
```

---

## 11. Извлечение данных из Response

```java
// Извлечение значения
Response response = get("/users/1");
int id = response.path("id");
String name = response.path("name");

// Извлечение списка
List<Integer> ids = response.path("data.id");
List<String> names = response.path("data.name");

// С JSON Path
JsonPath jsonPath = response.jsonPath();
String email = jsonPath.getString("email");
int age = jsonPath.getInt("age");

// Pretty print
response.then().body(jsonPath().prettyPrint());
```

---

## 12. Обработка ошибок

```java
// Try-catch для проверок
try {
    given()
    .when()
        .get("/users/999")
    .then()
        .statusCode(200);
} catch (AssertionError e) {
    // Обработка ошибки
    System.out.println("Test failed: " + e.getMessage());
}

// Мягкие проверки (Soft assertions с TestNG)
SoftAssert softAssert = new SoftAssert();

given()
.when()
    .get("/users/1")
.then()
    .body("id", equalTo(1));

softAssert.assertEquals(response.path("name"), "John");
softAssert.assertAll();  // Обязательно в конце!
```

---

## 13. Шпаргалка

| Метод | Описание | Пример |
|-------|----------|--------|
| given() | Предусловия | `given().header("Auth", "token")` |
| when() | Действие | `when().get("/users")` |
| then() | Проверки | `then().statusCode(200)` |
| get() | GET запрос | `get("/users")` |
| post() | POST запрос | `post("/users")` |
| put() | PUT запрос | `put("/users/1")` |
| delete() | DELETE запрос | `delete("/users/1")` |
| patch() | PATCH запрос | `patch("/users/1")` |
| statusCode() | Проверка кода | `statusCode(200)` |
| body() | Проверка body | `body("id", equalTo(1))` |
| contentType() | Проверка типа | `contentType(JSON)` |
| header() | Header | `header("Auth", "token")` |
| cookie() | Cookie | `cookie("session", "abc")` |
| queryParam() | Query параметр | `queryParam("page", 1)` |
| pathParam() | Path параметр | `pathParam("id", 1)` |
