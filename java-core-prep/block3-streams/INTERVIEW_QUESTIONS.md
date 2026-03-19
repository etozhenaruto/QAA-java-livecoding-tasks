# 🎯 Вопросы для собеседования: Стримы

## Базовые вопросы

### 1. Что такое Stream API?

**Ответ:**
Stream — это последовательность элементов поддерживающая конвейерную обработку данных.

**Особенности:**
- Не хранит данные (работает с источником)
- Не изменяет источник (immutable)
- Ленивое вычисление (intermediate операции)
- Одноразовый (нельзя переиспользовать)

---

### 2. В чём разница между Collection и Stream?

**Ответ:**

| Collection | Stream |
|------------|--------|
| Хранит данные | Не хранит |
| Можно итерировать多次 | Одноразовый |
| Eager | Lazy (intermediate) |
| Изменяемый | Не изменяет источник |

---

### 3. Какие операции бывают у Stream?

**Ответ:**

**Intermediate (возвращают Stream, ленивые):**
- filter, map, flatMap, sorted, distinct, limit, skip, peek

**Terminal (возвращают результат, eager):**
- forEach, collect, reduce, count, anyMatch, allMatch, findFirst, findAny

---

### 4. Что такое flatMap и чем отличается от map?

**Ответ:**

**map** — преобразует каждый элемент:
```java
List<String> upper = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

**flatMap** — "сплющивает" вложенные структуры:
```java
List<Integer> flat = nested.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
// [[1,2], [3,4]] → [1,2,3,4]
```

---

### 5. Что делает Collectors.groupingBy?

**Ответ:**
Группирует элементы по ключу (аналог SQL GROUP BY):

```java
Map<Integer, List<Person>> byAge = people.stream()
    .collect(Collectors.groupingBy(Person::getAge));
// {25=[Alice, Catherine], 30=[John, Bob]}
```

---

### 6. В чём разница между findFirst и findAny?

**Ответ:**
- **findFirst** — первый элемент (важен порядок)
- **findAny** — любой элемент (может быть быстрее в parallel stream)

---

### 7. Что такое parallel stream?

**Ответ:**
Параллельная обработка данных:

```java
list.parallelStream()
    .map(this::expensiveOperation)
    .collect(Collectors.toList());
```

**Когда использовать:**
- ✅ Большой объём данных
- ✅ CPU-intensive операции
- ❌ Не для I/O
- ❌ Не для маленьких коллекций

---

### 8. Что делает Optional?

**Ответ:**
Класс для представления значения которое может отсутствовать:

```java
Optional<String> opt = Optional.ofNullable(getValue());

String val = opt.orElse("default");
opt.ifPresent(System.out::println);
```

---

### 9. Как отфильтровать null значения в stream?

**Ответ:**
```java
list.stream()
    .filter(Objects::nonNull)
    .collect(Collectors.toList());
```

---

### 10. Как объединить два stream?

**Ответ:**
```java
Stream.concat(stream1, stream2)
    .collect(Collectors.toList());

// Или через flatMap
Stream.of(stream1, stream2)
    .flatMap(s -> s)
    .collect(Collectors.toList());
```

---

## Продвинутые вопросы

### 11. Что такое lazy evaluation?

**Ответ:**
Intermediate операции не выполняются пока не вызвана terminal:

```java
numbers.stream()
    .filter(n -> {
        System.out.println("Filter: " + n);
        return n % 2 == 0;
    }) // Не выполнится пока не вызвана terminal
    .limit(2)
    .forEach(System.out::println); // Terminal
```

---

### 12. Можно ли переиспользовать Stream?

**Ответ:**
Нет, stream одноразовый:

```java
Stream<Integer> stream = list.stream();
stream.count(); // Ok
stream.count(); // IllegalStateException!
```

---

### 13. В чём разница между peek и forEach?

**Ответ:**
- **peek** — intermediate операция (для отладки)
- **forEach** — terminal операция

```java
// peek выполнится только если есть terminal
stream.peek(System.out::println).count();

// forEach — terminal, stream нельзя использовать после
stream.forEach(System.out::println);
```

---

### 14. Что такое toMap и как обрабатывать дубликаты ключей?

**Ответ:**
```java
// Без обработки дубликатов (IllegalStateException если дубликаты)
Map<String, Integer> map = people.stream()
    .collect(Collectors.toMap(Person::getName, Person::getAge));

// С обработкой дубликатов
Map<String, Integer> map = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge,
        (existing, replacement) -> existing // или replacement
    ));
```

---

### 15. В чём разница между collect и reduce?

**Ответ:**
- **collect** — собирает в mutable контейнер (List, Set, Map)
- **reduce** — агрегирует в одно значение

```java
// collect
List<Integer> list = stream.collect(Collectors.toList());

// reduce
Integer sum = stream.reduce(0, Integer::sum);
```

---

### 16. Что такое partitioningBy?

**Ответ:**
Разделяет на две группы по условию:

```java
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

List<Integer> even = partitioned.get(true);
List<Integer> odd = partitioned.get(false);
```

---

### 17. Как получить средний возраст из списка Person?

**Ответ:**
```java
Double avg = people.stream()
    .collect(Collectors.averagingInt(Person::getAge));

// Или через mapToInt
Double avg = people.stream()
    .mapToInt(Person::getAge)
    .average()
    .orElse(0);
```

---

### 18. Что делают joining, summingInt, averagingInt?

**Ответ:**
```java
// joining — объединяет строки
String joined = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));

// summingInt — сумма int поля
Integer total = people.stream()
    .collect(Collectors.summingInt(Person::getAge));

// averagingInt — среднее
Double avg = people.stream()
    .collect(Collectors.averagingInt(Person::getAge));
```

---

### 19. Как работает Stream.generate и Stream.iterate?

**Ответ:**
```java
// generate — бесконечный stream
Stream<Double> randoms = Stream.generate(Math::random);

// iterate — итерация с функцией
Stream<Integer> powers = Stream.iterate(1, n -> n * 2);

// С ограничением
List<Integer> result = Stream.iterate(1, n -> n * 2)
    .limit(5)
    .collect(Collectors.toList());
// [1, 2, 4, 8, 16]
```

---

### 20. Когда parallel stream медленнее sequential?

**Ответ:**
- Маленькие коллекции (< 1000 элементов)
- I/O операции
- Элементы зависят друг от друга
- Накладные расходы на синхронизацию больше выигрыша
