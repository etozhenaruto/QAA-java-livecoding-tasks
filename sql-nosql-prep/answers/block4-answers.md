# ✅ Ответы: Оконные функции

## Задачи 4.1 - 4.5

### 4.1
```sql
SELECT 
    customer_id,
    order_date,
    total,
    ROW_NUMBER() OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS order_num
FROM orders;
```

### 4.2
```sql
SELECT * FROM (
    SELECT 
        customer_id,
        order_date,
        total,
        ROW_NUMBER() OVER (
            PARTITION BY customer_id 
            ORDER BY order_date
        ) AS rn
    FROM orders
) ranked
WHERE rn = 1;
```

### 4.3
```sql
SELECT 
    name,
    salary,
    RANK() OVER (ORDER BY salary DESC) AS salary_rank
FROM employees;
```

### 4.4
```sql
SELECT 
    name,
    salary,
    NTILE(4) OVER (ORDER BY salary DESC) AS quartile
FROM employees;
```

### 4.5
```sql
SELECT * FROM (
    SELECT 
        customer_id,
        order_date,
        total,
        ROW_NUMBER() OVER (
            PARTITION BY customer_id 
            ORDER BY total DESC
        ) AS rn
    FROM orders
) ranked
WHERE rn <= 3;
```

---

## Задачи 4.6 - 4.10

### 4.6
```sql
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
```

### 4.7
```sql
SELECT 
    customer_id,
    order_date,
    total,
    LEAD(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS next_order,
    LEAD(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) - total AS diff
FROM orders;
```

### 4.8
```sql
SELECT 
    customer_id,
    order_date,
    total,
    LAG(total) OVER (PARTITION BY customer_id ORDER BY order_date) AS prev,
    ROUND(
        (total - LAG(total) OVER (PARTITION BY customer_id ORDER BY order_date)) 
        / NULLIF(LAG(total) OVER (PARTITION BY customer_id ORDER BY order_date), 0) * 100,
        2
    ) AS change_percent
FROM orders;
```

### 4.9
```sql
SELECT 
    customer_id,
    order_date,
    total,
    LAG(order_date) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS prev_order_date
FROM orders;
```

### 4.10
```sql
SELECT 
    customer_id,
    order_date,
    total,
    FIRST_VALUE(order_date) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS first_order_date,
    LAST_VALUE(order_date) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
        ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
    ) AS last_order_date
FROM orders;
```

---

## Задачи 4.11 - 4.15

### 4.11
```sql
SELECT 
    order_date,
    total,
    SUM(total) OVER (ORDER BY order_date) AS running_total
FROM orders;
```

### 4.12
```sql
SELECT 
    p.name,
    p.price,
    p.category_id,
    AVG(p.price) OVER (PARTITION BY p.category_id) AS avg_category_price
FROM products p;
```

### 4.13
```sql
SELECT 
    customer_id,
    order_date,
    total,
    SUM(total) OVER (
        PARTITION BY customer_id 
        ORDER BY order_date
    ) AS running_total
FROM orders;
```

### 4.14
```sql
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
```

### 4.15
```sql
SELECT 
    o.id,
    o.total,
    SUM(o.total) OVER () AS total_sales,
    ROUND(o.total / SUM(o.total) OVER () * 100, 2) AS percent_of_total
FROM orders o;
```
