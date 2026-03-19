# ✅ Ответы: Блок 5 — Алгоритмы

## Задача 5.1: Bubble Sort

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

// Тест:
int[] arr = {64, 34, 25, 12, 22, 11, 90};
bubbleSort(arr);
System.out.println(Arrays.toString(arr)); // [11, 12, 22, 25, 34, 64, 90]
```

**Сложность:** O(n²)

---

## Задача 5.2: Selection Sort

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

// Тест:
int[] arr = {64, 34, 25, 12, 22, 11, 90};
selectionSort(arr);
System.out.println(Arrays.toString(arr)); // [11, 12, 22, 25, 34, 64, 90]
```

**Сложность:** O(n²)

---

## Задача 5.3: Insertion Sort

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

// Тест:
int[] arr = {64, 34, 25, 12, 22, 11, 90};
insertionSort(arr);
System.out.println(Arrays.toString(arr)); // [11, 12, 22, 25, 34, 64, 90]
```

**Сложность:** O(n²)

---

## Задача 5.4: Linear Search

```java
public int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1; // Не найдено
}

// Тест:
int[] arr = {10, 25, 30, 45, 50};
System.out.println(linearSearch(arr, 30)); // 2
System.out.println(linearSearch(arr, 100)); // -1
```

**Сложность:** O(n)

---

## Задача 5.5: Binary Search

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

// Тест:
int[] sorted = {1, 3, 5, 7, 9, 11, 13};
System.out.println(binarySearch(sorted, 7)); // 3
System.out.println(binarySearchRecursive(sorted, 0, sorted.length - 1, 7)); // 3
```

**Сложность:** O(log n)

---

## Задача 5.6: Merge Sort

```java
public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        
        merge(arr, left, mid, right);
    }
}

private void merge(int[] arr, int left, int mid, int right) {
    int n1 = mid - left + 1;
    int n2 = right - mid;
    
    int[] L = new int[n1];
    int[] R = new int[n2];
    
    for (int i = 0; i < n1; i++) L[i] = arr[left + i];
    for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];
    
    int i = 0, j = 0, k = left;
    
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k++] = L[i++];
        } else {
            arr[k++] = R[j++];
        }
    }
    
    while (i < n1) arr[k++] = L[i++];
    while (j < n2) arr[k++] = R[j++];
}

// Тест:
int[] arr = {64, 34, 25, 12, 22, 11, 90};
mergeSort(arr, 0, arr.length - 1);
System.out.println(Arrays.toString(arr)); // [11, 12, 22, 25, 34, 64, 90]
```

**Сложность:** O(n log n)

---

## Задача 5.7: Quick Sort

```java
public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

private int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    
    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    
    return i + 1;
}

// Тест:
int[] arr = {64, 34, 25, 12, 22, 11, 90};
quickSort(arr, 0, arr.length - 1);
System.out.println(Arrays.toString(arr)); // [11, 12, 22, 25, 34, 64, 90]
```

**Сложность:** O(n log n) средняя, O(n²) худшая

---

## Задача 5.8: Поиск дубликатов

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

// Тест:
int[] arr = {1, 2, 2, 3, 3, 3, 4, 4};
System.out.println(findDuplicates(arr)); // [2, 3, 4]
```

---

## Задача 5.9: Разворот массива

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

// Через Stream
public int[] reverseStream(int[] arr) {
    return IntStream.rangeClosed(1, arr.length)
        .map(i -> arr[arr.length - i])
        .toArray();
}

// Тест:
int[] arr = {1, 2, 3, 4, 5};
reverse(arr);
System.out.println(Arrays.toString(arr)); // [5, 4, 3, 2, 1]
```

---

## Задача 5.10: Два указателя — сумма

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
    
    return null;
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

// Тест:
int[] sorted = {1, 2, 3, 4, 5, 6};
System.out.println(Arrays.toString(twoSum(sorted, 9))); // [3, 6] или [4, 5]

int[] unsorted = {2, 7, 11, 15};
System.out.println(Arrays.toString(twoSumUnsorted(unsorted, 9))); // [2, 7]
```

---

## Вопросы для самопроверки — Ответы

1. **Какая сложность у Bubble Sort?**
   - O(n²) в среднем и худшем случае

2. **Какая сложность у Binary Search?**
   - O(log n)

3. **Какая сортировка самая быстрая?**
   - Quick Sort в среднем O(n log n), но может быть O(n²)

4. **Что такое Big O нотация?**
   - Способ описания производительности алгоритма

5. **Когда использовать два указателя?**
   - Для отсортированных массивов при поиске пары
