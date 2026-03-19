# 🎯 Вопросы для собеседования: Исключения

## Базовые вопросы

### 1. Что такое исключения и зачем они нужны?

**Ответ:**
Исключения — это механизм обработки ошибок во время выполнения программы.

**Зачем нужны:**
- Отделить код обработки ошибок от основного кода
- Предотвратить аварийное завершение программы
- Предоставить информацию об ошибке

---

### 2. Какая иерархия исключений в Java?

**Ответ:**
```
Throwable
├── Error (не ловим)
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception
    ├── RuntimeException (unchecked)
    └── Checked Exceptions
```

---

### 3. В чём разница между checked и unchecked исключениями?

**Ответ:**

| Checked | Unchecked |
|---------|-----------|
| Проверяются компилятором | Не проверяются |
| Обязательно обработать | Не обязательно |
| IOException, SQLException | NullPointerException, IllegalArgumentException |

---

### 4. Что такое try-with-resources?

**Ответ:**
Автоматическое закрытие ресурсов реализующих `AutoCloseable`:

```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    return br.readLine();
}
// br.close() вызывается автоматически
```

---

### 5. Выполнится ли finally если в try return?

**Ответ:**
Да, finally выполнится перед return:

```java
try {
    return 1;
} finally {
    System.out.println("Finally"); // Выполнится
}
```

**Когда finally НЕ выполняется:**
- `System.exit()` вызван
- JVM упала
- Бесконечный цикл

---

### 6. Можно ли ловить несколько исключений в одном catch?

**Ответ:**
Да, multi-catch (Java 7+):

```java
try {
    // код
} catch (IOException | SQLException e) {
    // общая обработка
}
```

---

### 7. В чём разница между throw и throws?

**Ответ:**
- `throw` — бросает исключение: `throw new Exception()`
- `throws` — объявляет что метод бросает: `void method() throws IOException`

---

### 8. Что такое custom exception и как создать?

**Ответ:**
Своё исключение для специфичных ошибок:

```java
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

---

### 9. Что такое exception propagation?

**Ответ:**
Исключение "поднимается" по стеку вызовов пока не будет поймано:

```java
void method3() { throw new Exception(); }
void method2() { method3(); }
void method1() { try { method2(); } catch(Exception e) {} }
```

---

### 10. Что такое chained exception?

**Ответ:**
Обёртывание одного исключения в другое:

```java
try {
    // ...
} catch (SQLException e) {
    throw new RuntimeException("DB error", e); // e — причина
}
```

---

## Продвинутые вопросы

### 11. Можно ли переопределить присваивание в catch переменной?

**Ответ:**
Нет, переменная в catch является `effectively final`:

```java
catch (Exception e) {
    e = new Exception(); // ОШИБКА КОМПИЛЯЦИИ
}
```

---

### 12. Что такое suppressed exceptions?

**Ответ:**
Если в try и finally бросаются исключения, finally "подавляет" try:

```java
try {
    throw new IOException("Primary");
} finally {
    throw new RuntimeException("Secondary");
}
// RuntimeException брошен, IOException — suppressed
```

---

### 13. Когда использовать custom exception?

**Ответ:**
- Когда нужно передать дополнительную информацию
- Для бизнес-ошибек предметной области
- Для лучшего разделения ошибок

---

### 14. Что лучше: вернуть error code или бросить exception?

**Ответ:**
Exception лучше:
- Нельзя игнорировать (компилятор требует обработки)
- Содержит стек вызовов
- Отделяет обработку ошибок от основного кода

---

### 15. Какие best practices для работы с исключениями?

**Ответ:**

✅ **Делать:**
- Ловить конкретные исключения
- Использовать try-with-resources
- Логировать исключения
- Оборачивать checked в RuntimeException

❌ **Не делать:**
- Пустой catch
- Ловить Exception
- Игнорировать исключения
- Использовать исключения для потока управления
