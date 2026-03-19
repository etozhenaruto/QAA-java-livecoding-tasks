# 🎯 Вопросы для собеседования: Коллекции

## Базовые вопросы

### 1. В чём разница между ArrayList и LinkedList?

**Ответ:**

| ArrayList | LinkedList |
|-----------|------------|
| Динамический массив | Двусвязный список |
| O(1) доступ по индексу | O(n) доступ |
| O(n) вставка/удаление | O(1) вставка/удаление (если известен узел) |
| Лучше для чтения | Лучше для частых модификаций |

---

### 2. Как работает HashMap?

**Ответ:**
- Использует хэш-таблицу
- Ключ → `hashCode()` → индекс в массиве (bucket)
- При коллизии — цепочка (Java 8+ — дерево при большом количестве)
- O(1) для get/put в среднем

**Важно:** Ключ должен переопределять `hashCode()` и `equals()`!

---

### 3. В чём разница между HashSet, LinkedHashSet и TreeSet?

**Ответ:**

| Коллекция | Порядок | Null | Сложность |
|-----------|---------|------|-----------|
| HashSet | Нет порядка | Один | O(1) |
| LinkedHashSet | Порядок вставки | Один | O(1) |
| TreeSet | Сортировка | Нет | O(log n) |

---

### 4. В чём разница между HashMap, LinkedHashMap и TreeMap?

**Ответ:**

| Map | Порядок ключей | Null ключ | Сложность |
|-----|----------------|-----------|-----------|
| HashMap | Нет порядка | Да | O(1) |
| LinkedHashMap | Порядок вставки | Да | O(1) |
| TreeMap | Сортировка ключей | Нет | O(log n) |

---

### 5. Что такое fail-fast итератор?

**Ответ:**
Бросает `ConcurrentModificationException` при изменении коллекции во время итерации:

```java
for (String s : list) {
    list.remove(s); // ConcurrentModificationException!
}
```

**Решение:** Использовать `Iterator.remove()` или `CopyOnWriteArrayList`

---

### 6. Comparable vs Comparator — в чём разница?

**Ответ:**

**Comparable** (внутри класса):
```java
class Person implements Comparable<Person> {
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
}
```

**Comparator** (внешний):
```java
Comparator<Person> byAge = Comparator.comparingInt(Person::getAge);
```

---

### 7. Какие коллекции потокобезопасны?

**Ответ:**

**Синхронизированные обёртки:**
- `Collections.synchronizedList()`
- `Collections.synchronizedMap()`

**Потокобезопасные:**
- `CopyOnWriteArrayList`
- `ConcurrentHashMap`
- `ConcurrentLinkedQueue`

---

### 8. В чём разница между List, Set и Map?

**Ответ:**

| Интерфейс | Дубликаты | Порядок | Null |
|-----------|-----------|---------|------|
| List | Разрешены | Сохраняется | Разрешены |
| Set | Не разрешены | Зависит от реализации | Зависит |
| Map | Ключи уникальны | Зависит | Ключ: 1, Значения: да |

---

### 9. Как работает HashSet внутри?

**Ответ:**
HashSet использует HashMap внутри:
- Элемент становится ключом
- Значение — `static final Object PRESENT`

```java
// Внутри HashSet
private transient HashMap<E,Object> map;
map.put(element, PRESENT);
```

---

### 10. Что будет если вставить null в TreeSet/TreeMap?

**Ответ:**
Бросит `NullPointerException` потому что null нельзя сравнить с другими элементами.

---

## Продвинутые вопросы

### 11. Как HashMap обрабатывает коллизии?

**Ответ:**
- До Java 8: цепочки (linked list)
- Java 8+: при большом количестве (> 8) — дерево (TreeNode)
- При удалении элементов дерево превращается обратно в цепочку

---

### 12. Что такое load factor в HashMap?

**Ответ:**
Коэффициент загрузки (по умолчанию 0.75):
- Когда заполнено > 75% — resizing (увеличение в 2 раза)
- Меньше load factor → меньше коллизий, но больше памяти
- Больше load factor → больше коллизий, но меньше памяти

---

### 13. Почему важно переопределять hashCode и equals вместе?

**Ответ:**
Иначе нарушится контракт HashMap:

```java
// ПЛОХО
class Person {
    String name;
    // equals переопределён, hashCode — нет
}

Map<Person, Integer> map = new HashMap<>();
Person p = new Person("John");
map.put(p, 1);
map.get(new Person("John")); // null! (разные hashCode)
```

---

### 14. В чём разница между Iterator и ListIterator?

**Ответ:**

| Iterator | ListIterator |
|----------|--------------|
| Только вперёд | Вперёд и назад |
| Только remove | add, set, remove |
| Для всех Collection | Только для List |

---

### 15. Когда использовать ConcurrentHashMap вместо Hashtable?

**Ответ:**
ConcurrentHashMap лучше:
- Не блокирует всю карту (сегментированная блокировка)
- Выше производительность
- Hashtable устарел

---

### 16. Что такое CopyOnWriteArrayList и когда использовать?

**Ответ:**
- При модификации создаётся новая копия массива
- Итераторы не видят изменения
- **Использовать:** когда много чтений, мало записи
- **Не использовать:** когда много записи (дорогие копии)

---

### 17. Как отсортировать ArrayList в обратном порядке?

**Ответ:**
```java
// Для объектов
Collections.reverse(list);
list.sort(Collections.reverseOrder());

// Для примитивов
list.stream()
    .sorted(Collections.reverseOrder())
    .collect(Collectors.toList());
```

---

### 18. Что такое PriorityQueue?

**Ответ:**
Очередь с приоритетами (куча):
- Элементы извлекаются по приоритету
- O(log n) для add/poll
- null не разрешён

```java
Queue<Integer> pq = new PriorityQueue<>();
pq.offer(50);
pq.offer(10);
pq.poll(); // 10 (меньший)
```

---

### 19. В чём разница между ArrayDeque и LinkedList?

**Ответ:**

| ArrayDeque | LinkedList |
|------------|------------|
| Быстрее | Медленнее |
| Меньше памяти | Больше памяти (узлы) |
| Нет null | Разрешает null |
| Кэш-дружелюбный | Нет |

---

### 20. Как выбрать коллекцию?

**Ответ:**

| Задача | Коллекция |
|--------|-----------|
| Быстрый доступ по индексу | ArrayList |
| Частые вставки/удаления | LinkedList |
| Уникальные элементы | HashSet |
| Уникальные + порядок | LinkedHashSet |
| Уникальные + сортировка | TreeSet |
| Быстрый доступ по ключу | HashMap |
| Потокобезопасная карта | ConcurrentHashMap |
