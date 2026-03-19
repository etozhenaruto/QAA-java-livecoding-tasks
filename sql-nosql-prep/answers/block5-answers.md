# ✅ Ответы: Оптимизация и индексы

## Задачи 5.1 - 5.3

### 5.1
```sql
CREATE INDEX idx_customers_email ON customers(email);
```

### 5.2
```sql
CREATE INDEX idx_orders_customer_date ON orders(customer_id, order_date);
```

### 5.3
```sql
CREATE UNIQUE INDEX idx_customers_email_unique ON customers(email);
```

---

## Задачи 5.4 - 5.6

### 5.4
```sql
EXPLAIN SELECT * FROM customers WHERE email = 'test@email.com';
```

### 5.5
```sql
-- Без индекса
EXPLAIN SELECT * FROM customers WHERE city = 'Москва';
-- Seq Scan

-- С индексом
CREATE INDEX idx_customers_city ON customers(city);
EXPLAIN SELECT * FROM customers WHERE city = 'Москва';
-- Index Scan
```

### 5.6
```sql
EXPLAIN ANALYZE SELECT * FROM customers WHERE email = 'test@email.com';
-- Смотрим: Index Scan или Seq Scan
```

---

## Задачи 5.7 - 5.10

### 5.7
```sql
-- ПЛОХО
SELECT * FROM orders WHERE DATE(order_date) >= CURRENT_DATE - 7;

-- ХОРОШО (использует индекс)
SELECT * FROM orders 
WHERE order_date >= CURRENT_DATE - INTERVAL '7 days';
```

### 5.8
```sql
-- ПЛОХО (не использует индекс)
SELECT * FROM customers WHERE name LIKE '%Иван%';

-- ХОРОШО (использует индекс, если есть)
SELECT * FROM customers WHERE name LIKE 'Иван%';

-- Или полнотекстовый поиск (PostgreSQL)
SELECT * FROM customers WHERE to_tsvector(name) @@ to_tsquery('Иван');
```

### 5.9
```sql
-- Один запрос с JOIN вместо N+1
SELECT c.id, c.name, o.id AS order_id, o.total
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id;
```

### 5.10
```sql
-- Создать частичный индекс
CREATE INDEX idx_orders_completed ON orders(id) WHERE status = 'completed';

-- Запрос использует индекс
SELECT COUNT(*) FROM orders WHERE status = 'completed';
```
