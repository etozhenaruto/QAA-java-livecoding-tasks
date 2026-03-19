# 📝 Блок 3: Assumptions (Предположения)

## Теория для повторения
- `assumeTrue(condition)` — выполнить тест только если условие true
- `assumeFalse(condition)` — выполнить тест только если условие false
- `assumingThat(condition, executable)` — выполнить блок кода если условие true
- Разница между Assertion failure (тест провален) и Assumption failure (тест пропущен)

---

## 📋 Задания

### Задание 3.1: assumeTrue для переменной окружения

Создайте тест, который выполняется **только если** установлена переменная окружения `TEST_ENV`:

```java
@Test
void testWithEnvironmentVariable() {
    String testEnv = System.getenv("TEST_ENV");
    
    // Пропустить тест если TEST_ENV не установлена
    assumeTrue(testEnv != null, "TEST_ENV переменная не установлена");
    
    // Этот код выполнится только если TEST_ENV установлена
    // Проверить что значение не пустое
}
```

---

### Задание 3.2: assumeTrue для ОС (Windows)

Создайте тест, который выполняется **только на Windows**:

```java
@Test
void testOnlyOnWindows() {
    String os = System.getProperty("os.name").toLowerCase();
    
    // Пропустить тест если не Windows
    assumeTrue(os.contains("win"), "Тест выполняется только на Windows");
    
    // Проверить что os.name содержит "Windows"
}
```

---

### Задание 3.3: assumeFalse для ОС (не Linux)

Создайте тест, который **НЕ выполняется на Linux**:

```java
@Test
void testNotOnLinux() {
    String os = System.getProperty("os.name").toLowerCase();
    
    // Пропустить тест если Linux
    assumeFalse(os.contains("linux"), "Тест не выполняется на Linux");
    
    // Проверить что os.name не содержит "Linux"
}
```

---

### Задание 3.4: assumingThat для блока кода

Используйте `assumingThat` для выполнения только части теста:

```java
@Test
void testWithConditionalBlock() {
    String os = System.getProperty("os.name").toLowerCase();
    
    // Этот код выполнится всегда
    String userName = System.getProperty("user.name");
    assertNotNull(userName);
    
    // Этот код выполнится только на Windows
    assumingThat(os.contains("win"), () -> {
        // Проверить что user.dir содержит диск (C:\, D:\, и т.д.)
        String userDir = System.getProperty("user.dir");
        assertTrue(userDir.matches("^[A-Z]:\\\\.*"));
    });
}
```

---

### Задание 3.5: assumeTrue с сообщением

Создайте тест с кастомным сообщением при пропуске:

```java
@Test
void testWithCustomSkipMessage() {
    String javaVersion = System.getProperty("java.version");
    
    // Пропустить если Java версия меньше 11
    assumeTrue(
        Integer.parseInt(javaVersion.split("\\.")[0]) >= 11,
        () -> "Тест требует Java 11+, текущая версия: " + javaVersion
    );
    
    // Проверить что версия Java >= 11
}
```

---

### Задание 3.6: Комбинация Assumptions

Создайте тест с несколькими предположениями:

```java
@Test
void testWithMultipleAssumptions() {
    String os = System.getProperty("os.name").toLowerCase();
    String userName = System.getProperty("user.name");
    String testEnv = System.getenv("TEST_ENV");
    
    // Несколько предположений
    assumeTrue(userName != null, "user.name не установлена");
    assumeTrue(testEnv != null, "TEST_ENV не установлена");
    
    // Тест выполнится только если ВСЕ предположения true
    // Проверить что userName не пустой
}
```

---

### Задание 3.7: EnvironmentChecker тесты

Создайте полные тесты для класса `EnvironmentChecker`:

1. **testGetEnvVariable** — с `assumeTrue` для существующей переменной
2. **testIsWindows** — с `assumeTrue` только для Windows
3. **testIsLinux** — с `assumeTrue` только для Linux
4. **testIsMac** — с `assumeTrue` только для macOS
5. **testGetOsName** — без assumptions (всегда выполняется)
6. **testGetUserName** — без assumptions (всегда выполняется)

---

## ✅ Чек-лист для самопроверки

- [ ] Используется `assumeTrue` для условий
- [ ] Используется `assumeFalse` для отрицаний
- [ ] Используется `assumingThat` для блоков кода
- [ ] Есть тесты с кастомными сообщениями
- [ ] Понимаете разницу между Assertion и Assumption
- [ ] Тесты пропускаются (skipped) при false assumption

---

## 📌 Подсказки

<details>
<summary>Подсказка: Импорты для Assumptions</summary>

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
```
</details>

<details>
<summary>Подсказка: Разница между Assertion и Assumption</summary>

```java
// Assertion failure = тест ПРОВАЛЕН ❌
assertEquals("expected", "actual");  // Fail

// Assumption failure = тест ПРОПУЩЕН ⏭️
assumeTrue(false);  // Skipped
```
</details>

<details>
<summary>Подсказка: Lambda для сообщения</summary>

```java
// Ленивое вычисление сообщения (только если assumption false)
assumeTrue(condition, () -> "Дорогостоящее вычисление сообщения");
```
</details>
