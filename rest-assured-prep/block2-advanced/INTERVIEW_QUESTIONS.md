# 🎯 Вопросы для собеседования: REST Assured Advanced & Authentication

## Блок 2: Продвинутые темы (1-15)

### 1. Что такое JSON Path и как его использовать?

**Ответ:**
Язык запросов для извлечения данных из JSON:
```java
response.path("user.name");
response.path("items.findAll { it.price > 100 }");
```

---

### 2. В чём разница между find и findAll?

**Ответ:**
- find — возвращает первый найденный элемент
- findAll — возвращает список всех подходящих элементов

---

### 3. Как использовать агрегатные функции в JSON Path?

**Ответ:**
```java
response.path("items.price.sum()");
response.path("items.price.average()");
response.path("items.size()");
```

---

### 4. Что такое POJO и зачем использовать?

**Ответ:**
Java класс для представления данных. Преимущества:
- Типобезопасность
- Переиспользование
- Читаемость

---

### 5. Какие аннотации Jackson вы знаете?

**Ответ:**
- @JsonProperty — маппинг имени поля
- @JsonIgnore — исключить поле
- @JsonInclude — условия включения

---

### 6. Что такое RequestSpecification?

**Ответ:**
Спецификация с общими настройками запроса для переиспользования.

---

### 7. Как создать RequestSpecification?

**Ответ:**
```java
RequestSpecification spec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setContentType(ContentType.JSON)
    .addHeader("Auth", "Bearer token")
    .build();
```

---

### 8. Что такое ResponseSpecification?

**Ответ:**
Спецификация с ожидаемыми проверками ответа.

---

### 9. Как использовать кастомный фильтр?

**Ответ:**
Реализовать интерфейс Filter:
```java
public class CustomFilter implements Filter {
    public Response filter(...) {
        // логика
        return ctx.next(requestSpec, responseSpec);
    }
}
```

---

### 10. Зачем нужен фильтр логирования?

**Ответ:**
Для отладки и отчётности (Allure).

---

### 11. Как установить таймаут для запроса?

**Ответ:**
```java
given().timeout(5000).get("/users");
```

---

### 12. Что такое Retry и когда использовать?

**Ответ:**
Повторные попытки при временных ошибках (5xx).

---

### 13. Как отправить файл через multipart?

**Ответ:**
```java
given()
    .multiPart("file", new File("path/to/file"))
.post("/upload");
```

---

### 14. В чём разница между formParam и multiPart?

**Ответ:**
- formParam — application/x-www-form-urlencoded
- multiPart — multipart/form-data (для файлов)

---

### 15. Как извлечь данные из response в POJO?

**Ответ:**
```java
User user = response.as(User.class);
```

---

## Блок 3: Аутентификация (1-15)

### 1. Какие типы аутентификации вы знаете?

**Ответ:**
- Basic Auth
- Bearer Token / OAuth2
- API Key
- Form Authentication
- NTLM

---

### 2. Как работает Basic Auth?

**Ответ:**
Отправляет username:password в base64 кодировке в header.

---

### 3. Что такое Bearer Token?

**Ответ:**
Токен доступа в формате "Bearer {token}" в Authorization header.

---

### 4. Как отправить API Key?

**Ответ:**
В header или query parameter:
```java
.header("X-API-Key", "key")
.queryParam("api_key", "key")
```

---

### 5. Что такое Allure RestAssured фильтр?

**Ответ:**
Фильтр для автоматического логирования запросов/ответов в Allure.

---

### 6. Какие Allure аннотации вы знаете?

**Ответ:**
- @Feature
- @Story
- @Severity
- @Description
- @Owner
- @Epic

---

### 7. Что такое DataProvider?

**Ответ:**
TestNG механизм для параметризации тестов.

---

### 8. Как использовать dependsOnMethods?

**Ответ:**
```java
@Test(dependsOnMethods = "testSetup")
public void testMain() { }
```

---

### 9. Что такое Page Object паттерн?

**Ответ:**
Инкапсуляция методов работы с API в классы-страницы.

---

### 10. Какие преимущества Page Object?

**Ответ:**
- DRY (Don't Repeat Yourself)
- Читаемость
- Легче поддерживать
- Сокрытие деталей реализации

---

### 11. Как организовать структуру проекта?

**Ответ:**
```
├── api/pages/      # Page Object
├── api/models/     # POJO
├── api/specs/      # Specifications
├── tests/          # Тесты
└── utils/          # Утилиты
```

---

### 12. Что такое Form Authentication?

**Ответ:**
Аутентификация через POST формы с username/password.

---

### 13. Как получить токен для тестов?

**Ответ:**
Через login endpoint:
```java
String token = given()
    .body(loginData)
.post("/auth/login")
.path("accessToken");
```

---

### 14. Что такое preemptive authentication?

**Ответ:**
Отправка credentials сразу, без ожидания 401 от сервера.

---

### 15. Как использовать Soft Assertions?

**Ответ:**
```java
SoftAssert softAssert = new SoftAssert();
softAssert.assertEquals(actual, expected);
softAssert.assertAll();  // Обязательно!
```
