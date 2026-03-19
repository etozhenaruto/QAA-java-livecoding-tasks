# 📚 SQL & NoSQL — Подготовка к собеседованию (AQ Java Automation)

Этот проект содержит подробную теорию, практические задания и вопросы для подготовки к собеседованию по SQL и NoSQL.

## 📁 Структура проекта

```
sql-nosql-prep/
├── README.md                        # Главный файл с навигацией
├── block1-sql-basics/
│   ├── THEORY.md                    # Основы SQL
│   ├── TASKS.md                     # 15 задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов
├── block2-joins-subqueries/
│   ├── THEORY.md                    # JOIN и подзапросы
│   ├── TASKS.md                     # 15 задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов
├── block3-aggregation/
│   ├── THEORY.md                    # Агрегация и группировка
│   ├── TASKS.md                     # 15 задач
│   └── INTERVIEW_QUESTIONS.md       # 15 вопросов
├── block4-window-functions/
│   ├── THEORY.md                    # Оконные функции
│   ├── TASKS.md                     # 15 задач
│   └── INTERVIEW_QUESTIONS.md       # 15 вопросов
├── block5-optimization-indexes/
│   ├── THEORY.md                    # Оптимизация и индексы
│   ├── TASKS.md                     # 10 задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов
├── block6-nosql/
│   ├── THEORY.md                    # NoSQL базы данных
│   ├── TASKS.md                     # 10 задач
│   └── INTERVIEW_QUESTIONS.md       # 20 вопросов
├── answers/
│   ├── block1-answers.md            # Ответы: Основы SQL
│   ├── block2-answers.md            # Ответы: JOIN и подзапросы
│   ├── block3-answers.md            # Ответы: Агрегация
│   ├── block4-answers.md            # Ответы: Оконные функции
│   ├── block5-answers.md            # Ответы: Оптимизация
│   └── block6-answers.md            # Ответы: NoSQL
└── data/                            # SQL скрипты для создания тестовых данных
```

---

## 📖 Содержание по блокам

### 🔹 Блок 1: Основы SQL

**Файлы:**
- `block1-sql-basics/THEORY.md` — теория
- `block1-sql-basics/TASKS.md` — 15 задач
- `block1-sql-basics/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- SELECT, FROM, WHERE
- Операторы сравнения и логические операторы
- ORDER BY, LIMIT, OFFSET
- LIKE, IN, BETWEEN, IS NULL
- DISTINCT
- UNION, INTERSECT, EXCEPT
- INSERT, UPDATE, DELETE
- Типы данных SQL

---

### 🔹 Блок 2: JOIN и подзапросы

**Файлы:**
- `block2-joins-subqueries/THEORY.md` — теория
- `block2-joins-subqueries/TASKS.md` — 15 задач
- `block2-joins-subqueries/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- INNER JOIN
- LEFT JOIN, RIGHT JOIN, FULL JOIN
- CROSS JOIN
- SELF JOIN
- Подзапросы в SELECT
- Подзапросы в WHERE
- Подзапросы в FROM
- EXISTS, NOT EXISTS
- ANY, ALL

---

### 🔹 Блок 3: Агрегация и группировка

**Файлы:**
- `block3-aggregation/THEORY.md` — теория
- `block3-aggregation/TASKS.md` — 15 задач
- `block3-aggregation/INTERVIEW_QUESTIONS.md` — 15 вопросов

**Темы:**
- GROUP BY
- HAVING
- COUNT, SUM, AVG, MIN, MAX
- GROUP_CONCAT, STRING_AGG
- ROLLUP, CUBE
- GROUPING SETS
- Фильтрация после агрегации

---

### 🔹 Блок 4: Оконные функции

**Файлы:**
- `block4-window-functions/THEORY.md` — теория
- `block4-window-functions/TASKS.md` — 15 задач
- `block4-window-functions/INTERVIEW_QUESTIONS.md` — 15 вопросов

**Темы:**
- OVER, PARTITION BY, ORDER BY
- ROW_NUMBER, RANK, DENSE_RANK
- LAG, LEAD
- FIRST_VALUE, LAST_VALUE
- NTH_VALUE
- NTILE
- SUM/AVG OVER
- Frame specification (ROWS, RANGE)

---

### 🔹 Блок 5: Оптимизация и индексы

**Файлы:**
- `block5-optimization-indexes/THEORY.md` — теория
- `block5-optimization-indexes/TASKS.md` — 10 задач
- `block5-optimization-indexes/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- Типы индексов (B-Tree, Hash, Composite)
- EXPLAIN, EXPLAIN ANALYZE
- Query execution plan
- Index scan vs Seq scan
- Covering index
- N+1 проблема
- Нормализация и денормализация
- Транзакции, ACID
- Уровни изоляции

---

### 🔹 Блок 6: NoSQL базы данных

**Файлы:**
- `block6-nosql/THEORY.md` — теория
- `block6-nosql/TASKS.md` — 10 задач
- `block6-nosql/INTERVIEW_QUESTIONS.md` — 20 вопросов

**Темы:**
- Типы NoSQL баз данных
- Document stores (MongoDB)
- Key-Value stores (Redis)
- Column-family stores (Cassandra)
- Graph databases (Neo4j)
- CAP теорема
- BASE vs ACID
- Шардирование и репликация
- Когда использовать SQL vs NoSQL

---

## 🎯 Как использовать этот проект

### Шаг 1: Изучение теории

1. Откройте папку нужного блока
2. Прочитайте `THEORY.md` — подробная теория с примерами
3. Изучите ключевые концепции

### Шаг 2: Практика

1. Откройте `TASKS.md` в папке блока
2. Выполните все задачи
3. Используйте подсказки если застряли

### Шаг 3: Самопроверка

1. Откройте `answers/` в корне проекта
2. Найдите файл с ответами для вашего блока
3. Сравните свои решения с эталонными

### Шаг 4: Подготовка к собеседованию

1. Откройте `INTERVIEW_QUESTIONS.md` в папке блока
2. Прочитайте вопросы и ответы
3. Попробуйте ответить самостоятельно

---

## 📊 Итоговая статистика

| Блок | Теория | Задачи | Вопросы |
|------|--------|--------|---------|
| Основы SQL | ✅ | 15 | 20 |
| JOIN и подзапросы | ✅ | 15 | 20 |
| Агрегация и группировка | ✅ | 15 | 15 |
| Оконные функции | ✅ | 15 | 15 |
| Оптимизация и индексы | ✅ | 10 | 20 |
| NoSQL | ✅ | 10 | 20 |
| **Итого** | **6 файлов** | **80 задач** | **110 вопросов** |

---

## 🗄️ Схема тестовой базы данных

Для практики используется следующая схема:

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  customers  │     │   orders    │     │order_items  │
├─────────────┤     ├─────────────┤     ├─────────────┤
│ id          │────<│ id          │────<│ id          │
│ name        │     │ customer_id │     │ order_id    │
│ email       │     │ order_date  │     │ product_id  │
│ city        │     │ status      │     │ quantity    │
│ created_at  │     │ total       │     │ price       │
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                           │
                    ┌─────────────┐     ┌─────────────┐
                    │  products   │     │  categories │
                    ├─────────────┤     ├─────────────┤
                    │ id          │>────│ id          │
                    │ name        │     │ name        │
                    │ description │     │ parent_id   │
                    │ price       │     └─────────────┘
                    │ category_id │
                    │ stock       │
                    └─────────────┘

┌─────────────┐     ┌─────────────┐
│   reviews   │     │  employees  │
├─────────────┤     ├─────────────┤
│ id          │     │ id          │
│ product_id  │     │ name        │
│ customer_id │     │ manager_id  │
│ rating      │     │ department  │
│ comment     │     │ salary      │
│ created_at  │     │ hire_date   │
└─────────────┘     └─────────────┘
```

---

## ✅ Чек-лист готовности

- [ ] Прочитал теорию по всем 6 блокам
- [ ] Выполнил все 80 практических задач
- [ ] Изучил ответы для самопроверки
- [ ] Повторил все 110 вопросов для собеседования
- [ ] Понимаю SQL и NoSQL концепции
- [ ] Готов к техническому интервью!

---

Удачи в подготовке! 🍀
