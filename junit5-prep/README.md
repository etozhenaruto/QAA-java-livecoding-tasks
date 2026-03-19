# 📚 JUnit5 — Подготовка к собеседованию (AQ Java Automation)

Этот проект содержит теорию и практические задания для подготовки к собеседованию по JUnit5.

## 📁 Структура проекта

```
junit5-prep/
├── README.md                    # Этот файл (теория)
├── block1-basics/               # Базовые аннотации и assertions
├── block2-parameterized/        # Parameterized тесты
├── block3-assumptions/          # Assumptions (предположения)
├── block4-nested-displayname/   # Nested тесты и DisplayName
├── block5-extensions/           # Extensions (расширения)
├── answers/                     # Ответы для самопроверки
└── src/                         # Классы-заготовки для тестирования
```

---

## 📖 Теория

### 1. Архитектура JUnit5

JUnit5 состоит из трёх основных модулей:

| Модуль | Описание |
|--------|----------|
| **JUnit Platform** | Запускает тесты на JVM, предоставляет TestEngine API |
| **JUnit Jupiter** | Новый API для написания тестов (annotations, extensions) |
| **JUnit Vintage** | Обратная совместимость с JUnit 3/4 |

**Важно:** В JUnit5 больше нет `@RunWith` — используется механизм **Extensions**.

---

### 2. Основные аннотации

| Аннотация | Описание |
|-----------|----------|
| `@Test` | Обозначает метод как тест |
| `@BeforeEach` | Выполняется перед каждым тестом (аналог `@Before`) |
| `@AfterEach` | Выполняется после каждого теста (аналог `@After`) |
| `@BeforeAll` | Выполняется один раз перед всеми тестами (должен быть `static`) |
| `@AfterAll` | Выполняется один раз после всех тестов (должен быть `static`) |
| `@Disabled` | Отключает тест или класс тестов |
| `@Tag("name")` | Тег для фильтрации тестов |
| `@Nested` | Вложенный класс для группировки тестов |
| `@DisplayName("name")` | Кастомное имя для теста/класса |
| `@RepeatedTest(n)` | Повторяет тест n раз |
| `@Timeout(value, unit)` | Ограничивает время выполнения теста |

#### Lifecycle аннотации — порядок выполнения:

```
@BeforeAll (1 раз)
    @BeforeEach → @Test → @AfterEach
    @BeforeEach → @Test → @AfterEach
    @BeforeEach → @Test → @AfterEach
@AfterAll (1 раз)
```

---

### 3. Assertions (Утверждения)

Все методы находятся в классе `org.junit.jupiter.api.Assertions`.

#### Основные assertions:

```java
// Равенство
assertEquals(expected, actual);
assertEquals(expected, actual, delta); // для double с погрешностью
assertNotEquals(unexpected, actual);

// Логические
assertTrue(condition);
assertFalse(condition);

// Null проверки
assertNull(object);
assertNotNull(object);

// Ссылки
assertSame(expected, actual);        // Одна и та же ссылка
assertNotSame(unexpected, actual);   // Разные ссылки

// Массивы
assertArrayEquals(expected, actual);

// Исключения
assertThrows(Exception.class, () -> {
    // код который должен бросить исключение
});

// Время выполнения
assertTimeout(Duration.ofMillis(100), () -> {
    // код должен выполниться за 100мс
});

// Группировка assertions
assertAll(
    () -> assertEquals(1, result.getX()),
    () -> assertEquals(2, result.getY()),
    () -> assertTrue(result.isValid())
);
```

#### Проверка исключения с деталями:

```java
Exception exception = assertThrows(IllegalArgumentException.class, () -> {
    methodThatThrows();
});
assertEquals("Expected message", exception.getMessage());
```

---

### 4. Assumptions (Предположения)

Assumptions используются для пропуска тестов при определённых условиях.

**Отличие от Assertions:** 
- Assertion failure = тест **провален** ❌
- Assumption failure = тест **пропущен** ⏭️

```java
import static org.junit.jupiter.api.Assumptions.*;

// Пропустить тест если условие false
assumeTrue(condition);
assumeFalse(condition);

// Пропустить тест с сообщением
assumeTrue(condition, "Reason message");

// Выполнить блок кода только если условие true
assumingThat(condition, () -> {
    // код выполняется только если condition == true
});
```

**Use cases:**
- Тесты только для определённой ОС
- Тесты только при наличии переменной окружения
- Тесты только в CI/CD

---

### 5. Parameterized Tests

Позволяют запускать один тест с разными параметрами.

**Зависимость (pom.xml):**
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

#### Источники данных:

**@ValueSource** — простые значения:
```java
@ParameterizedTest
@ValueSource(ints = {1, 3, 5, 7, 9})
void testOddNumbers(int number) {
    assertTrue(number % 2 != 0);
}
```

**@CsvSource** — CSV данные:
```java
@ParameterizedTest
@CsvSource({
    "1, one",
    "2, two", 
    "3, three"
})
void testNumberToWord(int number, String word) {
    // ...
}
```

**@MethodSource** — метод-поставщик данных:
```java
@ParameterizedTest
@MethodSource("dataProvider")
void testWithMethodSource(String input) {
    // ...
}

static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of("value1"),
        Arguments.of("value2")
    );
}
```

**@EnumSource** — значения enum:
```java
@ParameterizedTest
@EnumSource(DayOfWeek.class)
void testAllDays(DayOfWeek day) {
    // ...
}
```

**@NullSource, @EmptySource** — null и пустые значения:
```java
@ParameterizedTest
@NullSource
@EmptySource
void testNullOrEmpty(String value) {
    // ...
}
```

---

### 6. Nested Tests и DisplayName

**@Nested** — группировка тестов по сценариям:

```java
@Nested
class Registration {
    @Test
    void successfulRegistration() { }
    
    @Test
    void registrationWithInvalidEmail() { }
}

@Nested
class Login {
    @Test
    void successfulLogin() { }
    
    @Test
    void loginWithWrongPassword() { }
}
```

**@DisplayName** — читаемые имена:

```java
@DisplayName("Тесты для класса UserService")
class UserServiceTest {
    
    @DisplayName("Регистрация нового пользователя")
    @Test
    void register() { }
    
    @DisplayName("Удаление пользователя по ID")
    @Test
    void deleteById() { }
}
```

**@DisplayNameGeneration** — автоматическая генерация имён:

```java
@DisplayNameGeneration(ReplaceUnderscores.class)
class MyTest {
    @Test
    void should_return_true_when_input_is_valid() { }
}

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class MyTest { }
```

---

### 7. Extensions (Расширения)

Extensions заменяют `@RunWith` из JUnit4.

#### Встроенные extensions:

**@ExtendWith(MockitoExtension.class)** — моки через Mockito:
```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
}
```

**@TempDir** — временные файлы:
```java
@Test
void testWithTempFile(@TempDir Path tempDir) throws IOException {
    Path file = tempDir.resolve("test.txt");
    // ...
}
```

#### Создание своего Extension:

```java
public class TimingExtension implements BeforeEachCallback, AfterEachCallback {
    
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    
    @Override
    public void beforeEach(ExtensionContext context) {
        startTime.set(System.currentTimeMillis());
    }
    
    @Override
    public void afterEach(ExtensionContext context) {
        long duration = System.currentTimeMillis() - startTime.get();
        System.out.println("Тест " + context.getDisplayName() + " выполнен за " + duration + "мс");
    }
}

// Использование:
@ExtendWith(TimingExtension.class)
class MyTest { }
```

---

### 8. Test Instance Lifecycle

**PER_METHOD** (по умолчанию) — новый экземпляр для каждого теста:
```java
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MyTest {
    // Новый объект для каждого @Test
}
```

**PER_CLASS** — один экземпляр для всех тестов:
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MyTest {
    // Один объект для всех тестов
    // @BeforeAll может быть non-static
}
```

---

### 9. Повторяющиеся тесты

```java
@RepeatedTest(5)
void testRepeated(RepetitionInfo info) {
    System.out.println("Повторение " + info.getCurrentRepetition());
}

@RepeatedTest(value = 3, name = "{displayName} - повторение {currentRepetition} из {totalRepetitions}")
@DisplayName("Custom name test")
void testWithCustomName(RepetitionInfo info) { }
```

---

### 10. Параллельное выполнение тестов

**junit-platform.properties:**
```properties
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = concurrent
junit.jupiter.execution.parallel.mode.classes.default = concurrent
```

**Аннотации:**
```java
@ResourceLock(value = "system-properties", mode = READ)
@ResourceLock(value = "java.util.Locale", mode = READ_WRITE)
@Isolated  // Тест не должен выполняться параллельно
```

---

## 🎯 Вопросы для самопроверки

1. В чём разница между `@BeforeEach` и `@BeforeAll`?
2. Когда использовать Assumptions вместо Assertions?
3. Какие источники данных для Parameterized тестов вы знаете?
4. Как проверить, что метод бросает исключение?
5. В чём разница между `assertSame` и `assertEquals`?
6. Зачем нужны Nested тесты?
7. Как создать свой Extension?
8. Что такое TestInstance.Lifecycle.PER_CLASS?
9. Как ограничить время выполнения теста?
10. Как запустить тесты с определённым тегом?

---

## 🚀 Как использовать

1. Изучите теорию в этом файле
2. Выполняйте задания в папках `block1-*` ... `block5-*`
3. Сверяйтесь с ответами в папке `answers/`
4. Запускайте тесты через `mvn test`

Удачи в подготовке! 🍀
