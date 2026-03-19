# 📚 Теория: Оконные функции

## 1. Что такое оконные функции?

**Оконные функции** выполняют вычисления над набором строк, связанных с текущей строкой, но не группируют результат.

**Отличие от GROUP BY:**
- GROUP BY группирует строки в одну
- Оконная функция сохраняет все строки

```sql
-- GROUP BY (агрегация)
SELECT department, AVG(salary) AS avg_salary
FROM employees
GROUP BY department;
-- Результат: одна строка на отдел

-- Оконная функция
SELECT name, department, salary,
       AVG(salary) OVER (PARTITION BY department) AS avg_salary
FROM employees;
-- Результат: все строки + средняя по отделу
```

---

## 2. Синтаксис оконных функций

```sql
function_name() OVER (
    PARTITION BY column1, column2, ...
    ORDER BY column3
    ROWS BETWEEN ...
)
```

**Компоненты:**
- `OVER` — определяет окно
- `PARTITION BY` — разбивает на группы (как GROUP BY, но без агрегации)
- `ORDER BY` — сортировка внутри окна
- Frame specification — границы окна

---

## 3. Ранжирующие функции

### ROW_NUMBER

Нумерует строки внутри окна:

```sql
-- Нумерация заказов по клиенту
SELECT 
    customer_id,
    order_date,
    total,
    ROW_NUMBER() OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS row_num
FROM orders;

-- Топ-1 заказ каждого клиента
SELECT * FROM (
    SELECT 
        customer_id,
        total,
        ROW_NUMBER() OVER (
            PARTITION BY customer_id 
            ORDER BY total DESC
        ) AS rn
    FROM orders
) ranked
WHERE rn = 1;
```

### RANK и DENSE_RANK

```sql
-- RANK: пропускает номера при равных значениях
SELECT 
    name,
    salary,
    RANK() OVER (ORDER BY salary DESC) AS rank
FROM employees;

-- Результат:
-- name  | salary | rank
-- John  | 500000 | 1
-- Jane  | 400000 | 2
-- Bob   | 350000 | 3
-- Alice | 350000 | 3  (равно с Bob)
-- Carol | 250000 | 5  (пропустил 4!)

-- DENSE_RANK: не пропускает номера
SELECT 
    name,
    salary,
    DENSE_RANK() OVER (ORDER BY salary DESC) AS rank
FROM employees;

-- Результат:
-- name  | salary | rank
-- John  | 500000 | 1
-- Jane  | 400000 | 2
-- Bob   | 350000 | 3
-- Alice | 350000 | 3  (равно с Bob)
-- Carol | 250000 | 4  (не пропустил!)
```

### NTILE

Разделяет строки на N групп:

```sql
-- Разделить сотрудников на 4 группы по зарплате
SELECT 
    name,
    salary,
    NTILE(4) OVER (ORDER BY salary DESC) AS quartile
FROM employees;

-- Результат:
-- name  | salary | quartile
-- John  | 500000 | 1  (top 25%)
-- Jane  | 400000 | 1
-- Bob   | 350000 | 2
-- Alice | 350000 | 2
-- Carol | 250000 | 3
-- Dave  | 180000 | 4  (bottom 25%)
```

---

## 4. Функции смещения

### LAG

Доступ к предыдущей строке:

```sql
-- Сравнение с предыдущим заказом
SELECT 
    customer_id,
    order_date,
    total,
    LAG(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS prev_order,
    total - LAG(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS diff
FROM orders;

-- Процент изменения
SELECT 
    customer_id,
    order_date,
    total,
    LAG(total) OVER (PARTITION BY customer_id ORDER BY order_date) AS prev,
    ROUND(
        (total - LAG(total) OVER (PARTITION BY customer_id ORDER BY order_date)) 
        / LAG(total) OVER (PARTITION BY customer_id ORDER BY order_date) * 100,
        2
    ) AS change_percent
FROM orders;
```

### LEAD

Доступ к следующей строке:

```sql
-- Сравнение со следующим заказом
SELECT 
    customer_id,
    order_date,
    total,
    LEAD(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS next_order
FROM orders;
```

### FIRST_VALUE, LAST_VALUE, NTH_VALUE

```sql
-- Первый и последний заказ клиента
SELECT 
    customer_id,
    order_date,
    total,
    FIRST_VALUE(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS first_order,
    LAST_VALUE(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS last_order
FROM orders;

-- N-й элемент (3-й заказ)
SELECT 
    customer_id,
    total,
    NTH_VALUE(total, 3) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS third_order
FROM orders;
```

**Важно:** Для LAST_VALUE и NTH_VALUE нужно указывать `ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING`.

---

## 5. Агрегатные оконные функции

```sql
-- Сумма с нарастающим итогом
SELECT 
    order_date,
    total,
    SUM(total) OVER (ORDER BY order_date) AS running_total
FROM orders;

-- Сумма по клиенту с нарастающим итогом
SELECT 
    customer_id,
    order_date,
    total,
    SUM(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS customer_running_total
FROM orders;

-- Средняя цена по категории для каждого товара
SELECT 
    p.name,
    p.price,
    p.category_id,
    AVG(p.price) OVER (PARTITION BY p.category_id) AS avg_category_price
FROM products p;

-- Количество заказов с нарастающим итогом
SELECT 
    order_date,
    COUNT(*) OVER (ORDER BY order_date) AS cumulative_orders
FROM orders;
```

---

## 6. Frame specification (границы окна)

```sql
ROWS BETWEEN ... AND ...
RANGE BETWEEN ... AND ...
```

**Границы:**
- `UNBOUNDED PRECEDING` — начало окна
- `UNBOUNDED FOLLOWING` — конец окна
- `CURRENT ROW` — текущая строка
- `n PRECEDING` — n строк до
- `n FOLLOWING` — n строк после

**Примеры:**

```sql
-- Скользящее среднее за 3 заказа
SELECT 
    customer_id,
    order_date,
    total,
    AVG(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
        ROWS BETWEEN 1 PRECEDING AND 1 FOLLOWING
    ) AS moving_avg
FROM orders;

-- Сумма с начала
SELECT 
    order_date,
    total,
    SUM(total) OVER (
        ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS running_total
FROM orders;

-- Сумма всех строк (постоянное значение)
SELECT 
    name,
    salary,
    SUM(salary) OVER (
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS total_salary
FROM employees;
```

---

## 7. Практические примеры

### Пример 1: Топ-N в каждой группе

```sql
-- Топ-3 товара по продажам в каждой категории
SELECT * FROM (
    SELECT 
        p.name AS product,
        c.name AS category,
        SUM(oi.quantity * oi.price) AS revenue,
        RANK() OVER (
            PARTITION BY c.id 
            ORDER BY SUM(oi.quantity * oi.price) DESC
        ) AS rank
    FROM products p
    JOIN categories c ON p.category_id = c.id
    JOIN order_items oi ON p.id = oi.product_id
    GROUP BY p.id, p.name, c.name, c.id
) ranked
WHERE rank <= 3;
```

### Пример 2: Анализ изменений

```sql
-- Рост заказов по месяцам
SELECT 
    DATE_TRUNC('month', order_date) AS month,
    COUNT(*) AS orders,
    LAG(COUNT(*)) OVER (ORDER BY DATE_TRUNC('month', order_date)) AS prev_month,
    ROUND(
        (COUNT(*) - LAG(COUNT(*)) OVER (ORDER BY DATE_TRUNC('month', order_date))) 
        / LAG(COUNT(*)) OVER (ORDER BY DATE_TRUNC('month', order_date)) * 100,
        2
    ) AS growth_percent
FROM orders
GROUP BY DATE_TRUNC('month', order_date);
```

### Пример 3: Поиск дубликатов

```sql
-- Найти дубликаты email
SELECT * FROM (
    SELECT 
        id,
        name,
        email,
        ROW_NUMBER() OVER (PARTITION BY email ORDER BY id) AS rn
    FROM customers
) duplicates
WHERE rn > 1;
```

---

## 8. Шпаргалка по оконным функциям

| Функция | Описание | Пример |
|---------|----------|--------|
| ROW_NUMBER | Нумерация | `ROW_NUMBER() OVER (ORDER BY col)` |
| RANK | Ранг с пропусками | `RANK() OVER (ORDER BY col)` |
| DENSE_RANK | Ранг без пропусков | `DENSE_RANK() OVER (ORDER BY col)` |
| NTILE | Разделение на N групп | `NTILE(4) OVER (ORDER BY col)` |
| LAG | Предыдущая строка | `LAG(col, 1) OVER (...)` |
| LEAD | Следующая строка | `LEAD(col, 1) OVER (...)` |
| FIRST_VALUE | Первое значение | `FIRST_VALUE(col) OVER (...)` |
| LAST_VALUE | Последнее значение | `LAST_VALUE(col) OVER (...)` |
| SUM/AVG OVER | Агрегация в окне | `SUM(col) OVER (ORDER BY col)` |

---

## 9. PARTITION BY vs GROUP BY

| PARTITION BY | GROUP BY |
|--------------|----------|
| Сохраняет все строки | Группирует в одну строку |
| Для оконных функций | Для агрегации |
| Добавляет столбец | Уменьшает количество строк |

```sql
-- PARTITION BY (все строки)
SELECT name, department, salary,
       AVG(salary) OVER (PARTITION BY department) AS avg_sal
FROM employees;

-- GROUP BY (одна строка на отдел)
SELECT department, AVG(salary) AS avg_sal
FROM employees
GROUP BY department;
```
