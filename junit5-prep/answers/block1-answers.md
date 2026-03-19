# ✅ Ответы: Блок 1 — Базовые аннотации и Assertions

## Задание 1.1: CalculatorTest

```java
package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для Calculator")
class CalculatorTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    @DisplayName("Сложение двух чисел")
    void testAdd() {
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }
    
    @Test
    @DisplayName("Вычитание двух чисел")
    void testSubtract() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result);
    }
    
    @Test
    @DisplayName("Умножение двух чисел")
    void testMultiply() {
        int result = calculator.multiply(3, 7);
        assertEquals(21, result);
    }
    
    @Test
    @DisplayName("Деление двух чисел")
    void testDivide() {
        int result = calculator.divide(20, 4);
        assertEquals(5, result);
    }
    
    @Test
    @DisplayName("Деление на ноль бросает исключение")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(10, 0);
        });
    }
    
    @Test
    @DisplayName("Возведение в степень")
    void testPower() {
        double result = calculator.power(2, 3);
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Факториал положительного числа")
    void testFactorial() {
        long result = calculator.factorial(5);
        assertEquals(120, result);
    }
    
    @Test
    @DisplayName("Факториал отрицательного числа бросает исключение")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.factorial(-1);
        });
    }
}
```

---

## Задание 1.2: StringValidatorTest

```java
package com.example.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для StringValidator")
class StringValidatorTest {
    
    private StringValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new StringValidator();
    }
    
    @Test
    @DisplayName("isEmpty для null и пустой строки")
    void testIsEmpty() {
        assertTrue(validator.isEmpty(null));
        assertTrue(validator.isEmpty(""));
        assertFalse(validator.isEmpty("hello"));
    }
    
    @Test
    @DisplayName("isNullOrBlank для null и blank строк")
    void testIsNullOrBlank() {
        assertTrue(validator.isNullOrBlank(null));
        assertTrue(validator.isNullOrBlank("   "));
        assertFalse(validator.isNullOrBlank("hello"));
    }
    
    @Test
    @DisplayName("isNumeric для числовых строк")
    void testIsNumeric() {
        assertTrue(validator.isNumeric("12345"));
        assertFalse(validator.isNumeric("123abc"));
        assertFalse(validator.isNumeric(""));
    }
    
    @Test
    @DisplayName("isPalindrome для палиндромов")
    void testIsPalindrome() {
        assertTrue(validator.isPalindrome("A man a plan a canal Panama"));
        assertFalse(validator.isPalindrome("hello"));
        assertTrue(validator.isPalindrome("А роза упала на лапу Азора"));
    }
    
    @Test
    @DisplayName("truncate обрезает строку")
    void testTruncate() {
        assertEquals("hello", validator.truncate("hello world", 5));
        assertEquals("hi", validator.truncate("hi", 10));
        assertNull(validator.truncate(null, 5));
    }
    
    @Test
    @DisplayName("repeat повторяет строку")
    void testRepeat() {
        assertEquals("ababab", validator.repeat("ab", 3));
        assertEquals("", validator.repeat("x", 0));
    }
}
```

---

## Задание 1.3: Grouped Assertions

```java
@Test
@DisplayName("Проверка всех свойств пользователя")
void testUserProperties() {
    User user = new User(1, "John", "john@example.com");
    
    assertAll("User properties",
        () -> assertEquals(1, user.getId()),
        () -> assertEquals("John", user.getName()),
        () -> assertEquals("john@example.com", user.getEmail()),
        () -> assertFalse(user.isLoggedIn())
    );
}
```

---

## Задание 1.4: assertTimeout

```java
@Test
@DisplayName("Метод выполняется за 100мс")
void testTimeout() {
    assertTimeout(Duration.ofMillis(100), () -> {
        Thread.sleep(50);  // Быстрая операция
        assertTrue(true);
    });
}
```

---

## Задание 1.5: @Disabled и @BeforeAll/@AfterAll

```java
@DisplayName("Тесты с lifecycle методами")
class LifecycleTest {
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("=== Начало тестов ===");
    }
    
    @AfterAll
    static void afterAll() {
        System.out.println("=== Конец тестов ===");
    }
    
    @Test
    @DisplayName("Активный тест")
    void activeTest() {
        assertTrue(true);
    }
    
    @Test
    @Disabled("Тест отключен пока не будет реализована новая функциональность")
    @DisplayName("Отключенный тест")
    void disabledTest() {
        fail("Этот тест не должен выполняться");
    }
}
```

**Ответ на вопрос:** `@BeforeAll` и `@AfterAll` должны быть `static` потому что при lifecycle `PER_METHOD` (по умолчанию) для каждого теста создаётся новый экземпляр класса. Static методы принадлежат классу, а не экземпляру, поэтому могут быть вызваны до создания любого экземпляра.

---

## Вопросы для самопроверки — Ответы

1. **В чём разница между `@BeforeEach` и `@BeforeAll`?**
   - `@BeforeEach` выполняется перед **каждым** тестом
   - `@BeforeAll` выполняется **один раз** перед всеми тестами (должен быть static)

2. **Как проверить что метод бросает исключение?**
   - Использовать `assertThrows(Exception.class, () -> {...})`

3. **В чём разница между `assertSame` и `assertEquals`?**
   - `assertEquals` сравнивает **значения** (через equals())
   - `assertSame` сравнивает **ссылки** (на один ли объект)

4. **Зачем нужен `@Disabled`?**
   - Для временного отключения тестов (например, пока функциональность не реализована)

5. **Как ограничить время выполнения теста?**
   - Использовать `assertTimeout(Duration.ofMillis(100), () -> {...})`
