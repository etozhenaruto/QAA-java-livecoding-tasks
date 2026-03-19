# 📚 Теория: Оптимизация и индексы

## 1. Индексы

**Индекс** — структура данных для ускорения поиска в таблице.

### Типы индексов

#### B-Tree (по умолчанию)

```sql
-- Создание индекса
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_orders_customer_date ON orders(customer_id, order_date);

-- Уникальный индекс
CREATE UNIQUE INDEX idx_customers_email_unique ON customers(email);

-- Составной индекс
CREATE INDEX idx_products_category_price ON products(category_id, price);
```

**Когда использовать:**
- Поиск по точному значению (=)
- Поиск по диапазону (>, <, BETWEEN)
- Сортировка (ORDER BY)

#### Hash

```sql
-- Hash индекс (только для =)
CREATE INDEX idx_hash_email ON customers USING HASH(email);
```

**Когда использовать:**
- Только точное совпадение (=)
- Не подходит для диапазонов

#### Composite (составной)

```sql
-- Составной индекс (порядок важен!)
CREATE INDEX idx_orders_customer_status ON orders(customer_id, status);

-- Правило: левый префикс
-- Работает для:
WHERE customer_id = ?                           -- ✓
WHERE customer_id = ? AND status = ?            -- ✓
-- Не работает для:
WHERE status = ?                                -- ✗
```

#### Covering Index

```sql
-- Индекс покрывает запрос (все данные в индексе)
CREATE INDEX idx_covering ON orders(customer_id, total, order_date);

-- Запрос использует только индекс:
SELECT customer_id, total FROM orders WHERE customer_id = 1;
```

---

## 2. EXPLAIN и план выполнения

**EXPLAIN** показывает план выполнения запроса.

```sql
-- Базовый EXPLAIN
EXPLAIN SELECT * FROM customers WHERE email = 'test@email.com';

-- EXPLAIN ANALYZE (выполняет запрос)
EXPLAIN ANALYZE SELECT * FROM customers WHERE email = 'test@email.com';

-- EXPLAIN с буфером
EXPLAIN (ANALYZE, BUFFERS) SELECT * FROM customers WHERE city = 'Москва';
```

### Чтение плана выполнения

```
Seq Scan on customers  (cost=0.00..35.50 rows=10 width=100)
  Filter: (city = 'Москва'::text)

Index Scan using idx_customers_city on customers  (cost=0.28..8.29 rows=10 width=100)
  Index Cond: (city = 'Москва'::text)
```

**Параметры:**
- `Seq Scan` — последовательное сканирование (медленно)
- `Index Scan` — сканирование индекса (быстро)
- `Index Only Scan` — только индекс (очень быстро)
- `cost` — оценка стоимости (первое — запуск, второе — общая)
- `rows` — оценка количества строк
- `width` — средний размер строки

---

## 3. Типы сканирования

### Seq Scan (последовательное)

```sql
-- Полное сканирование таблицы
EXPLAIN SELECT * FROM customers;
-- Seq Scan on customers
```

**Когда используется:**
- Маленькие таблицы
- Нет подходящего индекса
- Выбирается большая часть таблицы

### Index Scan

```sql
-- Сканирование индекса
EXPLAIN SELECT * FROM customers WHERE email = 'test@email.com';
-- Index Scan using idx_customers_email
```

### Index Only Scan

```sql
-- Только индекс (все данные в индексе)
CREATE INDEX idx_covering ON orders(customer_id, id);
EXPLAIN SELECT customer_id, id FROM orders WHERE customer_id = 1;
-- Index Only Scan using idx_covering
```

### Bitmap Index Scan

```sql
-- Комбинация индексов
EXPLAIN SELECT * FROM orders 
WHERE customer_id = 1 AND status = 'completed';
-- Bitmap Index Scan
```

---

## 4. Оптимизация запросов

### Правило 1: SELECT только нужные столбцы

```sql
-- ПЛОХО
SELECT * FROM customers;

-- ХОРОШО
SELECT id, name, email FROM customers;
```

### Правило 2: WHERE вместо HAVING

```sql
-- ПЛОХО
SELECT customer_id, COUNT(*) 
FROM orders 
GROUP BY customer_id 
HAVING status = 'completed';

-- ХОРОШО
SELECT customer_id, COUNT(*) 
FROM orders 
WHERE status = 'completed'
GROUP BY customer_id;
```

### Правило 3: Избегать функций в WHERE

```sql
-- ПЛОХО (не использует индекс)
SELECT * FROM orders WHERE DATE(order_date) = '2024-06-01';

-- ХОРОШО (использует индекс)
SELECT * FROM orders 
WHERE order_date >= '2024-06-01' 
  AND order_date < '2024-06-02';
```

### Правило 4: Избегать LIKE с ведущим %

```sql
-- ПЛОХО (не использует индекс)
SELECT * FROM customers WHERE email LIKE '%@gmail.com';

-- ХОРОШО (использует индекс)
SELECT * FROM customers WHERE email LIKE 'ivan%';
```

### Правило 5: LIMIT для больших результатов

```sql
-- ПЛОХО (возвращает всё)
SELECT * FROM orders;

-- ХОРОШО (ограничивает)
SELECT * FROM orders LIMIT 100;
```

### Правило 6: EXISTS вместо IN для подзапросов

```sql
-- ПЛОХО (медленно для больших таблиц)
SELECT * FROM customers 
WHERE id IN (SELECT customer_id FROM orders);

-- ХОРОШО (быстрее)
SELECT * FROM customers 
WHERE EXISTS (SELECT 1 FROM orders WHERE customer_id = customers.id);
```

---

## 5. N+1 проблема

**Проблема:** Выполнение N+1 запросов вместо одного.

```python
# ПЛОХО: N+1 запросов
customers = db.query("SELECT * FROM customers")
for customer in customers:
    orders = db.query(f"SELECT * FROM orders WHERE customer_id = {customer.id}")
    # N запросов!

# ХОРОШО: 1 запрос с JOIN
result = db.query("""
    SELECT c.*, o.* 
    FROM customers c
    LEFT JOIN orders o ON c.id = o.customer_id
""")
```

**Решение в SQL:**
```sql
-- Вместо N запросов — один с JOIN
SELECT c.id, c.name, o.id AS order_id, o.total
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id;
```

---

## 6. Нормализация и денормализация

### Нормализация

**Разделение данных на таблицы:**

```sql
-- Нормализованная схема
customers (id, name, email, city_id)
cities (id, name, country)

-- JOIN для получения данных
SELECT c.name, ci.name, ci.country
FROM customers c
JOIN cities ci ON c.city_id = ci.id;
```

**Преимущества:**
- Меньше дублирования
- Целостность данных
- Легче обновлять

### Денормализация

**Добавление дублирующих данных:**

```sql
-- Денормализованная схема
customers (id, name, email, city, country)

-- Быстрый SELECT без JOIN
SELECT name, city, country FROM customers;
```

**Преимущества:**
- Быстрее чтение
- Меньше JOIN
- Проще запросы

**Когда денормализовать:**
- Чтение чаще записи
- Важна производительность чтения
- Данные редко меняются

---

## 7. Транзакции и ACID

### ACID свойства

- **Atomicity (Атомарность)** — всё или ничего
- **Consistency (Согласованность)** — данные валидны
- **Isolation (Изоляция)** — параллельные транзакции не мешают
- **Durability (Долговечность)** — сохранено навсегда

### Управление транзакциями

```sql
BEGIN;

UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

COMMIT;  -- или ROLLBACK;

-- Точка сохранения
BEGIN;
SAVEPOINT sp1;
-- операции
ROLLBACK TO sp1;
COMMIT;
```

---

## 8. Уровни изоляции

| Уровень | Dirty Read | Non-repeatable Read | Phantom Read |
|---------|------------|---------------------|--------------|
| READ UNCOMMITTED | Возможно | Возможно | Возможно |
| READ COMMITTED | Нет | Возможно | Возможно |
| REPEATABLE READ | Нет | Нет | Возможно |
| SERIALIZABLE | Нет | Нет | Нет |

```sql
-- Установка уровня изоляции
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- операции
COMMIT;
```

---

## 9. Блокировки

```sql
-- Явная блокировка строк
SELECT * FROM accounts WHERE id = 1 FOR UPDATE;
SELECT * FROM accounts WHERE id = 1 FOR SHARE;

-- Блокировка таблицы
LOCK TABLE accounts IN EXCLUSIVE MODE;
```

---

## 10. Практические примеры оптимизации

### Пример 1: Добавление индекса

```sql
-- Медленный запрос
EXPLAIN ANALYZE 
SELECT * FROM orders WHERE customer_id = 1;
-- Seq Scan

-- Добавляем индекс
CREATE INDEX idx_orders_customer ON orders(customer_id);

-- Быстрый запрос
EXPLAIN ANALYZE 
SELECT * FROM orders WHERE customer_id = 1;
-- Index Scan
```

### Пример 2: Оптимизация COUNT

```sql
-- Медленно для больших таблиц
SELECT COUNT(*) FROM orders WHERE status = 'completed';

-- Быстрее с частичным индексом
CREATE INDEX idx_orders_completed ON orders(id) WHERE status = 'completed';
SELECT COUNT(*) FROM orders WHERE status = 'completed';
```

### Пример 3: Partitioning

```sql
-- Разделение большой таблицы по датам
CREATE TABLE orders_2024 PARTITION OF orders
FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');

CREATE TABLE orders_2025 PARTITION OF orders
FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');
```

---

## 11. Шпаргалка по оптимизации

| Проблема | Решение |
|----------|---------|
| Медленный WHERE | Добавить индекс |
| Медленный JOIN | Индекс по ключам JOIN |
| Медленный ORDER BY | Индекс по сортируемым столбцам |
| N+1 запросов | Использовать JOIN |
| Много данных | Partitioning |
| Частое чтение | Денормализация, кэширование |
| Блокировки | Оптимизировать транзакции |

---

## 12. Команды обслуживания

```sql
-- Анализ статистики
ANALYZE customers;

-- Перестроение индекса
REINDEX TABLE customers;

-- Очистка (PostgreSQL)
VACUUM customers;
VACUUM FULL customers;  -- полная перестройка

-- Обновление статистики
ANALYZE;  -- все таблицы
```
