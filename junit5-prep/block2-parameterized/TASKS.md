# 📝 Блок 2: Parameterized Tests

## Теория для повторения
- `@ParameterizedTest`
- `@ValueSource` — простые значения
- `@CsvSource` — CSV данные
- `@MethodSource` — метод-поставщик данных
- `@EnumSource` — значения enum
- `@NullSource`, `@EmptySource`

---

## 📋 Задания

### Задание 2.1: ValueSource для Calculator

Создайте параметризованные тесты для метода `isEven`:

```java
@ParameterizedTest
@ValueSource(ints = {2, 4, 6, 8, 10, 100, 1000})
void testEvenNumbers(int number) {
    // Проверить что число чётное
}
```

Добавьте второй тест для нечётных чисел:
```java
@ValueSource(ints = {1, 3, 5, 7, 9, 99, 999})
```

---

### Задание 2.2: CsvSource для Calculator

Создайте параметризованный тест для метода `add`:

```java
@ParameterizedTest
@CsvSource({
    "1, 2, 3",
    "5, 5, 10",
    "10, 20, 30",
    "-5, 5, 0",
    "0, 0, 0",
    "100, 200, 300"
})
void testAddition(int a, int b, int expected) {
    // Проверить что a + b == expected
}
```

---

### Задание 2.3: CsvSource для StringValidator

Создайте параметризованные тесты для `truncate`:

```java
@ParameterizedTest
@CsvSource({
    "'hello world', 5, 'hello'",
    "'hi', 10, 'hi'",
    "'abcdef', 3, 'abc'",
    "'test', 4, 'test'"
})
void testTruncate(String input, int maxLength, String expected) {
    // Проверить truncate
}
```

**Примечание:** Используйте кавычки для строк содержащих пробелы.

---

### Задание 2.4: MethodSource для сложных данных

Создайте параметризованный тест для `isPalindrome` с использованием `@MethodSource`:

```java
@ParameterizedTest
@MethodSource("palindromeDataProvider")
void testPalindrome(String input, boolean expected) {
    // Проверить isPalindrome
}

static Stream<Arguments> palindromeDataProvider() {
    return Stream.of(
        Arguments.of("A man a plan a canal Panama", true),
        Arguments.of("hello", false),
        Arguments.of("А роза упала на лапу Азора", true),
        Arguments.of("12321", true),
        Arguments.of("12345", false),
        Arguments.of("", true),  // Пустая строка — палиндром
        Arguments.of("a", true)  // Один символ — палиндром
    );
}
```

---

### Задание 2.5: EnumSource

Создайте тест который проверяет что все дни недели имеют название:

```java
public enum DayOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");
    
    private final String russianName;
    
    DayOfWeek(String russianName) {
        this.russianName = russianName;
    }
    
    public String getRussianName() {
        return russianName;
    }
}

@ParameterizedTest
@EnumSource(DayOfWeek.class)
void testAllDaysHaveRussianName(DayOfWeek day) {
    // Проверить что russianName не null и не пустой
}
```

---

### Задание 2.6: NullSource и EmptySource

Создайте параметризованные тесты для `StringValidator.isEmpty`:

```java
@ParameterizedTest
@NullSource
@EmptySource
void testEmptyOrNullStrings(String str) {
    // isEmpty должен вернуть true для null и пустой строки
}
```

---

### Задание 2.7: MethodSource с несколькими параметрами

Создайте параметризованный тест для `Calculator.factorial`:

```java
@ParameterizedTest
@MethodSource("factorialDataProvider")
void testFactorial(int input, long expected) {
    // Проверить factorial
}

static Stream<Arguments> factorialDataProvider() {
    return Stream.of(
        Arguments.of(0, 1),
        Arguments.of(1, 1),
        Arguments.of(2, 2),
        Arguments.of(3, 6),
        Arguments.of(4, 24),
        Arguments.of(5, 120),
        Arguments.of(10, 3628800)
    );
}
```

---

### Задание 2.8: Custom Name для параметризованных тестов

Используйте `name` атрибут для кастомного имени теста:

```java
@ParameterizedTest
@CsvSource({"1, 2, 3", "5, 5, 10"})
@DisplayName("Тест сложения: {0} + {1} = {2}")
void testAddWithCustomName(int a, int b, int expected) {
    // ...
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Используется `@ParameterizedTest` вместо `@Test`
- [ ] Используется `@ValueSource` для простых значений
- [ ] Используется `@CsvSource` для табличных данных
- [ ] Используется `@MethodSource` для сложных данных
- [ ] Используется `@EnumSource` для enum
- [ ] Используется `@NullSource` и `@EmptySource`
- [ ] Есть кастомные имена для тестов

---

## 📌 Подсказки

<details>
<summary>Подсказка: Импорты для Parameterized тестов</summary>

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;
import org.junit.jupiter.api.Arguments;
```
</details>

<details>
<summary>Подсказка: Пример полного теста с MethodSource</summary>

```java
@ParameterizedTest
@MethodSource("dataProvider")
void testSomething(String input, boolean expected) {
    assertEquals(expected, validator.validate(input));
}

static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of("hello", true),
        Arguments.of("world", false)
    );
}
```
</details>
