# ✅ Ответы: Блок 4 — Массивы

## Задача 4.1: Создание и инициализация

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

## Задача 4.2: Доступ к элементам

```java
int[] arr = {10, 20, 30, 40, 50};

// 1. Получить первый элемент
int first = arr[0]; // 10

// 2. Получить последний элемент
int last = arr[arr.length - 1]; // 50

// 3. Изменить элемент
arr[2] = 100; // {10, 20, 100, 40, 50}

// 4. Получить длину
int len = arr.length; // 5

// 5. Перебрать все элементы
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

// For-each
for (int val : arr) {
    System.out.println(val);
}
```

---

## Задача 4.3: Arrays.sort

```java
int[] numbers = {5, 2, 8, 1, 9, 3};

// 1. Сортировка по возрастанию
Arrays.sort(numbers);
System.out.println(Arrays.toString(numbers)); // [1, 2, 3, 5, 8, 9]

// 2. Сортировка части массива
Arrays.sort(numbers, 0, 3); // сортировать первые 3 элемента

// 3. Сортировка в обратном порядке (через Integer)
Integer[] boxed = {5, 2, 8, 1, 9};
Arrays.sort(boxed, Collections.reverseOrder());
System.out.println(Arrays.toString(boxed)); // [9, 8, 5, 2, 1]

// 4. Сортировка строк по длине
String[] words = {"apple", "cat", "banana"};
Arrays.sort(words, Comparator.comparingInt(String::length));
System.out.println(Arrays.toString(words)); // [cat, apple, banana]
```

---

## Задача 4.4: Arrays.binarySearch

```java
int[] sorted = {1, 3, 5, 7, 9, 11};

// 1. Найти элемент
int index = Arrays.binarySearch(sorted, 7); // 3

// 2. Найти в диапазоне
int index2 = Arrays.binarySearch(sorted, 1, 4, 5); // 2

// 3. Если элемент не найден (вернёт отрицательное число)
int notFound = Arrays.binarySearch(sorted, 4); // -3 (индекс вставки)

// Важно: массив должен быть отсортирован!
```

---

## Задача 4.5: Arrays.copyOf

```java
int[] original = {1, 2, 3, 4, 5};

// 1. Копия всей длины
int[] copy = Arrays.copyOf(original, original.length);

// 2. Копия с увеличением размера
int[] bigger = Arrays.copyOf(original, 10); // {1,2,3,4,5,0,0,0,0,0}

// 3. Копия с уменьшением размера
int[] smaller = Arrays.copyOf(original, 3); // {1,2,3}

// 4. Копия диапазона
int[] range = Arrays.copyOfRange(original, 1, 4); // {2,3,4}
```

---

## Задача 4.6: Arrays.equals

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
int[] arr3 = {1, 2, 4};

// 1. Сравнение
boolean equal = Arrays.equals(arr1, arr2); // true
boolean notEqual = Arrays.equals(arr1, arr3); // false

// 2. Сравнение диапазонов
boolean rangeEqual = Arrays.equals(arr1, 0, 2, arr2, 0, 2); // true

// 3. Глубокое сравнение для многомерных
int[][] matrix1 = {{1, 2}, {3, 4}};
int[][] matrix2 = {{1, 2}, {3, 4}};
boolean deepEqual = Arrays.deepEquals(matrix1, matrix2); // true
```

---

## Задача 4.7: Двумерные массивы

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
    System.out.println();
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
System.out.println("Diagonal sum: " + sum); // 15
```

---

## Задача 4.8: Stream из массива

```java
int[] numbers = {1, 2, 3, 4, 5};

// 1. IntStream
IntStream stream = Arrays.stream(numbers);

// 2. Stream<Integer>
Stream<Integer> boxed = Arrays.stream(numbers).boxed();

// 3. Из части массива
IntStream range = Arrays.stream(numbers, 1, 4); // элементы 1,2,3

// 4. Stream -> Array
Integer[] arr = Stream.of(1, 2, 3)
    .toArray(Integer[]::new);
```

---

## Задача 4.9: Поиск минимума и максимума

```java
int[] numbers = {5, 2, 9, 1, 7, 3};

// 1. Через сортировку
int[] sorted = numbers.clone();
Arrays.sort(sorted);
int min = sorted[0];
int max = sorted[sorted.length - 1];

// 2. Через цикл
int min2 = numbers[0];
int max2 = numbers[0];
for (int n : numbers) {
    if (n < min2) min2 = n;
    if (n > max2) max2 = n;
}

// 3. Через Stream
int min3 = Arrays.stream(numbers).min().getAsInt();
int max3 = Arrays.stream(numbers).max().getAsInt();
```

---

## Задача 4.10: Удаление дубликатов

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

System.out.println(Arrays.toString(unique)); // [1, 2, 3, 4]
```

---

## Вопросы для самопроверки — Ответы

1. **Как получить длину массива?**
   - `array.length` (свойство, не метод!)

2. **В чём разница между Arrays.copyOf и Arrays.copyOfRange?**
   - copyOf — копирует с начала до указанной длины
   - copyOfRange — копирует диапазон от start до end

3. **Что вернёт binarySearch если элемент не найден?**
   - Отрицательное число: `-(insertion point) - 1`

4. **Как сравнить многомерные массивы?**
   - `Arrays.deepEquals(arr1, arr2)`

5. **Как создать Stream из массива?**
   - `Arrays.stream(array)` или `Stream.of(array)`
