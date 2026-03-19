# 🎯 Вопросы для собеседования: JOIN и подзапросы

## Базовые вопросы (1-10)

### 1. Что такое JOIN и какие типы вы знаете?

**Ответ:**
JOIN — объединение таблиц по связанному столбцу.

**Типы:**
- INNER JOIN
- LEFT JOIN
- RIGHT JOIN
- FULL JOIN
- CROSS JOIN
- SELF JOIN

---

### 2. В чём разница между INNER JOIN и LEFT JOIN?

**Ответ:**
- INNER JOIN — только совпадения в обеих таблицах
- LEFT JOIN — всё из левой + совпадения из правой (или NULL)

---

### 3. Что такое CROSS JOIN?

**Ответ:**
Декартово произведение — все комбинации строк:

```sql
SELECT * FROM table1 CROSS JOIN table2;
-- Если 10 и 5 строк → 50 результатов
```

---

### 4. Что такое SELF JOIN?

**Ответ:**
Соединение таблицы с самой собой:

```sql
SELECT e.name, m.name AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

---

### 5. В чём разница между WHERE и ON в JOIN?

**Ответ:**
- ON — условие соединения
- WHERE — фильтрация после соединения

```sql
SELECT * FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id AND o.status = 'completed'
WHERE c.city = 'Москва';
```

---

### 6. Что такое подзапрос?

**Ответ:**
Запрос внутри другого запроса.

---

### 7. Какие типы подзапросов вы знаете?

**Ответ:**
- В SELECT
- В WHERE
- В FROM
- Коррелированные

---

### 8. В чём разница между IN и EXISTS?

**Ответ:**
- IN — проверяет все значения
- EXISTS — останавливается после первого совпадения (быстрее)

---

### 9. Что такое коррелированный подзапрос?

**Ответ:**
Подзапрос, ссылающийся на внешнюю таблицу:

```sql
SELECT p.* FROM products p
WHERE p.price > (
    SELECT AVG(price) FROM products WHERE category_id = p.category_id
);
```

---

### 10. Что делает EXISTS?

**Ответ:**
Проверяет существование строк:

```sql
WHERE EXISTS (SELECT 1 FROM orders WHERE customer_id = customers.id)
```

---

## Продвинутые вопросы (11-20)

### 11. В чём разница между = ANY и = ALL?

**Ответ:**
- = ANY — равно любому значению
- = ALL — равно всем значениям (редко используется)

---

### 12. Что такое CTE?

**Ответ:**
Common Table Expression — именованный временный результат:

```sql
WITH cte AS (SELECT ...)
SELECT * FROM cte;
```

---

### 13. Когда использовать CTE вместо подзапроса?

**Ответ:**
- CTE читаемее
- CTE можно переиспользовать
- CTE для рекурсии

---

### 14. Что такое рекурсивный CTE?

**Ответ:**
CTE который ссылается на себя:

```sql
WITH RECURSIVE tree AS (
    SELECT * FROM categories WHERE parent_id IS NULL
    UNION ALL
    SELECT c.* FROM categories c
    JOIN tree t ON c.parent_id = t.id
)
```

---

### 15. Как найти дубликаты?

**Ответ:**
```sql
SELECT email, COUNT(*)
FROM customers
GROUP BY email
HAVING COUNT(*) > 1;
```

---

### 16. Как найти записи без связанных?

**Ответ:**
```sql
-- LEFT JOIN с проверкой на NULL
SELECT c.* FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;

-- Или NOT EXISTS
SELECT c.* FROM customers c
WHERE NOT EXISTS (SELECT 1 FROM orders WHERE customer_id = c.id);
```

---

### 17. Что лучше JOIN или подзапрос?

**Ответ:**
Обычно JOIN быстрее, но зависит от БД и данных.

---

### 18. В чём разница между RANK и DENSE_RANK?

**Ответ:**
- RANK — пропускает номера при равных значениях
- DENSE_RANK — не пропускает

---

### 19. Что такое NATURAL JOIN?

**Ответ:**
JOIN по всем столбцам с одинаковыми именами (не рекомендуется).

---

### 20. Как оптимизировать JOIN?

**Ответ:**
- Индексы по ключам JOIN
- SELECT только нужные столбцы
- WHERE для фильтрации до JOIN
