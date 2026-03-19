# 🎯 Вопросы для собеседования: Агрегация и группировка

## Вопросы (1-15)

### 1. Что такое GROUP BY?

**Ответ:**
Группирует строки с одинаковыми значениями:

```sql
SELECT customer_id, COUNT(*) 
FROM orders 
GROUP BY customer_id;
```

---

### 2. В чём разница между WHERE и HAVING?

**Ответ:**
- WHERE — до группировки
- HAVING — после группировки

---

### 3. Какие агрегатные функции вы знаете?

**Ответ:**
COUNT, SUM, AVG, MIN, MAX, STRING_AGG

---

### 4. Что делает COUNT(DISTINCT column)?

**Ответ:**
Считает уникальные не-NULL значения.

---

### 5. Можно ли использовать агрегатные функции в WHERE?

**Ответ:**
Нет, только в HAVING:

```sql
-- Неправильно:
WHERE COUNT(*) > 1

-- Правильно:
HAVING COUNT(*) > 1
```

---

### 6. Что такое STRING_AGG?

**Ответ:**
Объединяет строки в группе:

```sql
SELECT city, STRING_AGG(name, ', ') 
FROM customers 
GROUP BY city;
```

---

### 7. Что такое ROLLUP?

**Ответ:**
Иерархическая группировка с итогами:

```sql
GROUP BY ROLLUP(category, product)
```

---

### 8. В чём разница между ROLLUP и CUBE?

**Ответ:**
- ROLLUP — иерархия (a,b → a,b; a; total)
- CUBE — все комбинации

---

### 9. Что такое GROUPING SETS?

**Ответ:**
Конкретные наборы группировки:

```sql
GROUP BY GROUPING SETS ((a,b), (a), ())
```

---

### 10. Что делает GROUPING?

**Ответ:**
Возвращает 1 для итоговых строк (NULL от ROLLUP).

---

### 11. Как отфильтровать группы после агрегации?

**Ответ:**
HAVING:

```sql
HAVING COUNT(*) > 1
```

---

### 12. В чём разница между COUNT(*) и COUNT(1)?

**Ответ:**
Нет разницы, оба считают все строки.

---

### 13. Что такое FILTER в агрегации?

**Ответ:**
Условная агрегация (PostgreSQL):

```sql
COUNT(*) FILTER (WHERE status = 'completed')
```

---

### 14. Как посчитать нарастающий итог?

**Ответ:**
Оконная функция:

```sql
SUM(total) OVER (ORDER BY date)
```

---

### 15. Что такое медиана и как её найти?

**Ответ:**
Среднее значение в отсортированном наборе:

```sql
-- PostgreSQL
SELECT PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY salary)
FROM employees;
```
