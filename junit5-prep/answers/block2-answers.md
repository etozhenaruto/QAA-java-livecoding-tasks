# ✅ Ответы: Блок 2 — Parameterized Tests

## Задание 2.1: ValueSource для isEven

```java
package com.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Parameterized тесты для Calculator.isEven")
class CalculatorIsEvenTest {
    
    private final Calculator calculator = new Calculator();
    
    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8, 10, 100, 1000})
    @DisplayName("Чётные числа")
    void testEvenNumbers(int number) {
        assertTrue(calculator.isEven(number), 
            number + " должно быть чётным");
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7, 9, 99, 999})
    @DisplayName("Нечётные числа")
    void testOddNumbers(int number) {
        assertTrue(!calculator.isEven(number), 
            number + " должно быть нечётным");
    }
}
```

---

## Задание 2.2: CsvSource для add

```java
package com.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Parameterized тесты для Calculator.add")
class CalculatorAddTest {
    
    private final Calculator calculator = new Calculator();
    
    @ParameterizedTest
    @CsvSource({
        "1, 2, 3",
        "5, 5, 10",
        "10, 20, 30",
        "-5, 5, 0",
        "0, 0, 0",
        "100, 200, 300",
        "-10, -20, -30"
    })
    @DisplayName("Сложение: {0} + {1} = {2}")
    void testAddition(int a, int b, int expected) {
        int result = calculator.add(a, b);
        assertEquals(expected, result, 
            String.format("%d + %d должно равняться %d", a, b, expected));
    }
}
```

---

## Задание 2.3: CsvSource для truncate

```java
package com.example.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Parameterized тесты для StringValidator.truncate")
class StringValidatorTruncateTest {
    
    private StringValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new StringValidator();
    }
    
    @ParameterizedTest
    @CsvSource({
        "'hello world', 5, 'hello'",
        "'hi', 10, 'hi'",
        "'abcdef', 3, 'abc'",
        "'test', 4, 'test'",
        "'long string', 4, 'long'"
    })
    @DisplayName("Обрезка строки")
    void testTruncate(String input, int maxLength, String expected) {
        String result = validator.truncate(input, maxLength);
        assertEquals(expected, result);
    }
}
```

---

## Задание 2.4: MethodSource для isPalindrome

```java
package com.example.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Parameterized тесты для StringValidator.isPalindrome")
class StringValidatorPalindromeTest {
    
    private StringValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new StringValidator();
    }
    
    @ParameterizedTest
    @MethodSource("palindromeDataProvider")
    @DisplayName("Проверка палиндрома")
    void testPalindrome(String input, boolean expected) {
        boolean result = validator.isPalindrome(input);
        assertEquals(expected, result, 
            "'" + input + "' должен" + (expected ? "" : " не") + " быть палиндромом");
    }
    
    static Stream<Arguments> palindromeDataProvider() {
        return Stream.of(
            Arguments.of("A man a plan a canal Panama", true),
            Arguments.of("hello", false),
            Arguments.of("А роза упала на лапу Азора", true),
            Arguments.of("12321", true),
            Arguments.of("12345", false),
            Arguments.of("", true),
            Arguments.of("a", true),
            Arguments.of("ab", false)
        );
    }
}
```

---

## Задание 2.5: EnumSource для DayOfWeek

```java
package com.example.enums;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class DayOfWeekTest {
    
    enum DayOfWeek {
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
    @DisplayName("Все дни недели имеют русское название")
    void testAllDaysHaveRussianName(DayOfWeek day) {
        assertNotNull(day.getRussianName(), 
            day.name() + " должен иметь русское название");
        assertFalse(day.getRussianName().isEmpty(), 
            day.name() + " должен иметь непустое русское название");
    }
    
    @ParameterizedTest
    @EnumSource(value = DayOfWeek.class, mode = EnumSource.Mode.EXCLUDE, names = {"SATURDAY", "SUNDAY"})
    @DisplayName("Будние дни")
    void testWeekdays(DayOfWeek day) {
        assertTrue(day.ordinal() < 5);  // Пн-Пт имеют индекс 0-4
    }
}
```

---

## Задание 2.6: NullSource и EmptySource

```java
package com.example.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для пустых и null строк")
class StringValidatorEmptyTest {
    
    private StringValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new StringValidator();
    }
    
    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("isEmpty для null и пустой строки")
    void testEmptyOrNullStrings(String str) {
        assertTrue(validator.isEmpty(str));
    }
}
```

---

## Задание 2.7: MethodSource для factorial

```java
package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Parameterized тесты для Calculator.factorial")
class CalculatorFactorialTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @ParameterizedTest
    @MethodSource("factorialDataProvider")
    @DisplayName("Факториал {0}! = {1}")
    void testFactorial(int input, long expected) {
        long result = calculator.factorial(input);
        assertEquals(expected, result, 
            input + "! должно равняться " + expected);
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
}
```

---

## Задание 2.8: Custom Name для тестов

```java
package com.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorCustomNameTest {
    
    private final Calculator calculator = new Calculator();
    
    @ParameterizedTest
    @CsvSource({"1, 2, 3", "5, 5, 10", "100, 200, 300"})
    @DisplayName("Тест сложения: {0} + {1} = {2}")
    void testAddWithCustomName(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }
    
    @ParameterizedTest
    @CsvSource({"10, 4, 6", "20, 8, 12"})
    @DisplayName("Тест вычитания: {0} - {1} = {2}")
    void testSubtractWithCustomName(int a, int b, int expected) {
        assertEquals(expected, calculator.subtract(a, b));
    }
}
```

---

## Вопросы для самопроверки — Ответы

1. **Какие источники данных для Parameterized тестов вы знаете?**
   - `@ValueSource` — простые значения (int, long, String)
   - `@CsvSource` — CSV данные
   - `@MethodSource` — метод-поставщик данных
   - `@EnumSource` — значения enum
   - `@NullSource`, `@EmptySource` — null и пустые значения
   - `@ArgumentSources` — комбинация источников

2. **В чём разница между `@Test` и `@ParameterizedTest`?**
   - `@Test` запускается один раз
   - `@ParameterizedTest` запускается多次 с разными параметрами

3. **Когда использовать `@MethodSource` вместо `@CsvSource`?**
   - Когда нужны сложные объекты
   - Когда данные генерируются динамически
   - Когда нужно много параметров

4. **Как задать кастомное имя для параметризованного теста?**
   - Использовать атрибут `name`: `@CsvSource(value = {...}, name = "{0} + {1} = {2}")`
   - Или `@DisplayName` с плейсхолдерами
