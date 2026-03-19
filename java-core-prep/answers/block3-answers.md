# ✅ Ответы: Блок 3 — Стримы

## Задача 3.1: Filter — фильтрация

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> even = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());

System.out.println(even); // [2, 4, 6, 8, 10]
```

---

## Задача 3.2: Map — трансформация

```java
List<String> names = Arrays.asList("john", "alice", "bob");

List<String> upperNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

System.out.println(upperNames); // [JOHN, ALICE, BOB]
```

---

## Задача 3.3: Filter + Map + Sorted

```java
List<String> names = Arrays.asList("John", "Alice", "Bob", "Alexander", "Catherine");

List<Integer> sortedLengths = names.stream()
    .filter(n -> n.length() > 4)
    .mapToInt(String::length)
    .boxed()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());

System.out.println(sortedLengths); // [9, 9, 5] (Catherine, Alexander, John)
```

---

## Задача 3.4: Distinct — уникальные элементы

```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);

List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());

System.out.println(unique); // [1, 2, 3, 4]
```

---

## Задача 3.5: Reduce — агрегация

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Вариант 1: sum через reduce
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);

// Вариант 2: sum через method reference
int sum2 = numbers.stream()
    .reduce(0, Integer::sum);

// Вариант 3: sum через mapToInt
int sum3 = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();

System.out.println(sum); // 15
```

---

## Задача 3.6: Collectors.joining

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// 1. Просто объединить
String result = names.stream()
    .collect(Collectors.joining());
System.out.println(result); // JohnAliceBob

// 2. С разделителем
String result2 = names.stream()
    .collect(Collectors.joining(", "));
System.out.println(result2); // John, Alice, Bob

// 3. С префиксом и суффиксом
String result3 = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));
System.out.println(result3); // [John, Alice, Bob]
```

---

## Задача 3.7: Collectors.toMap

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

// С обработкой дубликатов ключей
Map<String, Integer> map = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge,
        (existing, replacement) -> existing
    ));
```

---

## Задача 3.8: Collectors.groupingBy

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

// Group by age -> только имена
Map<Integer, List<String>> namesByAge = people.stream()
    .collect(Collectors.groupingBy(
        Person::getAge,
        Collectors.mapping(Person::getName, Collectors.toList())
    ));

// byAge: {25=[Alice, Catherine], 30=[John, Bob]}
// namesByAge: {25=[Alice, Catherine], 30=[John, Bob]}
```

---

## Задача 3.9: Collectors.partitioningBy

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Разделить на чётные и нечётные
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

// Доступ:
List<Integer> even = partitioned.get(true);
List<Integer> odd = partitioned.get(false);

System.out.println("Even: " + even);  // [2, 4, 6, 8, 10]
System.out.println("Odd: " + odd);    // [1, 3, 5, 7, 9]
```

---

## Задача 3.10: Parallel Stream

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

System.out.println("Sequential time: " + seqTime + "ms");
System.out.println("Parallel time: " + parTime + "ms");
// Parallel обычно быстрее для CPU-intensive задач
```

---

## Вопросы для самопроверки — Ответы

1. **В чём разница между intermediate и terminal операциями?**
   - Intermediate возвращают Stream и ленивые
   - Terminal возвращают результат и запускают вычисление

2. **Что такое flatMap?**
   - Преобразует каждый элемент в Stream и "сплющивает" результат

3. **Как работает groupingBy?**
   - Группирует элементы по ключу (аналог SQL GROUP BY)

4. **В чём разница между findFirst и findAny?**
   - findFirst — первый элемент (важен порядок)
   - findAny — любой элемент (может быть быстрее в parallel stream)

5. **Можно ли переиспользовать Stream?**
   - Нет, stream одноразовый
