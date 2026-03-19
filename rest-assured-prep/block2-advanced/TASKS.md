# 📝 Задачи: Продвинутые темы REST Assured

## Уровень 1: JSON Path (задачи 1-5)

### Задача 2.1
Извлеките значение поля `id` из response используя JSON Path.

```java
@Test
public void testExtractId() {
    // Напишите код здесь
}
```

---

### Задача 2.2
Извлеките список всех имен пользователей из массива `data`.

```java
@Test
public void testExtractNamesList() {
    // Напишите код здесь
}
```

---

### Задача 2.3
Используйте `findAll` для фильтрации товаров с ценой больше 100.

```java
@Test
public void testFilterByPrice() {
    // Напишите код здесь
}
```

---

### Задача 2.4
Используйте `sum()` для подсчета общей стоимости всех товаров.

```java
@Test
public void testSumPrices() {
    // Напишите код здесь
}
```

---

### Задача 2.5
Используйте `find()` для поиска первого товара с категорией "electronics".

```java
@Test
public void testFindFirstElectronics() {
    // Напишите код здесь
}
```

---

## Уровень 2: POJO (задачи 6-10)

### Задача 2.6
Создайте POJO класс `User` с полями id, name, email.

```java
// Напишите класс здесь
```

---

### Задача 2.7
Отправьте POST запрос с POJO объектом в body.

```java
@Test
public void testPostWithPojo() {
    // Напишите код здесь
}
```

---

### Задача 2.8
Получите response и преобразуйте его в POJO объект.

```java
@Test
public void testGetAsPojo() {
    // Напишите код здесь
}
```

---

### Задача 2.9
Создайте POJO класс для response с вложенным объектом Address.

```java
// Напишите классы здесь
```

---

### Задача 2.10
Используйте `@JsonIgnore` для исключения поля из сериализации.

```java
// Напишите класс здесь
```

---

## Уровень 3: Request/Response Spec (задачи 11-15)

### Задача 2.11
Создайте RequestSpecification с базовым URI и contentType.

```java
// Напишите код здесь
```

---

### Задача 2.12
Создайте ResponseSpecification с проверкой statusCode и contentType.

```java
// Напишите код здесь
```

---

### Задача 2.13
Используйте RequestSpecification в тесте.

```java
@Test
public void testWithRequestSpec() {
    // Напишите код здесь
}
```

---

### Задача 2.14
Используйте ResponseSpecification в тесте.

```java
@Test
public void testWithResponseSpec() {
    // Напишите код здесь
}
```

---

### Задача 2.15
Создайте базовый класс для тестов с общими spec.

```java
// Напишите базовый класс здесь
```

---

## Уровень 4: Фильтры и таймауты (задачи 16-20)

### Задача 2.16
Создайте кастомный фильтр для добавления header ко всем запросам.

```java
// Напишите фильтр здесь
```

---

### Задача 2.17
Используйте фильтр для логирования только при ошибке.

```java
@Test
public void testLogOnError() {
    // Напишите код здесь
}
```

---

### Задача 2.18
Установите таймаут 10 секунд для запроса.

```java
@Test
public void testWithTimeout() {
    // Напишите код здесь
}
```

---

### Задача 2.19
Настройте retry фильтр для повторных попыток при 5xx ошибках.

```java
@Test
public void testWithRetry() {
    // Напишите код здесь
}
```

---

### Задача 2.20
Используйте Allure фильтр для отчётности.

```java
@Test
public void testWithAllure() {
    // Напишите код здесь
}
```

---

## Уровень 5: Multipart и Form (задачи 21-25)

### Задача 2.21
Отправьте form data с полями name и email.

```java
@Test
public void testFormData() {
    // Напишите код здесь
}
```

---

### Задача 2.22
Отправьте multipart запрос с файлом.

```java
@Test
public void testMultipartFile() {
    // Напишите код здесь
}
```

---

### Задача 2.23
Отправьте multipart с несколькими файлами.

```java
@Test
public void testMultipleFiles() {
    // Напишите код здесь
}
```

---

### Задача 2.24
Отправьте form data с Map параметров.

```java
@Test
public void testFormDataWithMap() {
    // Напишите код здесь
}
```

---

### Задача 2.25
Отправьте multipart с JSON content.

```java
@Test
public void testMultipartJson() {
    // Напишите код здесь
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Умею использовать JSON Path
- [ ] Умею использовать XML Path
- [ ] Умею создавать POJO классы
- [ ] Умею отправлять POJO в request
- [ ] Умею получать POJO из response
- [ ] Умею создавать RequestSpecification
- [ ] Умею создавать ResponseSpecification
- [ ] Умею создавать кастомные фильтры
- [ ] Умею устанавливать таймауты
- [ ] Умею настраивать retry
- [ ] Умею отправлять form data
- [ ] Умею отправлять multipart

---

## 📌 Подсказки

<details>
<summary>Подсказка: JSON Path фильтрация</summary>

```java
response.path("items.findAll { it.price > 100 }");
response.path("items.find { it.id == 1 }");
```
</details>

<details>
<summary>Подсказка: POJO класс</summary>

```java
public class User {
    private int id;
    private String name;
    private String email;
    // getters, setters, constructors
}
```
</details>

<details>
<summary>Подсказка: RequestSpecBuilder</summary>

```java
RequestSpecification spec = new RequestSpecBuilder()
    .setBaseUri("https://api.example.com")
    .setContentType(ContentType.JSON)
    .build();
```
</details>

<details>
<summary>Подсказка: Multipart</summary>

```java
given()
    .multiPart("file", new File("path/to/file.txt"))
    .multiPart("description", "Test file")
.when()
    .post("/upload")
.then()
    .statusCode(200);
```
</details>
