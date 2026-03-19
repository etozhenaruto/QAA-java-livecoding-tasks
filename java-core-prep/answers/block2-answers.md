# ✅ Ответы: Блок 2 — Коллекции

## Задача 2.1: ArrayList операции

```java
List<String> list = new ArrayList<>();

// 1. Добавить 5 элементов
list.add("Apple");
list.add("Banana");
list.add("Orange");
list.add("Mango");
list.add("Grape");

// 2. Получить элемент по индексу
String item = list.get(2); // "Orange"

// 3. Удалить элемент по индексу
list.remove(1); // Удалить "Banana"

// 4. Удалить элемент по значению
list.remove("Mango");

// 5. Проверить содержит ли элемент
boolean contains = list.contains("Apple"); // true

// 6. Получить размер
int size = list.size();

// 7. Очистить список
list.clear();
```

---

## Задача 2.2: LinkedList как стек и очередь

```java
LinkedList<String> list = new LinkedList<>();

// Как стек (LIFO):
list.push("A");  // push
list.push("B");
list.push("C");
String top = list.pop();    // "C"
String peek = list.peek();  // "B"

// Как очередь (FIFO):
list.clear();
list.offer("A");  // add
list.offer("B");
list.offer("C");
String first = list.poll();    // "A"
String head = list.peek();     // "B"
```

---

## Задача 2.3: HashSet — уникальные элементы

```java
Set<String> set = new HashSet<>();

// 1. Добавить элементы включая дубликаты
set.add("Apple");
set.add("Banana");
set.add("Apple"); // Дубликат, не добавится
set.add("Orange");

// 2. Проверить размер (дубликаты не учитываются)
System.out.println(set.size()); // 3

// 3. Перебрать элементы через for-each
for (String s : set) {
    System.out.println(s);
}

// 4. Удалить элемент
set.remove("Banana");

// 5. Проверить contains
boolean hasApple = set.contains("Apple"); // true
```

---

## Задача 2.4: LinkedHashSet — порядок вставки

```java
Set<String> set = new LinkedHashSet<>();
set.add("Z");
set.add("A");
set.add("M");

// При переборе: Z, A, M (порядок вставки)
for (String s : set) {
    System.out.println(s); // Z, A, M
}
```

---

## Задача 2.5: TreeSet — сортировка

```java
Set<Integer> set = new TreeSet<>();
set.add(50);
set.add(10);
set.add(30);
set.add(20);

// При переборе: 10, 20, 30, 50 (отсортировано)
for (Integer n : set) {
    System.out.println(n);
}

// Методы навигации:
Integer first = set.first();      // 10
Integer last = set.last();        // 50
Set<Integer> head = set.headSet(30); // [10, 20]
Set<Integer> tail = set.tailSet(30); // [30, 50]
Set<Integer> sub = set.subSet(20, 50); // [20, 30]
```

---

## Задача 2.6: HashMap операции

```java
Map<String, Integer> map = new HashMap<>();

// 1. put(key, value) — добавить
map.put("Apple", 10);
map.put("Banana", 20);
map.put("Orange", 15);

// 2. get(key) — получить
Integer price = map.get("Apple"); // 10

// 3. getOrDefault(key, default) — получить или default
Integer unknown = map.getOrDefault("Grape", 0); // 0

// 4. containsKey(key), containsValue(value)
boolean hasApple = map.containsKey("Apple");   // true
boolean hasPrice10 = map.containsValue(10);    // true

// 5. remove(key)
map.remove("Banana");

// 6. size()
int size = map.size(); // 2

// 7. keySet(), values(), entrySet()
for (String key : map.keySet()) { }
for (Integer value : map.values()) { }
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

---

## Задача 2.7: LinkedHashMap — порядок вставки

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("Z", 1);
map.put("A", 2);
map.put("M", 3);

// При переборе порядок: Z, A, M
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

---

## Задача 2.8: TreeMap — сортировка по ключам

```java
Map<String, Integer> map = new TreeMap<>();
map.put("Z", 1);
map.put("A", 2);
map.put("M", 3);

// При переборе: A, M, Z (отсортировано по ключам)
for (String key : map.keySet()) {
    System.out.println(key + " = " + map.get(key));
}

// Методы навигации:
String first = map.firstKey();      // "A"
String last = map.lastKey();        // "Z"
Map<String, Integer> head = map.headMap("M"); // {A=2}
Map<String, Integer> tail = map.tailMap("M"); // {M=3, Z=1}
```

---

## Задача 2.9: Comparator сортировка

```java
class Person {
    String name;
    int age;
    
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    String getName() { return name; }
    int getAge() { return age; }
}

List<Person> people = Arrays.asList(
    new Person("John", 30),
    new Person("Alice", 25),
    new Person("Bob", 35)
);

// 1. Сортировка по имени
people.sort(Comparator.comparing(Person::getName));

// 2. Сортировка по возрасту
people.sort(Comparator.comparingInt(Person::getAge));

// 3. Сортировка по возрасту в обратном порядке
people.sort(Comparator.comparingInt(Person::getAge).reversed());

// 4. Сортировка по имени затем по возрасту
people.sort(Comparator.comparing(Person::getName)
    .thenComparingInt(Person::getAge));
```

---

## Задача 2.10: Итерация по коллекциям

```java
List<String> list = Arrays.asList("A", "B", "C");

// 1. For-each loop
for (String s : list) {
    System.out.println(s);
}

// 2. Iterator
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    System.out.println(s);
}

// 3. ListIterator (двусторонний)
ListIterator<String> lit = list.listIterator();
while (lit.hasNext()) {
    System.out.println(lit.next());
}
while (lit.hasPrevious()) {
    System.out.println(lit.previous());
}

// 4. forEach с lambda
list.forEach(System.out::println);

// 5. Stream + forEach
list.stream().forEach(System.out::println);
```

---

## Вопросы для самопроверки — Ответы

1. **В чём разница между ArrayList и LinkedList?**
   - ArrayList — O(1) доступ по индексу, O(n) вставка/удаление
   - LinkedList — O(n) доступ, O(1) вставка/удаление (если известен узел)

2. **Как работает HashMap?**
   - Использует хэш-таблицу, ключ → хэш → индекс в массиве
   - O(1) для get/put в среднем случае

3. **Что такое fail-fast итератор?**
   - Бросает ConcurrentModificationException при изменении коллекции во время итерации

4. **В чём разница между HashSet и TreeSet?**
   - HashSet — не упорядочен, O(1) операции
   - TreeSet — сортированный, O(log n) операции

5. **Когда использовать LinkedHashMap?**
   - Когда нужен порядок вставки элементов
