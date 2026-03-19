# 🎯 Вопросы для собеседования по Java Core (AQ Java Automation)

## 🔹 Исключения (Exceptions)

### 1. Что такое исключения и какая иерархия?

**Ответ:**
```
Throwable
├── Error (не ловим: OutOfMemoryError, StackOverflowError)
└── Exception
    ├── RuntimeException (unchecked: NullPointerException, IllegalArgumentException)
    └── Checked Exceptions (IOException, SQLException)
```

---

### 2. В чём разница между checked и unchecked исключениями?

**Ответ:**
- **Checked** — компилятор требует обязательной обработки (IOException, SQLException)
- **Unchecked** — RuntimeException, не требуется обязательная обработка

---

### 3. Что такое try-with-resources?

**Ответ:**
Автоматическое закрытие ресурсов реализующих `AutoCloseable`:

```java
try (BufferedReader br = new BufferedReader(new FileReader(path))) {
    return br.readLine();
}
// Ресурс закроется автоматически
```

---

### 4. Выполнится ли finally если в try return?

**Ответ:**
Да, finally выполнится перед return:

```java
try {
    return 1;
} finally {
    System.out.println("Finally"); // Выполнится
}
```

---

### 5. Можно ли поймать несколько исключений в одном catch?

**Ответ:**
Да, multi-catch:

```java
try {
    // код
} catch (IOException | SQLException e) {
    // общая обработка
}
```

---

## 🔹 Коллекции (Collections)

### 6. В чём разница между ArrayList и LinkedList?

**Ответ:**

| ArrayList | LinkedList |
|-----------|------------|
| Динамический массив | Двусвязный список |
| O(1) доступ по индексу | O(n) доступ |
| O(n) вставка/удаление | O(1) вставка/удаление (если известен узел) |
| Лучше для чтения | Лучше для частых модификаций |

---

### 7. Как работает HashMap?

**Ответ:**
- Использует хэш-таблицу
- Ключ → hashCode() → индекс в массиве (bucket)
- При коллизии — цепочка или дерево (Java 8+)
- O(1) для get/put в среднем

**Важно:** Ключ должен переопределять `hashCode()` и `equals()`

---

### 8. В чём разница между HashSet, LinkedHashSet и TreeSet?

**Ответ:**

| Коллекция | Порядок | Null | Сложность |
|-----------|---------|------|-----------|
| HashSet | Нет порядка | Один | O(1) |
| LinkedHashSet | Порядок вставки | Один | O(1) |
| TreeSet | Сортировка | Нет | O(log n) |

---

### 9. В чём разница между HashMap, LinkedHashMap и TreeMap?

**Ответ:**

| Map | Порядок ключей | Null ключ | Сложность |
|-----|----------------|-----------|-----------|
| HashMap | Нет порядка | Да | O(1) |
| LinkedHashMap | Порядок вставки | Да | O(1) |
| TreeMap | Сортировка ключей | Нет | O(log n) |

---

### 10. Что такое fail-fast итератор?

**Ответ:**
Бросает `ConcurrentModificationException` при изменении коллекции во время итерации:

```java
for (String s : list) {
    list.remove(s); // ConcurrentModificationException!
}
```

**Решение:** Использовать `Iterator.remove()` или `CopyOnWriteArrayList`

---

### 11. Comparable vs Comparator — в чём разница?

**Ответ:**

**Comparable** (внутри класса):
```java
class Person implements Comparable<Person> {
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
}
```

**Comparator** (внешний):
```java
Comparator<Person> byAge = Comparator.comparingInt(Person::getAge);
list.sort(byAge);
```

---

## 🔹 Стримы (Streams)

### 12. Что такое Stream и чем отличается от Collection?

**Ответ:**
- **Collection** — хранит данные
- **Stream** — поток данных для обработки (не хранит)

**Особенности Stream:**
- Не изменяет источник
- Ленивое вычисление
- Одноразовый

---

### 13. Какие операции бывают у Stream?

**Ответ:**

**Intermediate** (возвращают Stream, ленивые):
- filter, map, flatMap, sorted, distinct, limit, skip, peek

**Terminal** (возвращают результат):
- forEach, collect, reduce, count, anyMatch, allMatch, findFirst, findAny

---

### 14. Что такое flatMap?

**Ответ:**
Преобразует каждый элемент в Stream и "сплющивает":

```java
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4)
);

List<Integer> flat = nested.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
// [1, 2, 3, 4]
```

---

### 15. Что делает Collectors.groupingBy?

**Ответ:**
Группирует элементы по ключу (аналог SQL GROUP BY):

```java
Map<Integer, List<Person>> byAge = people.stream()
    .collect(Collectors.groupingBy(Person::getAge));
```

---

### 16. В чём разница между findFirst и findAny?

**Ответ:**
- **findFirst** — первый элемент (важен порядок)
- **findAny** — любой элемент (может быть быстрее в parallel stream)

---

## 🔹 Массивы (Arrays)

### 17. Как отсортировать массив в обратном порядке?

**Ответ:**

```java
// Для объектов
Integer[] arr = {5, 2, 8, 1};
Arrays.sort(arr, Collections.reverseOrder());

// Для примитивов — через boxed
int[] primitive = {5, 2, 8, 1};
int[] reversed = Arrays.stream(primitive)
    .boxed()
    .sorted(Collections.reverseOrder())
    .mapToInt(Integer::intValue)
    .toArray();
```

---

### 18. В чём разница между массивом и ArrayList?

**Ответ:**

| Array | ArrayList |
|-------|-----------|
| Фиксированный размер | Динамический размер |
| Примитивы и объекты | Только объекты |
| `array.length` | `list.size()` |
| Быстрее | Медленнее (автобоксинг) |

---

## 🔹 Алгоритмы

### 19. Какие сложности алгоритмов вы знаете?

**Ответ:**
```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2^n) < O(n!)

O(1)         — доступ к элементу массива
O(log n)     — бинарный поиск
O(n)         — линейный поиск
O(n log n)   — быстрая сортировка
O(n²)        — bubble sort
O(2^n)       — рекурсивный Fibonacci
```

---

### 20. Сложности сортировок?

**Ответ:**

| Алгоритм | Лучший | Средний | Худший |
|----------|--------|---------|--------|
| Bubble | O(n) | O(n²) | O(n²) |
| Selection | O(n²) | O(n²) | O(n²) |
| Insertion | O(n) | O(n²) | O(n²) |
| Merge | O(n log n) | O(n log n) | O(n log n) |
| Quick | O(n log n) | O(n log n) | O(n²) |

---

### 21. Как работает бинарный поиск?

**Ответ:**
- Требует отсортированный массив
- Делит диапазон пополам на каждом шаге
- Сложность: O(log n)

```java
int mid = left + (right - left) / 2;
if (arr[mid] == target) return mid;
if (arr[mid] < target) left = mid + 1;
else right = mid - 1;
```

---

## 🔹 Многопоточность (Multithreading)

### 22. Как создать поток в Java?

**Ответ:**

1. **Наследование от Thread:**
```java
class MyThread extends Thread {
    public void run() { }
}
new MyThread().start();
```

2. **Реализация Runnable:**
```java
new Thread(() -> {}).start();
```

3. **Через ExecutorService:**
```java
ExecutorService executor = Executors.newFixedThreadPool(5);
executor.submit(() -> {});
```

4. **Callable + Future:**
```java
Future<Integer> future = executor.submit(() -> 42);
```

---

### 23. В чём разница между Runnable и Callable?

**Ответ:**

| Runnable | Callable |
|----------|----------|
| Не возвращает результат | Возвращает результат |
| `void run()` | `V call()` |
| Не бросает checked исключения | Может бросать |

---

### 24. Что такое synchronized и как работает?

**Ответ:**
Гарантирует что только один поток выполняет код одновременно:

```java
// synchronized метод
public synchronized void method() { }

// synchronized блок
synchronized(lock) { }
```

**Как работает:**
- Использует монитор (intrinsic lock)
- Только один поток может держать монитор

---

### 25. В чём разница между wait и sleep?

**Ответ:**

| wait() | sleep() |
|--------|---------|
| Метод Object | Метод Thread |
| Освобождает монитор | Не освобождает |
| Требует notify/notifyAll | Автоматически просыпается |
| В synchronized блоке | Где угодно |

---

### 26. Что делает volatile?

**Ответ:**
- Гарантирует **видимость** изменений между потоками
- Не гарантирует **атомарность**

```java
private volatile boolean running = true;

// Изменение видно всем потокам сразу
running = false;
```

---

### 27. Что такое AtomicInteger?

**Ответ:**
Атомарная операция без synchronized:

```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();
counter.compareAndSet(expected, newValue);
```

---

### 28. Что такое Thread Pool и зачем нужен?

**Ответ:**
Пул переиспользуемых потоков:

```java
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(() -> {});
executor.shutdown();
```

**Преимущества:**
- Меньше накладных расходов на создание потоков
- Контроль количества потоков
- Управление жизненным циклом

---

### 29. Что такое CompletableFuture?

**Ответ:**
Асинхронные вычисления с цепочкой операций:

```java
CompletableFuture.supplyAsync(() -> result)
    .thenApply(r -> r * 2)
    .thenAccept(System.out::println)
    .exceptionally(ex -> null);
```

---

### 30. Что такое deadlock и как избежать?

**Ответ:**
Deadlock — два потока ждут друг друга:

```
Поток 1: держит Lock A, ждёт Lock B
Поток 2: держит Lock B, ждёт Lock A
```

**Как избежать:**
- Блокировать в одинаковом порядке
- Использовать `tryLock` с таймаутом
- Использовать `java.util.concurrent` утилиты

---

## ✅ Чек-лист готовности

- [ ] Знаю иерархию исключений
- [ ] Понимаю разницу checked/unchecked
- [ ] Знаю основные коллекции и их отличия
- [ ] Понимаю как работает HashMap
- [ ] Умею работать со Stream API
- [ ] Знаю основные операции Stream
- [ ] Знаю сложности алгоритмов
- [ ] Умею создать поток
- [ ] Понимаю synchronized и volatile
- [ ] Знаю что такое ExecutorService
- [ ] Умею использовать CompletableFuture
