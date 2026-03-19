# 📝 Задачи: Аутентификация и фильтры

## Уровень 1: Basic Auth и Bearer Token (задачи 1-5)

### Задача 3.1
Напишите тест с Basic Authentication.

```java
@Test
public void testBasicAuth() {
    // Напишите код здесь
}
```

---

### Задача 3.2
Напишите тест с Bearer Token authentication.

```java
@Test
public void testBearerToken() {
    // Напишите код здесь
}
```

---

### Задача 3.3
Напишите тест с отправкой API Key в header.

```java
@Test
public void testApiKeyInHeader() {
    // Напишите код здесь
}
```

---

### Задача 3.4
Напишите тест с отправкой API Key в query parameter.

```java
@Test
public void testApiKeyInQuery() {
    // Напишите код здесь
}
```

---

### Задача 3.5
Напишите тест с preemptive Basic Auth.

```java
@Test
public void testPreemptiveBasicAuth() {
    // Напишите код здесь
}
```

---

## Уровень 2: Фильтры (задачи 6-10)

### Задача 3.6
Создайте фильтр для добавления timestamp header ко всем запросам.

```java
// Напишите фильтр здесь
```

---

### Задача 3.7
Создайте фильтр для логирования времени выполнения запроса.

```java
// Напишите фильтр здесь
```

---

### Задача 3.8
Используйте фильтр для логирования только при validation fails.

```java
@Test
public void testLogOnValidationFail() {
    // Напишите код здесь
}
```

---

### Задача 3.9
Создайте фильтр для автоматической проверки statusCode < 500.

```java
// Напишите фильтр здесь
```

---

### Задача 3.10
Используйте несколько фильтров в одном запросе.

```java
@Test
public void testMultipleFilters() {
    // Напишите код здесь
}
```

---

## Уровень 3: Allure и TestNG (задачи 11-15)

### Задача 3.11
Добавьте Allure фильтр в тест.

```java
@Test
public void testWithAllure() {
    // Напишите код здесь
}
```

---

### Задача 3.12
Добавьте Allure аннотации @Feature, @Story, @Severity.

```java
@Test
// Добавьте аннотации здесь
public void testWithAllureAnnotations() {
    // Напишите код здесь
}
```

---

### Задача 3.13
Создайте DataProvider для параметризации теста.

```java
@DataProvider
// Напишите data provider здесь
```

---

### Задача 3.14
Используйте DataProvider в тесте с REST Assured.

```java
@Test(dataProvider = "userData")
public void testCreateUser(String name, String email) {
    // Напишите код здесь
}
```

---

### Задача 3.15
Создайте тест с dependsOnMethods.

```java
@Test
public void testSetup() {
    // Напишите код здесь
}

@Test(dependsOnMethods = "testSetup")
public void testMain() {
    // Напишите код здесь
}
```

---

## Уровень 4: Page Object (задачи 16-20)

### Задача 3.16
Создайте API Page класс для работы с заказами (Orders).

```java
public class OrdersApiPage {
    // Напишите методы здесь
}
```

---

### Задача 3.17
Добавьте метод getAllOrders() в OrdersApiPage.

```java
// Напишите метод здесь
```

---

### Задача 3.18
Добавьте метод createOrder() в OrdersApiPage.

```java
// Напишите метод здесь
```

---

### Задача 3.19
Используйте OrdersApiPage в тесте.

```java
@Test
public void testCreateOrder() {
    // Напишите код здесь
}
```

---

### Задача 3.20
Создайте базовый класс для всех тестов с общими spec.

```java
public class BaseTest {
    // Напишите базовый класс здесь
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Умею использовать Basic Auth
- [ ] Умею использовать Bearer Token
- [ ] Умею отправлять API Key
- [ ] Умею создавать кастомные фильтры
- [ ] Умею использовать Allure
- [ ] Умею использовать TestNG DataProvider
- [ ] Понимаю Page Object для API
- [ ] Умею создавать базовые классы для тестов

---

## 📌 Подсказки

<details>
<summary>Подсказка: Basic Auth</summary>

```java
given()
    .auth()
    .basic("username", "password")
.when()
    .get("/protected")
.then()
    .statusCode(200);
```
</details>

<details>
<summary>Подсказка: Bearer Token</summary>

```java
given()
    .auth()
    .oauth2("your-token")
.when()
    .get("/protected")
.then()
    .statusCode(200);
```
</details>

<details>
<summary>Подсказка: Allure фильтр</summary>

```java
given()
    .filter(new AllureRestAssured())
.when()
    .get("/users")
.then()
    .statusCode(200);
```
</details>

<details>
<summary>Подсказка: DataProvider</summary>

```java
@DataProvider(name = "userData")
public Object[][] userData() {
    return new Object[][] {
        {"John", "john@example.com"},
        {"Jane", "jane@example.com"}
    };
}
```
</details>
