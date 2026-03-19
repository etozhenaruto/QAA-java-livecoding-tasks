# 🎯 Вопросы для собеседования по JUnit5 (AQ Java Automation)

## 🔹 Базовые вопросы

### 1. Что такое JUnit5 и чем он отличается от JUnit4?

**Ответ:**
JUnit5 — это фреймворк для тестирования Java-кода нового поколения.

**Основные отличия от JUnit4:**
| JUnit4 | JUnit5 |
|--------|--------|
| `@RunWith` | `@ExtendWith` |
| `@Before`, `@After` | `@BeforeEach`, `@AfterEach` |
| `@BeforeClass`, `@AfterClass` | `@BeforeAll`, `@AfterAll` |
| `@Ignore` | `@Disabled` |
| Один тестовый движок | Модульная архитектура (Platform + Jupiter + Vintage) |
| Нет параметризованных тестов | Есть `@ParameterizedTest` |
| Нет Extensions | Мощная система Extensions |

---

### 2. Назовите основные аннотации JUnit5

**Ответ:**
- `@Test` — метод теста
- `@BeforeEach` — перед каждым тестом
- `@AfterEach` — после каждого теста
- `@BeforeAll` — перед всеми тестами (static)
- `@AfterAll` — после всех тестов (static)
- `@Disabled` — отключить тест
- `@Tag` — тег для фильтрации
- `@Nested` — вложенный класс тестов
- `@DisplayName` — кастомное имя
- `@RepeatedTest` — повторяющийся тест
- `@Timeout` — ограничение по времени

---

### 3. В чём разница между `@BeforeEach` и `@BeforeAll`?

**Ответ:**
- `@BeforeEach` выполняется **перед каждым** `@Test` методом
- `@BeforeAll` выполняется **один раз перед всеми** тестами в классе
- `@BeforeAll` должен быть `static` (при lifecycle PER_METHOD)

---

### 4. Что такое Assertions? Назовите основные виды

**Ответ:**
Assertions — это утверждения для проверки результатов тестов.

**Основные виды:**
```java
assertEquals(expected, actual)           // Равенство
assertNotEquals(unexpected, actual)      // Неравенство
assertTrue(condition)                    // Истина
assertFalse(condition)                   // Ложь
assertNull(object)                       // Null
assertNotNull(object)                    // Not null
assertSame(expected, actual)             // Одинаковые ссылки
assertNotSame(unexpected, actual)        // Разные ссылки
assertArrayEquals(expected, actual)      // Массивы равны
assertThrows(Exception.class, () -> {})  // Бросает исключение
assertTimeout(duration, () -> {})        // Укладывается во время
assertAll(() -> {}, () -> {})            // Группа assertions
```

---

### 5. В чём разница между `assertEquals` и `assertSame`?

**Ответ:**
- `assertEquals` — сравнивает **значения** через метод `equals()`
- `assertSame` — сравнивает **ссылки** (на один ли объект в памяти)

```java
String a = new String("hello");
String b = new String("hello");

assertEquals(a, b);  // true (значения равны)
assertSame(a, b);    // false (разные объекты)
```

---

### 6. Как проверить что метод бросает исключение?

**Ответ:**
Использовать `assertThrows`:

```java
@Test
void testException() {
    assertThrows(IllegalArgumentException.class, () -> {
        methodThatThrows();
    });
}

// С проверкой сообщения
IllegalArgumentException exception = assertThrows(
    IllegalArgumentException.class, 
    () -> methodThatThrows()
);
assertEquals("Expected message", exception.getMessage());
```

---

### 7. Что такое Assumptions и чем отличаются от Assertions?

**Ответ:**
Assumptions — это предположения для пропуска тестов при определённых условиях.

**Разница:**
- **Assertion failure** = тест **ПРОВАЛЕН** ❌
- **Assumption failure** = тест **ПРОПУЩЕН** ⏭️

```java
assumeTrue(condition);        // Пропустить если false
assumeFalse(condition);       // Пропустить если true
assumingThat(cond, () -> {}); // Выполнить блок если true
```

---

## 🔹 Parameterized Tests

### 8. Какие источники данных для Parameterized тестов вы знаете?

**Ответ:**
- `@ValueSource` — простые значения (int, String, long)
- `@CsvSource` — CSV данные
- `@MethodSource` — метод-поставщик данных (Stream<Arguments>)
- `@EnumSource` — значения enum
- `@NullSource`, `@EmptySource` — null и пустые значения
- `@ArgumentSources` — комбинация источников

---

### 9. Пример использования @MethodSource

**Ответ:**
```java
@ParameterizedTest
@MethodSource("dataProvider")
void testSomething(String input, boolean expected) {
    assertEquals(expected, method(input));
}

static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of("hello", true),
        Arguments.of("world", false)
    );
}
```

---

## 🔹 Nested Tests

### 10. Зачем нужны @Nested тесты?

**Ответ:**
`@Nested` используется для логической группировки тестов по сценариям/функциональности.

**Преимущества:**
- Лучшая читаемость отчётов
- Организация тестов по доменным понятиям
- Возможность иметь отдельные `@BeforeEach` для каждой группы

```java
@Nested
class Registration {
    @Test void successful() {}
    @Test void withInvalidEmail() {}
}

@Nested
class Login {
    @Test void successful() {}
    @Test void withWrongPassword() {}
}
```

---

## 🔹 Extensions

### 11. Что такое Extensions и чем заменили @RunWith?

**Ответ:**
Extensions — это механизм расширения функциональности JUnit5.

В JUnit5 `@RunWith` заменён на `@ExtendWith`:

```java
@ExtendWith(MockitoExtension.class)
class MyTest {
    @Mock private Repository repo;
}
```

**Встроенные Extensions:**
- `MockitoExtension` — моки
- `TempDirectory` — временные файлы (`@TempDir`)
- `TimeoutExtension` — таймауты

---

### 12. Какие callback интерфейсы Extensions вы знаете?

**Ответ:**
- `BeforeEachCallback` — перед каждым тестом
- `AfterEachCallback` — после каждого теста
- `BeforeAllCallback` — перед всеми тестами
- `AfterAllCallback` — после всех тестов
- `TestInstancePostProcessor` — после создания экземпляра
- `ParameterResolver` — для кастомных параметров

---

### 13. Что такое ExtensionContext?

**Ответ:**
`ExtensionContext` — это контекст выполнения теста, который предоставляет информацию о:
- Имени теста (`getDisplayName()`)
- Классе теста (`getTestClass()`)
- Методе теста (`getTestMethod()`)
- Родительском контексте (`getParent()`)
- Исключении выполнения (`getExecutionException()`)

---

## 🔹 Test Lifecycle

### 14. Что такое TestInstance.Lifecycle?

**Ответ:**
Определяет как создаются экземпляры тестовых классов.

**PER_METHOD** (по умолчанию):
- Новый экземпляр для каждого `@Test`
- Тесты изолированы
- `@BeforeAll` должен быть `static`

**PER_CLASS**:
- Один экземпляр для всех тестов
- Тесты могут влиять друг на друга
- `@BeforeAll` может быть non-static

---

### 15. Почему @BeforeAll должен быть static?

**Ответ:**
При lifecycle `PER_METHOD` для каждого теста создаётся новый экземпляр класса. `@BeforeAll` выполняется до создания любого экземпляра, поэтому должен принадлежать классу, а не объекту.

При `PER_CLASS` можно использовать non-static `@BeforeAll`.

---

## 🔹 Продвинутые вопросы

### 16. Как запустить тесты с определённым тегом?

**Ответ:**
Через Maven/Gradle или IDE:

```bash
# Maven
mvn test -Dgroups=tagName

# Gradle
gradle test --tests "*tagName*"

# В коде
@Tag("slow")
@Test
void slowTest() {}

# Запуск только тестов с тегом "slow"
```

---

### 17. Как ограничить время выполнения теста?

**Ответ:**
Использовать `@Timeout` или `assertTimeout`:

```java
@Test
@Timeout(100)  // 100 мс
void testWithTimeout() {}

@Test
void testWithAssertTimeout() {
    assertTimeout(Duration.ofMillis(100), () -> {
        // код
    });
}
```

---

### 18. Что такое @RepeatedTest?

**Ответ:**
Повторяет тест указанное количество раз:

```java
@RepeatedTest(5)
void testRepeated(RepetitionInfo info) {
    System.out.println("Повторение " + info.getCurrentRepetition());
}

@RepeatedTest(value = 3, name = "{displayName} - {currentRepetition}/{totalRepetitions}")
@DisplayName("Custom test")
void testWithCustomName() {}
```

---

### 19. Как создать свой Extension?

**Ответ:**
Реализовать один из callback интерфейсов:

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
        System.out.println("Тест выполнен за " + duration + " мс");
    }
}

// Использование:
@ExtendWith(TimingExtension.class)
class MyTest {}
```

---

### 20. Как запустить тесты параллельно?

**Ответ:**
Через `junit-platform.properties`:

```properties
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = concurrent
junit.jupiter.execution.parallel.mode.classes.default = concurrent
```

Или через аннотации:
```java
@ResourceLock(value = "system-properties", mode = READ)
@Isolated  // Не выполнять параллельно
```

---

## 🔹 Практические вопросы

### 21. Чем MockitoExtension полезен?

**Ответ:**
Автоматически создаёт и инжектирует моки:

```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
    
    @Test
    void test() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        // ...
    }
}
```

---

### 22. Что делает @TempDir?

**Ответ:**
Создаёт временную директорию которая автоматически очищается после теста:

```java
@Test
void testWithTempFile(@TempDir Path tempDir) throws IOException {
    Path file = tempDir.resolve("test.txt");
    Files.write(file, List.of("content"));
    // ...
}
```

---

### 23. Как пропустить тест на определённой ОС?

**Ответ:**
Использовать Assumptions:

```java
@Test
void testOnlyOnWindows() {
    String os = System.getProperty("os.name").toLowerCase();
    assumeTrue(os.contains("win"), "Тест только для Windows");
    // ...
}
```

---

## 📚 Дополнительные темы для изучения

1. **Mockito** — мокирование зависимостей
2. **AssertJ** — fluent assertions
3. **TestContainers** — интеграционные тесты с Docker
4. **Spring Boot Test** — тестирование Spring приложений
5. **WireMock** — мокирование HTTP сервисов
6. **ArchUnit** — тестирование архитектуры кода

---

## ✅ Чек-лист готовности

- [ ] Знаю все основные аннотации JUnit5
- [ ] Понимаю разницу между Assertions и Assumptions
- [ ] Умею писать Parameterized тесты
- [ ] Понимаю как работают Nested тесты
- [ ] Знаю как создать свой Extension
- [ ] Понимаю TestInstance Lifecycle
- [ ] Умею проверять исключения
- [ ] Знаю как ограничить время теста
- [ ] Понимаю разницу между JUnit4 и JUnit5
