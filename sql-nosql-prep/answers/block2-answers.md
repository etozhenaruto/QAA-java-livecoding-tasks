# ✅ Ответы: JOIN и подзапросы

## Задачи 2.1 - 2.5

### 2.1
```sql
SELECT o.id, o.order_date, o.total, c.name, c.email
FROM orders o
INNER JOIN customers c ON o.customer_id = c.id;
```

### 2.2
```sql
SELECT p.name AS product, c.name AS category
FROM products p
INNER JOIN categories c ON p.category_id = c.id;
```

### 2.3
```sql
SELECT o.id, o.order_date, o.total, c.name
FROM orders o
INNER JOIN customers c ON o.customer_id = c.id
WHERE o.status = 'completed';
```

### 2.4
```sql
SELECT oi.quantity, oi.price, p.name AS product
FROM order_items oi
INNER JOIN products p ON oi.product_id = p.id;
```

### 2.5
```sql
SELECT r.rating, r.comment, p.name AS product, c.name AS customer
FROM reviews r
INNER JOIN products p ON r.product_id = p.id
INNER JOIN customers c ON r.customer_id = c.id;
```

---

## Задачи 2.6 - 2.10

### 2.6
```sql
SELECT c.name, c.email, o.id AS order_id, o.total
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id;
```

### 2.7
```sql
SELECT c.name, c.email
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;
```

### 2.8
```sql
SELECT p.name AS product, r.rating, r.comment
FROM products p
LEFT JOIN reviews r ON p.id = r.product_id;
```

### 2.9
```sql
SELECT c.name AS category, p.name AS product
FROM categories c
LEFT JOIN products p ON c.id = p.category_id;
```

### 2.10
```sql
SELECT e.name AS employee, m.name AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

---

## Задачи 2.11 - 2.15

### 2.11
```sql
SELECT c.name, COUNT(o.id) AS order_count
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name;
```

### 2.12
```sql
SELECT c.name, SUM(o.total) AS total_spent
FROM customers c
INNER JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name;
```

### 2.13
```sql
SELECT p.name, SUM(oi.quantity * oi.price) AS revenue
FROM products p
INNER JOIN order_items oi ON p.id = oi.product_id
GROUP BY p.id, p.name;
```

### 2.14
```sql
SELECT c.name, COUNT(p.id) AS product_count
FROM categories c
LEFT JOIN products p ON c.id = p.category_id
GROUP BY c.id, c.name;
```

### 2.15
```sql
SELECT p.name, AVG(r.rating) AS avg_rating
FROM products p
LEFT JOIN reviews r ON p.id = r.product_id
GROUP BY p.id, p.name;
```

---

## Задачи 2.16 - 2.20

### 2.16
```sql
SELECT * FROM products
WHERE price > (SELECT AVG(price) FROM products);
```

### 2.17
```sql
SELECT * FROM customers
WHERE id IN (SELECT DISTINCT customer_id FROM orders);
```

### 2.18
```sql
SELECT * FROM products
WHERE id NOT IN (SELECT DISTINCT product_id FROM order_items);
```

### 2.19
```sql
SELECT * FROM orders
WHERE total > (SELECT AVG(total) FROM orders);
```

### 2.20
```sql
SELECT e1.name, e1.salary, e1.department
FROM employees e1
WHERE e1.salary > (
    SELECT AVG(e2.salary)
    FROM employees e2
    WHERE e2.department = e1.department
);
```

---

## Задачи 2.21 - 2.25

### 2.21
```sql
SELECT c.*
FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o 
    WHERE o.customer_id = c.id AND o.status = 'completed'
);
```

### 2.22
```sql
SELECT p.*
FROM products p
WHERE EXISTS (
    SELECT 1 FROM reviews r WHERE r.product_id = p.id
);
```

### 2.23
```sql
SELECT c.*
FROM categories c
WHERE EXISTS (
    SELECT 1 FROM products p WHERE p.category_id = c.id
);
```

### 2.24
```sql
SELECT e.*
FROM employees e
WHERE EXISTS (
    SELECT 1 FROM employees m WHERE m.manager_id = e.id
);
```

### 2.25
```sql
SELECT DISTINCT p.*
FROM products p
INNER JOIN order_items oi ON p.id = oi.product_id
INNER JOIN orders o ON oi.order_id = o.id
INNER JOIN customers c ON o.customer_id = c.id
WHERE c.city = 'Москва';
```

---

## Задачи 2.26 - 2.30

### 2.26
```sql
WITH large_orders AS (
    SELECT o.*, c.name AS customer_name
    FROM orders o
    JOIN customers c ON o.customer_id = c.id
)
SELECT * FROM large_orders WHERE total > 10000;
```

### 2.27
```sql
WITH RECURSIVE category_tree AS (
    SELECT id, name, parent_id, 0 AS level
    FROM categories WHERE parent_id IS NULL
    UNION ALL
    SELECT c.id, c.name, c.parent_id, ct.level + 1
    FROM categories c
    JOIN category_tree ct ON c.parent_id = ct.id
)
SELECT * FROM category_tree ORDER BY level, name;
```

### 2.28
```sql
SELECT c.name, SUM(o.total) AS total_spent
FROM customers c
JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.name
ORDER BY total_spent DESC
LIMIT 3;
```

### 2.29
```sql
SELECT p1.name, p1.price, p1.category_id
FROM products p1
WHERE p1.price = (
    SELECT MAX(p2.price)
    FROM products p2
    WHERE p2.category_id = p1.category_id
);
```

### 2.30
```sql
SELECT department, 
       AVG(salary) AS avg_salary,
       COUNT(*) AS employee_count
FROM employees
GROUP BY department;
```
