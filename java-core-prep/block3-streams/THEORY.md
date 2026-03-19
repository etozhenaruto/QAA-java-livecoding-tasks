# 📚 Теория: Стримы (Streams)

## 1. Что такое Stream?

**Stream (поток)** — это последовательность элементов поддерживающая конвейерную обработку данных.

**Важные особенности:**
- ❌ **Не хранит данные** — работает с источником (коллекция, массив)
- ❌ **Не изменяет источник** — операции создают новый stream
- ✅ **Ленивое вычисление** — intermediate операции не выполняются пока не будет terminal
- ❌ **Одноразовый** — нельзя переиспользовать после terminal операции

```java
List<String> list = Arrays.asList("A", "B", "C");

// Stream не изменяет list
list.stream()
    .map(String::toLowerCase)
    .collect(Collectors.toList());

// list всё ещё ["A", "B", "C"]
```

---

## 2. Создание Stream

### 2.1. Из коллекции
```java
List<String> list = Arrays.asList("A", "B", "C");
Stream<String> stream = list.stream();
Stream<String> parallelStream = list.parallelStream();
```

### 2.2. Из массива
```java
int[] array = {1, 2, 3};
IntStream stream = Arrays.stream(array);
Stream<Integer> boxed = Arrays.stream(array).boxed();
```

### 2.3. Из значений
```java
Stream<String> stream = Stream.of("A", "B", "C");
Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);
```

### 2.4. Пустой stream
```java
Stream<String> empty = Stream.empty();
```

### 2.5. Генерация
```java
// Бесконечный stream
Stream<Double> randoms = Stream.generate(Math::random);

// Итерация
Stream<Integer> powers = Stream.iterate(1, n -> n * 2);

// С ограничением
List<Integer> result = Stream.iterate(1, n -> n * 2)
    .limit(5)
    .collect(Collectors.toList());
// [1, 2, 4, 8, 16]
```

### 2.6. IntStream, LongStream, DoubleStream
```java
// Диапазон чисел
IntStream range = IntStream.range(1, 10);        // 1 до 9
IntStream rangeClosed = IntStream.rangeClosed(1, 10); // 1 до 10

// Из значений
IntStream of = IntStream.of(1, 2, 3);

// Конвертация
int[] array = IntStream.range(1, 10).toArray();
```

---

## 3. Операции со Stream

### 3.1. Intermediate операции (возвращают Stream)

**Ленивые** — не выполняются пока не вызвана terminal операция.

```java
Stream<T> filter(Predicate<T> predicate)
Stream<R> map(Function<T, R> mapper)
IntStream mapToInt(ToIntFunction<T> mapper)
Stream<R> flatMap(Function<T, Stream<R>> mapper)
Stream<T> distinct()
Stream<T> sorted()
Stream<T> sorted(Comparator<T> comparator)
Stream<T> limit(long maxSize)
Stream<T> skip(long n)
Stream<T> peek(Consumer<T> action)
Stream<T> takeWhile(Predicate<T> predicate)     // Java 9+
Stream<T> dropWhile(Predicate<T> predicate)     // Java 9+
```

---

### 3.2. Terminal операции (возвращают результат)

**Eager** — запускают выполнение всего конвейера.

```java
void forEach(Consumer<T> action)
List<T> collect(Collector<T, A, R> collector)
Optional<T> reduce(BinaryOperator<T> accumulator)
long count()
boolean anyMatch(Predicate<T> predicate)
boolean allMatch(Predicate<T> predicate)
boolean noneMatch(Predicate<T> predicate)
Optional<T> findFirst()
Optional<T> findAny()
Optional<T> min(Comparator<T> comparator)
Optional<T> max(Comparator<T> comparator)
T[] toArray(IntFunction<T[]> generator)
```

---

## 4. Intermediate операции подробно

### 4.1. filter

Фильтрует элементы по условию:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> even = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
// [2, 4, 6, 8, 10]

// Отрицание
List<Integer> odd = numbers.stream()
    .filter(n -> n % 2 != 0)
    .collect(Collectors.toList());
// [1, 3, 5, 7, 9]
```

---

### 4.2. map

Преобразует каждый элемент:

```java
List<String> names = Arrays.asList("john", "alice", "bob");

// В верхний регистр
List<String> upper = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
// [JOHN, ALICE, BOB]

// Длина строк
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());
// [4, 5, 3]
```

---

### 4.3. flatMap

"Сплющивает" вложенные структуры:

```java
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4),
    Arrays.asList(5, 6)
);

// flatten
List<Integer> flat = nested.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
// [1, 2, 3, 4, 5, 6]

// Пример со строками
List<String> sentences = Arrays.asList("Hello World", "Java Stream");
List<String> words = sentences.stream()
    .flatMap(s -> Arrays.stream(s.split(" ")))
    .collect(Collectors.toList());
// [Hello, World, Java, Stream]
```

---

### 4.4. distinct

Удаляет дубликаты (использует `equals()`):

```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4);

List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
// [1, 2, 3, 4]
```

---

### 4.5. sorted

Сортирует элементы:

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);

// Натуральная сортировка
List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());
// [1, 2, 5, 8, 9]

// Обратная сортировка
List<Integer> reversed = numbers.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
// [9, 8, 5, 2, 1]

// Свой Comparator
List<String> names = Arrays.asList("John", "Alice", "Bob");
List<String> byLength = names.stream()
    .sorted(Comparator.comparingInt(String::length))
    .collect(Collectors.toList());
// [Bob, John, Alice]
```

---

### 4.6. limit и skip

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Первые 5 элементов
List<Integer> first5 = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());
// [1, 2, 3, 4, 5]

// Пропустить первые 5
List<Integer> after5 = numbers.stream()
    .skip(5)
    .collect(Collectors.toList());
// [6, 7, 8, 9, 10]

// Комбинация
List<Integer> middle = numbers.stream()
    .skip(2)
    .limit(3)
    .collect(Collectors.toList());
// [3, 4, 5]
```

---

### 4.7. peek

Выполняет действие для каждого элемента (обычно для отладки):

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

List<Integer> result = numbers.stream()
    .peek(n -> System.out.println("Before filter: " + n))
    .filter(n -> n % 2 == 0)
    .peek(n -> System.out.println("After filter: " + n))
    .collect(Collectors.toList());
```

---

## 5. Terminal операции подробно

### 5.1. forEach

Выполняет действие для каждого элемента:

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// Lambda
names.stream().forEach(name -> System.out.println(name));

// Method reference
names.stream().forEach(System.out::println);

// С индексом (через AtomicInteger)
AtomicInteger index = new AtomicInteger();
names.stream().forEach(name -> 
    System.out.println((index.incrementAndGet()) + ": " + name)
);
```

---

### 5.2. collect

Собирает результаты в коллекцию:

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// В список
List<String> list = names.stream()
    .collect(Collectors.toList());

// В множество
Set<String> set = names.stream()
    .collect(Collectors.toSet());

// В конкретную коллекцию
ArrayList<String> arrayList = names.stream()
    .collect(Collectors.toCollection(ArrayList::new));
```

---

### 5.3. reduce

Агрегирует элементы в одно значение:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Сумма
Integer sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);
// или
Integer sum2 = numbers.stream()
    .reduce(0, Integer::sum);

// Произведение
Integer product = numbers.stream()
    .reduce(1, (a, b) -> a * b);

// Максимум
Optional<Integer> max = numbers.stream()
    .reduce(Integer::max);

// Минимум
Optional<Integer> min = numbers.stream()
    .reduce(Integer::min);

// Без начального значения
Optional<Integer> sum3 = numbers.stream()
    .reduce((a, b) -> a + b);
```

---

### 5.4. count

Возвращает количество элементов:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

long count = numbers.stream().count(); // 5

// С фильтром
long evenCount = numbers.stream()
    .filter(n -> n % 2 == 0)
    .count(); // 2
```

---

### 5.5. match

Проверяют условие:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Есть ли хотя бы один чётный?
boolean anyEven = numbers.stream()
    .anyMatch(n -> n % 2 == 0); // true

// Все ли чётные?
boolean allEven = numbers.stream()
    .allMatch(n -> n % 2 == 0); // false

// Нет ли чётных?
boolean noneEven = numbers.stream()
    .noneMatch(n -> n % 2 == 0); // false
```

---

### 5.6. findFirst и findAny

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// Первый элемент
Optional<String> first = names.stream()
    .findFirst();

// Любой элемент (полезно в parallel stream)
Optional<String> any = names.stream()
    .findAny();

// С фильтром
Optional<String> found = names.stream()
    .filter(n -> n.startsWith("A"))
    .findFirst();

// Получить значение или default
String result = found.orElse("Unknown");
```

---

### 5.7. min и max

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);

Optional<Integer> min = numbers.stream()
    .min(Integer::compare); // 1

Optional<Integer> max = numbers.stream()
    .max(Integer::compare); // 9

// С Comparator
List<String> names = Arrays.asList("John", "Alice", "Bob");
Optional<String> longest = names.stream()
    .max(Comparator.comparingInt(String::length)); // Alice
```

---

### 5.8. toArray

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// В Object[]
Object[] array = names.stream().toArray();

// В String[]
String[] stringArray = names.stream()
    .toArray(String[]::new);
```

---

## 6. Collectors подробно

### 6.1. toList, toSet, toCollection

```java
List<String> list = stream.collect(Collectors.toList());
Set<String> set = stream.collect(Collectors.toSet());
ArrayList<String> arrayList = stream.collect(
    Collectors.toCollection(ArrayList::new)
);
```

---

### 6.2. toMap

```java
List<Person> people = Arrays.asList(
    new Person("John", 30),
    new Person("Alice", 25),
    new Person("Bob", 35)
);

// Map<Name, Age>
Map<String, Integer> nameToAge = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge
    ));

// С обработкой дубликатов
Map<String, Integer> map = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge,
        (existing, replacement) -> existing // или replacement
    ));

// С кастомной картой (TreeMap)
Map<String, Integer> treeMap = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge,
        (e, r) -> e,
        TreeMap::new
    ));
```

---

### 6.3. joining

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// Без разделителя
String joined = names.stream()
    .collect(Collectors.joining());
// JohnAliceBob

// С разделителем
String withDelimiter = names.stream()
    .collect(Collectors.joining(", "));
// John, Alice, Bob

// С префиксом и суффиксом
String withPrefixSuffix = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));
// [John, Alice, Bob]
```

---

### 6.4. groupingBy

```java
List<Person> people = Arrays.asList(
    new Person("John", 30),
    new Person("Alice", 25),
    new Person("Bob", 30),
    new Person("Catherine", 25)
);

// Group by age
Map<Integer, List<Person>> byAge = people.stream()
    .collect(Collectors.groupingBy(Person::getAge));
// {25=[Alice, Catherine], 30=[John, Bob]}

// Group by age -> только имена
Map<Integer, List<String>> namesByAge = people.stream()
    .collect(Collectors.groupingBy(
        Person::getAge,
        Collectors.mapping(Person::getName, Collectors.toList())
    ));
// {25=[Alice, Catherine], 30=[John, Bob]}

// Group by первой букве имени
Map<Character, List<Person>> byFirstLetter = people.stream()
    .collect(Collectors.groupingBy(
        p -> p.getName().charAt(0)
    ));
```

---

### 6.5. partitioningBy

Разделяет на две группы по условию:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Чётные и нечётные
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

List<Integer> even = partitioned.get(true);  // [2, 4, 6, 8, 10]
List<Integer> odd = partitioned.get(false);  // [1, 3, 5, 7, 9]

// С вложенным collector
Map<Boolean, Long> countByEven = numbers.stream()
    .collect(Collectors.partitioningBy(
        n -> n % 2 == 0,
        Collectors.counting()
    ));
// {false=5, true=5}
```

---

### 6.6. summarizingInt, summarizingLong, summarizingDouble

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

IntSummaryStatistics stats = numbers.stream()
    .collect(Collectors.summarizingInt(Integer::intValue));

stats.getCount();   // 5
stats.getSum();     // 15
stats.getAverage(); // 3.0
stats.getMin();     // 1
stats.getMax();     // 5
```

---

### 6.7. summingInt, averagingInt

```java
List<Person> people = Arrays.asList(
    new Person("John", 30),
    new Person("Alice", 25),
    new Person("Bob", 35)
);

// Сумма возрастов
Integer totalAge = people.stream()
    .collect(Collectors.summingInt(Person::getAge)); // 90

// Средний возраст
Double avgAge = people.stream()
    .collect(Collectors.averagingInt(Person::getAge)); // 30.0
```

---

### 6.8. reducing

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Сумма через reducing
Integer sum = numbers.stream()
    .collect(Collectors.reducing(0, Integer::sum));

// Максимум через reducing
Optional<Integer> max = numbers.stream()
    .collect(Collectors.reducing(Integer::max));
```

---

## 7. Parallel Stream

Параллельная обработка данных:

```java
List<Integer> numbers = IntStream.rangeClosed(1, 1000)
    .boxed()
    .collect(Collectors.toList());

// Sequential
long start = System.currentTimeMillis();
numbers.stream()
    .map(n -> {
        try { Thread.sleep(1); } catch (InterruptedException e) {}
        return n * 2;
    })
    .collect(Collectors.toList());
long seqTime = System.currentTimeMillis() - start;

// Parallel
start = System.currentTimeMillis();
numbers.parallelStream()
    .map(n -> {
        try { Thread.sleep(1); } catch (InterruptedException e) {}
        return n * 2;
    })
    .collect(Collectors.toList());
long parTime = System.currentTimeMillis() - start;

System.out.println("Sequential: " + seqTime + "ms");
System.out.println("Parallel: " + parTime + "ms");
```

**Когда использовать parallel stream:**
- ✅ Большой объём данных
- ✅ CPU-intensive операции
- ✅ Элементы независимы
- ❌ Не для I/O операций
- ❌ Не для маленьких коллекций

---

## 8. Optional

Класс для представления значения которое может отсутствовать:

```java
// Создание
Optional<String> empty = Optional.empty();
Optional<String> value = Optional.of("Hello");
Optional<String> nullable = Optional.ofNullable(getValue());

// Получение значения
String val = value.get(); // Бросает NoSuchElementException если пустой
String orElse = nullable.orElse("default");
String orElseGet = nullable.orElseGet(() -> "computed");
String orElseThrow = nullable.orElseThrow(() -> new IllegalStateException());

// Проверка
if (nullable.isPresent()) {
    System.out.println(nullable.get());
}

// Методы
nullable.ifPresent(System.out::println);
nullable.ifPresentOrElse(
    System.out::println,
    () -> System.out.println("Empty")
);

// Трансформация
Optional<Integer> length = nullable.map(String::length);
Optional<String> upper = nullable.flatMap(s -> Optional.of(s.toUpperCase()));
Optional<String> filtered = nullable.filter(s -> s.length() > 3);
```

---

## 9. Шпаргалка

### Intermediate vs Terminal

| Intermediate (ленивые) | Terminal (eager) |
|------------------------|------------------|
| filter | forEach |
| map | collect |
| flatMap | reduce |
| sorted | count |
| distinct | anyMatch, allMatch, noneMatch |
| limit, skip | findFirst, findAny |
| peek | min, max |
| takeWhile, dropWhile | toArray |

---

### Common Patterns

```java
// Фильтрация + трансформация
list.stream()
    .filter(x -> condition)
    .map(x -> transform)
    .collect(Collectors.toList());

// Группировка
map.put(key, list.stream()
    .collect(Collectors.groupingBy(classifier)));

// Агрегация
int sum = list.stream()
    .mapToInt(x -> x.value)
    .sum();

// Проверка условий
boolean allValid = list.stream()
    .allMatch(x -> x.isValid());
```
