# ✅ Ответы: Агрегация и группировка

## Задачи 3.1 - 3.5

### 3.1
```sql
SELECT COUNT(*) FROM orders;
```

### 3.2
```sql
SELECT SUM(total) FROM orders;
```

### 3.3
```sql
SELECT AVG(total) FROM orders;
```

### 3.4
```sql
SELECT MIN(total), MAX(total) FROM orders;
```

### 3.5
```sql
SELECT COUNT(DISTINCT customer_id) FROM orders;
```

---

## Задачи 3.6 - 3.10

### 3.6
```sql
SELECT customer_id, COUNT(*) AS order_count
FROM orders
GROUP BY customer_id;
```

### 3.7
```sql
SELECT customer_id, SUM(total) AS total_spent
FROM orders
GROUP BY customer_id;
```

### 3.8
```sql
SELECT category_id, COUNT(*) AS product_count
FROM products
GROUP BY category_id;
```

### 3.9
```sql
SELECT category_id, AVG(price) AS avg_price
FROM products
GROUP BY category_id;
```

### 3.10
```sql
SELECT product_id, COUNT(*) AS review_count
FROM reviews
GROUP BY product_id;
```

---

## Задачи 3.11 - 3.15

### 3.11
```sql
SELECT customer_id, COUNT(*) AS order_count
FROM orders
GROUP BY customer_id
HAVING COUNT(*) > 2;
```

### 3.12
```sql
SELECT product_id, COUNT(*) AS times_ordered
FROM order_items
GROUP BY product_id
HAVING COUNT(*) > 1;
```

### 3.13
```sql
SELECT category_id, COUNT(*) AS product_count
FROM products
GROUP BY category_id
HAVING COUNT(*) > 2;
```

### 3.14
```sql
SELECT customer_id, SUM(total) AS total_spent
FROM orders
GROUP BY customer_id
HAVING SUM(total) > 100000;
```

### 3.15
```sql
SELECT product_id, AVG(rating) AS avg_rating
FROM reviews
GROUP BY product_id
HAVING AVG(rating) > 4;
```

---

## Задачи 3.16 - 3.20

### 3.16
```sql
-- PostgreSQL
SELECT city, STRING_AGG(name, ', ') AS customers
FROM customers
GROUP BY city;

-- MySQL
SELECT city, GROUP_CONCAT(name SEPARATOR ', ') AS customers
FROM customers
GROUP BY city;
```

### 3.17
```sql
SELECT status, COUNT(*) AS order_count, SUM(total) AS total_revenue
FROM orders
GROUP BY status;
```

### 3.18
```sql
SELECT 
    EXTRACT(YEAR FROM order_date) AS year,
    EXTRACT(MONTH FROM order_date) AS month,
    COUNT(*) AS order_count
FROM orders
GROUP BY EXTRACT(YEAR FROM order_date), EXTRACT(MONTH FROM order_date)
ORDER BY year, month;
```

### 3.19
```sql
SELECT 
    customer_id,
    COUNT(*) AS frequency,
    SUM(total) AS monetary,
    MAX(order_date) AS last_order,
    CURRENT_DATE - MAX(order_date) AS recency_days
FROM orders
WHERE status = 'completed'
GROUP BY customer_id;
```

### 3.20
```sql
SELECT 
    c.name AS category,
    p.name AS product,
    SUM(oi.quantity * oi.price) AS revenue
FROM products p
JOIN categories c ON p.category_id = c.id
JOIN order_items oi ON p.id = oi.product_id
GROUP BY ROLLUP(c.name, p.name)
ORDER BY c.name, p.name;
```
