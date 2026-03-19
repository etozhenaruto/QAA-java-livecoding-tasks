# ✅ Ответы: Основы SQL

## Задачи 1.1 - 1.5

### 1.1
```sql
SELECT * FROM customers;
```

### 1.2
```sql
SELECT name, email, city FROM customers;
```

### 1.3
```sql
SELECT name, price FROM products;
```

### 1.4
```sql
SELECT DISTINCT city FROM customers;
```

### 1.5
```sql
SELECT * FROM products WHERE price > 5000;
```

---

## Задачи 1.6 - 1.10

### 1.6
```sql
SELECT * FROM customers WHERE city = 'Москва';
```

### 1.7
```sql
SELECT * FROM products WHERE price BETWEEN 1000 AND 10000;
```

### 1.8
```sql
SELECT * FROM products WHERE category_id <> 1;
```

### 1.9
```sql
SELECT * FROM customers WHERE email LIKE '%@email.com';
```

### 1.10
```sql
SELECT * FROM products WHERE name LIKE 'S%';
```

---

## Задачи 1.11 - 1.15

### 1.11
```sql
SELECT * FROM products ORDER BY price DESC LIMIT 5;
```

### 1.12
```sql
SELECT name, price FROM products ORDER BY price ASC LIMIT 3;
```

### 1.13
```sql
SELECT * FROM customers ORDER BY name ASC;
```

### 1.14
```sql
SELECT * FROM products ORDER BY price DESC LIMIT 10 OFFSET 5;
```

### 1.15
```sql
SELECT * FROM products LIMIT 10 OFFSET 10;
-- Страница 2: OFFSET = (2-1) * 10 = 10
```

---

## Задачи 1.16 - 1.20

### 1.16
```sql
SELECT name, COALESCE(city, 'Город не указан') AS city FROM customers;
```

### 1.17
```sql
SELECT name, price * 0.9 AS discounted_price FROM products;
```

### 1.18
```sql
SELECT UPPER(name) AS name_upper FROM products;
```

### 1.19
```sql
SELECT name, SUBSTRING(name FROM 1 FOR 5) AS short_name FROM products;
```

### 1.20
```sql
SELECT name || ' (' || price || ')' AS info FROM products;
-- Или:
SELECT CONCAT(name, ' (', price, ')') AS info FROM products;
```

---

## Задачи 1.21 - 1.25

### 1.21
```sql
SELECT name FROM products
UNION
SELECT name FROM categories;
```

### 1.22
```sql
SELECT name, stock,
    CASE 
        WHEN stock > 100 THEN 'Много'
        WHEN stock >= 50 THEN 'Средне'
        ELSE 'Мало'
    END AS stock_status
FROM products;
```

### 1.23
```sql
SELECT id,
    CASE status
        WHEN 'pending' THEN 'В обработке'
        WHEN 'completed' THEN 'Выполнен'
        WHEN 'cancelled' THEN 'Отменён'
        ELSE status
    END AS status_ru
FROM orders;
```

### 1.24
```sql
SELECT * FROM products WHERE price > 5000 OR stock < 30;
```

### 1.25
```sql
SELECT * FROM customers 
WHERE city NOT IN ('Москва', 'Санкт-Петербург');
-- Или:
WHERE city <> 'Москва' AND city <> 'Санкт-Петербург';
```

---

## Задачи 1.26 - 1.30

### 1.26
```sql
SELECT * FROM orders 
WHERE order_date >= '2024-06-01' AND order_date < '2024-07-01';
-- Или:
WHERE DATE_TRUNC('month', order_date) = '2024-06-01';
```

### 1.27
```sql
SELECT EXTRACT(YEAR FROM order_date) AS year,
       EXTRACT(MONTH FROM order_date) AS month
FROM orders;
```

### 1.28
```sql
SELECT * FROM orders ORDER BY order_date DESC;
```

### 1.29
```sql
SELECT TO_CHAR(order_date, 'DD.MM.YYYY') AS date_formatted FROM orders;
```

### 1.30
```sql
SELECT * FROM products 
WHERE EXTRACT(YEAR FROM created_at) = 2024;
```

---

## Задачи 1.31 - 1.35

### 1.31
```sql
INSERT INTO customers (name, email, city)
VALUES ('Тест Тестов', 'test@test.com', 'Москва');
```

### 1.32
```sql
INSERT INTO products (name, price) VALUES
('Товар 1', 100),
('Товар 2', 200),
('Товар 3', 300);
```

### 1.33
```sql
UPDATE products SET price = price * 1.05;
```

### 1.34
```sql
UPDATE customers SET city = 'Нижний Новгород' WHERE id = 1;
```

### 1.35
```sql
DELETE FROM orders WHERE status = 'cancelled';
```
