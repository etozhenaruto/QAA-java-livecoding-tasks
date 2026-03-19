# 📚 Теория: Коллекции (Collections)

## 1. Иерархия коллекций

```
java.util.Collection (интерфейс)
│
├── java.util.List (список, дубликаты разрешены, порядок сохраняется)
│   ├── ArrayList (динамический массив)
│   ├── LinkedList (двусвязный список)
│   └── Vector (синхронизированный ArrayList, устарел)
│       └── Stack (стек, устарел)
│
├── java.util.Set (множество, дубликаты НЕ разрешены)
│   ├── HashSet (хэш-таблица)
│   ├── LinkedHashSet (HashSet + порядок вставки)
│   └── TreeSet (отсортированное множество)
│
└── java.util.Queue (очередь, FIFO)
    ├── PriorityQueue (приоритетная очередь)
    └── ArrayDeque (дек, очередь/стек)

java.util.Map (интерфейс, НЕ наследует Collection)
├── HashMap (хэш-таблица)
├── LinkedHashMap (HashMap + порядок вставки)
├── TreeMap (отсортированная карта)
└── Hashtable (синхронизированная HashMap, устарел)
```

---

## 2. List (Списки)

### 2.1. ArrayList

**Что это:** Динамический массив который автоматически увеличивается.

**Характеристики:**
- ✅ Быстрый доступ по индексу: O(1)
- ❌ Медленная вставка/удаление в середине: O(n)
- ✅ Разрешает дубликаты
- ✅ Сохраняет порядок вставки
- ✅ Разрешает null элементы
- ❌ Не синхронизирован (не потокобезопасен)

**Создание:**
```java
List<String> list = new ArrayList<>();
List<String> list2 = new ArrayList<>(100); // Начальная ёмкость
List<String> immutable = List.of("A", "B", "C"); // Неизменяемый (Java 9+)
```

**Основные методы:**
```java
list.add("Apple");           // Добавить в конец
list.add(0, "Banana");       // Вставить по индексу
list.get(0);                 // Получить по индексу
list.set(0, "Orange");       // Заменить по индексу
list.remove(0);              // Удалить по индексу
list.remove("Apple");        // Удалить по значению
list.size();                 // Размер
list.contains("Apple");      // Проверка наличия
list.isEmpty();              // Пустой ли
list.indexOf("Apple");       // Индекс элемента
list.clear();                // Очистить
```

**Когда использовать:**
- Частый доступ по индексу
- Редкие вставки/удаления в середине
- Итерация в цикле

---

### 2.2. LinkedList

**Что это:** Двусвязный список (каждый элемент содержит ссылку на предыдущий и следующий).

**Характеристики:**
- ❌ Медленный доступ по индексу: O(n)
- ✅ Быстрая вставка/удаление в начале/середине: O(1) если известен узел
- ✅ Разрешает дубликаты
- ✅ Сохраняет порядок вставки
- ✅ Разрешает null элементы
- ❌ Не синхронизирован

**Основные методы:**
```java
LinkedList<String> list = new LinkedList<>();

// Как список:
list.add("A");
list.get(0);
list.remove(0);

// Как стек (LIFO):
list.push("A");      // Добавить в начало
String top = list.pop();  // Взять и удалить первый
String peek = list.peek(); // Посмотреть первый

// Как очередь (FIFO):
list.offer("A");     // Добавить в конец
String first = list.poll(); // Взять и удалить первый
String head = list.peek();  // Посмотреть первый
```

**Когда использовать:**
- Частые вставки/удаления в начале/середине
- Реализация стека или очереди
- Не нужен доступ по индексу

---

### 2.3. ArrayList vs LinkedList

| Операция | ArrayList | LinkedList |
|----------|-----------|------------|
| get(i) | O(1) | O(n) |
| add(element) | O(1)* | O(1) |
| add(i, element) | O(n) | O(1)* |
| remove(i) | O(n) | O(1)* |
| contains | O(n) | O(n) |

*амортизированное, *если известен узел

---

## 3. Set (Множества)

### 3.1. HashSet

**Что это:** Коллекция основанная на хэш-таблице (внутри HashMap).

**Характеристики:**
- ❌ Не сохраняет порядок
- ✅ Один null элемент разрешён
- ❌ Не разрешает дубликаты
- ✅ O(1) для add/remove/contains

**Создание:**
```java
Set<String> set = new HashSet<>();
Set<String> set2 = new HashSet<>(100); // Начальная ёмкость
Set<String> set3 = new HashSet<>(100, 0.75f); // Ёмкость и load factor
```

**Когда использовать:**
- Нужны уникальные элементы
- Порядок не важен
- Важна производительность

---

### 3.2. LinkedHashSet

**Что это:** HashSet который сохраняет порядок вставки.

**Характеристики:**
- ✅ Сохраняет порядок вставки
- ✅ Один null элемент разрешён
- ❌ Не разрешает дубликаты
- ✅ O(1) для add/remove/contains (чуть медленнее HashSet)

**Создание:**
```java
Set<String> set = new LinkedHashSet<>();
set.add("Z");
set.add("A");
set.add("M");

// При переборе: Z, A, M (порядок вставки)
```

**Когда использовать:**
- Нужны уникальные элементы
- Важен порядок вставки
- Кэши с LRU eviction

---

### 3.3. TreeSet

**Что это:** Отсортированное множество (красно-чёрное дерево).

**Характеристики:**
- ✅ Сортирует элементы (натуральный порядок или Comparator)
- ❌ null не разрешён
- ❌ Не разрешает дубликаты
- ✅ O(log n) для add/remove/contains

**Создание:**
```java
// Натуральная сортировка
Set<Integer> set = new TreeSet<>();
set.add(50);
set.add(10);
set.add(30);
// Порядок: 10, 30, 50

// Свой Comparator
Set<String> set2 = new TreeSet<>(Comparator.reverseOrder());
set2.add("Z");
set2.add("A");
// Порядок: Z, A

// Навигационные методы:
TreeSet<Integer> treeSet = new TreeSet<>(Arrays.asList(10, 20, 30, 40, 50));
treeSet.first();      // 10
treeSet.last();       // 50
treeSet.headSet(30);  // [10, 20]
treeSet.tailSet(30);  // [30, 40, 50]
treeSet.subSet(20, 40); // [20, 30]
treeSet.lower(30);    // 20 (меньше чем 30)
treeSet.higher(30);   // 40 (больше чем 30)
```

**Когда использовать:**
- Нужны уникальные отсортированные элементы
- Нужен диапазон элементов
- Поиск min/max

---

## 4. Map (Ассоциативные массивы)

### 4.1. HashMap

**Что это:** Хэш-таблица (ключ-значение).

**Характеристики:**
- ❌ Не сохраняет порядок
- ✅ Один null ключ разрешён
- ✅ Multiple null значений разрешены
- ❌ Ключи уникальны
- ✅ O(1) для get/put

**Создание:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("Apple", 10);
map.put("Banana", 20);
```

**Основные методы:**
```java
map.put("key", value);           // Добавить
map.get("key");                  // Получить
map.getOrDefault("key", 0);      // Получить или default
map.containsKey("key");          // Проверка ключа
map.containsValue(value);        // Проверка значения
map.remove("key");               // Удалить по ключу
map.size();                      // Размер
map.keySet();                    // Множество ключей
map.values();                    // Коллекция значений
map.entrySet();                  // Множество entry (ключ-значение)
map.merge("key", 1, Integer::sum); // Обновить или добавить
map.compute("key", (k, v) -> v + 1); // Вычислить новое значение
```

**Как работает:**
1. Ключ → `hashCode()` → хэш
2. Хэш → индекс в массиве (bucket)
3. Если коллизия — цепочка или дерево (Java 8+)

**Важно:** Ключ должен переопределять `hashCode()` и `equals()`!

---

### 4.2. LinkedHashMap

**Что это:** HashMap который сохраняет порядок вставки.

**Характеристики:**
- ✅ Сохраняет порядок вставки (или порядок доступа)
- ✅ Один null ключ разрешён
- ✅ Multiple null значений разрешены
- ✅ O(1) для get/put

**Создание:**
```java
// Порядок вставки
Map<String, Integer> map = new LinkedHashMap<>();
map.put("Z", 1);
map.put("A", 2);
map.put("M", 3);
// При переборе: Z=1, A=2, M=3

// Порядок доступа (LRU cache)
Map<String, Integer> lruMap = new LinkedHashMap<>(16, 0.75f, true);
```

**Когда использовать:**
- Кэши с LRU eviction
- Нужен порядок вставки

---

### 4.3. TreeMap

**Что это:** Отсортированная карта (красно-чёрное дерево).

**Характеристики:**
- ✅ Сортирует по ключам (натуральный порядок или Comparator)
- ❌ null ключ не разрешён
- ✅ Multiple null значений разрешены
- ✅ O(log n) для get/put

**Создание:**
```java
// Натуральная сортировка
Map<String, Integer> map = new TreeMap<>();
map.put("Z", 1);
map.put("A", 2);
map.put("M", 3);
// Порядок ключей: A, M, Z

// Обратная сортировка
Map<String, Integer> map2 = new TreeMap<>(Comparator.reverseOrder());

// Навигационные методы:
TreeMap<String, Integer> treeMap = new TreeMap<>();
treeMap.put("A", 1);
treeMap.put("B", 2);
treeMap.put("C", 3);

treeMap.firstKey();      // "A"
treeMap.lastKey();       // "C"
treeMap.headMap("B");    // {A=1}
treeMap.tailMap("B");    // {B=2, C=3}
treeMap.subMap("A", "C"); // {A=1, B=2}
treeMap.lowerKey("B");   // "A"
treeMap.higherKey("B");  // "C"
```

**Когда использовать:**
- Нужны отсортированные ключи
- Поиск по диапазону ключей

---

## 5. Queue (Очереди)

### 5.1. PriorityQueue

**Что это:** Очередь с приоритетами (куча).

**Характеристики:**
- ✅ Элементы извлекаются по приоритету
- ❌ null не разрешён
- ✅ Дубликаты разрешены
- ✅ O(log n) для add/poll

**Создание:**
```java
// Натуральный порядок
Queue<Integer> queue = new PriorityQueue<>();
queue.offer(50);
queue.offer(10);
queue.offer(30);

System.out.println(queue.poll()); // 10 (меньший)

// Свой Comparator (максимальная куча)
Queue<Integer> maxQueue = new PriorityQueue<>(Comparator.reverseOrder());
```

---

### 5.2. ArrayDeque

**Что это:** Двусторонняя очередь (дек).

**Характеристики:**
- ✅ Быстрая вставка/удаление с обоих концов
- ❌ null не разрешён
- ✅ Быстрее Stack и LinkedList

**Создание:**
```java
Deque<String> deque = new ArrayDeque<>();

// Как стек:
deque.push("A");
deque.pop();
deque.peek();

// Как очередь:
deque.offer("A");
deque.poll();
deque.peek();
```

---

## 6. Итерация по коллекциям

### 6.1. For-each loop
```java
for (String item : list) {
    System.out.println(item);
}
```

### 6.2. Iterator
```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String item = it.next();
    System.out.println(item);
    it.remove(); // Безопасное удаление
}
```

### 6.3. ListIterator (только List)
```java
ListIterator<String> lit = list.listIterator();
while (lit.hasNext()) {
    System.out.println(lit.next());
}
while (lit.hasPrevious()) {
    System.out.println(lit.previous());
}
```

### 6.4. forEach с lambda
```java
list.forEach(System.out::println);
list.forEach(item -> System.out.println(item));
```

### 6.5. Stream
```java
list.stream().forEach(System.out::println);
```

### 6.6. Итерация по Map
```java
// По ключам
for (String key : map.keySet()) {
    System.out.println(key);
}

// По значениям
for (Integer value : map.values()) {
    System.out.println(value);
}

// По entry (эффективно)
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}

// Lambda
map.forEach((key, value) -> System.out.println(key + " = " + value));
```

---

## 7. Comparable vs Comparator

### 7.1. Comparable (внутри класса)

```java
class Person implements Comparable<Person> {
    String name;
    int age;
    
    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
}

// Использование:
Collections.sort(people);
people.sort(null);
```

### 7.2. Comparator (внешний)

```java
// Сортировка по возрасту
people.sort(Comparator.comparingInt(Person::getAge));

// Обратная сортировка
people.sort(Comparator.comparingInt(Person::getAge).reversed());

// По нескольким полям
people.sort(Comparator.comparing(Person::getName)
    .thenComparingInt(Person::getAge));

// Анонимный класс
Comparator<Person> comparator = new Comparator<Person>() {
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
};
```

---

## 8. Fail-fast итераторы

**Fail-fast** — бросает `ConcurrentModificationException` если коллекция изменена во время итерации:

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");

for (String s : list) {
    list.remove(s); // ConcurrentModificationException!
}
```

**Решения:**
```java
// 1. Iterator.remove()
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    it.remove();
}

// 2. CopyOnWriteArrayList
List<String> list = new CopyOnWriteArrayList<>();

// 3. Stream filter
list = list.stream()
    .filter(s -> !condition)
    .collect(Collectors.toList());
```

---

## 9. Синхронизированные коллекции

```java
// Синхронизированные обёртки
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Потокобезопасные коллекции
List<String> concurrentList = new CopyOnWriteArrayList<>();
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
Queue<String> concurrentQueue = new ConcurrentLinkedQueue<>();
```

---

## 10. Шпаргалка: Когда какую коллекцию использовать

| Задача | Коллекция |
|--------|-----------|
| Быстрый доступ по индексу | ArrayList |
| Частые вставки/удаления | LinkedList |
| Уникальные элементы, порядок не важен | HashSet |
| Уникальные + порядок вставки | LinkedHashSet |
| Уникальные + сортировка | TreeSet |
| Быстрый доступ по ключу | HashMap |
| Доступ по ключу + порядок вставки | LinkedHashMap |
| Доступ по ключу + сортировка ключей | TreeMap |
| Стек | ArrayDeque / Stack |
| Очередь | ArrayDeque / LinkedList |
| Приоритетная очередь | PriorityQueue |
| Потокобезопасная карта | ConcurrentHashMap |
