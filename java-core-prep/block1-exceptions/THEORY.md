# 📚 Теория: Исключения (Exceptions)

## 1. Что такое исключения?

**Исключение (Exception)** — это событие, которое возникает во время выполнения программы и нарушает нормальный поток её выполнения.

Когда возникает ошибка, Java создаёт объект исключения (instance of `Throwable`) и "бросает" его. Программа может "поймать" это исключение и обработать его, либо завершиться.

---

## 2. Иерархия исключений

```
java.lang.Throwable (базовый класс для всех ошибок и исключений)
│
├── java.lang.Error (серьёзные ошибки JVM, которые мы НЕ ловим)
│   ├── OutOfMemoryError (память исчерпана)
│   ├── StackOverflowError (переполнение стека)
│   ├── VirtualMachineError (ошибка JVM)
│   └── ...
│
└── java.lang.Exception (исключения которые можно обработать)
    │
    ├── java.lang.RuntimeException (unchecked — не обязательны к обработке)
    │   ├── NullPointerException (обращение к null)
    │   ├── IllegalArgumentException (неверный аргумент)
    │   ├── IndexOutOfBoundsException (выход за границы)
    │   ├── ClassCastException (неверное приведение типа)
    │   ├── ArithmeticException (деление на ноль)
    │   └── ...
    │
    └── Checked Exceptions (обязательны к обработке)
        ├── IOException (ошибки ввода-вывода)
        ├── SQLException (ошибки базы данных)
        ├── ClassNotFoundException (класс не найден)
        ├── InterruptedException (поток прерван)
        └── ...
```

---

## 3. Типы исключений

### 3.1. Checked Exceptions (Проверяемые)

Компилятор **требует** обработки этих исключений. Если метод может бросить checked exception, вы должны:
- Либо поймать его в `try-catch`
- Либо объявить в `throws`

**Примеры:** `IOException`, `SQLException`, `ClassNotFoundException`

```java
// ОШИБКА КОМПИЛЯЦИИ — не обработано checked exception
public void readFile() {
    FileReader file = new FileReader("test.txt"); // IOException!
}

// Правильно — вариант 1: try-catch
public void readFile() {
    try {
        FileReader file = new FileReader("test.txt");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// Правильно — вариант 2: throws
public void readFile() throws IOException {
    FileReader file = new FileReader("test.txt");
}
```

---

### 3.2. Unchecked Exceptions (Непроверяемые)

Это `RuntimeException` и его наследники. Компилятор **не требует** их обработки.

**Примеры:** `NullPointerException`, `IllegalArgumentException`, `ArrayIndexOutOfBoundsException`

```java
// Компилируется без ошибок
public void divide(int a, int b) {
    int result = a / b; // Может бросить ArithmeticException
}

// Но лучше обработать:
public int divideSafe(int a, int b) {
    try {
        return a / b;
    } catch (ArithmeticException e) {
        System.out.println("Деление на ноль!");
        return -1;
    }
}
```

---

### 3.3. Error (Ошибки JVM)

Серьёзные проблемы которые **не следует ловить**:

```java
// НЕ ДЕЛАЙТЕ ТАК:
try {
    // какой-то код
} catch (OutOfMemoryError e) {
    // Бесполезно — JVM уже в критическом состоянии
}
```

---

## 4. Обработка исключений

### 4.1. try-catch

```java
try {
    // Код который может бросить исключение
    int result = 10 / 0;
} catch (ArithmeticException e) {
    // Обработка конкретного исключения
    System.out.println("Ошибка: " + e.getMessage());
}
```

---

### 4.2. Multiple catch

Можно поймать несколько типов исключений:

```java
try {
    int[] arr = new int[5];
    arr[10] = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Деление на ноль");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Индекс вне массива");
}
```

---

### 4.3. Multi-catch (Java 7+)

Один блок catch для нескольких типов:

```java
try {
    // Код
} catch (IOException | SQLException e) {
    // Общая обработка
    System.out.println("Ошибка: " + e.getMessage());
}
```

**Важно:** Переменная `e` является `effectively final` — нельзя присваивать новое значение.

---

### 4.4. finally

Блок `finally` выполняется **всегда**, даже если было исключение или `return`:

```java
public int testFinally() {
    try {
        return 1;
    } catch (Exception e) {
        return 2;
    } finally {
        System.out.println("Finally выполнится всегда!");
        // Выполнится перед return
    }
}
```

**Когда finally НЕ выполняется:**
- `System.exit()` вызван в try
- JVM упала (OutOfMemoryError)
- Бесконечный цикл в try

---

### 4.5. try-with-resources (Java 7+)

Автоматическое закрытие ресурсов реализующих `AutoCloseable`:

```java
// Старый способ
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("file.txt"));
    String line = reader.readLine();
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Новый способ (Java 7+)
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line = reader.readLine();
} catch (IOException e) {
    e.printStackTrace();
}
// reader.close() вызывается автоматически!
```

**Ресурсы закрываются в обратном порядке создания.**

---

## 5. Создание исключений

### 5.1. Ключевое слово throw

Бросаем исключение явно:

```java
public void checkAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }
    if (age < 18) {
        throw new IllegalArgumentException("Must be 18 or older");
    }
}
```

---

### 5.2. Ключевое слово throws

Объявляем что метод может бросить exception:

```java
public void readFile() throws IOException {
    FileReader file = new FileReader("test.txt");
}

// Вызывающий код должен обработать:
try {
    readFile();
} catch (IOException e) {
    e.printStackTrace();
}
```

---

### 5.3. Custom Exception

Создаём своё исключение:

```java
// Checked exception (наследуемся от Exception)
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Unchecked exception (наследуемся от RuntimeException)
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
```

**Использование:**

```java
public User getUserById(int id) throws UserNotFoundException {
    if (id <= 0) {
        throw new UserNotFoundException("User not found: " + id);
    }
    return new User(id);
}
```

---

## 6. Best Practices

### ✅ Что делать:

1. **Ловить конкретные исключения:**
```java
// ХОРОШО
try {
    readFile();
} catch (FileNotFoundException e) {
    // ...
} catch (IOException e) {
    // ...
}
```

2. **Использовать try-with-resources:**
```java
try (FileReader file = new FileReader("test.txt")) {
    // ...
}
```

3. **Логировать исключения:**
```java
catch (Exception e) {
    logger.error("Error reading file", e);
}
```

4. **Оборачивать checked exceptions в RuntimeException:**
```java
try {
    // ...
} catch (SQLException e) {
    throw new RuntimeException("Database error", e);
}
```

---

### ❌ Чего НЕ делать:

1. **Пустой catch:**
```java
// ПЛОХО
try {
    // ...
} catch (Exception e) {
    // Игнорируем ошибку
}
```

2. **Ловить Exception:**
```java
// ПЛОХО
try {
    // ...
} catch (Exception e) {
    // Ловим ВСЁ включая RuntimeException
}
```

3. **Глотать исключения:**
```java
// ПЛОХО
try {
    // ...
} catch (IOException e) {
    System.out.println("Error"); // Без деталей
}
```

---

## 7. Exception Propagation

Исключение "поднимается" по стеку вызовов пока не будет поймано:

```java
public class Propagation {
    void method3() {
        int x = 10 / 0; // ArithmeticException
    }
    
    void method2() {
        method3(); // Exception propagates вверх
    }
    
    void method1() {
        try {
            method2();
        } catch (ArithmeticException e) {
            System.out.println("Caught in method1");
        }
    }
}
```

---

## 8. Chained Exceptions (Цепочка исключений)

Можно обернуть одно исключение в другое:

```java
try {
    // ...
} catch (SQLException e) {
    throw new RuntimeException("Database error", e); // e — причина
}

// Получить причину:
try {
    // ...
} catch (RuntimeException e) {
    Throwable cause = e.getCause(); // Оригинальное исключение
}
```

---

## 9. SuppressedException (Java 7+)

Если в try и finally бросаются исключения, finally "подавляет" try:

```java
try {
    throw new IOException("Primary exception");
} finally {
    throw new RuntimeException("Secondary exception");
}
// RuntimeException будет брошен, IOException — suppressed

// Получить suppressed:
try {
    // ...
} catch (Exception e) {
    for (Throwable suppressed : e.getSuppressed()) {
        System.out.println("Suppressed: " + suppressed);
    }
}
```

---

## 10. Шпаргалка

| Ключевое слово | Описание |
|----------------|----------|
| `try` | Блок кода где может возникнуть исключение |
| `catch` | Обработка исключения |
| `finally` | Выполняется всегда (закрытие ресурсов) |
| `throw` | Бросить исключение |
| `throws` | Объявить что метод бросает exception |

---

## 11. Примеры для запоминания

```java
// Полный пример
public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int result = divide(10, 0);
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Done");
        }
    }
    
    public static int divide(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}
```
