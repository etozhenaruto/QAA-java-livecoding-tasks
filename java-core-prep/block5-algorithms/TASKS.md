# 📝 Блок 5: Алгоритмы — 10 задач

## Теория для повторения
- Сортировки: Bubble, Selection, Insertion, Merge, Quick
- Поиск: Linear, Binary
- Big O нотация
- Рекурсия
- Сложности алгоритмов

---

## 📋 Задания

### Задача 5.1: Bubble Sort

Реализуйте сортировку пузырьком:

```java
public void bubbleSort(int[] arr) {
    int n = arr.length;
    // Внешний цикл — количество проходов
    for (int i = 0; i < n - 1; i++) {
        // Внутренний цикл — сравнение соседних элементов
        for (int j = 0; j < n - i - 1; j++) {
            // Если следующий элемент меньше — меняем
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

### Задача 5.2: Selection Sort

Реализуйте сортировку выбором:

```java
public void selectionSort(int[] arr) {
    int n = arr.length;
    
    for (int i = 0; i < n - 1; i++) {
        // Найти индекс минимального элемента в неотсортированной части
        int minIdx = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < arr[minIdx]) {
                minIdx = j;
            }
        }
        
        // Поменять местами с первым элементом неотсортированной части
        int temp = arr[minIdx];
        arr[minIdx] = arr[i];
        arr[i] = temp;
    }
}
```

**Сложность:** O(n²)

---

### Задача 5.3: Insertion Sort

Реализуйте сортировку вставками:

```java
public void insertionSort(int[] arr) {
    int n = arr.length;
    
    for (int i = 1; i < n; i++) {
        int key = arr[i];
        int j = i - 1;
        
        // Переместить элементы большие чем key на одну позицию вправо
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        
        arr[j + 1] = key;
    }
}
```

**Сложность:** O(n²)

---

### Задача 5.4: Linear Search

Реализуйте линейный поиск:

```java
public int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1; // Не найдено
}
```

**Сложность:** O(n)

---

### Задача 5.5: Binary Search

Реализуйте бинарный поиск (итеративно и рекурсивно):

```java
// Итеративно
public int binarySearch(int[] sortedArr, int target) {
    int left = 0;
    int right = sortedArr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (sortedArr[mid] == target) {
            return mid;
        }
        
        if (sortedArr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1; // Не найдено
}

// Рекурсивно
public int binarySearchRecursive(int[] arr, int left, int right, int target) {
    if (left > right) {
        return -1;
    }
    
    int mid = left + (right - left) / 2;
    
    if (arr[mid] == target) {
        return mid;
    }
    
    if (arr[mid] < target) {
        return binarySearchRecursive(arr, mid + 1, right, target);
    } else {
        return binarySearchRecursive(arr, left, mid - 1, target);
    }
}
```

**Сложность:** O(log n)

---

### Задача 5.6: Merge Sort

Реализуйте сортировку слиянием:

```java
public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        
        // Рекурсивно сортируем левую и правую части
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        
        // Сливаем отсортированные части
        merge(arr, left, mid, right);
    }
}

private void merge(int[] arr, int left, int mid, int right) {
    int n1 = mid - left + 1;
    int n2 = right - mid;
    
    // Временные массивы
    int[] L = new int[n1];
    int[] R = new int[n2];
    
    // Копирование данных
    for (int i = 0; i < n1; i++) L[i] = arr[left + i];
    for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
    
    // Слияние
    int i = 0, j = 0, k = left;
    
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k++] = L[i++];
        } else {
            arr[k++] = R[j++];
        }
    }
    
    // Копировать оставшиеся элементы
    while (i < n1) arr[k++] = L[i++];
    while (j < n2) arr[k++] = R[j++];
}
```

**Сложность:** O(n log n)

---

### Задача 5.7: Quick Sort

Реализуйте быструю сортировку:

```java
public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        // Получить индекс разбиения
        int pi = partition(arr, low, high);
        
        // Рекурсивно сортировать до и после разбиения
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

private int partition(int[] arr, int low, int high) {
    int pivot = arr[high]; // Опорный элемент — последний
    int i = low - 1; // Индекс меньшего элемента
    
    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            // Поменять arr[i] и arr[j]
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    
    // Поменять arr[i+1] и arr[high] (pivot)
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    
    return i + 1;
}
```

**Сложность:** O(n log n) средняя, O(n²) худшая

---

### Задача 5.8: Поиск дубликатов

Найдите все дубликаты в массиве:

```java
public List<Integer> findDuplicates(int[] arr) {
    Set<Integer> seen = new HashSet<>();
    Set<Integer> duplicates = new HashSet<>();
    
    for (int num : arr) {
        if (!seen.add(num)) {
            duplicates.add(num);
        }
    }
    
    return new ArrayList<>(duplicates);
}

// Или через Stream
public List<Integer> findDuplicatesStream(int[] arr) {
    return Arrays.stream(arr)
        .boxed()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .filter(e -> e.getValue() > 1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
}
```

---

### Задача 5.9: Разворот массива

Разверните массив в обратном порядке:

```java
public void reverse(int[] arr) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left < right) {
        // Поменять местами
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        
        left++;
        right--;
    }
}

// Через Stream
public int[] reverseStream(int[] arr) {
    return IntStream.rangeClosed(1, arr.length)
        .map(i -> arr[arr.length - i])
        .toArray();
}
```

---

### Задача 5.10: Два указателя — сумма

Найдите два числа которые дают целевую сумму:

```java
// Для отсортированного массива
public int[] twoSum(int[] sortedArr, int target) {
    int left = 0;
    int right = sortedArr.length - 1;
    
    while (left < right) {
        int sum = sortedArr[left] + sortedArr[right];
        
        if (sum == target) {
            return new int[]{sortedArr[left], sortedArr[right]};
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    
    return null; // Не найдено
}

// Для неотсортированного (через HashMap)
public int[] twoSumUnsorted(int[] arr, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    
    for (int i = 0; i < arr.length; i++) {
        int complement = target - arr[i];
        if (map.containsKey(complement)) {
            return new int[]{complement, arr[i]};
        }
        map.put(arr[i], i);
    }
    
    return null;
}
```

---

## ✅ Чек-лист для самопроверки

- [ ] Знаете Bubble Sort и его сложность
- [ ] Знаете Selection Sort и его сложность
- [ ] Знаете Insertion Sort и его сложность
- [ ] Умеете реализовать Linear Search
- [ ] Умеете реализовать Binary Search (итеративно и рекурсивно)
- [ ] Знаете Merge Sort и его сложность
- [ ] Знаете Quick Sort и его сложность
- [ ] Умеете находить дубликаты в массиве
- [ ] Умеете разворачивать массив
- [ ] Знаете паттерн "два указателя"

---

## 📌 Подсказки

<details>
<summary>Подсказка: Big O сложности сортировок</summary>

| Алгоритм | Лучший | Средний | Худший | Память |
|----------|--------|---------|--------|--------|
| Bubble | O(n) | O(n²) | O(n²) | O(1) |
| Selection | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion | O(n) | O(n²) | O(n²) | O(1) |
| Merge | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick | O(n log n) | O(n log n) | O(n²) | O(log n) |
</details>

<details>
<summary>Подсказка: Big O нотация</summary>

```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2^n) < O(n!)

O(1)         — константа (доступ к элементу массива)
O(log n)     — логарифм (бинарный поиск)
O(n)         — линейная (линейный поиск)
O(n log n)   — линейно-логарифм (быстрая сортировка)
O(n²)        — квадратичная (bubble sort)
O(2^n)       — экспонента (рекурсивный Fibonacci)
O(n!)        — факториал (перебор перестановок)
```
</details>

<details>
<summary>Подсказка: Когда какую сортировку использовать</summary>

- **Bubble/Selection/Insertion** — только для обучения или очень маленьких массивов
- **Merge Sort** — стабильная сортировка, нужна гарантия O(n log n)
- **Quick Sort** — быстрая сортировка для больших массивов
- **Arrays.sort()** — использует Dual-Pivot Quicksort для примитивов и Timsort для объектов
</details>
