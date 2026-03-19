# 📝 Блок 4: Nested Tests и DisplayName

## Теория для повторения
- `@Nested` — вложенные классы для группировки тестов
- `@DisplayName("name")` — кастомное имя теста/класса
- `@DisplayNameGeneration` — автоматическая генерация имён
- `TestInstance.Lifecycle` — жизненный цикл тестовых экземпляров

---

## 📋 Задания

### Задание 4.1: Nested тесты для UserService

Создайте класс `UserServiceTest` с вложенными классами для группировки по сценариям:

```java
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
        // Тесты для регистрации
    }
    
    @Nested
    @DisplayName("Логин пользователя")
    class Login {
        // Тесты для логина
    }
    
    @Nested
    @DisplayName("Удаление пользователя")
    class DeleteUser {
        // Тесты для удаления
    }
    
    @Nested
    @DisplayName("Получение пользователя")
    class GetUser {
        // Тесты для получения
    }
}
```

---

### Задание 4.2: Тесты для Registration

Внутри `@Nested class Registration` создайте тесты:

```java
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
```

---

### Задание 4.3: Тесты для Login

Внутри `@Nested class Login` создайте тесты:

```java
@Test
@DisplayName("Успешный логин с правильными учётными данными")
void successfulLogin() {
    userService.register("John", "john@example.com");
    assertTrue(userService.login("john@example.com", "defaultPassword"));
}

@Test
@DisplayName("Логин с неправильным паролем возвращает false")
void loginWithWrongPassword() {
    userService.register("John", "john@example.com");
    assertFalse(userService.login("john@example.com", "wrongPassword"));
}

@Test
@DisplayName("Логин незарегистрированного пользователя возвращает false")
void loginNotRegisteredUser() {
    assertFalse(userService.login("unknown@example.com", "password"));
}
```

---

### Задание 4.4: Тесты для DeleteUser

Внутри `@Nested class DeleteUser` создайте тесты:

```java
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
```

---

### Задание 4.5: DisplayNameGeneration

Используйте автоматическую генерацию имён:

```java
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringValidatorTest {
    
    @Test
    @DisplayName("should_return_true_for_null_string")
    void should_return_true_for_null_string() {
        assertTrue(validator.isEmpty(null));
    }
    
    @Test
    @DisplayName("should_return_true_for_empty_string")
    void should_return_true_for_empty_string() {
        assertTrue(validator.isEmpty(""));
    }
    
    @Test
    @DisplayName("should_return_false_for_non_empty_string")
    void should_return_false_for_non_empty_string() {
        assertFalse(validator.isEmpty("hello"));
    }
}
```

---

### Задание 4.6: TestInstance.Lifecycle.PER_CLASS

Используйте `@TestInstance` для изменения жизненного цикла:

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceLifecycleTest {
    
    private UserService userService;
    
    @BeforeAll
    void initAll() {
        // Может быть non-static благодаря PER_CLASS
        userService = new UserService();
    }
    
    @Test
    void test1() {
        userService.register("John", "john@example.com");
        assertEquals(1, userService.count());
    }
    
    @Test
    void test2() {
        // userService сохраняется между тестами!
        assertEquals(1, userService.count());
    }
}
```

**Вопрос:** В чём разница между `PER_METHOD` и `PER_CLASS`?

---

### Задание 4.7: Комбинированный тест

Создайте полный тест для `Calculator` с использованием:
- `@DisplayName` для класса и всех тестов
- `@Nested` для группировки по операциям:
  - `ArithmeticOperations` (add, subtract, multiply, divide)
  - `AdvancedOperations` (power, factorial)
  - `ValidationTests` (isEven, edge cases)

---

## ✅ Чек-лист для самопроверки

- [ ] Используется `@Nested` для группировки тестов
- [ ] Используется `@DisplayName` для классов и методов
- [ ] Вложенные классы имеют понятные имена
- [ ] Используется `@DisplayNameGeneration`
- [ ] Понимаете разницу между `PER_METHOD` и `PER_CLASS`
- [ ] Тесты организованы логически по сценариям

---

## 📌 Подсказки

<details>
<summary>Подсказка: Импорты для Nested тестов</summary>

```java
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
```
</details>

<details>
<summary>Подсказка: Пример структуры Nested теста</summary>

```
UserServiceTest
├── Registration
│   ├── successfulRegistration
│   ├── registrationWithInvalidEmail
│   └── registrationWithExistingEmail
├── Login
│   ├── successfulLogin
│   └── loginWithWrongPassword
└── DeleteUser
    ├── deleteUserById
    └── deleteUserByEmail
```
</details>

<details>
<summary>Подсказка: DisplayNameGenerator классы</summary>

```java
// Заменяет подчёркивания на пробелы
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

// Простая генерация (метод без параметров)
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)

// Стандартная генерация (по умолчанию)
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
```
</details>
