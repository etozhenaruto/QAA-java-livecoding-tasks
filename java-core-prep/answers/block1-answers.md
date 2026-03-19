# ✅ Ответы: Блок 1 — Исключения

## Задача 1.1: Базовый try-catch

```java
public int divide(int a, int b) {
    try {
        return a / b;
    } catch (ArithmeticException e) {
        System.out.println("Ошибка: деление на ноль");
        return -1;
    }
}
```

---

## Задача 1.2: Multiple catch

```java
public int getElement(int[] arr, int index) {
    try {
        return arr[index];
    } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Ошибка: индекс вне диапазона");
        return -1;
    } catch (NullPointerException e) {
        System.out.println("Ошибка: массив null");
        return -1;
    }
}
```

---

## Задача 1.3: Finally блок

```java
public void testFinally() {
    try {
        int x = 10 / 0;
    } catch (ArithmeticException e) {
        System.out.println("Exception caught");
    } finally {
        System.out.println("Finally block executed");
    }
}
```

**Вопрос:** Выполнится ли finally если в try/return?
**Ответ:** Да, finally выполнится перед return.

---

## Задача 1.4: Throw исключение

```java
public class AgeRestrictionException extends Exception {
    public AgeRestrictionException(String message) {
        super(message);
    }
}

public void checkAge(int age) throws AgeRestrictionException {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Invalid age: " + age);
    }
    if (age < 18) {
        throw new AgeRestrictionException("Age must be 18 or older");
    }
    System.out.println("Access granted");
}
```

---

## Задача 1.5: Throws декларация

```java
public void readFile(String path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line = reader.readLine();
    reader.close();
    System.out.println(line);
}
```

---

## Задача 1.6: Try-with-resources

```java
public String readFile(String path) throws IOException {
    StringBuilder content = new StringBuilder();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
    }
    // reader закроется автоматически
    
    return content.toString();
}
```

---

## Задача 1.7: Custom Exception

```java
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

public User getUserById(int id) throws UserNotFoundException {
    // Эмуляция поиска
    if (id <= 0) {
        throw new UserNotFoundException("User not found with id: " + id);
    }
    return new User(id, "John");
}
```

---

## Задача 1.8: Catch с переменной

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    // e = new ArithmeticException(); // ОШИБКА: variable is effectively final
    System.out.println(e.getMessage()); // Можно читать
}
```

---

## Задача 1.9: Multi-catch

```java
public void multiCatchExample() {
    try {
        // Код который может бросить IOException или SQLException
        throw new IOException("IO error");
    } catch (IOException | SQLException e) {
        System.out.println("Exception caught: " + e.getMessage());
        // e является effectively final
    }
}
```

---

## Задача 1.10: Exception propagation

```java
public class ExceptionPropagation {
    void method3() throws ArithmeticException {
        int x = 10 / 0;
    }
    
    void method2() {
        method3(); // Исключение propagates вверх
    }
    
    void method1() {
        try {
            method2();
        } catch (ArithmeticException e) {
            System.out.println("Exception caught in method1");
        }
    }
    
    public static void main(String[] args) {
        new ExceptionPropagation().method1();
    }
}
```

---

## Вопросы для самопроверки — Ответы

1. **В чём разница между checked и unchecked исключениями?**
   - Checked — компилятор требует обработки (IOException)
   - Unchecked — RuntimeException, не требуется обработка

2. **Что такое try-with-resources?**
   - Автоматическое закрытие ресурсов реализующих AutoCloseable

3. **Выполнится ли finally если в try return?**
   - Да, finally выполнится перед return

4. **Можно ли поймать несколько исключений в одном catch?**
   - Да, multi-catch: `catch (IOException | SQLException e)`

5. **Зачем нужен throws?**
   - Объявляет что метод может бросить checked exception
