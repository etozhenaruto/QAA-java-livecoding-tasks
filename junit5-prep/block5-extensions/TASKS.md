# 📝 Блок 5: Extensions (Расширения)

## Теория для повторения
- `@ExtendWith` — подключение расширений
- Встроенные расширения: `MockitoExtension`, `TempDirectory`
- Создание своих расширений через callback интерфейсы
- `BeforeEachCallback`, `AfterEachCallback`, `BeforeAllCallback`, `AfterAllCallback`
- `ExtensionContext` — доступ к информации о тесте

---

## 📋 Задания

### Задание 5.1: @TempDir для работы с файлами

Создайте тест с использованием временной директории:

```java
@ExtendWith(TempDirectory.class)
class FileTest {
    
    @Test
    void testWithTempFile(@TempDir Path tempDir) throws IOException {
        // Создать файл во временной директории
        Path file = tempDir.resolve("test.txt");
        Files.write(file, List.of("Hello, World!"));
        
        // Проверить что файл существует
        assertTrue(Files.exists(file));
        
        // Прочитать содержимое
        String content = Files.readString(file);
        assertEquals("Hello, World!", content);
    }
    
    @Test
    void testWithTempFile2(@TempDir Path tempDir) throws IOException {
        // Создать поддиректорию
        Path subDir = tempDir.resolve("subdir");
        Files.createDirectory(subDir);
        
        // Проверить что директория существует
        assertTrue(Files.exists(subDir));
        assertTrue(Files.isDirectory(subDir));
    }
}
```

---

### Задание 5.2: Создание TimingExtension

Создайте расширение для замера времени выполнения тестов:

```java
public class TimingExtension implements BeforeEachCallback, AfterEachCallback {
    
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        startTime.set(System.currentTimeMillis());
    }
    
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        long duration = System.currentTimeMillis() - startTime.get();
        String testName = context.getDisplayName();
        System.out.println(String.format("⏱️  Тест '%s' выполнен за %d мс", testName, duration));
    }
}
```

Используйте расширение в тесте:

```java
@ExtendWith(TimingExtension.class)
class TimingTest {
    
    @Test
    void testFastOperation() throws InterruptedException {
        Thread.sleep(10);  // Имитация быстрой операции
        assertTrue(true);
    }
    
    @Test
    void testSlowOperation() throws InterruptedException {
        Thread.sleep(100);  // Имитация медленной операции
        assertTrue(true);
    }
}
```

---

### Задание 5.3: Создание TestCounterExtension

Создайте расширение для подсчёта количества тестов:

```java
public class TestCounterExtension implements BeforeEachCallback, AfterAllCallback {
    
    private static int testCount = 0;
    private static int successCount = 0;
    private static int failureCount = 0;
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        testCount++;
    }
    
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("=".repeat(50));
        System.out.println("📊 СТАТИСТИКА ВЫПОЛНЕНИЯ ТЕСТОВ");
        System.out.println("=".repeat(50));
        System.out.println("Всего тестов: " + testCount);
        System.out.println("Успешных: " + successCount);
        System.out.println("Проваленных: " + failureCount);
        System.out.println("=".repeat(50));
    }
    
    // Методы для обновления счётчиков (вызывать из тестов)
    public static void incrementSuccess() { successCount++; }
    public static void incrementFailure() { failureCount++; }
}
```

---

### Задание 5.4: ExtensionContext для получения информации о тесте

Создайте расширение для логирования информации о тесте:

```java
public class LoggingExtension implements BeforeEachCallback {
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("=".repeat(50));
        System.out.println("🚀 Запуск теста: " + context.getDisplayName());
        System.out.println("   Класс: " + context.getTestClass().orElse(null));
        System.out.println("   Метод: " + context.getTestMethod().orElse(null));
        
        // Получить родительский контекст
        context.getParent().ifPresent(parent -> {
            System.out.println("   Родитель: " + parent.getDisplayName());
        });
        
        System.out.println("=".repeat(50));
    }
}
```

---

### Задание 5.5: Комбинированный Extension

Создайте расширение которое объединяет Timing и Logging:

```java
public class TestInfoExtension implements BeforeEachCallback, AfterEachCallback {
    
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        startTime.set(System.currentTimeMillis());
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("▶️  " + context.getDisplayName());
        System.out.println("=".repeat(60));
    }
    
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        long duration = System.currentTimeMillis() - startTime.get();
        
        String result = context.getExecutionException().isPresent() ? "❌ FAILED" : "✅ PASSED";
        
        System.out.println("=".repeat(60));
        System.out.println(result + " | Время: " + duration + " мс");
        System.out.println("=".repeat(60) + "\n");
    }
}
```

---

### Задание 5.6: Тесты для DataRepository с Extension

Создайте полные тесты для `DataRepository` с использованием `TestInfoExtension`:

```java
@ExtendWith(TestInfoExtension.class)
class DataRepositoryTest {
    
    private DataRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new DataRepository();
    }
    
    @Test
    @DisplayName("Сохранение элемента")
    void testSave() {
        repository.save("item1");
        assertEquals(1, repository.count());
    }
    
    @Test
    @DisplayName("Получение всех элементов")
    void testFindAll() {
        repository.save("item1");
        repository.save("item2");
        List<String> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains("item1"));
        assertTrue(all.contains("item2"));
    }
    
    @Test
    @DisplayName("Удаление элемента")
    void testDelete() {
        repository.save("item1");
        repository.delete("item1");
        assertEquals(0, repository.count());
    }
    
    @Test
    @DisplayName("Проверка существования элемента")
    void testExists() {
        repository.save("item1");
        assertTrue(repository.exists("item1"));
        assertFalse(repository.exists("item2"));
    }
    
    @Test
    @DisplayName("Очистка репозитория")
    void testClear() {
        repository.save("item1");
        repository.save("item2");
        repository.clear();
        assertEquals(0, repository.count());
    }
}
```

---

### Задание 5.7: Condition Extension

Создайте расширение которое пропускает тесты если не выполнено условие:

```java
public class OsConditionExtension implements BeforeEachCallback {
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        // Получить кастомную аннотацию (если есть)
        // Пропустить тест если ОС не соответствует
        
        // Подсказка: используйте context.getTestMethod() для получения аннотаций
    }
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Используется `@ExtendWith` для подключения расширений
- [ ] Используется `@TempDir` для временных файлов
- [ ] Создано расширение с `BeforeEachCallback`
- [ ] Создано расширение с `AfterEachCallback`
- [ ] Используется `ThreadLocal` для хранения состояния между callback
- [ ] Используется `ExtensionContext` для получения информации о тесте
- [ ] Понимаете lifecycle расширений

---

## 📌 Подсказки

<details>
<summary>Подсказка: Импорты для Extensions</summary>

```java
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
```
</details>

<details>
<summary>Подсказка: Extension интерфейсы</summary>

```java
BeforeEachCallback    // Перед каждым тестом
AfterEachCallback     // После каждого теста
BeforeAllCallback     // Перед всеми тестами
AfterAllCallback      // После всех тестов
TestInstancePostProcessor  // После создания экземпляра теста
ParameterResolver     // Для кастомных параметров
```
</details>

<details>
<summary>Подсказка: ExtensionContext методы</summary>

```java
context.getDisplayName()           // Имя теста
context.getTestClass()             // Класс теста
context.getTestMethod()            // Метод теста
context.getParent()                // Родительский контекст
context.getExecutionException()    // Исключение если тест провалился
```
</details>
