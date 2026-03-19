# 📝 Блок 4: Массивы (Arrays) — 10 задач

## Теория для повторения
- Одномерные и многомерные массивы
- Методы класса Arrays: sort, binarySearch, copyOf, fill, equals
- ArrayList vs Array
- Stream из массива

---

## 📋 Задания

### Задача 4.1: Создание и инициализация

Создайте и инициализируйте массив разными способами:

```java
// 1. Создание массива размером 10
int[] arr1 = new int[10];

// 2. Инициализация значениями
int[] arr2 = {1, 2, 3, 4, 5};

// 3. Создание и заполнение
int[] arr3 = new int[]{10, 20, 30};

// 4. Двумерный массив
int[][] matrix = {{1, 2}, {3, 4}, {5, 6}};

// 5. Заполнить массив значением
Arrays.fill(arr1, 5);
```

---

### Задача 4.2: Доступ к элементам

Работа с элементами массива:

```java
int[] arr = {10, 20, 30, 40, 50};

// 1. Получить первый элемент
int first = arr[0];

// 2. Получить последний элемент
int last = arr[arr.length - 1];

// 3. Изменить элемент
arr[2] = 100;

// 4. Получить длину
int len = arr.length;

// 5. Перебрать все элементы
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}
```

---

### Задача 4.3: Arrays.sort

Отсортируйте массив:

```java
int[] numbers = {5, 2, 8, 1, 9, 3};

// 1. Сортировка по возрастанию
Arrays.sort(numbers);

// 2. Сортировка части массива
Arrays.sort(numbers, 0, 3); // сортировать первые 3 элемента

// 3. Сортировка в обратном порядке (через Integer)
Integer[] boxed = {5, 2, 8, 1, 9};
Arrays.sort(boxed, Collections.reverseOrder());

// 4. Сортировка строк по длине
String[] words = {"apple", "cat", "banana"};
Arrays.sort(words, Comparator.comparingInt(String::length));
```

---

### Задача 4.4: Arrays.binarySearch

Выполните бинарный поиск:

```java
int[] sorted = {1, 3, 5, 7, 9, 11};

// 1. Найти элемент
int index = Arrays.binarySearch(sorted, 7);

// 2. Найти в диапазоне
int index2 = Arrays.binarySearch(sorted, 1, 4, 5);

// 3. Если элемент не найден (вернёт отрицательное число)
int notFound = Arrays.binarySearch(sorted, 4);

// Важно: массив должен быть отсортирован!
```

---

### Задача 4.5: Arrays.copyOf

Скопируйте массив:

```java
int[] original = {1, 2, 3, 4, 5};

// 1. Копия всей длины
int[] copy = Arrays.copyOf(original, original.length);

// 2. Копия с увеличением размера
int[] bigger = Arrays.copyOf(original, 10);

// 3. Копия с уменьшением размера
int[] smaller = Arrays.copyOf(original, 3);

// 4. Копия диапазона
int[] range = Arrays.copyOfRange(original, 1, 4); // элементы 1, 2, 3
```

---

### Задача 4.6: Arrays.equals

Сравните массивы:

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
int[] arr3 = {1, 2, 4};

// 1. Сравнение
boolean equal = Arrays.equals(arr1, arr2); // true
boolean notEqual = Arrays.equals(arr1, arr3); // false

// 2. Сравнение диапазонов
boolean rangeEqual = Arrays.equals(arr1, 0, 2, arr2, 0, 2);

// 3. Глубокое сравнение для многомерных
int[][] matrix1 = {{1, 2}, {3, 4}};
int[][] matrix2 = {{1, 2}, {3, 4}};
boolean deepEqual = Arrays.deepEquals(matrix1, matrix2);
```

---

### Задача 4.7: Двумерные массивы

Работа с матрицами:

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// 1. Получить элемент [row][col]
int val = matrix[1][2]; // 6

// 2. Перебрать все элементы
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
}

// 3. For-each
for (int[] row : matrix) {
    for (int cell : row) {
        System.out.print(cell + " ");
    }
}

// 4. Сумма диагонали
int sum = 0;
for (int i = 0; i < matrix.length; i++) {
    sum += matrix[i][i];
}
```

---

### Задача 4.8: Stream из массива

Создайте Stream из массива:

```java
int[] numbers = {1, 2, 3, 4, 5};

// 1. IntStream
IntStream stream = Arrays.stream(numbers);

// 2. Stream<Integer>
Stream<Integer> boxed = Arrays.stream(numbers)
    .boxed();

// 3. Из части массива
IntStream range = Arrays.stream(numbers, 1, 4);

// 4. Stream -> Array
Integer[] arr = Stream.of(1, 2, 3)
    .toArray(Integer[]::new);
```

---

### Задача 4.9: Поиск минимума и максимума

Найдите min и max в массиве:

```java
int[] numbers = {5, 2, 9, 1, 7, 3};

// 1. Через сортировку
Arrays.sort(numbers);
int min = numbers[0];
int max = numbers[numbers.length - 1];

// 2. Через цикл
int min = numbers[0];
int max = numbers[0];
for (int n : numbers) {
    if (n < min) min = n;
    if (n > max) max = n;
}

// 3. Через Stream
int min = Arrays.stream(numbers).min().getAsInt();
int max = Arrays.stream(numbers).max().getAsInt();
```

---

### Задача 4.10: Удаление дубликатов

Удалите дубликаты из массива:

```java
int[] numbers = {1, 2, 2, 3, 3, 3, 4, 4};

// 1. Через Stream
int[] unique = Arrays.stream(numbers)
    .distinct()
    .toArray();

// 2. Через Set
int[] unique2 = Arrays.stream(numbers)
    .boxed()
    .collect(Collectors.toSet())
    .stream()
    .mapToInt(Integer::intValue)
    .toArray();
```

---

## ✅ Чек-лист для самопроверки

- [ ] Умеете создавать и инициализировать массивы
- [ ] Знаете как получить длину массива
- [ ] Умеете сортировать массив (Arrays.sort)
- [ ] Умеете выполнять бинарный поиск
- [ ] Умеете копировать массив (copyOf, copyOfRange)
- [ ] Умеете сравнивать массивы (equals, deepEquals)
- [ ] Работаете с двумерными массивами
- [ ] Умеете создавать Stream из массива
- [ ] Знаете как найти min/max в массиве

---

## 📌 Подсказки

<details>
<summary>Подсказка: Arrays методы</summary>

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
</details>

<details>
<summary>Подсказка: Двумерный массив</summary>

```java
// Прямоугольная матрица
int[][] matrix = new int[3][3];

// Зубчатый массив (разная длина строк)
int[][] jagged = new int[3][];
jagged[0] = new int[2];
jagged[1] = new int[4];
jagged[2] = new int[1];
```
</details>

<details>
<summary>Подсказка: Stream операции с массивами</summary>

```java
// Сумма
int sum = Arrays.stream(arr).sum();

// Среднее
double avg = Arrays.stream(arr).average().orElse(0);

// Количество
long count = Arrays.stream(arr).count();
```
</details>
