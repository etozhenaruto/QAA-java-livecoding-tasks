# 📝 Задачи: Основы REST Assured

## Уровень 1: Базовые GET запросы (задачи 1-5)

### Задача 1.1
Напишите тест для GET запроса к `/users` и проверьте статус код 200.

```java
@Test
public void testGetUsers() {
    // Напишите код здесь
}
```

---

### Задача 1.2
Напишите тест для GET запроса к `/users/1` и проверьте что `id` равен 1.

```java
@Test
public void testGetUserById() {
    // Напишите код здесь
}
```

---

### Задача 1.3
Напишите тест для GET запроса к `/users` с query параметром `page=1`.

```java
@Test
public void testGetUsersWithPage() {
    // Напишите код здесь
}
```

---

### Задача 1.4
Напишите тест для GET запроса и проверьте что массив пользователей не пустой.

```java
@Test
public void testUsersNotEmpty() {
    // Напишите код здесь
}
```

---

### Задача 1.5
Напишите тест для GET запроса и проверьте что response имеет Content-Type JSON.

```java
@Test
public void testContentType() {
    // Напишите код здесь
}
```

---

## Уровень 2: POST запросы (задачи 6-10)

### Задача 1.6
Напишите тест для POST запроса создания пользователя с Map body.

```java
@Test
public void testCreateUser() {
    // Напишите код здесь
}
```

---

### Задача 1.7
Напишите тест для POST запроса и проверьте что статус код 201.

```java
@Test
public void testCreateUserStatusCode() {
    // Напишите код здесь
}
```

---

### Задача 1.8
Напишите тест для POST запроса и проверьте что в ответе вернулся `id`.

```java
@Test
public void testCreateUserId() {
    // Напишите код здесь
}
```

---

### Задача 1.9
Напишите тест для POST запроса с JSON строкой в body.

```java
@Test
public void testCreateUserWithJsonString() {
    // Напишите код здесь
}
```

---

### Задача 1.10
Напишите тест для POST запроса и проверьте что имя пользователя совпадает с отправленным.

```java
@Test
public void testCreateUserNameMatch() {
    // Напишите код здесь
}
```

---

## Уровень 3: PUT и DELETE (задачи 11-15)

### Задача 1.11
Напишите тест для PUT запроса обновления пользователя.

```java
@Test
public void testUpdateUser() {
    // Напишите код здесь
}
```

---

### Задача 1.12
Напишите тест для PATCH запроса частичного обновления.

```java
@Test
public void testPatchUser() {
    // Напишите код здесь
}
```

---

### Задача 1.13
Напишите тест для DELETE запроса и проверьте статус код 204.

```java
@Test
public void testDeleteUser() {
    // Напишите код здесь
}
```

---

### Задача 1.14
Напишите тест для GET запроса с path parameter `{id}`.

```java
@Test
public void testGetUserWithPathParam() {
    // Напишите код здесь
}
```

---

### Задача 1.15
Напишите тест с проверкой нескольких полей в response body.

```java
@Test
public void testMultipleFields() {
    // Напишите код здесь
}
```

---

## Уровень 4: Headers и Cookies (задачи 16-20)

### Задача 1.16
Напишите тест с отправкой Authorization header.

```java
@Test
public void testWithAuthHeader() {
    // Напишите код здесь
}
```

---

### Задача 1.17
Напишите тест с отправкой cookie.

```java
@Test
public void testWithCookie() {
    // Напишите код здесь
}
```

---

### Задача 1.18
Напишите тест с проверкой response header.

```java
@Test
public void testResponseHeader() {
    // Напишите код здесь
}
```

---

### Задача 1.19
Напишите тест с несколькими headers.

```java
@Test
public void testMultipleHeaders() {
    // Напишите код здесь
}
```

---

### Задача 1.20
Напишите тест с логированием запроса и ответа.

```java
@Test
public void testWithLogging() {
    // Напишите код здесь
}
```

---

## Уровень 5: Комплексные задачи (задачи 21-25)

### Задача 1.21
Напишите тест с использованием RequestSpecification.

```java
@Test
public void testWithRequestSpec() {
    // Напишите код здесь
}
```

---

### Задача 1.22
Напишите тест с использованием ResponseSpecification.

```java
@Test
public void testWithResponseSpec() {
    // Напишите код здесь
}
```

---

### Задача 1.23
Напишите тест с извлечением данных из response.

```java
@Test
public void testExtractData() {
    // Напишите код здесь
}
```

---

### Задача 1.24
Напишите тест с проверкой массива в response.

```java
@Test
public void testArrayInResponse() {
    // Напишите код здесь
}
```

---

### Задача 1.25
Напишите тест с проверкой вложенных объектов в response.

```java
@Test
public void testNestedObjects() {
    // Напишите код здесь
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Умею делать GET запросы
- [ ] Умею делать POST запросы
- [ ] Умею делать PUT и DELETE запросы
- [ ] Умею использовать query параметры
- [ ] Умею использовать path параметры
- [ ] Умею отправлять headers
- [ ] Умею отправлять cookies
- [ ] Умею проверять статус код
- [ ] Умею проверять response body
- [ ] Умею использовать Hamcrest матчеры
- [ ] Умею логировать запросы/ответы
- [ ] Умею извлекать данные из response

---

## 📌 Подсказки

<details>
<summary>Подсказка: Базовый синтаксис</summary>

```java
given()
    .baseUri("https://api.example.com")
.when()
    .get("/users")
.then()
    .statusCode(200);
```
</details>

<details>
<summary>Подсказка: POST с Map</summary>

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
</details>

<details>
<summary>Подсказка: Path параметры</summary>

```java
given()
    .pathParam("id", 1)
.when()
    .get("/users/{id}")
.then()
    .statusCode(200);
```
</details>

<details>
<summary>Подсказка: Hamcrest матчеры</summary>

```java
.body("id", equalTo(1))
.body("name", is("John"))
.body("email", containsString("@"))
.body("items", hasSize(10))
```
</details>
