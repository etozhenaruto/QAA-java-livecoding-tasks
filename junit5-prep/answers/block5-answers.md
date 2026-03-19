# ✅ Ответы: Блок 5 — Extensions

## Задание 5.1: @TempDir для работы с файлами

```java
package com.example.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты с временными файлами")
class TempDirTest {
    
    @Test
    @DisplayName("Создание и чтение временного файла")
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
    @DisplayName("Создание поддиректории")
    void testWithTempSubdir(@TempDir Path tempDir) throws IOException {
        // Создать поддиректорию
        Path subDir = tempDir.resolve("subdir");
        Files.createDirectory(subDir);
        
        // Проверить что директория существует
        assertTrue(Files.exists(subDir));
        assertTrue(Files.isDirectory(subDir));
        
        // Создать файл в поддиректории
        Path file = subDir.resolve("nested.txt");
        Files.write(file, List.of("Nested content"));
        
        assertTrue(Files.exists(file));
        assertEquals("Nested content", Files.readString(file));
    }
    
    @Test
    @DisplayName("Временные файлы очищаются после теста")
    void testTempFilesAreCleanedUp(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("temp.txt");
        Files.write(file, List.of("temp"));
        
        assertTrue(Files.exists(file));
        // После завершения теста tempDir будет очищена автоматически
    }
}
```

---

## Задание 5.2: TimingExtension

```java
package com.example.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension для замера времени выполнения тестов.
 */
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

**Использование:**

```java
package com.example.extensions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TimingExtension.class)
@DisplayName("Тесты с замером времени")
class TimingTest {
    
    @Test
    @DisplayName("Быстрый тест")
    void testFastOperation() throws InterruptedException {
        Thread.sleep(10);
        assertTrue(true);
    }
    
    @Test
    @DisplayName("Медленный тест")
    void testSlowOperation() throws InterruptedException {
        Thread.sleep(100);
        assertTrue(true);
    }
    
    @Test
    @DisplayName("Очень медленный тест")
    void testVerySlowOperation() throws InterruptedException {
        Thread.sleep(250);
        assertTrue(true);
    }
}
```

---

## Задание 5.3: TestCounterExtension

```java
package com.example.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension для подсчёта количества тестов.
 */
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
    
    /**
     * Вызвать после успешного теста.
     */
    public static void reportSuccess() {
        successCount++;
    }
    
    /**
     * Вызвать после проваленного теста.
     */
    public static void reportFailure() {
        failureCount++;
    }
    
    /**
     * Сбросить счётчики (для новых тестовых классов).
     */
    public static void reset() {
        testCount = 0;
        successCount = 0;
        failureCount = 0;
    }
}
```

**Использование с RegisterExtension:**

```java
package com.example.extensions;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCounterExtensionTest {
    
    @RegisterExtension
    static TestCounterExtension counter = new TestCounterExtension();
    
    @BeforeEach
    void setUp() {
        // После каждого теста сообщаем о результате
    }
    
    @AfterEach
    void tearDown(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            TestCounterExtension.reportFailure();
        } else {
            TestCounterExtension.reportSuccess();
        }
    }
    
    @Test
    @DisplayName("Успешный тест 1")
    void test1() {
        assertTrue(true);
    }
    
    @Test
    @DisplayName("Успешный тест 2")
    void test2() {
        assertEquals(4, 2 + 2);
    }
    
    @Test
    @DisplayName("Проваленный тест")
    void test3() {
        fail("Этот тест должен провалиться");
    }
}
```

---

## Задание 5.4: LoggingExtension

```java
package com.example.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension для логирования информации о тесте.
 */
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

## Задание 5.5: Комбинированный TestInfoExtension

```java
package com.example.extensions;

import org.junit.jupiter.api.extension.*;

/**
 * Extension который объединяет Timing и Logging.
 */
public class TestInfoExtension implements BeforeEachCallback, AfterEachCallback {
    
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        startTime.set(System.currentTimeMillis());
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("▶️  " + context.getDisplayName());
        System.out.println("=".repeat(60));
        System.out.println("Класс: " + context.getTestClass().orElse(null));
        System.out.println("Метод: " + context.getTestMethod().orElse(null));
    }
    
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        long duration = System.currentTimeMillis() - startTime.get();
        
        boolean failed = context.getExecutionException().isPresent();
        String result = failed ? "❌ FAILED" : "✅ PASSED";
        
        System.out.println("=".repeat(60));
        System.out.println(result + " | Время: " + duration + " мс");
        
        if (failed) {
            Throwable exception = context.getExecutionException().get();
            System.out.println("Ошибка: " + exception.getMessage());
        }
        
        System.out.println("=".repeat(60) + "\n");
    }
}
```

---

## Задание 5.6: Тесты для DataRepository с Extension

```java
package com.example.data;

import com.example.extensions.TestInfoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestInfoExtension.class)
@DisplayName("Тесты для DataRepository")
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
    
    @Test
    @DisplayName("Сохранение null бросает исключение")
    void testSaveNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            repository.save(null);
        });
    }
}
```

---

## Задание 5.7: OsConditionExtension (продвинутый)

```java
package com.example.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания требуемой ОС.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OsCondition {
    Os value();
    
    enum Os {
        WINDOWS, LINUX, MAC, ANY
    }
}

/**
 * Extension для проверки условия ОС.
 */
public class OsConditionExtension implements BeforeEachCallback {
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        // Ищем аннотацию на методе или классе
        OsCondition annotation = context.getTestMethod()
            .flatMap(m -> m.getAnnotation(OsCondition.class))
            .or(() -> context.getTestClass()
                .flatMap(c -> c.getAnnotation(OsCondition.class)))
            .orElse(null);
        
        if (annotation == null || annotation.value() == OsCondition.Os.ANY) {
            return;
        }
        
        String osName = System.getProperty("os.name").toLowerCase();
        boolean conditionMet = switch (annotation.value()) {
            case WINDOWS -> osName.contains("win");
            case LINUX -> osName.contains("linux");
            case MAC -> osName.contains("mac");
            case ANY -> true;
        };
        
        if (!conditionMet) {
            throw new AssumptionViolatedException(
                "Тест требует ОС: " + annotation.value());
        }
    }
}

// Импорт для AssumptionViolatedException:
// import org.junit.jupiter.api.extension.ExtensionContextException;
// или использовать:
// import org.junit.platform.commons.util.AssumptionViolationException;
```

**Использование:**

```java
package com.example.extensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(OsConditionExtension.class)
class OsConditionTest {
    
    @Test
    @OsCondition(OsCondition.Os.WINDOWS)
    void testOnlyOnWindows() {
        // Выполнится только на Windows
        System.out.println("Running on Windows");
    }
    
    @Test
    @OsCondition(OsCondition.Os.LINUX)
    void testOnlyOnLinux() {
        // Выполнится только на Linux
        System.out.println("Running on Linux");
    }
    
    @Test
    void testOnAnyOs() {
        // Выполнится на любой ОС
        System.out.println("Running on any OS");
    }
}
```

---

## Вопросы для самопроверки — Ответы

1. **Как подключить Extension к тесту?**
   - `@ExtendWith(MyExtension.class)` на классе или методе
   - `@RegisterExtension` для программной регистрации

2. **Какие callback интерфейсы вы знаете?**
   - `BeforeEachCallback`, `AfterEachCallback`
   - `BeforeAllCallback`, `AfterAllCallback`
   - `TestInstancePostProcessor`
   - `ParameterResolver`

3. **Зачем нужен `ThreadLocal` в Extensions?**
   - Для хранения состояния между `beforeEach` и `afterEach`
   - Для потокобезопасности при параллельном выполнении тестов

4. **Что такое `ExtensionContext`?**
   - Контекст выполнения теста
   - Позволяет получить информацию о тесте (имя, класс, метод, исключения)

5. **Как создать временную директорию в тесте?**
   - Использовать `@TempDir Path tempDir` в параметрах метода
   - Или `@ExtendWith(TempDirectory.class)`

6. **В чём разница между `@ExtendWith` и `@RegisterExtension`?**
   - `@ExtendWith` — декларативное подключение
   - `@RegisterExtension` — программное подключение через поле
