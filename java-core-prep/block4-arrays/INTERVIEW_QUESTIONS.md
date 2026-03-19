# 🎯 Вопросы для собеседования: Массивы

## Базовые вопросы

### 1. Что такое массив и какие у него характеристики?

**Ответ:**
Массив — структура данных для хранения элементов одного типа в непрерывной памяти.

**Характеристики:**
- Фиксированный размер
- Быстрый доступ по индексу: O(1)
- Хранит примитивы и объекты
- Индексы начинаются с 0

---

### 2. Как создать массив в Java?

**Ответ:**
```java
// Способ 1: Создание массива размером 10
int[] arr1 = new int[10];

// Способ 2: Инициализация значениями
int[] arr2 = {1, 2, 3, 4, 5};

// Способ 3: Создание и заполнение
int[] arr3 = new int[]{10, 20, 30};
```

---

### 3. Как получить длину массива?

**Ответ:**
```java
int len = arr.length; // Свойство, не метод!
```

---

### 4. Какие значения по умолчанию у элементов массива?

**Ответ:**

| Тип | Значение |
|-----|----------|
| int, long, short, byte | 0 |
| float, double | 0.0 |
| char | '\u0000' |
| boolean | false |
| Объекты | null |

---

### 5. Что такое многомерный массив?

**Ответ:**
Массив массивов:

```java
// Двумерный массив (матрица)
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};

// Доступ к элементу
int val = matrix[0][1]; // 2

// Зубчатый массив (разная длина строк)
int[][] jagged = new int[3][];
jagged[0] = new int[2];
jagged[1] = new int[4];
```

---

### 6. Какие методы есть у класса Arrays?

**Ответ:**
```java
Arrays.sort(arr)              // Сортировка
Arrays.binarySearch(arr, key) // Бинарный поиск
Arrays.copyOf(arr, len)       // Копия
Arrays.copyOfRange(arr, f, t) // Копия диапазона
Arrays.fill(arr, val)         // Заполнить
Arrays.equals(arr1, arr2)     // Сравнение
Arrays.toString(arr)          // В строку
Arrays.stream(arr)            // Stream
```

---

### 7. Как работает Arrays.binarySearch?

**Ответ:**
Бинарный поиск требует отсортированный массив:

```java
int[] sorted = {1, 3, 5, 7, 9};
int index = Arrays.binarySearch(sorted, 7); // 3

// Если не найден — вернёт -(insertion point) - 1
int notFound = Arrays.binarySearch(sorted, 4); // -3
```

**Важно:** Массив должен быть отсортирован!

---

### 8. В чём разница между Arrays.copyOf и Arrays.copyOfRange?

**Ответ:**
- **copyOf** — копирует с начала до указанной длины
- **copyOfRange** — копирует диапазон от start до end

```java
int[] arr = {1, 2, 3, 4, 5};

Arrays.copyOf(arr, 3);        // [1, 2, 3]
Arrays.copyOfRange(arr, 1, 4); // [2, 3, 4]
```

---

### 9. Как отсортировать массив в обратном порядке?

**Ответ:**
```java
// Для объектов
Integer[] arr = {5, 2, 8, 1};
Arrays.sort(arr, Collections.reverseOrder());

// Для примитивов — через boxed
int[] primitive = {5, 2, 8, 1};
int[] reversed = Arrays.stream(primitive)
    .boxed()
    .sorted(Collections.reverseOrder())
    .mapToInt(Integer::intValue)
    .toArray();
```

---

### 10. В чём разница между массивом и ArrayList?

**Ответ:**

| Array | ArrayList |
|-------|-----------|
| Фиксированный размер | Динамический размер |
| Примитивы и объекты | Только объекты |
| `array.length` | `list.size()` |
| Быстрее | Медленнее (автобоксинг) |

---

## Продвинутые вопросы

### 11. Как сравнить два массива?

**Ответ:**
```java
// Для одномерных
boolean equal = Arrays.equals(arr1, arr2);

// Для многомерных
boolean deepEqual = Arrays.deepEquals(matrix1, matrix2);
```

---

### 12. Как клонировать массив?

**Ответ:**
```java
int[] clone1 = original.clone();
int[] clone2 = Arrays.copyOf(original, original.length);

int[] clone3 = new int[original.length];
System.arraycopy(original, 0, clone3, 0, original.length);
```

---

### 13. Как удалить дубликаты из массива?

**Ответ:**
```java
// Через Stream
int[] unique = Arrays.stream(numbers)
    .distinct()
    .toArray();

// Через Set
int[] unique = Arrays.stream(numbers)
    .boxed()
    .collect(Collectors.toSet())
    .stream()
    .mapToInt(Integer::intValue)
    .toArray();
```

---

### 14. Как найти min и max в массиве?

**Ответ:**
```java
// Через Stream
int min = Arrays.stream(arr).min().getAsInt();
int max = Arrays.stream(arr).max().getAsInt();

// Через SummaryStatistics
IntSummaryStatistics stats = Arrays.stream(arr).summaryStatistics();
int min = stats.getMin();
int max = stats.getMax();
double avg = stats.getAverage();
```

---

### 15. Как объединить два массива?

**Ответ:**
```java
// Через System.arraycopy
int[] merged = new int[arr1.length + arr2.length];
System.arraycopy(arr1, 0, merged, 0, arr1.length);
System.arraycopy(arr2, 0, merged, arr1.length, arr2.length);

// Через Stream
int[] merged = IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
    .toArray();
```

---

### 16. Как развернуть массив?

**Ответ:**
```java
// Два указателя
int left = 0, right = arr.length - 1;
while (left < right) {
    int temp = arr[left];
    arr[left] = arr[right];
    arr[right] = temp;
    left++;
    right--;
}

// Через Stream
int[] reversed = IntStream.rangeClosed(1, arr.length)
    .map(i -> arr[arr.length - i])
    .toArray();
```

---

### 17. Что такое System.arraycopy?

**Ответ:**
Нативный метод для быстрого копирования:

```java
System.arraycopy(src, srcPos, dest, destPos, length);

int[] src = {1, 2, 3, 4, 5};
int[] dest = new int[5];
System.arraycopy(src, 0, dest, 0, src.length);
```

---

### 18. Как заполнить массив значениями?

**Ответ:**
```java
// Arrays.fill
Arrays.fill(arr, 42);

// Заполнить диапазон
Arrays.fill(arr, 1, 4, 99);

// Через Stream
int[] arr = IntStream.generate(() -> 42)
    .limit(10)
    .toArray();
```

---

### 19. Как преобразовать массив в Stream?

**Ответ:**
```java
// IntStream
IntStream stream = Arrays.stream(arr);

// Stream<Integer>
Stream<Integer> boxed = Arrays.stream(arr).boxed();

// Из диапазона
IntStream range = Arrays.stream(arr, 1, 4);
```

---

### 20. Какая сложность операций с массивом?

**Ответ:**

| Операция | Сложность |
|----------|-----------|
| Доступ по индексу | O(1) |
| Поиск (линейный) | O(n) |
| Поиск (бинарный) | O(log n) |
| Сортировка | O(n log n) |
| Вставка/удаление | O(n) |
