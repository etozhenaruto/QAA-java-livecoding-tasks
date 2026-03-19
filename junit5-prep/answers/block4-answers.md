# ✅ Ответы: Блок 4 — Nested Tests и DisplayName

## Задание 4.1-4.4: Полный UserServiceTest с Nested классами

```java
package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для UserService")
class UserServiceTest {
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    @Nested
    @DisplayName("Регистрация пользователя")
    class Registration {
        
        @Test
        @DisplayName("Успешная регистрация нового пользователя")
        void successfulRegistration() {
            int id = userService.register("John", "john@example.com");
            assertEquals(1, id);
            assertEquals(1, userService.count());
        }
        
        @Test
        @DisplayName("Регистрация с невалидным email выбрасывает исключение")
        void registrationWithInvalidEmail() {
            assertThrows(IllegalArgumentException.class, () -> {
                userService.register("John", "invalid-email");
            });
        }
        
        @Test
        @DisplayName("Регистрация с существующим email выбрасывает исключение")
        void registrationWithExistingEmail() {
            userService.register("John", "john@example.com");
            
            assertThrows(IllegalArgumentException.class, () -> {
                userService.register("Jane", "john@example.com");
            });
        }
        
        @Test
        @DisplayName("Регистрация с null email выбрасывает исключение")
        void registrationWithNullEmail() {
            assertThrows(IllegalArgumentException.class, () -> {
                userService.register("John", null);
            });
        }
    }
    
    @Nested
    @DisplayName("Логин пользователя")
    class Login {
        
        @BeforeEach
        void setUp() {
            userService.register("John", "john@example.com");
        }
        
        @Test
        @DisplayName("Успешный логин с правильными учётными данными")
        void successfulLogin() {
            assertTrue(userService.login("john@example.com", "defaultPassword"));
        }
        
        @Test
        @DisplayName("Логин с неправильным паролем возвращает false")
        void loginWithWrongPassword() {
            assertFalse(userService.login("john@example.com", "wrongPassword"));
        }
        
        @Test
        @DisplayName("Логин незарегистрированного пользователя возвращает false")
        void loginNotRegisteredUser() {
            assertFalse(userService.login("unknown@example.com", "password"));
        }
        
        @Test
        @DisplayName("После логина пользователь залогинен")
        void userLoggedInAfterLogin() {
            userService.login("john@example.com", "defaultPassword");
            
            var user = userService.getById(1);
            assertTrue(user.isPresent());
            assertTrue(user.get().isLoggedIn());
        }
    }
    
    @Nested
    @DisplayName("Удаление пользователя")
    class DeleteUser {
        
        @Test
        @DisplayName("Удаление пользователя по ID")
        void deleteUserById() {
            int id = userService.register("John", "john@example.com");
            userService.deleteById(id);
            assertEquals(0, userService.count());
        }
        
        @Test
        @DisplayName("Удаление пользователя по email")
        void deleteUserByEmail() {
            userService.register("John", "john@example.com");
            assertTrue(userService.deleteByEmail("john@example.com"));
            assertEquals(0, userService.count());
        }
        
        @Test
        @DisplayName("Удаление несуществующего пользователя по email возвращает false")
        void deleteNotExistingUser() {
            assertFalse(userService.deleteByEmail("unknown@example.com"));
        }
        
        @Test
        @DisplayName("Logout пользователя")
        void logoutUser() {
            int id = userService.register("John", "john@example.com");
            userService.login("john@example.com", "defaultPassword");
            
            userService.logout(id);
            
            var user = userService.getById(id);
            assertTrue(user.isPresent());
            assertFalse(user.get().isLoggedIn());
        }
    }
    
    @Nested
    @DisplayName("Получение пользователя")
    class GetUser {
        
        @Test
        @DisplayName("Получение пользователя по ID")
        void getUserById() {
            userService.register("John", "john@example.com");
            
            var user = userService.getById(1);
            assertTrue(user.isPresent());
            assertEquals("John", user.get().getName());
            assertEquals("john@example.com", user.get().getEmail());
        }
        
        @Test
        @DisplayName("Получение несуществующего пользователя возвращает empty")
        void getUserNotExisting() {
            var user = userService.getById(999);
            assertFalse(user.isPresent());
        }
        
        @Test
        @DisplayName("Получение всех пользователей")
        void getAllUsers() {
            userService.register("John", "john@example.com");
            userService.register("Jane", "jane@example.com");
            
            var users = userService.getAll();
            assertEquals(2, users.size());
        }
        
        @Test
        @DisplayName("Обновление имени пользователя")
        void updateUserName() {
            userService.register("John", "john@example.com");
            
            boolean updated = userService.updateName(1, "John Updated");
            
            assertTrue(updated);
            var user = userService.getById(1);
            assertEquals("John Updated", user.get().getName());
        }
        
        @Test
        @DisplayName("Обновление имени несуществующего пользователя возвращает false")
        void updateNotExistingUserName() {
            boolean updated = userService.updateName(999, "Name");
            assertFalse(updated);
        }
    }
}
```

---

## Задание 4.5: DisplayNameGeneration

```java
package com.example.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringValidatorWithGenerationTest {
    
    private StringValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new StringValidator();
    }
    
    @Test
    void should_return_true_for_null_string() {
        assertTrue(validator.isEmpty(null));
    }
    
    @Test
    void should_return_true_for_empty_string() {
        assertTrue(validator.isEmpty(""));
    }
    
    @Test
    void should_return_false_for_non_empty_string() {
        assertFalse(validator.isEmpty("hello"));
    }
    
    @Test
    void should_return_true_for_numeric_string() {
        assertTrue(validator.isNumeric("12345"));
    }
    
    @Test
    void should_return_false_for_alphabetic_string() {
        assertFalse(validator.isNumeric("hello"));
    }
}
```

---

## Задание 4.6: TestInstance.Lifecycle.PER_CLASS

```java
package com.example.service;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Тесты с lifecycle PER_CLASS")
class UserServiceLifecycleTest {
    
    private UserService userService;
    
    @BeforeAll
    void initAll() {
        // Может быть non-static благодаря PER_CLASS
        userService = new UserService();
        System.out.println("=== Инициализация один раз ===");
    }
    
    @AfterAll
    void cleanupAll() {
        System.out.println("=== Очистка один раз ===");
    }
    
    @BeforeEach
    void setUp() {
        System.out.println("Перед тестом: " + userService.count() + " пользователей");
    }
    
    @Test
    @DisplayName("Первый тест добавляет пользователя")
    void test1() {
        userService.register("John", "john@example.com");
        assertEquals(1, userService.count());
    }
    
    @Test
    @DisplayName("Второй тест видит пользователя из первого")
    void test2() {
        // userService сохраняется между тестами!
        // В этом тесте уже есть 1 пользователь из test1
        assertEquals(1, userService.count());
    }
    
    @Test
    @DisplayName("Третий тест добавляет ещё пользователя")
    void test3() {
        userService.register("Jane", "jane@example.com");
        // Теперь 2 пользователя (из test1 и test3)
        assertEquals(2, userService.count());
    }
}
```

**Ответ на вопрос:** Разница между `PER_METHOD` и `PER_CLASS`:

| PER_METHOD (по умолчанию) | PER_CLASS |
|---------------------------|-----------|
| Новый экземпляр для каждого `@Test` | Один экземпляр для всех тестов |
| `@BeforeAll` должен быть `static` | `@BeforeAll` может быть non-static |
| Тесты изолированы друг от друга | Тесты могут влиять друг на друга |
| Лучше для параллельного выполнения | Требует осторожности |

---

## Задание 4.7: Комбинированный тест для Calculator

```java
package com.example.calculator;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для Calculator")
class CalculatorFullTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Nested
    @DisplayName("Арифметические операции")
    class ArithmeticOperations {
        
        @Test
        @DisplayName("Сложение двух чисел")
        void add() {
            assertEquals(5, calculator.add(2, 3));
            assertEquals(0, calculator.add(-5, 5));
            assertEquals(-10, calculator.add(-5, -5));
        }
        
        @Test
        @DisplayName("Вычитание двух чисел")
        void subtract() {
            assertEquals(6, calculator.subtract(10, 4));
            assertEquals(-10, calculator.subtract(-5, 5));
            assertEquals(0, calculator.subtract(5, 5));
        }
        
        @Test
        @DisplayName("Умножение двух чисел")
        void multiply() {
            assertEquals(21, calculator.multiply(3, 7));
            assertEquals(-15, calculator.multiply(-3, 5));
            assertEquals(0, calculator.multiply(0, 100));
        }
        
        @Test
        @DisplayName("Деление двух чисел")
        void divide() {
            assertEquals(5, calculator.divide(20, 4));
            assertEquals(-2, calculator.divide(-10, 5));
        }
        
        @Test
        @DisplayName("Деление на ноль бросает исключение")
        void divideByZero() {
            assertThrows(ArithmeticException.class, () -> {
                calculator.divide(10, 0);
            });
        }
    }
    
    @Nested
    @DisplayName("Продвинутые операции")
    class AdvancedOperations {
        
        @Test
        @DisplayName("Возведение в степень")
        void power() {
            assertEquals(8.0, calculator.power(2, 3), 0.001);
            assertEquals(1.0, calculator.power(5, 0), 0.001);
            assertEquals(0.0, calculator.power(0, 5), 0.001);
        }
        
        @Test
        @DisplayName("Факториал положительного числа")
        void factorial() {
            assertEquals(120, calculator.factorial(5));
            assertEquals(1, calculator.factorial(0));
            assertEquals(1, calculator.factorial(1));
            assertEquals(720, calculator.factorial(6));
        }
        
        @Test
        @DisplayName("Факториал отрицательного числа бросает исключение")
        void factorialNegative() {
            assertThrows(IllegalArgumentException.class, () -> {
                calculator.factorial(-1);
            });
        }
    }
    
    @Nested
    @DisplayName("Проверки значений")
    class ValidationTests {
        
        @Test
        @DisplayName("Чётные числа")
        void isEven() {
            assertTrue(calculator.isEven(2));
            assertTrue(calculator.isEven(100));
            assertTrue(calculator.isEven(0));
        }
        
        @Test
        @DisplayName("Нечётные числа")
        void isOdd() {
            assertFalse(calculator.isEven(1));
            assertFalse(calculator.isEven(99));
        }
        
        @Test
        @DisplayName("Граничные значения")
        void edgeCases() {
            // Integer.MAX_VALUE
            int max = Integer.MAX_VALUE;
            assertThrows(ArithmeticException.class, () -> {
                calculator.add(max, 1);  // Переполнение
            });
        }
    }
}
```

---

## Вопросы для самопроверки — Ответы

1. **Зачем нужны Nested тесты?**
   - Для логической группировки тестов по сценариям
   - Для лучшей читаемости отчётов
   - Для организации тестов по функциональности

2. **Что делает `@DisplayName`?**
   - Задаёт кастомное читаемое имя для теста или класса

3. **Какие бывают DisplayNameGenerator?**
   - `Standard` — по умолчанию
   - `Simple` — только имя метода
   - `ReplaceUnderscores` — заменяет `_` на пробелы

4. **Когда использовать `PER_CLASS` lifecycle?**
   - Когда нужно сохранить состояние между тестами
   - Когда `@BeforeAll` должен быть non-static
   - Когда инициализация дорогая

5. **Могут ли вложенные классы иметь свои `@BeforeEach`?**
   - Да, каждый `@Nested` класс может иметь свои lifecycle методы
