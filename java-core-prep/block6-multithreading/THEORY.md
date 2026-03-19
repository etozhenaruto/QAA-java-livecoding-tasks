# 📚 Теория: Многопоточность (Multithreading)

## 1. Что такое многопоточность?

**Поток (Thread)** — это минимальная единица выполнения в Java.

**Многопоточность** позволяет выполнять несколько задач одновременно, что улучшает:
- Производительность (использование нескольких ядер CPU)
- Отзывчивость приложения
- Утилизацию ресурсов

---

## 2. Создание потоков

### 2.1. Наследование от Thread

```java
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Использование:
MyThread t1 = new MyThread();
MyThread t2 = new MyThread();
t1.start(); // Запускает новый поток, вызывает run()
t2.start();
```

---

### 2.2. Реализация Runnable

```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Поток выполняется: " + Thread.currentThread().getName());
    }
}

// Использование:
Thread t1 = new Thread(new MyRunnable(), "Thread-1");
Thread t2 = new Thread(new MyRunnable(), "Thread-2");
t1.start();
t2.start();
```

---

### 2.3. Lambda выражения (Java 8+)

```java
new Thread(() -> {
    System.out.println("Hello from thread: " + Thread.currentThread().getName());
}, "Lambda-Thread").start();

// Короткая запись
Runnable r = () -> System.out.println("Running");
new Thread(r).start();
```

---

### 2.4. Callable и Future

**Callable** — как Runnable, но возвращает результат и может бросать checked исключения.

```java
Callable<Integer> callable = () -> {
    int sum = 0;
    for (int i = 1; i <= 100; i++) {
        sum += i;
    }
    return sum;
};

// Использование с ExecutorService
ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(callable);

// Блокирующее получение результата
Integer result = future.get(); // Ждёт завершения
System.out.println("Result: " + result); // 5050

executor.shutdown();
```

---

## 3. Жизненный цикл потока

```
NEW → RUNNABLE → RUNNING → BLOCKED/WAITING/TIMED_WAITING → TERMINATED
```

| Состояние | Описание |
|-----------|----------|
| NEW | Поток создан, но не запущен |
| RUNNABLE | Готов к выполнению, ждёт CPU |
| RUNNING | Выполняется |
| BLOCKED | Ждёт монитор (synchronized) |
| WAITING | Ждёт notify/notifyAll |
| TIMED_WAITING | Ждёт sleep/notify с таймаутом |
| TERMINATED | Завершён |

---

## 4. Методы Thread

```java
// Статические методы
Thread.sleep(1000);           // Пауза 1 секунда
Thread.currentThread();       // Текущий поток
Thread.yield();               // Уступить CPU другим потокам

// Методы экземпляра
thread.start();               // Запустить поток
thread.run();                 // Просто вызвать метод (в текущем потоке!)
thread.join();                // Ждать завершения потока
thread.join(1000);            // Ждать максимум 1 секунду
thread.interrupt();           // Прервать поток
thread.isAlive();             // Поток ещё выполняется?
thread.isInterrupted();       // Поток прерван?
thread.getName();             // Имя потока
thread.setName("New Name");   // Установить имя
thread.setPriority(10);       // Приоритет (1-10)
thread.setDaemon(true);       // Демон-поток
```

---

## 5. Проблемы многопоточности

### 5.1. Race Condition

Ситуация когда результат зависит от порядка выполнения потоков:

```java
class Counter {
    private int count = 0;
    
    public void increment() {
        count++; // НЕ атомарно!
        // 1. Прочитать count
        // 2. Увеличить на 1
        // 3. Записать обратно
    }
}

// Два потока вызывают increment() одновременно
// Поток 1: прочитал 0
// Поток 2: прочитал 0
// Поток 1: записал 1
// Поток 2: записал 1
// Ожидали 2, получили 1!
```

---

### 5.2. Решение: synchronized

```java
class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
    }
    
    public synchronized int getCount() {
        return count;
    }
}
```

---

### 5.3. Deadlock

Два потока ждут друг друга:

```
Поток 1: держит Lock A, ждёт Lock B
Поток 2: держит Lock B, ждёт Lock A
```

**Пример:**
```java
Object lock1 = new Object();
Object lock2 = new Object();

// Поток 1
synchronized(lock1) {
    Thread.sleep(100);
    synchronized(lock2) { }
}

// Поток 2
synchronized(lock2) {
    Thread.sleep(100);
    synchronized(lock1) { }
}
```

**Как избежать:**
- Блокировать в одинаковом порядке
- Использовать `tryLock` с таймаутом
- Использовать `java.util.concurrent` утилиты

---

## 6. synchronized

### 6.1. synchronized метод

```java
public synchronized void method() {
    // Только один поток может выполнять
}

// Эквивалентно:
public void method() {
    synchronized(this) {
        // ...
    }
}
```

### 6.2. synchronized блок

```java
private final Object lock = new Object();

public void method() {
    synchronized(lock) {
        // Критическая секция
    }
}
```

### 6.3. synchronized static метод

```java
public static synchronized void staticMethod() {
    // Блокируется на Class объекте
}

// Эквивалентно:
public static void staticMethod() {
    synchronized(MyClass.class) {
        // ...
    }
}
```

---

## 7. wait, notify, notifyAll

Методы класса `Object` для взаимодействия потоков:

```java
class Message {
    private String message;
    private boolean empty = true;
    
    public synchronized void produce(String msg) {
        // Ждать пока потребитель не заберёт
        while (!empty) {
            try {
                wait(); // Освобождает монитор и ждёт
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        message = msg;
        empty = false;
        notify(); // Уведомить один ожидающий поток
    }
    
    public synchronized String consume() {
        // Ждать пока производитель не произведёт
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        empty = true;
        notifyAll(); // Уведомить все ожидающие потоки
        return message;
    }
}
```

**Важно:**
- Вызывать только в `synchronized` блоке
- `wait()` освобождает монитор
- Использовать `while` вместо `if` для проверки условия

---

## 8. volatile

**volatile** гарантирует видимость изменений между потоками:

```java
class VolatileExample {
    private volatile boolean running = true;
    
    public void start() {
        new Thread(() -> {
            while (running) {
                // ...
            }
        }).start();
    }
    
    public void stop() {
        running = false; // Изменение видно всем потокам сразу
    }
}
```

**Что даёт volatile:**
- ✅ Видимость изменений между потоками
- ✅ Запрещает переупорядочивание инструкций
- ❌ Не гарантирует атомарность

---

## 9. Atomic variables

Атомарные операции без synchronized:

```java
AtomicInteger atomicInt = new AtomicInteger(0);

// Атомарные операции
atomicInt.incrementAndGet();      // ++i
atomicInt.getAndIncrement();      // i++
atomicInt.decrementAndGet();      // --i
atomicInt.addAndGet(5);           // i += 5
atomicInt.compareAndSet(5, 10);   // CAS: если == 5, установить 10

// Использование в счётчике
class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);
    
    public void increment() {
        count.incrementAndGet();
    }
    
    public int getCount() {
        return count.get();
    }
}
```

**Другие атомарные классы:**
- `AtomicLong`, `AtomicBoolean`
- `AtomicReference<T>`
- `AtomicIntegerArray`, `AtomicLongArray`
- `AtomicIntegerFieldUpdater`

---

## 10. ExecutorService (Thread Pool)

Пул потоков для управления многопоточностью:

### 10.1. Создание пула

```java
// Фиксированный размер
ExecutorService executor = Executors.newFixedThreadPool(5);

// Кэширующий (создаёт новые по необходимости)
ExecutorService cached = Executors.newCachedThreadPool();

// Один поток
ExecutorService single = Executors.newSingleThreadExecutor();

// Планировщик
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
```

### 10.2. Использование

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Выполнить Runnable
executor.submit(() -> {
    System.out.println("Task running in " + Thread.currentThread().getName());
});

// Выполнить Callable
Future<Integer> future = executor.submit(() -> {
    return 42;
});

// Получить результат
Integer result = future.get(); // Блокируется

// Завершение
executor.shutdown(); // Не принимать новые задачи
try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow(); // Принудительно
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
}
```

---

## 11. CompletableFuture

Асинхронные вычисления с цепочкой операций:

### 11.1. Базовое использование

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        // Шаг 1: Получить данные (в другом потоке)
        return "Hello";
    })
    .thenApply(s -> {
        // Шаг 2: Преобразовать
        return s + " World";
    })
    .thenApply(String::toUpperCase)
    .thenAccept(System.out::println) // Шаг 3: Использовать результат
    .exceptionally(ex -> {
        // Обработка ошибок
        System.out.println("Error: " + ex.getMessage());
        return null;
    });

// Блокирующее ожидание
String result = future.join();
```

### 11.2. Комбинирование

```java
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

// Комбинировать два future
CompletableFuture<Integer> combined = future1.thenCombine(future2, (a, b) -> a + b);
combined.thenAccept(System.out::println); // 30

// Все вместе
CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2);
all.thenRun(() -> System.out.println("All completed"));

// Любое завершённое
CompletableFuture<Object> any = CompletableFuture.anyOf(future1, future2);
```

### 11.3. Обработка ошибок

```java
future.exceptionally(ex -> "default");
future.handle((result, ex) -> ex != null ? "default" : result);
future.whenComplete((result, ex) -> {
    if (ex != null) {
        System.out.println("Error: " + ex.getMessage());
    }
});
```

---

## 12. Concurrent коллекции

Потокобезопасные коллекции:

### 12.1. ConcurrentHashMap

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Потокобезопасные операции
map.put("A", 1);
map.get("A");
map.computeIfAbsent("B", k -> 2);
map.merge("A", 1, Integer::sum);

// Размер не блокируется
int size = map.size();
```

### 12.2. CopyOnWriteArrayList

```java
List<String> list = new CopyOnWriteArrayList<>();

// Безопасная итерация без блокировки
for (String s : list) {
    // Можно модифицировать list во время итерации
}
```

### 12.3. BlockingQueue

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

// Блокирующие операции
queue.put("item");    // Ждёт если очередь полна
String item = queue.take(); // Ждёт если очередь пуста

// С таймаутом
boolean added = queue.offer("item", 1, TimeUnit.SECONDS);
String polled = queue.poll(1, TimeUnit.SECONDS);
```

---

## 13. Lock интерфейсы

### 13.1. ReentrantLock

```java
Lock lock = new ReentrantLock();

lock.lock();
try {
    // Критическая секция
} finally {
    lock.unlock(); // Обязательно в finally!
}

// С таймаутом
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try {
        // ...
    } finally {
        lock.unlock();
    }
} else {
    // Не удалось получить lock
}
```

### 13.2. ReadWriteLock

```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();

// Чтение (множество потоков)
rwLock.readLock().lock();
try {
    // ...
} finally {
    rwLock.readLock().unlock();
}

// Запись (один поток)
rwLock.writeLock().lock();
try {
    // ...
} finally {
    rwLock.writeLock().unlock();
}
```

---

## 14. Semaphore

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

## 15. CountDownLatch и CyclicBarrier

### 15.1. CountDownLatch

Ждёт пока N потоков завершат работу:

```java
CountDownLatch latch = new CountDownLatch(3);

// В трёх потоках:
new Thread(() -> {
    // Работа
    latch.countDown();
}).start();

// Ждать завершения всех
latch.await();
System.out.println("All threads completed");
```

### 15.2. CyclicBarrier

Барьер для синхронизации N потоков:

```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> {
    System.out.println("Все потоки достигли барьера");
});

// В трёх потоках:
new Thread(() -> {
    // Работа
    barrier.await(); // Ждать остальные
    // Продолжить после барьера
}).start();
```

---

## 16. Шпаргалка

### Создание потоков

| Способ | Когда использовать |
|--------|-------------------|
| extends Thread | Когда нужно переопределить методы Thread |
| implements Runnable | Разумный выбор по умолчанию |
| Lambda | Короткие задачи |
| Callable + Future | Когда нужен результат |
| CompletableFuture | Асинхронные цепочки |

### Синхронизация

| Механизм | Когда использовать |
|----------|-------------------|
| synchronized | Простая синхронизация |
| volatile | Только видимость |
| Atomic* | Счётчики, флаги |
| Lock | Продвинутые возможности |
| Concurrent collections | Потокобезопасные коллекции |

### Взаимодействие

| Механизм | Описание |
|----------|----------|
| wait/notify | Базовое взаимодействие |
| CountDownLatch | Ждать N потоков |
| CyclicBarrier | Синхронизировать N потоков |
| Semaphore | Ограничить доступ |
| BlockingQueue | Producer-Consumer |

---

## 17. Best Practices

1. **Использовать ExecutorService вместо создания потоков вручную**
2. **Всегда освобождать ресурсы в finally**
3. **Предпочитать concurrent коллекции**
4. **Избегать deadlock — блокировать в одинаковом порядке**
5. **Использовать volatile только для флагов**
6. **Предпочитать атомарные операции synchronized**
7. **Обрабатывать InterruptedException корректно**
