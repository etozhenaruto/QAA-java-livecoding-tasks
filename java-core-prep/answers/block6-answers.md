# ✅ Ответы: Блок 6 — Многопоточность

## Задача 6.1: Создание потока через Thread

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

try {
    t1.join();
    t2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

---

## Задача 6.2: Создание потока через Runnable

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

## Задача 6.3: Runnable vs Callable

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
System.out.println("Result: " + result); // 5050
executor.shutdown();
```

---

## Задача 6.4: ExecutorService — Fixed Thread Pool

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 10; i++) {
    int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " выполняется в " + 
                          Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

## Задача 6.5: Synchronized метод

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
executor.awaitTermination(1, TimeUnit.MINUTES);

System.out.println("Final count: " + counter.getCount()); // 1000
```

---

## Задача 6.6: Synchronized блок

```java
class SharedResource {
    private final Object lock = new Object();
    private int value = 0;
    
    public void update(int newValue) {
        synchronized (lock) {
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

## Задача 6.7: Wait и Notify

```java
class Message {
    private String message;
    private boolean empty = true;
    
    public synchronized void produce(String msg) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        message = msg;
        empty = false;
        System.out.println("Produced: " + message);
        notify();
    }
    
    public synchronized String consume() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = true;
        System.out.println("Consumed: " + message);
        notify();
        return message;
    }
}

// Использование:
Message msg = new Message();

new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        msg.produce("Message " + i);
    }
}).start();

new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        msg.consume();
    }
}).start();
```

---

## Задача 6.8: Volatile

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
        running = false;
    }
}

// Использование:
VolatileExample example = new VolatileExample();
example.start();

try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}

example.stop();
```

---

## Задача 6.9: AtomicInteger

```java
class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);
    
    public void increment() {
        count.incrementAndGet();
    }
    
    public int getCount() {
        return count.get();
    }
    
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
executor.awaitTermination(1, TimeUnit.MINUTES);

System.out.println("Final count: " + counter.getCount()); // 1000
```

---

## Задача 6.10: CompletableFuture

```java
// Цепочка асинхронных операций
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        return "Hello";
    })
    .thenApply(s -> s + " World")
    .thenApply(String::toUpperCase)
    .thenAccept(System.out::println)
    .exceptionally(ex -> {
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
all.thenRun(() -> System.out.println("All completed"));

// Блокирующее ожидание
String result = future.join();
```

---

## Вопросы для самопроверки — Ответы

1. **В чём разница между Runnable и Callable?**
   - Runnable не возвращает результат, Callable возвращает и может бросать checked исключения

2. **Что делает synchronized?**
   - Гарантирует что только один поток может выполнять код одновременно

3. **В чём разница между wait и sleep?**
   - wait освобождает монитор и требует notify
   - sleep просто пауза, не освобождает монитор

4. **Что делает volatile?**
   - Гарантирует видимость изменений между потоками

5. **Что такое ExecutorService?**
   - Пул потоков для управления многопоточностью
