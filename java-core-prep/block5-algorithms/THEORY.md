# 📚 Теория: Алгоритмы

## 1. Big O нотация

**Big O** — это способ описания производительности алгоритма в худшем случае.

### 1.1. Основные сложности

```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2^n) < O(n!)
```

| Сложность | Название | Пример |
|-----------|----------|--------|
| O(1) | Константное | Доступ к элементу массива |
| O(log n) | Логарифмическое | Бинарный поиск |
| O(n) | Линейное | Линейный поиск |
| O(n log n) | Линейно-логарифмическое | Быстрая сортировка |
| O(n²) | Квадратичное | Bubble sort |
| O(2^n) | Экспоненциальное | Рекурсивный Fibonacci |
| O(n!) | Факториал | Перебор перестановок |

### 1.2. Визуализация

```
n = 10:
O(1)     = 1
O(log n) = 3
O(n)     = 10
O(n log n) = 30
O(n²)    = 100
O(2^n)   = 1024
O(n!)    = 3,628,800
```

---

## 2. Сортировки

### 2.1. Bubble Sort (Пузырьковая)

**Идея:** Соседние элементы сравниваются и меняются местами если не в порядке.

**Алгоритм:**
1. Сравниваем arr[0] и arr[1], меняем если arr[0] > arr[1]
2. Сравниваем arr[1] и arr[2], меняем если arr[1] > arr[2]
3. Повторяем до конца массива — наибольший элемент "всплыл"
4. Повторяем для оставшихся элементов

```java
public void bubbleSort(int[] arr) {
    int n = arr.length;
    boolean swapped;
    
    for (int i = 0; i < n - 1; i++) {
        swapped = false;
        
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // Поменять местами
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                swapped = true;
            }
        }
        
        // Если не было обменов — массив отсортирован
        if (!swapped) break;
    }
}
```

**Сложность:**
- Лучший случай: O(n) — массив уже отсортирован
- Средний: O(n²)
- Худший: O(n²)
- Память: O(1)

**Когда использовать:** Только для обучения!

---

### 2.2. Selection Sort (Выбором)

**Идея:** Находим минимальный элемент и ставим его на первую позицию.

**Алгоритм:**
1. Найти минимальный элемент в массиве
2. Поменять его с первым элементом
3. Найти минимальный в оставшейся части
4. Поменять со вторым элементом
5. Повторять пока не отсортируем всё

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

**Сложность:**
- Лучший: O(n²)
- Средний: O(n²)
- Худший: O(n²)
- Память: O(1)

**Когда использовать:** Только для обучения!

---

### 2.3. Insertion Sort (Вставками)

**Идея:** Берём элемент и вставляем его в правильную позицию в отсортированной части.

**Алгоритм:**
1. Первый элемент считаем отсортированным
2. Берём второй элемент, вставляем перед первым если он меньше
3. Берём третий элемент, вставляем в правильную позицию
4. Повторяем для всех элементов

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

**Сложность:**
- Лучший: O(n) — массив уже отсортирован
- Средний: O(n²)
- Худший: O(n²)
- Память: O(1)

**Когда использовать:** Для маленьких или почти отсортированных массивов

---

### 2.4. Merge Sort (Слиянием)

**Идея:** Разделяй и властвуй — делим массив пополам, сортируем части, сливаем.

**Алгоритм:**
1. Разделить массив пополам
2. Рекурсивно отсортировать левую часть
3. Рекурсивно отсортировать правую часть
4. Слить две отсортированные части

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

**Сложность:**
- Лучший: O(n log n)
- Средний: O(n log n)
- Худший: O(n log n)
- Память: O(n)

**Когда использовать:** Когда нужна стабильная сортировка с гарантированной O(n log n)

---

### 2.5. Quick Sort (Быстрая)

**Идея:** Разделяй и властвуй — выбираем опорный элемент, делим на части.

**Алгоритм:**
1. Выбрать опорный элемент (pivot)
2. Разделить массив: элементы меньше pivot слева, больше — справа
3. Рекурсивно отсортировать левую и правую части

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

**Сложность:**
- Лучший: O(n log n)
- Средний: O(n log n)
- Худший: O(n²) — массив отсортирован, pivot всегда крайний
- Память: O(log n) — стек рекурсии

**Когда использовать:** Для больших массивов, самая быстрая на практике

---

### 2.6. Сравнение сортировок

| Алгоритм | Лучший | Средний | Худший | Память | Стабильная |
|----------|--------|---------|--------|--------|------------|
| Bubble | O(n) | O(n²) | O(n²) | O(1) | Да |
| Selection | O(n²) | O(n²) | O(n²) | O(1) | Нет |
| Insertion | O(n) | O(n²) | O(n²) | O(1) | Да |
| Merge | O(n log n) | O(n log n) | O(n log n) | O(n) | Да |
| Quick | O(n log n) | O(n log n) | O(n²) | O(log n) | Нет |
| Arrays.sort() | O(n log n) | O(n log n) | O(n²) | O(log n) | Нет* |

*Для примитивов — Dual-Pivot Quicksort, для объектов — Timsort

---

## 3. Поиск

### 3.1. Linear Search (Линейный)

**Идея:** Проверяем каждый элемент пока не найдём.

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

**Сложность:**
- Лучший: O(1) — элемент первый
- Средний: O(n)
- Худший: O(n) — элемент последний или отсутствует

**Когда использовать:** Для маленьких или неотсортированных массивов

---

### 3.2. Binary Search (Бинарный)

**Идея:** Делим отсортированный массив пополам и отбрасываем половину.

**Алгоритм:**
1. Найти средний элемент
2. Если средний == искомому — готово
3. Если средний < искомого — ищем в правой половине
4. Если средний > искомого — ищем в левой половине
5. Повторять пока не найдём или диапазон не станет пустым

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

**Сложность:**
- Лучший: O(1)
- Средний: O(log n)
- Худший: O(log n)

**Когда использовать:** Для отсортированных массивов

**Важно:** Массив должен быть отсортирован!

---

## 4. Паттерны алгоритмов

### 4.1. Два указателя (Two Pointers)

**Идея:** Используем два указателя для прохода по массиву.

**Пример: Найти пару с суммой target (отсортированный массив)**

```java
public int[] twoSum(int[] sortedArr, int target) {
    int left = 0;
    int right = sortedArr.length - 1;
    
    while (left < right) {
        int sum = sortedArr[left] + sortedArr[right];
        
        if (sum == target) {
            return new int[]{sortedArr[left], sortedArr[right]};
        } else if (sum < target) {
            left++;  // Увеличиваем сумму
        } else {
            right--; // Уменьшаем сумму
        }
    }
    
    return null; // Не найдено
}
```

**Пример: Разворот массива**

```java
public void reverse(int[] arr) {
    int left = 0;
    int right = arr.length - 1;
    
    while (left < right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        
        left++;
        right--;
    }
}
```

---

### 4.2. Скользящее окно (Sliding Window)

**Идея:** Держим "окно" в массиве и двигаем его.

**Пример: Максимальная сумма подмассива размера k**

```java
public int maxSumSubarray(int[] arr, int k) {
    int n = arr.length;
    int windowSum = 0;
    
    // Сумма первого окна
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    
    int maxSum = windowSum;
    
    // Скользим по массиву
    for (int i = k; i < n; i++) {
        windowSum += arr[i] - arr[i - k];
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}
```

---

### 4.3. Hash Map для поиска

**Идея:** Используем HashMap для ускорения поиска.

**Пример: Two Sum (неотсортированный массив)**

```java
public int[] twoSum(int[] arr, int target) {
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

**Сложность:** O(n) вместо O(n²)

---

### 4.4. Префиксные суммы (Prefix Sum)

**Идея:** Предвычисляем суммы префиксов для быстрого ответа на запросы.

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

## 5. Рекурсия

**Рекурсия** — функция вызывающая саму себя.

**Пример: Факториал**

```java
public int factorial(int n) {
    if (n <= 1) {
        return 1; // Базовый случай
    }
    return n * factorial(n - 1); // Рекурсивный вызов
}
```

**Пример: Числа Фибоначчи**

```java
// Неэффективно: O(2^n)
public int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

// Эффективно с мемоизацией: O(n)
Map<Integer, Integer> memo = new HashMap<>();
public int fibonacciMemo(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    
    int result = fibonacciMemo(n - 1) + fibonacciMemo(n - 2);
    memo.put(n, result);
    return result;
}
```

---

## 6. Шпаргалка

### Сложности алгоритмов

| Алгоритм | Время | Память |
|----------|-------|--------|
| Bubble Sort | O(n²) | O(1) |
| Selection Sort | O(n²) | O(1) |
| Insertion Sort | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(log n) |
| Linear Search | O(n) | O(1) |
| Binary Search | O(log n) | O(1) |

### Когда что использовать

| Задача | Алгоритм |
|--------|----------|
| Маленький массив (< 50) | Insertion Sort |
| Большой массив | Quick Sort |
| Нужна стабильность | Merge Sort |
| Отсортированный массив | Binary Search |
| Неотсортированный массив | Linear Search или HashMap |
| Найти пару с суммой | Two Pointers или HashMap |
