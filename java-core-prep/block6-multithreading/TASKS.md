# 📝 Блок 6: Многопоточность (Multithreading) — 10 задач

## Теория для повторения
- Создание потоков: Thread, Runnable, Callable
- ExecutorService и Thread Pool
- synchronized, Lock, ReentrantLock
- wait, notify, notifyAll
- volatile, atomic variables
- CompletableFuture
- Concurrent коллекции

---

## 📋 Задания

### Задача 6.1: Создание потока через Thread

Создайте поток наследуясь от Thread:

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
t1.start();
t2.start();
```

---

### Задача 6.2: Создание потока через Runnable

Создайте поток реализовав Runnable:

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

// Lambda версия:
new Thread(() -> System.out.println("Hello from thread"), "Lambda-Thread").start();
```

---

### Задача 6.3: Runnable vs Callable

Сравните Runnable и Callable:

```java
// Runnable — не возвращает результат
Runnable runnable = () -> {
    System.out.println("Выполняется без результата");
};
new Thread(runnable).start();

// Callable — возвращает результат и может бросать исключения
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
Integer result = future.get(); // Блокируется до завершения
executor.shutdown();
```

---

### Задача 6.4: ExecutorService — Fixed Thread Pool

Используйте пул потоков:

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 10; i++) {
    int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " выполняется в " + 
                          Thread.currentThread().getName());
    });
}

executor.shutdown();
try {
    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
}
```

---

### Задача 6.5: Synchronized метод

Создайте синхронизированный метод:

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

// Использование с несколькими потоками:
Counter counter = new Counter();

ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 1000; i++) {
    executor.submit(counter::increment);
}
executor.shutdown();
```

---

### Задача 6.6: Synchronized блок

Используйте synchronized блок с монитором:

```java
class SharedResource {
    private final Object lock = new Object();
    private int value = 0;
    
    public void update(int newValue) {
        synchronized (lock) {
            // Критическая секция
            value = newValue;
            System.out.println("Value updated to: " + value);
        }
    }
    
    public int read() {
        synchronized (lock) {
            return value;
        }
    }
}
```

---

### Задача 6.7: Wait и Notify

Реализуйте взаимодействие потоков через wait/notify:

```java
class Message {
    private String message;
    private boolean empty = true;
    
    public synchronized void produce(String msg) {
        while (!empty) {
            try {
                wait(); // Ждать пока потребитель не заберёт
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        message = msg;
        empty = false;
        notify(); // Уведомить потребителя
    }
    
    public synchronized String consume() {
        while (empty) {
            try {
                wait(); // Ждать пока производитель не произведёт
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = true;
        notify(); // Уведомить производителя
        return message;
    }
}
```

---

### Задача 6.8: Volatile

Продемонстрируйте использование volatile:

```java
class VolatileExample {
    private volatile boolean running = true;
    
    public void start() {
        new Thread(() -> {
            int i = 0;
            while (running) {
                if (i % 1000000 == 0) {
                    System.out.println("Running... " + i);
                }
                i++;
            }
            System.out.println("Stopped at " + i);
        }).start();
    }
    
    public void stop() {
        running = false; // Изменение видно всем потокам сразу
    }
}
```

---

### Задача 6.9: AtomicInteger

Используйте атомарные операции:

```java
class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);
    
    public void increment() {
        count.incrementAndGet();
    }
    
    public int getCount() {
        return count.get();
    }
    
    // Compare and set
    public boolean compareAndSet(int expected, int newValue) {
        return count.compareAndSet(expected, newValue);
    }
}

// Использование:
AtomicCounter counter = new AtomicCounter();
ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 1000; i++) {
    executor.submit(counter::increment);
}
executor.shutdown();
System.out.println("Final count: " + counter.getCount());
```

---

### Задача 6.10: CompletableFuture

Используйте CompletableFuture для асинхронных операций:

```java
// Цепочка асинхронных операций
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        // Шаг 1: Получить данные
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

// Комбинирование нескольких CompletableFuture
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> combined = future1.thenCombine(future2, (a, b) -> a + b);
combined.thenAccept(System.out::println); // 30

// Все вместе
CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2);
```

---

## ✅ Чек-лист для самопроверки

- [ ] Умеете создать поток через Thread
- [ ] Умеете создать поток через Runnable
- [ ] Знаете разницу между Runnable и Callable
- [ ] Умеете использовать ExecutorService
- [ ] Понимаете synchronized методы и блоки
- [ ] Умеете использовать wait/notify
- [ ] Понимаете что делает volatile
- [ ] Умеете использовать AtomicInteger
- [ ] Знаете что такое CompletableFuture
- [ ] Понимаете проблему race condition

---

## 📌 Подсказки

<details>
<summary>Подсказка: start() vs run()</summary>

```java
// start() — запускает новый поток, вызывает run() в новом потоке
thread.start();

// run() — просто метод, выполняется в текущем потоке
thread.run();
```
</details>

<details>
<summary>Подсказка: Thread.sleep vs wait()</summary>

```java
// Thread.sleep(ms) — пауза, не освобождает монитор
Thread.sleep(1000);

// wait() — ждёт notify(), освобождает монитор (в synchronized блоке)
synchronized(lock) {
    lock.wait();
}
```
</details>

<details>
<summary>Подсказка: Concurrent коллекции</summary>

```java
// Потокобезопасные коллекции
ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
CopyOnWriteArrayList<T> list = new CopyOnWriteArrayList<>();
BlockingQueue<T> queue = new LinkedBlockingQueue<>();
ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
```
</details>

<details>
<summary>Подсказка: ExecutorService типы</summary>

```java
Executors.newFixedThreadPool(n)      // Пул с фиксированным числом потоков
Executors.newCachedThreadPool()      // Пул с кэшированием
Executors.newSingleThreadExecutor()  // Один поток
Executors.newScheduledThreadPool(n)  // Планировщик
```
</details>
