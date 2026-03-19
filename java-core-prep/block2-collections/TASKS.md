# 📝 Блок 2: Коллекции (Collections) — 10 задач

## Теория для повторения
- List: ArrayList, LinkedList, Vector
- Set: HashSet, LinkedHashSet, TreeSet
- Map: HashMap, LinkedHashMap, TreeMap, Hashtable
- Queue: PriorityQueue, ArrayDeque
- Iterator, ListIterator
- Comparable vs Comparator
- Fail-fast итераторы

---

## 📋 Задания

### Задача 2.1: ArrayList операции

Создайте список и выполните операции:

```java
List<String> list = new ArrayList<>();

// 1. Добавить 5 элементов
// 2. Получить элемент по индексу
// 3. Удалить элемент по индексу
// 4. Удалить элемент по значению
// 5. Проверить содержит ли элемент
// 6. Получить размер
// 7. Очистить список
```

---

### Задача 2.2: LinkedList как стек и очередь

Используйте LinkedList как стек (LIFO) и очередь (FIFO):

```java
LinkedList<String> list = new LinkedList<>();

// Как стек:
// push(element), pop(), peek()

// Как очередь:
// add(element), remove(), element()
// или offer(), poll(), peek()
```

---

### Задача 2.3: HashSet — уникальные элементы

Создайте Set и продемонстрируйте что дубликаты не добавляются:

```java
Set<String> set = new HashSet<>();

// 1. Добавить элементы включая дубликаты
// 2. Проверить размер (дубликаты не учитываются)
// 3. Перебрать элементы через for-each
// 4. Удалить элемент
// 5. Проверить contains
```

---

### Задача 2.4: LinkedHashSet — порядок вставки

Продемонстрируйте что LinkedHashSet сохраняет порядок вставки:

```java
Set<String> set = new LinkedHashSet<>();
set.add("Z");
set.add("A");
set.add("M");

// При переборе: Z, A, M (порядок вставки)
```

---

### Задача 2.5: TreeSet — сортировка

Создайте TreeSet и продемонстрируйте автоматическую сортировку:

```java
Set<Integer> set = new TreeSet<>();
set.add(50);
set.add(10);
set.add(30);

// При переборе: 10, 30, 50 (отсортировано)

// Методы:
// first(), last(), headSet(), tailSet(), subSet()
```

---

### Задача 2.6: HashMap операции

Работа с HashMap:

```java
Map<String, Integer> map = new HashMap<>();

// 1. put(key, value) — добавить
// 2. get(key) — получить
// 3. getOrDefault(key, default) — получить или default
// 4. containsKey(key), containsValue(value)
// 5. remove(key)
// 6. size()
// 7. keySet(), values(), entrySet()
```

---

### Задача 2.7: LinkedHashMap — порядок вставки

Продемонстрируйте что LinkedHashMap сохраняет порядок вставки:

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("Z", 1);
map.put("A", 2);
map.put("M", 3);

// При переборе порядок: Z, A, M
```

---

### Задача 2.8: TreeMap — сортировка по ключам

Создайте TreeMap с сортировкой по ключам:

```java
Map<String, Integer> map = new TreeMap<>();
map.put("Z", 1);
map.put("A", 2);
map.put("M", 3);

// При переборе: A, M, Z (отсортировано по ключам)

// Методы:
// firstKey(), lastKey(), headMap(), tailMap(), subMap()
```

---

### Задача 2.9: Comparator сортировка

Отсортируйте список с помощью Comparator:

```java
List<Person> people = Arrays.asList(
    new Person("John", 30),
    new Person("Alice", 25),
    new Person("Bob", 35)
);

// 1. Сортировка по имени
// 2. Сортировка по возрасту
// 3. Сортировка по возрасту в обратном порядке
// 4. Сортировка по имени затем по возрасту

class Person {
    String name;
    int age;
}
```

---

### Задача 2.10: Итерация по коллекциям

Продемонстрируйте разные способы итерации:

```java
List<String> list = Arrays.asList("A", "B", "C");

// 1. For-each loop
for (String s : list) { }

// 2. Iterator
Iterator<String> it = list.iterator();
while (it.hasNext()) { }

// 3. ListIterator (двусторонний)
ListIterator<String> lit = list.listIterator();

// 4. forEach с lambda
list.forEach(System.out::println);

// 5. Stream + forEach
list.stream().forEach(System.out::println);
```

---

## ✅ Чек-лист для самопроверки

- [ ] Знаете различия между List, Set, Map
- [ ] Умеете работать с ArrayList и LinkedList
- [ ] Понимаете разницу между HashSet, LinkedHashSet, TreeSet
- [ ] Понимаете разницу между HashMap, LinkedHashMap, TreeMap
- [ ] Умеете использовать Comparator для сортировки
- [ ] Знаете разные способы итерации по коллекциям
- [ ] Понимаете что такое fail-fast итератор
- [ ] Умеете работать с Map (keySet, values, entrySet)

---

## 📌 Подсказки

<details>
<summary>Подсказка: Когда какую коллекцию использовать</summary>

- **ArrayList** — частый доступ по индексу, редкие вставки/удаления
- **LinkedList** — частые вставки/удаления в середине
- **HashSet** — уникальные элементы, порядок не важен
- **LinkedHashSet** — уникальные + порядок вставки
- **TreeSet** — уникальные + сортировка
- **HashMap** — быстрый доступ по ключу
- **TreeMap** — доступ по ключу + сортировка ключей
</details>

<details>
<summary>Подсказка: Comparator</summary>

```java
// Сортировка по возрасту
list.sort(Comparator.comparingInt(Person::getAge));

// Обратная сортировка
list.sort(Comparator.comparingInt(Person::getAge).reversed());

// По нескольким полям
list.sort(Comparator.comparing(Person::getName)
    .thenComparingInt(Person::getAge));
```
</details>

<details>
<summary>Подсказка: Итерация по Map</summary>

```java
// По ключам
for (String key : map.keySet()) { }

// По значениям
for (Integer value : map.values()) { }

// По entry (эффективно)
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
}

// Lambda
map.forEach((k, v) -> System.out.println(k + "=" + v));
```
</details>
