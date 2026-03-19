# 📚 Теория: JOIN и подзапросы

## 1. Что такое JOIN?

**JOIN** — операция для объединения строк из двух или более таблиц по связанному столбцу.

---

## 2. Типы JOIN

### 2.1. INNER JOIN (внутреннее соединение)

Возвращает только те строки, для которых есть совпадение в обеих таблицах.

**Синтаксис:**
```sql
SELECT columns
FROM table1
INNER JOIN table2 ON table1.column = table2.column;
```

**Пример:**
```sql
-- Заказы с информацией о клиентах
SELECT o.id, o.order_date, c.name, c.email
FROM orders o
INNER JOIN customers c ON o.customer_id = c.id;

-- Только выполненные заказы с клиентами
SELECT o.id, c.name, o.total
FROM orders o
INNER JOIN customers c ON o.customer_id = c.id
WHERE o.status = 'completed';
```

**Визуализация:**
```
Table A:        Table B:
1               1
2               3
3               4

INNER JOIN: результат = [1, 3] (общие значения)
```

---

### 2.2. LEFT JOIN (левое внешнее соединение)

Возвращает все строки из левой таблицы и совпадающие из правой. Если нет совпадения — NULL справа.

**Синтаксис:**
```sql
SELECT columns
FROM table1
LEFT JOIN table2 ON table1.column = table2.column;
```

**Пример:**
```sql
-- Все клиенты и их заказы (даже если заказов нет)
SELECT c.name, c.email, o.id AS order_id, o.total
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id;

-- Клиенты без заказов
SELECT c.name, c.email
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;
```

**Визуализация:**
```
Table A:        Table B:
1               1
2               3
3               4

LEFT JOIN: результат = [1, 2, 3] (все из A + совпадения из B)
```

---

### 2.3. RIGHT JOIN (правое внешнее соединение)

Возвращает все строки из правой таблицы и совпадающие из левой.

**Синтаксис:**
```sql
SELECT columns
FROM table1
RIGHT JOIN table2 ON table1.column = table2.column;
```

**Пример:**
```sql
-- Все заказы и клиенты (даже если клиент удалён)
SELECT c.name, o.id, o.total
FROM customers c
RIGHT JOIN orders o ON c.id = o.customer_id;
```

**Примечание:** RIGHT JOIN можно заменить на LEFT JOIN, поменяв таблицы местами.

---

### 2.4. FULL JOIN (полное внешнее соединение)

Возвращает все строки из обеих таблиц.

**Синтаксис:**
```sql
SELECT columns
FROM table1
FULL JOIN table2 ON table1.column = table2.column;
```

**Пример:**
```sql
-- Все клиенты и все заказы (полное объединение)
SELECT c.name, o.id
FROM customers c
FULL JOIN orders o ON c.id = o.customer_id;
```

**Визуализация:**
```
Table A:        Table B:
1               1
2               3
3               4

FULL JOIN: результат = [1, 2, 3, 4] (все значения)
```

---

### 2.5. CROSS JOIN (декартово произведение)

Возвращает все возможные комбинации строк.

**Синтаксис:**
```sql
SELECT columns
FROM table1
CROSS JOIN table2;
```

**Пример:**
```sql
-- Все комбинации товаров и категорий
SELECT p.name AS product, c.name AS category
FROM products p
CROSS JOIN categories c;

-- Если 10 товаров и 5 категорий → 50 строк
```

**Осторожно:** Может создать очень большой результат!

---

### 2.6. SELF JOIN (соединение с самим собой)

Таблица соединяется сама с собой.

**Пример:**
```sql
-- Сотрудники и их менеджеры
SELECT e.name AS employee, m.name AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;

-- Категории и их родительские категории
SELECT c.name AS category, p.name AS parent_category
FROM categories c
LEFT JOIN categories p ON c.parent_id = p.id;
```

---

## 3. Подзапросы (Subqueries)

**Подзапрос** — запрос внутри другого запроса.

---

### 3.1. Подзапросы в SELECT

```sql
-- Заказы с именем клиента (подзапрос в SELECT)
SELECT 
    o.id,
    o.total,
    (SELECT name FROM customers WHERE id = o.customer_id) AS customer_name
FROM orders o;

-- Товары со средней ценой по категории
SELECT 
    p.name,
    p.price,
    (SELECT AVG(price) FROM products WHERE category_id = p.category_id) AS avg_category_price
FROM products p;
```

---

### 3.2. Подзапросы в WHERE

```sql
-- Товары дороже среднего
SELECT * FROM products
WHERE price > (SELECT AVG(price) FROM products);

-- Клиенты, сделавшие заказы
SELECT * FROM customers
WHERE id IN (SELECT DISTINCT customer_id FROM orders);

-- Клиенты без заказов
SELECT * FROM customers
WHERE id NOT IN (SELECT DISTINCT customer_id FROM orders);

-- Товары, которые заказывали
SELECT * FROM products
WHERE id = ANY (SELECT product_id FROM order_items);

-- Товары, которые не заказывали
SELECT * FROM products
WHERE id != ALL (SELECT product_id FROM order_items);
```

---

### 3.3. Подзапросы в FROM

```sql
-- Средняя сумма заказа по клиентам
SELECT customer_id, AVG(total) AS avg_order
FROM (
    SELECT customer_id, total
    FROM orders
    WHERE status = 'completed'
) AS completed_orders
GROUP BY customer_id;

-- Топ клиентов по сумме заказов
SELECT customer_id, SUM(total) AS total_spent
FROM orders
GROUP BY customer_id
ORDER BY total_spent DESC
LIMIT 10;
```

---

## 4. EXISTS и NOT EXISTS

**EXISTS** — проверяет существование строк.

```sql
-- Клиенты, у которых есть заказы
SELECT * FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);

-- Клиенты без заказов
SELECT * FROM customers c
WHERE NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);

-- Товары с отзывами
SELECT * FROM products p
WHERE EXISTS (
    SELECT 1 FROM reviews r WHERE r.product_id = p.id
);
```

**Преимущество EXISTS перед IN:**
- EXISTS останавливается после первой найденной строки
- IN проверяет все значения

---

## 5. ANY и ALL

**ANY** — сравнение с любым значением из списка.

```sql
-- Товары дороже хотя бы одного товара из категории 1
SELECT * FROM products
WHERE price > ANY (SELECT price FROM products WHERE category_id = 1);
```

**ALL** — сравнение со всеми значениями.

```sql
-- Товары дороже всех товаров из категории 1
SELECT * FROM products
WHERE price > ALL (SELECT price FROM products WHERE category_id = 1);

-- Эквивалентно MAX:
SELECT * FROM products
WHERE price > (SELECT MAX(price) FROM products WHERE category_id = 1);
```

---

## 6. Коррелированные подзапросы

**Коррелированный подзапрос** — подзапрос, который ссылается на внешнюю таблицу.

```sql
-- Товары дороже среднего по своей категории
SELECT p1.name, p1.price, p1.category_id
FROM products p1
WHERE p1.price > (
    SELECT AVG(p2.price)
    FROM products p2
    WHERE p2.category_id = p1.category_id  -- ссылка на внешний запрос
);

-- Сотрудники с зарплатой выше средней по отделу
SELECT e1.name, e1.salary, e1.department
FROM employees e1
WHERE e1.salary > (
    SELECT AVG(e2.salary)
    FROM employees e2
    WHERE e2.department = e1.department
);
```

**Производительность:** Коррелированные подзапросы могут быть медленными (выполняются для каждой строки).

---

## 7. CTE (Common Table Expressions)

**CTE** — именованный временный результат.

**Синтаксис:**
```sql
WITH cte_name AS (
    SELECT columns
    FROM table
    WHERE condition
)
SELECT * FROM cte_name;
```

**Примеры:**
```sql
-- Заказы с клиентами
WITH customer_orders AS (
    SELECT o.id, o.total, c.name AS customer_name
    FROM orders o
    JOIN customers c ON o.customer_id = c.id
)
SELECT * FROM customer_orders
WHERE total > 10000;

-- Рекурсивный CTE (иерархия)
WITH RECURSIVE category_tree AS (
    -- Базовый случай: корневые категории
    SELECT id, name, parent_id, 0 AS level
    FROM categories
    WHERE parent_id IS NULL
    
    UNION ALL
    
    -- Рекурсивный случай: дочерние категории
    SELECT c.id, c.name, c.parent_id, ct.level + 1
    FROM categories c
    JOIN category_tree ct ON c.parent_id = ct.id
)
SELECT * FROM category_tree;
```

---

## 8. Сравнение подходов

### INNER JOIN vs EXISTS

```sql
-- Вариант 1: INNER JOIN
SELECT DISTINCT c.*
FROM customers c
INNER JOIN orders o ON c.id = o.customer_id;

-- Вариант 2: EXISTS (часто быстрее)
SELECT c.*
FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);
```

### IN vs EXISTS

```sql
-- IN (проверяет все значения)
SELECT * FROM customers
WHERE id IN (SELECT customer_id FROM orders);

-- EXISTS (останавливается после первого совпадения)
SELECT * FROM customers
WHERE EXISTS (
    SELECT 1 FROM orders WHERE customer_id = customers.id
);
```

---

## 9. Практические примеры

### Пример 1: Отчёт по заказам

```sql
SELECT 
    c.name AS customer,
    c.city,
    COUNT(o.id) AS order_count,
    SUM(o.total) AS total_spent,
    AVG(o.total) AS avg_order
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name, c.city
HAVING COUNT(o.id) > 0
ORDER BY total_spent DESC;
```

### Пример 2: Топ товаров

```sql
SELECT 
    p.name AS product,
    c.name AS category,
    SUM(oi.quantity) AS total_sold,
    SUM(oi.quantity * oi.price) AS revenue
FROM products p
JOIN categories c ON p.category_id = c.id
JOIN order_items oi ON p.id = oi.product_id
GROUP BY p.id, p.name, c.name
ORDER BY revenue DESC
LIMIT 10;
```

### Пример 3: Анализ отзывов

```sql
SELECT 
    p.name AS product,
    AVG(r.rating) AS avg_rating,
    COUNT(r.id) AS review_count,
    MAX(r.created_at) AS last_review
FROM products p
LEFT JOIN reviews r ON p.id = r.product_id
GROUP BY p.id, p.name
HAVING COUNT(r.id) > 0
ORDER BY avg_rating DESC;
```

---

## 10. Шпаргалка по JOIN

| Тип JOIN | Описание | Когда использовать |
|----------|----------|-------------------|
| INNER JOIN | Только совпадения | Нужны данные из обеих таблиц |
| LEFT JOIN | Всё из левой + совпадения | Все записи из основной + связанные |
| RIGHT JOIN | Всё из правой + совпадения | Редко, можно заменить LEFT |
| FULL JOIN | Всё из обеих | Полное объединение |
| CROSS JOIN | Все комбинации | Генерация комбинаций |
| SELF JOIN | Соединение с собой | Иерархии, деревья |

---

## 11. Визуальное представление JOIN

```
INNER JOIN:          LEFT JOIN:           RIGHT JOIN:          FULL JOIN:
   ┌───┐                ┌───┐                ┌───┐                ┌───┐
   │ A │                │ A │                │ A │                │ A │
   └─┬─┘                └─┬─┘                └─┬─┘                └─┬─┘
     ∩                    │                    │                    │
   ┌─┴─┐                ┌─┴─┐                ┌─┴─┐                ┌─┴─┐
   │ B │                │ B │                │ B │                │ B │
   └───┘                └───┘                └───┘                └───┘
   [A∩B]                [A]                  [B]                  [A∪B]
```
