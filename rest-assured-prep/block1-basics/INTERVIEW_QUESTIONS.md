# 🎯 Вопросы для собеседования: REST Assured

## Блок 1: Основы (1-20)

### 1. Что такое REST Assured?

**Ответ:**
Java библиотека для тестирования REST API с простым DSL синтаксисом.

---

### 2. Какой синтаксис используется в REST Assured?

**Ответ:**
Given-When-Then:
- given() — предусловия (headers, body, params)
- when() — действие (GET, POST, etc.)
- then() — проверки (statusCode, body)

---

### 3. Какие HTTP методы поддерживает REST Assured?

**Ответ:**
GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS

---

### 4. Как проверить статус код?

**Ответ:**
```java
.then().statusCode(200);
```

---

### 5. Как проверить значение в response body?

**Ответ:**
```java
.then().body("id", equalTo(1));
```

---

### 6. Что такое Hamcrest матчеры?

**Ответ:**
Библиотека для проверок:
- equalTo() — равенство
- containsString() — содержит строку
- hasSize() — размер коллекции
- nullValue() / notNullValue()

---

### 7. Как отправить POST запрос с JSON body?

**Ответ:**
```java
Map<String, String> body = new HashMap<>();
body.put("name", "John");

given()
    .contentType(ContentType.JSON)
    .body(body)
.when()
    .post("/users")
.then()
    .statusCode(201);
```

---

### 8. В чём разница между queryParam и pathParam?

**Ответ:**
- queryParam — параметры запроса (?page=1&limit=10)
- pathParam — параметры пути (/users/{id})

---

### 9. Как установить header?

**Ответ:**
```java
given()
    .header("Authorization", "Bearer token")
```

---

### 10. Как проверить Content-Type ответа?

**Ответ:**
```java
.then().contentType(ContentType.JSON);
```

---

### 11. Что такое basePath и baseURI?

**Ответ:**
- baseURI — базовый URL (https://api.example.com)
- basePath — базовый путь (/api/v1)

---

### 12. Как логировать запрос и ответ?

**Ответ:**
```java
given()
    .log().all()
.when()
    .get("/users")
.then()
    .log().all();
```

---

### 13. Как извлечь значение из response?

**Ответ:**
```java
int id = response.path("id");
String name = response.path("name");
```

---

### 14. Как проверить массив в response?

**Ответ:**
```java
.body("items", hasSize(10))
.body("items.id", hasItem(1))
```

---

### 15. Как проверить вложенные объекты?

**Ответ:**
```java
.body("user.address.city", equalTo("Moscow"))
```

---

### 16. Что делает contentType()?

**Ответ:**
Устанавливает Content-Type заголовок запроса.

---

### 17. Как отправить DELETE запрос?

**Ответ:**
```java
delete("/users/1")
```

---

### 18. Как проверить что поле не null?

**Ответ:**
```java
.body("id", notNullValue())
```

---

### 19. Как использовать несколько проверок?

**Ответ:**
```java
.then()
    .statusCode(200)
    .body("id", equalTo(1))
    .body("name", equalTo("John"))
```

---

### 20. Как сбросить настройки REST Assured?

**Ответ:**
```java
RestAssured.reset();
```

---

## Блок 2: Продвинутые (1-15)

### 1. Что такое JSON Path?

**Ответ:**
Язык запросов для извлечения данных из JSON.

---

### 2. Как отфильтровать массив в JSON Path?

**Ответ:**
```java
response.path("items.findAll { it.price > 100 }");
```

---

### 3. Что такое POJO в контексте REST Assured?

**Ответ:**
Java класс для представления request/response данных.

---

### 4. Как отправить POJO в body?

**Ответ:**
```java
User user = new User("John", "john@example.com");
given()
    .contentType(ContentType.JSON)
    .body(user)
.post("/users");
```

---

### 5. Как получить response как POJO?

**Ответ:**
```java
User user = response.as(User.class);
```

---

### 6. Что такое RequestSpecification?

**Ответ:**
Спецификация с общими настройками запроса.

---

### 7. Как создать RequestSpecification?

**Ответ:**
```java
RequestSpecification spec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setContentType(ContentType.JSON)
    .build();
```

---

### 8. Что такое ResponseSpecification?

**Ответ:**
Спецификация с ожидаемыми проверками ответа.

---

### 9. Как использовать фильтр?

**Ответ:**
```java
given()
    .filter(new CustomFilter())
.get("/users");
```

---

### 10. Как установить таймаут?

**Ответ:**
```java
given()
    .timeout(5000)
.get("/users");
```

---

### 11. Что такое Retry фильтр?

**Ответ:**
Фильтр для повторных попыток при ошибках.

---

### 12. Как отправить multipart/form-data?

**Ответ:**
```java
given()
    .multiPart("file", new File("path/to/file"))
.post("/upload");
```

---

### 13. Как отправить form data?

**Ответ:**
```java
given()
    .formParam("name", "John")
.post("/users");
```

---

### 14. Что такое @JsonIgnore?

**Ответ:**
Аннотация Jackson для исключения поля из сериализации.

---

### 15. Как извлечь список из response?

**Ответ:**
```java
List<Integer> ids = response.path("data.id");
```

---

## Блок 3: Аутентификация (1-15)

### 1. Какие типы аутентификации поддерживает REST Assured?

**Ответ:**
Basic, Bearer/OAuth2, API Key, Form, NTLM

---

### 2. Как использовать Basic Auth?

**Ответ:**
```java
.auth().basic("username", "password")
```

---

### 3. Как использовать Bearer Token?

**Ответ:**
```java
.auth().oauth2("token")
```

---

### 4. Как отправить API Key в header?

**Ответ:**
```java
.header("X-API-Key", "key123")
```

---

### 5. Что такое Allure RestAssured фильтр?

**Ответ:**
Фильтр для логирования запросов/ответов в Allure отчёт.

---

### 6. Как добавить Allure аннотации?

**Ответ:**
```java
@Feature("Users")
@Story("CRUD")
@Severity(SeverityLevel.CRITICAL)
@Test
public void test() { }
```

---

### 7. Что такое DataProvider в TestNG?

**Ответ:**
Механизм для параметризации тестов.

---

### 8. Как использовать DataProvider?

**Ответ:**
```java
@DataProvider(name = "data")
public Object[][] data() { return [...]; }

@Test(dataProvider = "data")
public void test(String param) { }
```

---

### 9. Что такое Page Object для API?

**Ответ:**
Паттерн инкапсуляции API методов в классы.

---

### 10. Какие преимущества Page Object?

**Ответ:**
- Переиспользование кода
- Читаемость
- Легче поддерживать

---

### 11. Как создать базовый класс для тестов?

**Ответ:**
```java
public class BaseTest {
    @BeforeClass
    public static void setup() {
        // общие настройки
    }
}
```

---

### 12. Что такое Form Authentication?

**Ответ:**
Аутентификация через форму логина.

---

### 13. Как использовать NTLM?

**Ответ:**
```java
.auth().ntlm("user", "pass", "workstation", "domain")
```

---

### 14. Что такое preemptive authentication?

**Ответ:**
Отправка credentials до server challenge.

---

### 15. Как получить токен для тестов?

**Ответ:**
Через POST запрос к endpoint аутентификации:
```java
String token = given()
    .body(credentials)
.post("/auth/login")
.path("accessToken");
```
