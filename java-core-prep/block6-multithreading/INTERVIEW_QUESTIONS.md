# 🎯 Вопросы для собеседования: Многопоточность

## Базовые вопросы

### 1. Как создать поток в Java?

**Ответ:**

**1. Наследование от Thread:**
```java
class MyThread extends Thread {
    public void run() { }
}
new MyThread().start();
```

**2. Реализация Runnable:**
```java
new Thread(() -> System.out.println("Hello")).start();
```

**3. Через ExecutorService:**
```java
ExecutorService executor = Executors.newFixedThreadPool(5);
executor.submit(() -> { });
```

**4. Callable + Future:**
```java
Future<Integer> future = executor.submit(() -> 42);
Integer result = future.get();
```

---

### 2. В чём разница между Runnable и Callable?

**Ответ:**

| Runnable | Callable |
|----------|----------|
| Не возвращает результат | Возвращает результат |
| `void run()` | `V call()` |
| Не бросает checked исключения | Может бросать |

---

### 3. В чём разница между start() и run()?

**Ответ:**
- **start()** — запускает новый поток, вызывает `run()` в новом потоке
- **run()** — просто метод, выполняется в текущем потоке

```java
thread.start(); // Новый поток
thread.run();   // Текущий поток (просто вызов метода)
```

---

### 4. Что такое synchronized и как работает?

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

### 5. В чём разница между wait и sleep?

**Ответ:**

| wait() | sleep() |
|--------|---------|
| Метод Object | Метод Thread |
| Освобождает монитор | Не освобождает |
| Требует notify/notifyAll | Автоматически просыпается |
| В synchronized блоке | Где угодно |

---

### 6. Что делает volatile?

**Ответ:**
- Гарантирует **видимость** изменений между потоками
- Не гарантирует **атомарность**

```java
private volatile boolean running = true;

// Изменение видно всем потокам сразу
running = false;
```

---

### 7. Что такое AtomicInteger?

**Ответ:**
Атомарная операция без synchronized:

```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();
counter.compareAndSet(expected, newValue);
```

---

### 8. Что такое Thread Pool и зачем нужен?

**Ответ:**
Пул переиспользуемых потоков:

```java
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(() -> {});
executor.shutdown();
```

**Преимущества:**
- Меньше накладных расходов
- Контроль количества потоков
- Управление жизненным циклом

---

### 9. Какие типы ExecutorService вы знаете?

**Ответ:**
```java
Executors.newFixedThreadPool(n)      // Фиксированный размер
Executors.newCachedThreadPool()      // Кэширующий
Executors.newSingleThreadExecutor()  // Один поток
Executors.newScheduledThreadPool(n)  // Планировщик
```

---

### 10. Что такое CompletableFuture?

**Ответ:**
Асинхронные вычисления с цепочкой операций:

```java
CompletableFuture.supplyAsync(() -> result)
    .thenApply(r -> r * 2)
    .thenAccept(System.out::println)
    .exceptionally(ex -> null);
```

---

## Продвинутые вопросы

### 11. Что такое deadlock и как избежать?

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

### 12. В чём разница между ReentrantLock и synchronized?

**Ответ:**

| synchronized | ReentrantLock |
|--------------|---------------|
| Встроенный в JVM | API класс |
| Автоматическое освобождение | Ручное (unlock в finally) |
| Нет таймаута | `tryLock(timeout)` |
| Нет fairness | Можно указать fairness |

---

### 13. Что такое CountDownLatch?

**Ответ:**
Ждёт пока N потоков завершат работу:

```java
CountDownLatch latch = new CountDownLatch(3);

// В трёх потоках:
latch.countDown();

// Ждать завершения всех
latch.await();
```

---

### 14. Что такое CyclicBarrier?

**Ответ:**
Барьер для синхронизации N потоков:

```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> {
    System.out.println("Все потоки достигли барьера");
});

// В потоках:
barrier.await(); // Ждать остальные
```

---

### 15. Что такое ConcurrentHashMap?

**Ответ:**
Потокобезопасная HashMap:
- Не блокирует всю карту (сегментированная блокировка)
- Выше производительность чем у synchronized HashMap

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("A", 1);
map.computeIfAbsent("B", k -> 2);
```

---

### 16. В чём разница между CopyOnWriteArrayList и ArrayList?

**Ответ:**

| ArrayList | CopyOnWriteArrayList |
|-----------|---------------------|
| Не потокобезопасна | Потокобезопасна |
| Быстрая запись | Медленная запись (копия) |
| Итератор fail-fast | Итератор не видит изменения |
| Для частых записей | Для частых чтений |

---

### 17. Что такое BlockingQueue?

**Ответ:**
Очередь с блокирующими операциями:

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

queue.put("item");    // Ждёт если очередь полна
String item = queue.take(); // Ждёт если очередь пуста
```

**Использование:** Producer-Consumer паттерн

---

### 18. Что такое Semaphore?

**Ответ:**
Ограничивает количество потоков в критической секции:

```java
Semaphore semaphore = new Semaphore(3); // Максимум 3 потока

semaphore.acquire(); // Получить разрешение
try {
    // Критическая секция
} finally {
    semaphore.release(); // Освободить
}
```

---

### 19. В чём разница между notify и notifyAll?

**Ответ:**
- **notify** — будит один случайный ожидающий поток
- **notifyAll** — будит все ожидающие потоки

**Лучше использовать notifyAll** чтобы избежать deadlock.

---

### 20. Что такое ThreadLocal?

**Ответ:**
Переменная которая имеет отдельное значение для каждого потока:

```java
private static final ThreadLocal<Integer> threadLocal = 
    ThreadLocal.withInitial(() -> 0);

threadLocal.set(42);
Integer value = threadLocal.get();
```

**Использование:**
- Сессионные данные
- Форматирование дат (SimpleDateFormat не потокобезопасен)

---

### 21. Что такое daemon поток?

**Ответ:**
Фоновый поток который не предотвращает завершение JVM:

```java
thread.setDaemon(true);
thread.start();
```

**Примеры:** Garbage Collector, Finalizer

---

### 22. Как остановить поток?

**Ответ:**
```java
// Через volatile флаг
private volatile boolean running = true;

public void stop() {
    running = false;
}

public void run() {
    while (running) {
        // работа
    }
}

// Через interrupt
thread.interrupt();

// В потоке:
if (Thread.interrupted()) {
    // завершение
}
```

**Не использовать:** `thread.stop()` — устарел и опасен!

---

### 23. Что такое join и зачем нужен?

**Ответ:**
Ждёт завершения потока:

```java
thread.start();
thread.join();      // Ждать бесконечно
thread.join(1000);  // Ждать максимум 1 секунду
```

---

### 24. Что такое yield?

**Ответ:**
Уступает CPU другим потокам:

```java
Thread.yield();
```

**Не гарантирует** что другой поток сразу получит CPU.

---

### 25. Какие проблемы многопоточности вы знаете?

**Ответ:**

| Проблема | Описание | Решение |
|----------|----------|---------|
| Race Condition | Результат зависит от порядка | synchronized, Atomic |
| Deadlock | Потоки ждут друг друга | Одинаковый порядок lock |
| Livelock | Потоки постоянно меняют состояние | Backoff алгоритм |
| Starvation | Поток не получает CPU | Fairness, приоритеты |

---

### 26. Что такое CAS (Compare-And-Swap)?

**Ответ:**
Атомарная операция:

```java
AtomicInteger atomic = new AtomicInteger(0);
atomic.compareAndSet(0, 1); // Если == 0, установить 1
```

**Используется в:** AtomicInteger, ConcurrentHashMap

---

### 27. Когда parallel stream медленнее sequential?

**Ответ:**
- Маленькие коллекции
- I/O операции
- Накладные расходы на синхронизацию больше выигрыша

---

### 28. Что такое Fork/Join框架?

**Ответ:**
Фреймворк для параллельной обработки:
- Разделяет задачу на подзадачи
- Выполняет параллельно
- Объединяет результаты

**Используется в:** parallel stream

---

### 29. Что такое ScheduledExecutorService?

**Ответ:**
Планировщик задач:

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

// Выполнить через 1 секунду
scheduler.schedule(() -> {}, 1, TimeUnit.SECONDS);

// Выполнять каждые 1 секунду
scheduler.scheduleAtFixedRate(() -> {}, 0, 1, TimeUnit.SECONDS);
```

---

### 30. Best Practices для многопоточности?

**Ответ:**

✅ **Делать:**
- Использовать ExecutorService
- Освобождать lock в finally
- Предпочитать concurrent коллекции
- Использовать атомарные операции

❌ **Не делать:**
- Создавать потоки вручную
- Использовать `thread.stop()`
- Игнорировать InterruptedException
- Блокировать в разном порядке
