# 📝 Блок 3: Стримы (Streams) — 10 задач

## Теория для повторения
- Intermediate vs Terminal операции
- filter, map, flatMap, sorted, distinct, limit, skip
- forEach, collect, reduce, count, anyMatch, allMatch
- Collectors: toList, toSet, toMap, joining, groupingBy, partitioningBy
- Parallel streams

---

## 📋 Задания

### Задача 3.1: Filter — фильтрация

Отфильтруйте список чисел, оставив только чётные:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> even = numbers.stream()
    // Добавить filter
    .collect(Collectors.toList());
```

---

### Задача 3.2: Map — трансформация

Преобразуйте список строк в верхний регистр:

```java
List<String> names = Arrays.asList("john", "alice", "bob");

List<String> upperNames = names.stream()
    // Добавить map
    .collect(Collectors.toList());
```

---

### Задача 3.3: Filter + Map + Sorted

Найдите длину самых длинных имён (после фильтрации):

```java
List<String> names = Arrays.asList("John", "Alice", "Bob", "Alexander", "Catherine");

// 1. Отфильтровать имена с длиной > 4
// 2. Преобразовать в длину (mapToInt)
// 3. Отсортировать по убыванию
// 4. Собрать в список
```

---

### Задача 3.4: Distinct — уникальные элементы

Удалите дубликаты из списка:

```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);

List<Integer> unique = numbers.stream()
    // Добавить distinct
    .collect(Collectors.toList());
```

---

### Задача 3.5: Reduce — агрегация

Вычислите сумму всех элементов:

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
```

---

### Задача 3.6: Collectors.joining

Объедините строки в одну с разделителем:

```java
List<String> names = Arrays.asList("John", "Alice", "Bob");

// 1. Просто объединить
String result = names.stream()
    .collect(Collectors.joining());

// 2. С разделителем
String result2 = names.stream()
    .collect(Collectors.joining(", "));

// 3. С префиксом и суффиксом
String result3 = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));
```

---

### Задача 3.7: Collectors.toMap

Создайте Map из списка объектов:

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
        (existing, replacement) -> existing  // или replacement
    ));
```

---

### Задача 3.8: Collectors.groupingBy

Сгруппируйте людей по возрасту:

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
```

---

### Задача 3.9: Collectors.partitioningBy

Разделите на две группы по условию:

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Разделить на чётные и нечётные
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

// Доступ:
List<Integer> even = partitioned.get(true);
List<Integer> odd = partitioned.get(false);
```

---

### Задача 3.10: Parallel Stream

Продемонстрируйте parallel stream:

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

// Сравнить время
```

---

## ✅ Чек-лист для самопроверки

- [ ] Понимаете разницу между intermediate и terminal операциями
- [ ] Умеете использовать filter, map, flatMap
- [ ] Умеете использовать sorted, distinct, limit, skip
- [ ] Умеете использовать reduce
- [ ] Знаете Collectors: toList, toSet, toMap
- [ ] Знаете Collectors: joining, groupingBy, partitioningBy
- [ ] Понимаете что такое parallel stream
- [ ] Знаете что stream нельзя переиспользовать

---

## 📌 Подсказки

<details>
<summary>Подсказка: Intermediate vs Terminal</summary>

**Intermediate** (возвращают Stream, ленивые):
- filter, map, flatMap, sorted, distinct, limit, skip, peek

**Terminal** (возвращают результат, eager):
- forEach, collect, reduce, count, anyMatch, allMatch, noneMatch, findFirst, findAny
</details>

<details>
<summary>Подсказка: FlatMap пример</summary>

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
```
</details>

<details>
<summary>Подсказка: Primitive streams</summary>

```java
// IntStream
IntStream.range(1, 10)       // 1 до 9
IntStream.rangeClosed(1, 10) // 1 до 10
IntStream.of(1, 2, 3)

// Методы: sum(), average(), count(), toArray()
```
</details>
