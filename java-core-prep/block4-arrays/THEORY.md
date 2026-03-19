# 📚 Теория: Массивы (Arrays)

## 1. Что такое массив?

**Массив** — это структура данных для хранения элементов одного типа в непрерывной области памяти.

**Характеристики:**
- Фиксированный размер (нельзя изменить после создания)
- Быстрый доступ по индексу: O(1)
- Хранит примитивы и объекты
- Индексы начинаются с 0

---

## 2. Создание массивов

### 2.1. Объявление и инициализация

```java
// Способ 1: Создание массива размером 10
int[] arr1 = new int[10];
// Все элементы инициализированы значениями по умолчанию (0)

// Способ 2: Инициализация значениями
int[] arr2 = {1, 2, 3, 4, 5};

// Способ 3: Создание и заполнение
int[] arr3 = new int[]{10, 20, 30};

// Способ 4: Массив объектов
String[] strings = new String[5];
// Все элементы = null

// Способ 5: Массив с инициализацией
String[] names = {"John", "Alice", "Bob"};
```

### 2.2. Значения по умолчанию

| Тип | Значение по умолчанию |
|-----|----------------------|
| byte, short, int, long | 0 |
| float, double | 0.0 |
| char | '\u0000' |
| boolean | false |
| Объекты | null |

---

## 3. Доступ к элементам

```java
int[] arr = {10, 20, 30, 40, 50};

// Получить элемент по индексу
int first = arr[0];      // 10
int third = arr[2];      // 30

// Получить последний элемент
int last = arr[arr.length - 1]; // 50

// Изменить элемент
arr[2] = 100; // {10, 20, 100, 40, 50}

// Получить длину массива
int len = arr.length; // 5 (свойство, не метод!)

// Перебор элементов
for (int i = 0; i < arr.length; i++) {
    System.out.println("arr[" + i + "] = " + arr[i]);
}

// For-each цикл
for (int value : arr) {
    System.out.println(value);
}
```

---

## 4. Многомерные массивы

### 4.1. Двумерный массив (матрица)

```java
// Создание матрицы 3x3
int[][] matrix = new int[3][3];

// Инициализация значениями
int[][] matrix2 = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Доступ к элементу [row][col]
int val = matrix2[1][2]; // 6 (вторая строка, третий столбец)

// Перебор всех элементов
for (int i = 0; i < matrix2.length; i++) {
    for (int j = 0; j < matrix2[i].length; j++) {
        System.out.print(matrix2[i][j] + " ");
    }
    System.out.println();
}

// For-each
for (int[] row : matrix2) {
    for (int cell : row) {
        System.out.print(cell + " ");
    }
    System.out.println();
}

// Сумма главной диагонали
int sum = 0;
for (int i = 0; i < matrix2.length; i++) {
    sum += matrix2[i][i];
}
// sum = 1 + 5 + 9 = 15
```

### 4.2. Зубчатые массивы (Jagged Arrays)

Массивы где строки имеют разную длину:

```java
int[][] jagged = new int[3][];
jagged[0] = new int[2]; // {0, 0}
jagged[1] = new int[4]; // {0, 0, 0, 0}
jagged[2] = new int[1]; // {0}

// Инициализация
jagged[0][0] = 1;
jagged[0][1] = 2;
jagged[1][0] = 3;
// ...
```

---

## 5. Класс Arrays (вспомогательные методы)

### 5.1. Arrays.sort

Сортировка массива:

```java
int[] numbers = {5, 2, 8, 1, 9, 3};

// Сортировка по возрастанию
Arrays.sort(numbers);
System.out.println(Arrays.toString(numbers));
// [1, 2, 3, 5, 8, 9]

// Сортировка части массива
Arrays.sort(numbers, 0, 3); // сортировать элементы 0, 1, 2

// Сортировка в обратном порядке (только для объектов!)
Integer[] boxed = {5, 2, 8, 1, 9};
Arrays.sort(boxed, Collections.reverseOrder());
// [9, 8, 5, 2, 1]

// Свой Comparator
String[] words = {"apple", "cat", "banana"};
Arrays.sort(words, Comparator.comparingInt(String::length));
// [cat, apple, banana]
```

---

### 5.2. Arrays.binarySearch

Бинарный поиск (требует отсортированный массив):

```java
int[] sorted = {1, 3, 5, 7, 9, 11};

// Найти элемент
int index = Arrays.binarySearch(sorted, 7); // 3

// Найти в диапазоне
int index2 = Arrays.binarySearch(sorted, 1, 4, 5); // 2

// Если элемент не найден
int notFound = Arrays.binarySearch(sorted, 4);
// Вернёт: -(insertion point) - 1 = -3

// Индекс вставки = -(notFound) - 1 = 2
// (между 3 и 5)
```

**Важно:** Массив должен быть отсортирован! Иначе результат не определён.

---

### 5.3. Arrays.copyOf

Копирование массива:

```java
int[] original = {1, 2, 3, 4, 5};

// Копия всей длины
int[] copy = Arrays.copyOf(original, original.length);
// [1, 2, 3, 4, 5]

// Копия с увеличением размера
int[] bigger = Arrays.copyOf(original, 10);
// [1, 2, 3, 4, 5, 0, 0, 0, 0, 0]

// Копия с уменьшением размера
int[] smaller = Arrays.copyOf(original, 3);
// [1, 2, 3]

// Копия диапазона
int[] range = Arrays.copyOfRange(original, 1, 4);
// [2, 3, 4] (элементы с индексами 1, 2, 3)
```

---

### 5.4. Arrays.fill

Заполнение массива значением:

```java
int[] arr = new int[5];

// Заполнить нулями
Arrays.fill(arr, 0);

// Заполнить другим значением
Arrays.fill(arr, 42);
// [42, 42, 42, 42, 42]

// Заполнить диапазон
Arrays.fill(arr, 1, 4, 99);
// [42, 99, 99, 99, 42]
```

---

### 5.5. Arrays.equals

Сравнение массивов:

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
int[] arr3 = {1, 2, 4};

// Сравнение
boolean equal = Arrays.equals(arr1, arr2); // true
boolean notEqual = Arrays.equals(arr1, arr3); // false

// Сравнение диапазонов
boolean rangeEqual = Arrays.equals(arr1, 0, 2, arr2, 0, 2); // true

// Глубокое сравнение для многомерных
int[][] matrix1 = {{1, 2}, {3, 4}};
int[][] matrix2 = {{1, 2}, {3, 4}};
boolean deepEqual = Arrays.deepEquals(matrix1, matrix2); // true
```

---

### 5.6. Arrays.toString

Преобразование в строку:

```java
int[] arr = {1, 2, 3, 4, 5};
System.out.println(Arrays.toString(arr));
// [1, 2, 3, 4, 5]

// Для многомерных
int[][] matrix = {{1, 2}, {3, 4}};
System.out.println(Arrays.toString(matrix));
// [[I@hashcode (ссылка!)
System.out.println(Arrays.deepToString(matrix));
// [[1, 2], [3, 4]]
```

---

### 5.7. Arrays.stream

Создание Stream из массива:

```java
int[] numbers = {1, 2, 3, 4, 5};

// IntStream
IntStream stream = Arrays.stream(numbers);

// Stream<Integer>
Stream<Integer> boxed = Arrays.stream(numbers).boxed();

// Из части массива
IntStream range = Arrays.stream(numbers, 1, 4); // элементы 1, 2, 3

// Stream -> Array
Integer[] arr = Stream.of(1, 2, 3)
    .toArray(Integer[]::new);
```

---

## 6. Поиск минимума и максимума

```java
int[] numbers = {5, 2, 9, 1, 7, 3};

// Способ 1: Через сортировку
int[] sorted = numbers.clone();
Arrays.sort(sorted);
int min = sorted[0];           // 1
int max = sorted[sorted.length - 1]; // 9

// Способ 2: Через цикл
int min2 = numbers[0];
int max2 = numbers[0];
for (int n : numbers) {
    if (n < min2) min2 = n;
    if (n > max2) max2 = n;
}

// Способ 3: Через Stream
int min3 = Arrays.stream(numbers).min().getAsInt(); // 1
int max3 = Arrays.stream(numbers).max().getAsInt(); // 9

// Способ 4: Stream SummaryStatistics
IntSummaryStatistics stats = Arrays.stream(numbers).summaryStatistics();
int min4 = stats.getMin(); // 1
int max4 = stats.getMax(); // 9
double avg = stats.getAverage(); // 4.5
```

---

## 7. Удаление дубликатов

```java
int[] numbers = {1, 2, 2, 3, 3, 3, 4, 4};

// Способ 1: Через Stream
int[] unique = Arrays.stream(numbers)
    .distinct()
    .toArray();
// [1, 2, 3, 4]

// Способ 2: Через Set
int[] unique2 = Arrays.stream(numbers)
    .boxed()
    .collect(Collectors.toSet())
    .stream()
    .mapToInt(Integer::intValue)
    .toArray();

// Способ 3: Вручную (для отсортированного)
int[] sorted = {1, 2, 2, 3, 3, 3, 4};
int[] temp = new int[sorted.length];
int j = 0;

for (int i = 0; i < sorted.length - 1; i++) {
    if (sorted[i] != sorted[i + 1]) {
        temp[j++] = sorted[i];
    }
}
temp[j++] = sorted[sorted.length - 1];
int[] result = Arrays.copyOf(temp, j);
```

---

## 8. Разворот массива

```java
int[] arr = {1, 2, 3, 4, 5};

// Способ 1: Два указателя
int left = 0;
int right = arr.length - 1;

while (left < right) {
    int temp = arr[left];
    arr[left] = arr[right];
    arr[right] = temp;
    left++;
    right--;
}
// [5, 4, 3, 2, 1]

// Способ 2: Через Stream
int[] reversed = IntStream.rangeClosed(1, arr.length)
    .map(i -> arr[arr.length - i])
    .toArray();
```

---

## 9. Клонирование массива

```java
int[] original = {1, 2, 3, 4, 5};

// Способ 1: clone()
int[] clone1 = original.clone();

// Способ 2: Arrays.copyOf()
int[] clone2 = Arrays.copyOf(original, original.length);

// Способ 3: System.arraycopy()
int[] clone3 = new int[original.length];
System.arraycopy(original, 0, clone3, 0, original.length);

// Все три создают shallow copy (для примитивов — полная копия)
```

---

## 10. Array vs ArrayList

| Array | ArrayList |
|-------|-----------|
| `int[] arr = new int[10]` | `List<Integer> list = new ArrayList<>()` |
| Фиксированный размер | Динамический размер |
| Примитивы и объекты | Только объекты (автобоксинг) |
| `arr.length` (свойство) | `list.size()` (метод) |
| `arr[0] = 5` | `list.add(5)` |
| Быстрее (нет накладных расходов) | Медленнее (автобоксинг, resize) |
| Нет полезных методов | Есть add, remove, contains и др. |

---

## 11. Примеры задач

### 11.1. Сумма элементов массива

```java
public int sum(int[] arr) {
    int sum = 0;
    for (int n : arr) {
        sum += n;
    }
    return sum;
}

// Или через Stream
public int sumStream(int[] arr) {
    return Arrays.stream(arr).sum();
}
```

### 11.2. Среднее значение

```java
public double average(int[] arr) {
    if (arr.length == 0) return 0;
    return (double) sum(arr) / arr.length;
}

// Или через Stream
public double averageStream(int[] arr) {
    return Arrays.stream(arr).average().orElse(0);
}
```

### 11.3. Поиск элемента

```java
public int findIndex(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1; // Не найдено
}
```

### 11.4. Объединение массивов

```java
public int[] merge(int[] arr1, int[] arr2) {
    int[] result = new int[arr1.length + arr2.length];
    System.arraycopy(arr1, 0, result, 0, arr1.length);
    System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
    return result;
}

// Или через Stream
public int[] mergeStream(int[] arr1, int[] arr2) {
    return IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2))
        .toArray();
}
```

---

## 12. Шпаргалка

### Основные методы Arrays

| Метод | Описание |
|-------|----------|
| `sort(arr)` | Сортировка |
| `binarySearch(arr, key)` | Бинарный поиск |
| `copyOf(arr, len)` | Копия |
| `copyOfRange(arr, from, to)` | Копия диапазона |
| `fill(arr, val)` | Заполнить значением |
| `equals(arr1, arr2)` | Сравнение |
| `deepEquals(arr1, arr2)` | Глубокое сравнение |
| `toString(arr)` | В строку |
| `deepToString(arr)` | В строку (многомерные) |
| `stream(arr)` | Stream из массива |

---

### Сложность операций

| Операция | Сложность |
|----------|-----------|
| Доступ по индексу | O(1) |
| Поиск (линейный) | O(n) |
| Поиск (бинарный) | O(log n) |
| Сортировка | O(n log n) |
| Вставка/удаление | O(n) |
