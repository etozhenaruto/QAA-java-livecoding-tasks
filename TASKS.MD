## Задания

<a name="task-1"></a>

### FizzBuzz

* **Описание:** Написать программу, которая выводит числа от 1 до N. Но для чисел, кратных 3, вывести "Fizz", для чисел,
  кратных 5 - "Buzz", а для чисел, кратных и 3, и 5 - "FizzBuzz". (Проверяет: циклы, условия)
* **Задание:** Напишите метод, например `generateFizzBuzz(int n)`, который принимает целое число n и возвращает
  последовательность FizzBuzz от 1 до n в виде списка строк (List<String>).
* **Пример:** Вызов `generateFizzBuzz(15)` должен вернуть список строк, эквивалентный: `List.of("1", "2", "Fizz", "4", "
  Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz")`
* **Решение:**
  [`FizzBuzz.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/FizzBuzz.java),
  [`FizzBuzzTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/FizzBuzzTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-2"></a>

### Перевернуть строку (Reverse String)

* **Описание:** Написать функцию, которая принимает строку и возвращает её в перевернутом виде. (Проверяет: работа со
  строками, циклы/рекурсия/StringBuilder)
* **Задание:** Напишите метод `String reverseString(String str)`, который принимает строку и возвращает новую
  строку, являющуюся перевернутой версией исходной.
* **Пример:** `reverseString("hello")` -> `"olleh"`, `reverseString("Java")` -> `"avaJ"`.
* **Решение:**
  [`ReverseString.java`](src/main/java/com/svedentsov/aqa/tasks/strings/ReverseString.java),
  [`ReverseStringTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/ReverseStringTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-3"></a>

### Проверка на палиндром (Palindrome Check)

* **Описание:** Написать функцию, которая проверяет, является ли строка палиндромом (читается одинаково слева направо и
  справа налево), игнорируя регистр и не буквенно-цифровые символы. (Проверяет: работа со строками, циклы, условия)
* **Задание:** Напишите метод `boolean isPalindrome(String str)`, который возвращает `true`, если строка `str`
  является палиндромом (игнорируя регистр и не буквенно-цифровые символы), и `false` в противном случае.
* **Пример:** `isPalindrome("A man, a plan, a canal: Panama")` -> `true`, `isPalindrome("race a car")` -> `false`,
  `isPalindrome(" ")` -> `true`.
* **Решение:**
  [`PalindromeCheck.java`](src/main/java/com/svedentsov/aqa/tasks/strings/PalindromeCheck.java),
  [`PalindromeCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/PalindromeCheckTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-4"></a>

### Найти дубликаты в массиве/списке (Find Duplicates in Array/List)

* **Описание:** Написать функцию, которая находит и возвращает дублирующиеся элементы в массиве или списке целых
  чисел/строк. (Проверяет: работа с коллекциями, Set/Map, циклы)
* **Задание:** Напишите метод `List<Integer> findDuplicates(List<Integer> numbers)`, который принимает список целых
  чисел и возвращает список уникальных чисел, которые встречаются в исходном списке более одного раза.
* **Пример:** `findDuplicates(List.of(1, 2, 3, 2, 4, 5, 1, 5))` -> `[1, 2, 5]` (порядок не важен, можно вернуть
  `Set`).
* **Решение:**
  [`FindDuplicatesList.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindDuplicatesList.java),
  [`FindDuplicatesListTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindDuplicatesListTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-5"></a>

### Посчитать количество вхождений символа в строке (Count Character Occurrences)

* **Описание:** Написать функцию, которая подсчитывает, сколько раз каждый символ встречается в строке. (Проверяет:
  работа со строками, Map, циклы)
* **Задание:** Напишите метод `Map<Character, Integer> countCharacters(String str)`, который возвращает Map, где
  ключами являются символы из строки `str`, а значениями - количество их вхождений.
* **Пример:** `countCharacters("hello world")` ->
  `{'h': 1, 'e': 1, 'l': 3, 'o': 2, ' ': 1, 'w': 1, 'r': 1, 'd': 1}`.
* **Решение:**
  [`CountCharOccurrences.java`](src/main/java/com/svedentsov/aqa/tasks/maps_sets/CountCharOccurrences.java),
  [`CountCharOccurrencesTest.java`](src/test/java/com/svedentsov/aqa/tasks/maps_sets/CountCharOccurrencesTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-6"></a>

### Найти максимальный/минимальный элемент в массиве/списке (Find Max/Min in Array/List)

* **Описание:** Написать функцию для поиска наибольшего или наименьшего числа в массиве/списке без использования
  встроенных функций `max`/`min` коллекций (иногда просят). (Проверяет: циклы, сравнения)
* **Задание:** Напишите метод `int findMax(int[] numbers)`, который находит и возвращает максимальное значение в
  массиве `numbers` без использования `Collections.max()` или `Stream.max()`. Обработайте случай пустого массива (
  например, выбросив исключение или вернув `Integer.MIN_VALUE`).
* **Пример:** `findMax(new int[]{1, 5, 2, 9, 3})` -> `9`. `findMax(new int[]{-1, -5, -2})` -> `-1`.
* **Решение:**
  [`FindMaxMinArray.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindMaxMinArray.java),
  [`FindMaxMinArrayTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindMaxMinArrayTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-7"></a>

### Удалить дубликаты из списка (Remove Duplicates from List)

* **Описание:** Написать функцию, которая принимает список и возвращает новый список без дубликатов. (Проверяет: работа
  с коллекциями, Set, циклы)
* **Задание:** Напишите метод `List<String> removeDuplicates(List<String> list)`, который принимает список строк и
  возвращает новый список, содержащий только уникальные строки из исходного списка, сохраняя порядок первого
  вхождения.
* **Пример:** `removeDuplicates(List.of("a", "b", "a", "c", "b"))` -> `["a", "b", "c"]`.
* **Решение:**
  [`RemoveDuplicatesList.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/RemoveDuplicatesList.java),
  [`RemoveDuplicatesListTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/RemoveDuplicatesListTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-8"></a>

### Проверка на анаграммы (Anagram Check)

* **Описание:** Написать функцию, которая проверяет, являются ли две строки анаграммами друг друга (состоят из одних и
  тех же символов в разном порядке). (Проверяет: работа со строками, сортировка/Map, сравнение)
* **Задание:** Напишите метод `boolean areAnagrams(String str1, String str2)`, который возвращает `true`, если
  строки `str1` и `str2` являются анаграммами (игнорируя регистр и пробелы), и `false` иначе.
* **Пример:** `areAnagrams("listen", "silent")` -> `true`, `areAnagrams("Dormitory", "dirty room")` -> `true`,
  `areAnagrams("hello", "world")` -> `false`.
* **Решение:**
  [`AnagramCheck.java`](src/main/java/com/svedentsov/aqa/tasks/strings/AnagramCheck.java),
  [`AnagramCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/AnagramCheckTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-9"></a>

### Сумма элементов массива/списка (Sum Array/List Elements)

* **Описание:** Написать функцию для вычисления суммы всех числовых элементов в массиве/списке. (Проверяет: циклы,
  арифметика)
* **Задание:** Напишите метод `long sumElements(int[] numbers)`, который вычисляет и возвращает сумму всех элементов
  в массиве `numbers`.
* **Пример:** `sumElements(new int[]{1, 2, 3, 4, 5})` -> `15`. `sumElements(new int[]{})` -> `0`.
* **Решение:**
  [`SumArrayElements.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/SumArrayElements.java),
  [`SumArrayElementsTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/SumArrayElementsTest.java)

[К оглавлению](#table-of-contents)
