# 📚 Теория: Агрегация и группировка

## 1. Агрегатные функции

**Агрегатные функции** выполняют вычисления над набором строк и возвращают одно значение.

### Основные агрегатные функции:

```sql
-- COUNT: количество строк
SELECT COUNT(*) FROM customers;
SELECT COUNT(DISTINCT city) FROM customers;

-- SUM: сумма значений
SELECT SUM(total) FROM orders;

-- AVG: среднее значение
SELECT AVG(price) FROM products;

-- MIN/MAX: минимальное/максимальное значение
SELECT MIN(price), MAX(price) FROM products;
```

---

## 2. GROUP BY

**GROUP BY** группирует строки с одинаковыми значениями.

```sql
-- Количество заказов по клиентам
SELECT customer_id, COUNT(*) AS order_count
FROM orders
GROUP BY customer_id;

-- Сумма продаж по товарам
SELECT product_id, SUM(quantity * price) AS revenue
FROM order_items
GROUP BY product_id;

-- Группировка по нескольким столбцам
SELECT customer_id, status, COUNT(*) AS order_count
FROM orders
GROUP BY customer_id, status;
```

---

## 3. HAVING

**HAVING** фильтрует результаты группировки.

```sql
-- Клиенты с более чем 2 заказами
SELECT customer_id, COUNT(*) AS order_count
FROM orders
GROUP BY customer_id
HAVING COUNT(*) > 2;

-- Товары с общей продажей больше 10000
SELECT product_id, SUM(quantity * price) AS revenue
FROM order_items
GROUP BY product_id
HAVING SUM(quantity * price) > 10000;

-- HAVING vs WHERE
-- WHERE фильтрует строки ДО группировки
-- HAVING фильтрует результаты ПОСЛЕ группировки

SELECT customer_id, COUNT(*) AS order_count
FROM orders
WHERE status = 'completed'  -- Фильтр до группировки
GROUP BY customer_id
HAVING COUNT(*) > 1;        -- Фильтр после группировки
```

---

## 4. STRING_AGG и GROUP_CONCAT

**Объединение строк в группе:**

```sql
-- PostgreSQL
SELECT city, STRING_AGG(name, ', ') AS customers
FROM customers
GROUP BY city;

-- MySQL
SELECT city, GROUP_CONCAT(name SEPARATOR ', ') AS customers
FROM customers
GROUP BY city;

-- С сортировкой внутри агрегации
SELECT city, STRING_AGG(name, ', ' ORDER BY name) AS customers
FROM customers
GROUP BY city;
```

---

## 5. ROLLUP, CUBE, GROUPING SETS

### ROLLUP — иерархическая группировка

```sql
-- Сумма продаж по категориям и товарам с итогами
SELECT 
    c.name AS category,
    p.name AS product,
    SUM(oi.quantity * oi.price) AS revenue
FROM products p
JOIN categories c ON p.category_id = c.id
JOIN order_items oi ON p.id = oi.product_id
GROUP BY ROLLUP(c.name, p.name)
ORDER BY c.name, p.name;

-- Результат:
-- category | product | revenue
-- Электроника | iPhone | 99990
-- Электроника | Samsung | 89990
-- Электроника | NULL | 189980  (итог по категории)
-- NULL | NULL | 500000         (общий итог)
```

### CUBE — все комбинации

```sql
-- Все комбинации группировки
SELECT 
    c.city,
    p.category_id,
    SUM(o.total) AS revenue
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN products p ON o.id = p.id
GROUP BY CUBE(c.city, p.category_id);
```

### GROUPING SETS — конкретные наборы

```sql
-- Только указанные группировки
SELECT 
    c.city,
    p.category_id,
    SUM(o.total) AS revenue
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN products p ON o.id = p.id
GROUP BY GROUPING SETS (
    (c.city, p.category_id),
    (c.city),
    (p.category_id),
    ()
);
```

---

## 6. GROUPING и GROUPING_ID

```sql
-- Определение уровня группировки
SELECT 
    c.name AS category,
    p.name AS product,
    SUM(oi.quantity * oi.price) AS revenue,
    GROUPING(c.name) AS g_category,
    GROUPING(p.name) AS g_product
FROM products p
JOIN categories c ON p.category_id = c.id
JOIN order_items oi ON p.id = oi.product_id
GROUP BY ROLLUP(c.name, p.name);

-- GROUPING возвращает 1 для итоговых строк (NULL от ROLLUP)
```

---

## 7. FILTER (PostgreSQL)

**Условная агрегация:**

```sql
-- Количество заказов по статусам
SELECT 
    COUNT(*) FILTER (WHERE status = 'completed') AS completed,
    COUNT(*) FILTER (WHERE status = 'pending') AS pending,
    COUNT(*) FILTER (WHERE status = 'cancelled') AS cancelled
FROM orders;

-- Средняя цена с фильтром
SELECT 
    AVG(price) FILTER (WHERE price > 1000) AS avg_expensive,
    AVG(price) FILTER (WHERE price <= 1000) AS avg_cheap
FROM products;
```

---

## 8. Практические примеры

### Пример 1: Отчёт по продажам

```sql
SELECT 
    c.name AS customer,
    COUNT(o.id) AS orders_count,
    SUM(o.total) AS total_spent,
    AVG(o.total) AS avg_order,
    MIN(o.order_date) AS first_order,
    MAX(o.order_date) AS last_order
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name
ORDER BY total_spent DESC;
```

### Пример 2: Анализ товаров

```sql
SELECT 
    p.name AS product,
    c.name AS category,
    COUNT(oi.id) AS times_ordered,
    SUM(oi.quantity) AS total_sold,
    SUM(oi.quantity * oi.price) AS revenue,
    AVG(r.rating) AS avg_rating
FROM products p
JOIN categories c ON p.category_id = c.id
LEFT JOIN order_items oi ON p.id = oi.product_id
LEFT JOIN reviews r ON p.id = r.product_id
GROUP BY p.id, p.name, c.name
ORDER BY revenue DESC;
```

### Пример 3: RFM анализ клиентов

```sql
WITH customer_stats AS (
    SELECT 
        customer_id,
        COUNT(*) AS frequency,
        SUM(total) AS monetary,
        MAX(order_date) AS recency
    FROM orders
    WHERE status = 'completed'
    GROUP BY customer_id
)
SELECT 
    customer_id,
    frequency,
    monetary,
    recency,
    CURRENT_DATE - recency AS days_since_order
FROM customer_stats
ORDER BY monetary DESC;
```

---

## 9. Шпаргалка

| Функция | Описание | Пример |
|---------|----------|--------|
| COUNT | Количество | `COUNT(*)`, `COUNT(DISTINCT col)` |
| SUM | Сумма | `SUM(price)` |
| AVG | Среднее | `AVG(price)` |
| MIN | Минимум | `MIN(price)` |
| MAX | Максимум | `MAX(price)` |
| STRING_AGG | Конкатенация | `STRING_AGG(name, ',')` |
| GROUP BY | Группировка | `GROUP BY column` |
| HAVING | Фильтр групп | `HAVING COUNT > 1` |
| ROLLUP | Иерархия | `ROLLUP(a, b)` |
| CUBE | Все комбинации | `CUBE(a, b)` |

---

## 10. WHERE vs HAVING

| WHERE | HAVING |
|-------|--------|
| До группировки | После группировки |
| Фильтрует строки | Фильтр группы |
| Не может использовать агрегаты | Может использовать агрегаты |

```sql
-- Правильно:
SELECT customer_id, COUNT(*) AS cnt
FROM orders
WHERE status = 'completed'  -- WHERE до GROUP BY
GROUP BY customer_id
HAVING COUNT(*) > 1;        -- HAVING после GROUP BY

-- Неправильно:
SELECT customer_id, COUNT(*) AS cnt
FROM orders
HAVING status = 'completed';  -- Ошибка!
```
