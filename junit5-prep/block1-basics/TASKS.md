# 📝 Блок 1: Базовые аннотации и Assertions

## Теория для повторения
- `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`
- `@Disabled`, `@DisplayName`
- Все виды Assertions: `assertEquals`, `assertTrue`, `assertThrows`, `assertTimeout`, `assertAll`

---

## 📋 Задания

### Задание 1.1: Тесты для Calculator

Создайте класс `CalculatorTest` и протестируйте методы `Calculator`:

1. **Тест на сложение** — проверьте `add(2, 3) == 5`
2. **Тест на вычитание** — проверьте `subtract(10, 4) == 6`
3. **Тест на умножение** — проверьте `multiply(3, 7) == 21`
4. **Тест на деление** — проверьте `divide(20, 4) == 5`
5. **Тест на деление на ноль** — проверьте что бросается `ArithmeticException`
6. **Тест на степень** — проверьте `power(2, 3) == 8.0`
7. **Тест на факториал** — проверьте `factorial(5) == 120`
8. **Тест на факториал отрицательного числа** — проверьте `IllegalArgumentException`

**Требования:**
- Используйте `@BeforeEach` для создания экземпляра `Calculator`
- Добавьте `@DisplayName` к каждому тесту
- Используйте `assertThrows` для проверки исключений
- Для `power` используйте `assertEquals` с delta `0.001`

---

### Задание 1.2: Тесты для StringValidator

Создайте класс `StringValidatorTest`:

1. **isEmpty тесты:**
   - `isEmpty(null)` → `true`
   - `isEmpty("")` → `true`
   - `isEmpty("hello")` → `false`

2. **isNullOrBlank тесты:**
   - `isNullOrBlank(null)` → `true`
   - `isNullOrBlank("   ")` → `true`
   - `isNullOrBlank("hello")` → `false`

3. **isNumeric тесты:**
   - `isNumeric("12345")` → `true`
   - `isNumeric("123abc")` → `false`
   - `isNumeric("")` → `false`

4. **isPalindrome тесты:**
   - `isPalindrome("A man a plan a canal Panama")` → `true`
   - `isPalindrome("hello")` → `false`
   - `isPalindrome("А роза упала на лапу Азора")` → `true`

5. **truncate тесты:**
   - `truncate("hello world", 5)` → `"hello"`
   - `truncate("hi", 10)` → `"hi"`
   - `truncate(null, 5)` → `null`

6. **repeat тесты:**
   - `repeat("ab", 3)` → `"ababab"`
   - `repeat("x", 0)` → `""`

---

### Задание 1.3: Grouped Assertions

Создайте тест для проверки объекта `User`:

```java
User user = new User(1, "John", "john@example.com");
```

Используйте `assertAll` для одновременной проверки:
- `id == 1`
- `name == "John"`
- `email == "john@example.com"`
- `isLoggedIn() == false`

---

### Задание 1.4: assertTimeout

Создайте тест, который проверяет что метод выполняется за определённое время:

```java
// Метод должен выполниться за 100мс
// Используйте assertTimeout
```

---

### Задание 1.5: @Disabled и @BeforeAll/@AfterAll

1. Создайте тест с аннотацией `@Disabled` и сообщением почему он отключен
2. Создайте `@BeforeAll` метод который выводит "=== Начало тестов ==="
3. Создайте `@AfterAll` метод который выводит "=== Конец тестов ==="

**Вопрос:** Почему `@BeforeAll` и `@AfterAll` должны быть `static`?

---

## ✅ Чек-лист для самопроверки

- [ ] Все тесты помечены `@Test`
- [ ] Используется `@BeforeEach` для инициализации
- [ ] Используется `@DisplayName` для читаемых имён
- [ ] Исключения проверяются через `assertThrows`
- [ ] Используется `assertAll` для группировки проверок
- [ ] Используется `assertTimeout` для проверки времени
- [ ] Есть тест с `@Disabled`

---

## 📌 Подсказки

<details>
<summary>Подсказка к заданию 1.1 (деление на ноль)</summary>

```java
@Test
void testDivideByZero() {
    assertThrows(ArithmeticException.class, () -> {
        calculator.divide(10, 0);
    });
}
```
</details>

<details>
<summary>Подсказка к заданию 1.3 (assertAll)</summary>

```java
@Test
void testUserProperties() {
    assertAll(
        () -> assertEquals(1, user.getId()),
        () -> assertEquals("John", user.getName()),
        () -> assertEquals("john@example.com", user.getEmail()),
        () -> assertFalse(user.isLoggedIn())
    );
}
```
</details>
