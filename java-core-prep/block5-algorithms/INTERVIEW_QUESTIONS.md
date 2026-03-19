# 🎯 Вопросы для собеседования: Алгоритмы

## Базовые вопросы

### 1. Что такое Big O нотация?

**Ответ:**
Big O — способ описания производительности алгоритма в худшем случае.

**Основные сложности:**
```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2^n) < O(n!)
```

---

### 2. Какая сложность у линейного поиска?

**Ответ:**
O(n) — проверяем каждый элемент:

```java
public int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) return i;
    }
    return -1;
}
```

---

### 3. Какая сложность у бинарного поиска?

**Ответ:**
O(log n) — делим диапазон пополам:

```java
public int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

**Важно:** Требуется отсортированный массив!

---

### 4. Какие сортировки O(n²) вы знаете?

**Ответ:**
- Bubble Sort (пузырьковая)
- Selection Sort (выбором)
- Insertion Sort (вставками)

---

### 5. Как работает Bubble Sort?

**Ответ:**
Соседние элементы сравниваются и меняются местами если не в порядке:

```java
public void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        for (int j = 0; j < arr.length - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
```

**Сложность:** O(n²)

---

### 6. Как работает Insertion Sort?

**Ответ:**
Берём элемент и вставляем в правильную позицию в отсортированной части:

```java
public void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}
```

**Сложность:** O(n²), но O(n) для почти отсортированных

---

### 7. Какие сортировки O(n log n) вы знаете?

**Ответ:**
- Merge Sort (слиянием)
- Quick Sort (быстрая)
- Heap Sort (пирамидальная)

---

### 8. Как работает Merge Sort?

**Ответ:**
Разделяй и властвуй — делим пополам, сортируем, сливаем:

```java
public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}
```

**Сложность:** O(n log n) всегда

---

### 9. Как работает Quick Sort?

**Ответ:**
Выбираем pivot, делим на части, сортируем рекурсивно:

```java
public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}
```

**Сложность:** O(n log n) средняя, O(n²) худшая

---

### 10. Что такое паттерн "два указателя"?

**Ответ:**
Используем два указателя для прохода по массиву:

```java
// Разворот массива
int left = 0, right = arr.length - 1;
while (left < right) {
    int temp = arr[left];
    arr[left] = arr[right];
    arr[right] = temp;
    left++;
    right--;
}

// Two Sum для отсортированного
int left = 0, right = arr.length - 1;
while (left < right) {
    int sum = arr[left] + arr[right];
    if (sum == target) return new int[]{left, right};
    if (sum < target) left++;
    else right--;
}
```

---

## Продвинутые вопросы

### 11. Когда Quick Sort имеет сложность O(n²)?

**Ответ:**
Когда pivot всегда крайний элемент и массив уже отсортирован.

**Решение:**
- Randomized pivot
- Median-of-three pivot

---

### 12. В чём разница между стабильной и нестабильной сортировкой?

**Ответ:**
- **Стабильная** — сохраняет порядок равных элементов
- **Нестабильная** — может изменить порядок

| Стабильные | Нестабильные |
|------------|--------------|
| Bubble Sort | Selection Sort |
| Insertion Sort | Quick Sort |
| Merge Sort | Heap Sort |

---

### 13. Какая сортировка используется в Arrays.sort()?

**Ответ:**
- **Для примитивов:** Dual-Pivot Quicksort (O(n log n))
- **Для объектов:** Timsort (стабильная, O(n log n))

---

### 14. Что такое рекурсия и какие проблемы?

**Ответ:**
Рекурсия — функция вызывающая саму себя.

**Проблемы:**
- StackOverflowError при глубокой рекурсии
- Неэффективность (Fibonacci O(2^n))

**Решение:** Мемоизация или итерация

```java
// Мемоизация Fibonacci
Map<Integer, Integer> memo = new HashMap<>();
int fib(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    int result = fib(n-1) + fib(n-2);
    memo.put(n, result);
    return result;
}
```

---

### 15. Что такое паттерн "скользящее окно"?

**Ответ:**
Держим "окно" в массиве и двигаем его:

```java
// Максимальная сумма подмассива размера k
public int maxSumSubarray(int[] arr, int k) {
    int windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += arr[i];
    
    int maxSum = windowSum;
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i - k];
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

---

### 16. Что такое префиксные суммы?

**Ответ:**
Предвычисляем суммы префиксов для быстрого ответа на запросы:

```java
// Предвычисление
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}

// Сумма диапазона [l, r] за O(1)
int sumRange(int l, int r) {
    return prefix[r + 1] - prefix[l];
}
```

---

### 17. Как найти дубликаты в массиве?

**Ответ:**
```java
// Через HashSet
Set<Integer> seen = new HashSet<>();
Set<Integer> duplicates = new HashSet<>();
for (int num : arr) {
    if (!seen.add(num)) {
        duplicates.add(num);
    }
}

// Через Stream
List<Integer> dups = Arrays.stream(arr)
    .boxed()
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
    .entrySet().stream()
    .filter(e -> e.getValue() > 1)
    .map(Map.Entry::getKey)
    .collect(Collectors.toList());
```

---

### 18. Что такое жадный алгоритм?

**Ответ:**
Алгоритм который делает локально оптимальный выбор на каждом шаге:

**Примеры:**
- Задача о рюкзаке (дробная)
- Алгоритм Хаффмана
- Алгоритм Дейкстры

---

### 19. Когда использовать какую сортировку?

**Ответ:**

| Ситуация | Сортировка |
|----------|------------|
| Маленький массив (< 50) | Insertion Sort |
| Большой массив | Quick Sort |
| Нужна стабильность | Merge Sort |
| Почти отсортирован | Insertion Sort |
| Ограниченная память | Heap Sort |

---

### 20. Какая сложность у различных операций?

**Ответ:**

| Операция | Сложность |
|----------|-----------|
| Доступ к элементу массива | O(1) |
| Линейный поиск | O(n) |
| Бинарный поиск | O(log n) |
| Сортировка пузырьком | O(n²) |
| Быстрая сортировка | O(n log n) |
| Перебор перестановок | O(n!) |
| Рекурсивный Fibonacci | O(2^n) |
