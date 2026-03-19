# 📝 Блок 1: Исключения (Exceptions) — 10 задач

## Теория для повторения
- Иерархия исключений: Throwable → Exception/Error → RuntimeException
- Checked vs Unchecked исключения
- try-catch-finally, try-with-resources
- throw, throws
- Создание собственных исключений

---

## 📋 Задания

### Задача 1.1: Базовый try-catch

Создайте метод который делит два числа и обрабатывает `ArithmeticException`:

```java
public int divide(int a, int b) {
    // Реализовать деление с обработкой деления на ноль
}
```

**Требования:**
- Вернуть результат деления
- Поймать `ArithmeticException`
- Вывести сообщение об ошибке
- Вернуть -1 в случае ошибки

---

### Задача 1.2: Multiple catch

Создайте метод который работает с массивом и обрабатывает несколько исключений:

```java
public int getElement(int[] arr, int index) {
    // Вернуть элемент массива
    // Обработать ArrayIndexOutOfBoundsException
    // Обработать NullPointerException
}
```

---

### Задача 1.3: Finally блок

Создайте метод который демонстрирует работу finally:

```java
public void testFinally() {
    try {
        // Код который бросает исключение
        int x = 10 / 0;
    } catch (ArithmeticException e) {
        // Обработка
    } finally {
        // Этот блок выполнится всегда
        System.out.println("Finally block executed");
    }
}
```

**Вопрос:** Выполнится ли finally если в try/return?

---

### Задача 1.4: Throw исключение

Создайте метод который бросает исключение при определённых условиях:

```java
public void checkAge(int age) {
    // Бросить IllegalArgumentException если age < 0 или age > 150
    // Бросить custom exception если age < 18
}
```

---

### Задача 1.5: Throws декларация

Создайте метод который объявляет выбрасываемые исключения:

```java
public void readFile(String path) throws IOException {
    // Метод который может бросить IOException
    // Не обрабатывать внутри, а объявить в signature
}
```

---

### Задача 1.6: Try-with-resources

Создайте метод который читает файл с автоматическим закрытием ресурсов:

```java
public String readFile(String path) throws IOException {
    // Использовать try-with-resources
    // BufferedReader для чтения
}
```

---

### Задача 1.7: Custom Exception

Создайте собственное исключение:

```java
// Проверить существование пользователя
public class UserNotFoundException extends Exception {
    // Конструктор с сообщением
    // Конструктор с сообщением и причиной
}

// Использовать в методе
public User getUserById(int id) throws UserNotFoundException {
    // Бросить исключение если пользователь не найден
}
```

---

### Задача 1.8: Catch с переменной

Продемонстрируйте что переменная в catch effectively final:

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    // e = new ArithmeticException(); // НЕ скомпилируется!
    System.out.println(e.getMessage());
}
```

---

### Задача 1.9: Multi-catch

Используйте multi-catch для обработки нескольких исключений в одном блоке:

```java
try {
    // Код который может бросить IOException или SQLException
} catch (IOException | SQLException e) {
    // Общая обработка
}
```

---

### Задача 1.10: Exception propagation

Создайте цепочку вызовов методов с propagating исключениями:

```java
public class ExceptionPropagation {
    void method3() throws ArithmeticException {
        int x = 10 / 0;
    }
    
    void method2() {
        method3();
    }
    
    void method1() {
        try {
            method2();
        } catch (ArithmeticException e) {
            System.out.println("Exception caught in method1");
        }
    }
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Понимаете разницу между checked и unchecked исключениями
- [ ] Умеете использовать try-catch-finally
- [ ] Знаете что такое try-with-resources
- [ ] Умеете бросать исключения с throw
- [ ] Умеете объявлять исключения с throws
- [ ] Можете создать custom exception
- [ ] Понимаете multi-catch синтаксис
- [ ] Знаете что такое exception propagation

---

## 📌 Подсказки

<details>
<summary>Подсказка: Иерархия исключений</summary>

```
Throwable
├── Error (не ловим)
└── Exception
    ├── RuntimeException (unchecked)
    └── Checked Exceptions (IOException, SQLException)
```
</details>

<details>
<summary>Подсказка: Try-with-resources</summary>

```java
try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    return br.readLine();
}
// Ресурс закроется автоматически
```
</details>

<details>
<summary>Подсказка: Custom Exception</summary>

```java
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
    
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
</details>
