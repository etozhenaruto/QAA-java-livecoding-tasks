# 📚 REST Assured — Подготовка к собеседованию (AQ Java Automation)

Этот проект содержит подробную теорию, практические задания и вопросы для подготовки к собеседованию по REST Assured.

## 📁 Структура проекта

```
rest-assured-prep/
├── README.md                        # Главный файл с навигацией
├── block1-basics/
│   ├── THEORY.md                    # Основы REST Assured
│   ├── TASKS.md                     # 15 задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов
├── block2-advanced/
│   ├── THEORY.md                    # Продвинутые темы
│   ├── TASKS.md                     # 15 задач
│   └── INTERVIEW_QUESTIONS.md       # 15 вопросов
├── block3-authentication/
│   ├── THEORY.md                    # Аутентификация и фильтры
│   ├── TASKS.md                     # 10 задач
│   └── INTERVIEW_QUESTIONS.md       # 15 вопросов
├── answers/
│   ├── block1-answers.md            # Ответы: Основы
│   ├── block2-answers.md            # Ответы: Продвинутые
│   └── block3-answers.md            # Ответы: Аутентификация
└── data/                            # Примеры JSON и спецификации
```

---

## 📖 Содержание по блокам

### 🔹 Блок 1: Основы REST Assured

**Файлы:**
- `block1-basics/THEORY.md` — теория
- `block1-basics/TASKS.md` — 15 задач
- `block1-basics/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- Что такое REST Assured
- Maven зависимости
- Базовый синтаксис (given/when/then)
- HTTP методы: GET, POST, PUT, DELETE
- Проверка статус кодов
- Проверка response body
- Query parameters и path parameters
- Headers и cookies

---

### 🔹 Блок 2: Продвинутые темы

**Файлы:**
- `block2-advanced/THEORY.md` — теория
- `block2-advanced/TASKS.md` — 15 задач
- `block2-advanced/INTERVIEW_QUESTIONS.md` — 15 вопросов

**Темы:**
- JSON Path и XML Path
- Hamcrest матчеры
- Serialization/Deserialization
- POJO классы (Request/Response)
- Request и Response спецификации
- Логирование запросов и ответов
- Таймауты и retry
- Мultipart/form-data

---

### 🔹 Блок 3: Аутентификация и фильтры

**Файлы:**
- `block3-authentication/THEORY.md` — теория
- `block3-authentication/TASKS.md` — 10 задач
- `block3-authentication/INTERVIEW_QUESTIONS.md` — 15 вопросов

**Темы:**
- Basic Auth
- Bearer Token / OAuth2
- API Key
- Фильтры (RequestFilter, ResponseFilter)
- Allure интеграция
- TestNG интеграция
- Page Object для API
- Best practices

---

## 🎯 Как использовать этот проект

### Шаг 1: Настройка проекта

**Maven зависимости (pom.xml):**
```xml
<dependencies>
    <!-- REST Assured -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- JSON Path -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>json-path</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Jackson для JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.0</version>
    </dependency>
    
    <!-- TestNG (опционально) -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Allure (опционально) -->
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-rest-assured</artifactId>
        <version>2.27.0</version>
    </dependency>
</dependencies>
```

---

### Шаг 2: Изучение теории

1. Откройте папку нужного блока
2. Прочитайте `THEORY.md` — подробная теория с примерами
3. Изучите ключевые концепции

---

### Шаг 3: Практика

1. Откройте `TASKS.md` в папке блока
2. Выполните все задачи
3. Используйте подсказки если застряли

---

### Шаг 4: Самопроверка

1. Откройте `answers/` в корне проекта
2. Найдите файл с ответами для вашего блока
3. Сравните свои решения с эталонными

---

### Шаг 5: Подготовка к собеседованию

1. Откройте `INTERVIEW_QUESTIONS.md` в папке блока
2. Прочитайте вопросы и ответы
3. Попробуйте ответить самостоятельно

---

## 📊 Итоговая статистика

| Блок | Теория | Задачи | Вопросы |
|------|--------|--------|---------|
| Основы REST Assured | ✅ | 15 | 20 |
| Продвинутые темы | ✅ | 15 | 15 |
| Аутентификация | ✅ | 10 | 15 |
| **Итого** | **3 файла** | **40 задач** | **50 вопросов** |

---

## 🚀 Быстрый старт

### Простой тест GET запроса:

```java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleTest {
    
    @Test
    public void testGetUsers() {
        given()
            .baseUri("https://api.example.com")
            .basePath("/users")
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("id", hasItem(1));
    }
}
```

### Простой тест POST запроса:

```java
@Test
public void testCreateUser() {
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("name", "John");
    requestBody.put("email", "john@example.com");
    
    given()
        .contentType(ContentType.JSON)
        .body(requestBody)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("name", equalTo("John"));
}
```

---

## ✅ Чек-лист готовности

- [ ] Прочитал теорию по всем 3 блокам
- [ ] Выполнил все 40 практических задач
- [ ] Изучил ответы для самопроверки
- [ ] Повторил все 50 вопросов для собеседования
- [ ] Понимаю REST API концепции
- [ ] Умею писать тесты на REST Assured
- [ ] Готов к техническому интервью!

---

Удачи в подготовке! 🍀
