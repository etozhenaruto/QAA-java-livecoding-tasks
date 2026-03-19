# 📚 Java Core — Подготовка к собеседованию (AQ Java Automation)

Этот проект содержит подробную теорию, практические задания и вопросы для подготовки к собеседованию по Java Core.

## 📁 Структура проекта

```
java-core-prep/
├── README.md                        # Главный файл с навигацией
├── block1-exceptions/
│   ├── THEORY.md                    # Подробная теория по исключениям
│   ├── TASKS.md                     # 10 практических задач
│   ├── INTERVIEW_QUESTIONS.md       # 15 вопросов для собеседования
│   └── answers/                     # Ответы (в общей папке answers)
├── block2-collections/
│   ├── THEORY.md                    # Подробная теория по коллекциям
│   ├── TASKS.md                     # 10 практических задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов для собеседования
├── block3-streams/
│   ├── THEORY.md                    # Подробная теория по стримам
│   ├── TASKS.md                     # 10 практических задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов для собеседования
├── block4-arrays/
│   ├── THEORY.md                    # Подробная теория по массивам
│   ├── TASKS.md                     # 10 практических задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов для собеседования
├── block5-algorithms/
│   ├── THEORY.md                    # Подробная теория по алгоритмам
│   ├── TASKS.md                     # 10 практических задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов для собеседования
├── block6-multithreading/
│   ├── THEORY.md                    # Подробная теория по многопоточности
│   ├── TASKS.md                     # 10 практических задач
│   └── INTERVIEW_QUESTIONS.md       # 30 вопросов для собеседования
├── answers/
│   ├── block1-answers.md            # Ответы: исключения
│   ├── block2-answers.md            # Ответы: коллекции
│   ├── block3-answers.md            # Ответы: стримы
│   ├── block4-answers.md            # Ответы: массивы
│   ├── block5-answers.md            # Ответы: алгоритмы
│   └── block6-answers.md            # Ответы: многопоточность
└── src/                             # Вспомогательные классы
```

---

## 📖 Содержание по блокам

### 🔹 Блок 1: Исключения (Exceptions)

**Файлы:**
- `block1-exceptions/THEORY.md` — развёрнутая теория
- `block1-exceptions/TASKS.md` — 10 задач
- `block1-exceptions/INTERVIEW_QUESTIONS.md` — 15 вопросов

**Темы:**
- Иерархия исключений (Throwable, Error, Exception)
- Checked vs Unchecked исключения
- try-catch-finally, try-with-resources
- throw, throws, custom exceptions
- Exception propagation, chained exceptions
- Best practices

---

### 🔹 Блок 2: Коллекции (Collections)

**Файлы:**
- `block2-collections/THEORY.md` — развёрнутая теория
- `block2-collections/TASKS.md` — 10 задач
- `block2-collections/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- List: ArrayList, LinkedList, Vector
- Set: HashSet, LinkedHashSet, TreeSet
- Map: HashMap, LinkedHashMap, TreeMap, Hashtable
- Queue: PriorityQueue, ArrayDeque
- Iterator, ListIterator
- Comparable vs Comparator
- Fail-fast итераторы
- Синхронизированные коллекции

---

### 🔹 Блок 3: Стримы (Streams)

**Файлы:**
- `block3-streams/THEORY.md` — развёрнутая теория
- `block3-streams/TASKS.md` — 10 задач
- `block3-streams/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- Intermediate vs Terminal операции
- filter, map, flatMap, sorted, distinct, limit, skip
- forEach, collect, reduce, count, match operations
- Collectors: toList, toSet, toMap, joining, groupingBy, partitioningBy
- Parallel streams
- Optional

---

### 🔹 Блок 4: Массивы (Arrays)

**Файлы:**
- `block4-arrays/THEORY.md` — развёрнутая теория
- `block4-arrays/TASKS.md` — 10 задач
- `block4-arrays/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- Одномерные и многомерные массивы
- Методы класса Arrays: sort, binarySearch, copyOf, fill, equals
- ArrayList vs Array
- Stream из массива
- Поиск min/max, удаление дубликатов

---

### 🔹 Блок 5: Алгоритмы

**Файлы:**
- `block5-algorithms/THEORY.md` — развёрнутая теория
- `block5-algorithms/TASKS.md` — 10 задач
- `block5-algorithms/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- Big O нотация
- Сортировки: Bubble, Selection, Insertion, Merge, Quick
- Поиск: Linear, Binary
- Паттерны: два указателя, скользящее окно, префиксные суммы
- Рекурсия

---

### 🔹 Блок 6: Многопоточность (Multithreading)

**Файлы:**
- `block6-multithreading/THEORY.md` — развёрнутая теория
- `block6-multithreading/TASKS.md` — 10 задач
- `block6-multithreading/INTERVIEW_QUESTIONS.md` — 30 вопросов

**Темы:**
- Создание потоков: Thread, Runnable, Callable
- ExecutorService и Thread Pool
- synchronized, Lock, ReentrantLock
- wait, notify, notifyAll
- volatile, atomic variables
- CompletableFuture
- Concurrent коллекции
- CountDownLatch, CyclicBarrier, Semaphore

---

## 🎯 Как использовать этот проект

### Шаг 1: Изучение теории

1. Откройте папку нужного блока (например, `block1-exceptions/`)
2. Прочитайте `THEORY.md` — подробная теория с примерами
3. Изучите ключевые концепции и best practices

### Шаг 2: Практика

1. Откройте `TASKS.md` в папке блока
2. Выполните все 10 задач
3. Используйте подсказки если застряли

### Шаг 3: Самопроверка

1. Откройте `answers/` в корне проекта
2. Найдите файл с ответами для вашего блока (например, `block1-answers.md`)
3. Сравните свои решения с эталонными

### Шаг 4: Подготовка к собеседованию

1. Откройте `INTERVIEW_QUESTIONS.md` в папке блока
2. Прочитайте вопросы и ответы
3. Попробуйте ответить самостоятельно перед просмотром ответа

---

## 📊 Итоговая статистика

| Блок | Теория | Задачи | Вопросы |
|------|--------|--------|---------|
| Исключения | ✅ | 10 | 15 |
| Коллекции | ✅ | 10 | 20 |
| Стримы | ✅ | 10 | 20 |
| Массивы | ✅ | 10 | 20 |
| Алгоритмы | ✅ | 10 | 20 |
| Многопоточность | ✅ | 10 | 30 |
| **Итого** | **6 файлов** | **60 задач** | **125 вопросов** |

---

## ✅ Чек-лист готовности

- [ ] Прочитал теорию по всем 6 блокам
- [ ] Выполнил все 60 практических задач
- [ ] Изучил ответы для самопроверки
- [ ] Повторил все 125 вопросов для собеседования
- [ ] Понимаю ключевые концепции Java Core
- [ ] Готов к техническому интервью!

---

Удачи в подготовке! 🍀
