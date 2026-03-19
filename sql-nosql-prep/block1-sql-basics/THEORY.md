# 📚 Теория: Основы SQL

## 1. Что такое SQL?

**SQL (Structured Query Language)** — язык запросов для работы с реляционными базами данных.

**Основные операции:**
- **DDL (Data Definition Language)** — создание структуры
- **DML (Data Manipulation Language)** — работа с данными
- **DCL (Data Control Language)** — управление доступом
- **TCL (Transaction Control Language)** — управление транзакциями

---

## 2. Основные операторы SQL

### 2.1. SELECT — выборка данных

**Синтаксис:**
```sql
SELECT column1, column2, ...
FROM table_name
WHERE condition
ORDER BY column
LIMIT n OFFSET m;
```

**Примеры:**
```sql
-- Выбрать все столбцы
SELECT * FROM customers;

-- Выбрать конкретные столбцы
SELECT name, email FROM customers;

-- С псевдонимами
SELECT name AS customer_name, email AS customer_email
FROM customers;

-- С выражениями
SELECT price, price * 0.9 AS discounted_price
FROM products;

-- Уникальные значения
SELECT DISTINCT city FROM customers;
```

---

### 2.2. WHERE — фильтрация

**Операторы сравнения:**
```sql
=    -- Равно
<>   -- Не равно (!= тоже работает)
>    -- Больше
<    -- Меньше
>=   -- Больше или равно
<=   -- Меньше или равно
```

**Логические операторы:**
```sql
AND  -- И
OR   -- ИЛИ
NOT  -- НЕ
```

**Примеры:**
```sql
-- Простое условие
SELECT * FROM products WHERE price > 1000;

-- Несколько условий
SELECT * FROM products 
WHERE price > 1000 AND stock > 50;

-- Диапазон
SELECT * FROM products 
WHERE price BETWEEN 1000 AND 10000;

-- Список значений
SELECT * FROM customers 
WHERE city IN ('Москва', 'Санкт-Петербург');

-- Проверка на NULL
SELECT * FROM customers 
WHERE city IS NULL;

SELECT * FROM customers 
WHERE city IS NOT NULL;

-- LIKE для паттернов
SELECT * FROM customers 
WHERE email LIKE '%@gmail.com';

SELECT * FROM products 
WHERE name LIKE 'iPhone%';
```

---

### 2.3. LIKE — поиск по шаблону

**Спецсимволы:**
- `%` — любое количество любых символов
- `_` — ровно один любой символ

**Примеры:**
```sql
-- Начинается на 'A'
SELECT * FROM products WHERE name LIKE 'A%';

-- Заканчивается на 's'
SELECT * FROM products WHERE name LIKE '%s';

-- Содержит 'phone'
SELECT * FROM products WHERE name LIKE '%phone%';

-- Второй символ 'p'
SELECT * FROM products WHERE name LIKE '_p%';

-- Регулярные выражения (PostgreSQL)
SELECT * FROM customers WHERE email ~ '^[a-z]+@';
```

---

### 2.4. ORDER BY — сортировка

**Синтаксис:**
```sql
SELECT * FROM products
ORDER BY price DESC, name ASC;
```

**Примеры:**
```sql
-- По возрастанию (по умолчанию)
SELECT * FROM products ORDER BY price;

-- По убыванию
SELECT * FROM products ORDER BY price DESC;

-- По нескольким столбцам
SELECT * FROM products 
ORDER BY category_id ASC, price DESC;

-- С выражениями
SELECT * FROM products 
ORDER BY price * stock DESC;
```

---

### 2.5. LIMIT и OFFSET — ограничение результатов

**Синтаксис:**
```sql
SELECT * FROM products
ORDER BY price DESC
LIMIT 10 OFFSET 20;
```

**Примеры:**
```sql
-- Первые 10 записей
SELECT * FROM products LIMIT 10;

-- Пропустить первые 20, взять следующие 10
SELECT * FROM products LIMIT 10 OFFSET 20;

-- Для пагинации (страница 3, по 10 на странице)
SELECT * FROM products LIMIT 10 OFFSET 20;
-- Формула: OFFSET = (page - 1) * page_size
```

---

### 2.6. UNION, INTERSECT, EXCEPT — множественные операции

**UNION** — объединение результатов:
```sql
SELECT name FROM customers WHERE city = 'Москва'
UNION
SELECT name FROM customers WHERE city = 'Казань';
```

**UNION ALL** — объединение с дубликатами:
```sql
SELECT name FROM customers WHERE city = 'Москва'
UNION ALL
SELECT name FROM customers WHERE city = 'Казань';
```

**INTERSECT** — пересечение (общие элементы):
```sql
SELECT product_id FROM order_items WHERE order_id = 1
INTERSECT
SELECT product_id FROM order_items WHERE order_id = 2;
```

**EXCEPT** — разность (элементы из первого, которых нет во втором):
```sql
SELECT id FROM products
EXCEPT
SELECT product_id FROM order_items;
```

---

## 3. Операторы модификации данных

### 3.1. INSERT — вставка данных

```sql
-- Вставка одной строки
INSERT INTO customers (name, email, city)
VALUES ('Новый клиент', 'new@email.com', 'Москва');

-- Вставка нескольких строк
INSERT INTO customers (name, email, city) VALUES
('Клиент 1', 'client1@email.com', 'Москва'),
('Клиент 2', 'client2@email.com', 'СПб'),
('Клиент 3', 'client3@email.com', 'Казань');

-- Вставка из SELECT
INSERT INTO vip_customers (name, email)
SELECT name, email FROM customers 
WHERE total_orders > 100000;
```

---

### 3.2. UPDATE — обновление данных

```sql
-- Обновление всех записей
UPDATE products SET price = price * 1.1;

-- Обновление по условию
UPDATE products 
SET price = price * 0.9, stock = stock - 1
WHERE id = 1;

-- Обновление из другой таблицы
UPDATE orders o
SET total = (
    SELECT SUM(oi.quantity * oi.price)
    FROM order_items oi
    WHERE oi.order_id = o.id
);
```

---

### 3.3. DELETE — удаление данных

```sql
-- Удаление всех записей
DELETE FROM order_items;

-- Удаление по условию
DELETE FROM orders WHERE status = 'cancelled';

-- Удаление с USING (из другой таблицы)
DELETE FROM orders o
USING customers c
WHERE o.customer_id = c.id 
  AND c.city = 'Москва';
```

**Важно:** `DELETE` удаляет строки, `DROP TABLE` удаляет таблицу целиком.

---

## 4. Типы данных SQL

### 4.1. Числовые типы

| Тип | Размер | Диапазон |
|-----|--------|----------|
| SMALLINT | 2 байта | -32768 до 32767 |
| INTEGER | 4 байта | -2×10⁹ до 2×10⁹ |
| BIGINT | 8 байт | -9×10¹⁸ до 9×10¹⁸ |
| DECIMAL(p,s) | Зависит | Точное число |
| REAL | 4 байта | Приблизительное |
| DOUBLE PRECISION | 8 байт | Приблизительное |

```sql
CREATE TABLE products (
    id INTEGER,
    price DECIMAL(10, 2),  -- 10 цифр всего, 2 после запятой
    stock INTEGER
);
```

---

### 4.2. Строковые типы

| Тип | Описание |
|-----|----------|
| CHAR(n) | Фиксированная длина, дополняется пробелами |
| VARCHAR(n) | Переменная длина, максимум n |
| TEXT | Переменная длина, без ограничения |

```sql
CREATE TABLE customers (
    email VARCHAR(100),  -- email до 100 символов
    description TEXT     -- неограниченный текст
);
```

---

### 4.3. Дата и время

| Тип | Описание |
|-----|----------|
| DATE | Дата (год, месяц, день) |
| TIME | Время (часы, минуты, секунды) |
| TIMESTAMP | Дата и время |
| INTERVAL | Разница между датами |

```sql
CREATE TABLE orders (
    order_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    delivery_interval INTERVAL
);

-- Примеры использования
SELECT order_date + INTERVAL '7 days' AS delivery_date
FROM orders;

SELECT AGE(CURRENT_DATE, hire_date) AS work_experience
FROM employees;
```

---

### 4.4. Логический тип

```sql
BOOLEAN  -- TRUE, FALSE, NULL

CREATE TABLE products (
    is_active BOOLEAN DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE
);
```

---

## 5. NULL и работа с ним

**NULL** — отсутствие значения (не 0 и не пустая строка!)

```sql
-- Правильная проверка
SELECT * FROM customers WHERE city IS NULL;
SELECT * FROM customers WHERE city IS NOT NULL;

-- НЕПРАВИЛЬНО (всегда вернёт пустой результат)
SELECT * FROM customers WHERE city = NULL;

-- COALESCE — первое не-NULL значение
SELECT COALESCE(city, 'Город не указан') AS city
FROM customers;

-- NULLIF — возвращает NULL если значения равны
SELECT NULLIF(price, 0) AS safe_price
FROM products;
```

---

## 6. Функции работы со строками

```sql
-- Длина строки
SELECT LENGTH(name) FROM products;

-- Верхний/нижний регистр
SELECT UPPER(name), LOWER(email) FROM customers;

-- Подстрока
SELECT SUBSTRING(name FROM 1 FOR 5) FROM products;

-- Замена
SELECT REPLACE(name, 'iPhone', 'Apple iPhone') FROM products;

-- Trim (удаление пробелов)
SELECT TRIM('  hello  ');  -- 'hello'
SELECT LTRIM('  hello');   -- 'hello'
SELECT RTRIM('hello  ');   -- 'hello'

-- Конкатенация
SELECT name || ' (' || email || ')' AS info FROM customers;
SELECT CONCAT(name, ' (', email, ')') AS info FROM customers;
```

---

## 7. Функции работы с числами

```sql
-- Округление
SELECT ROUND(123.456, 2);  -- 123.46
SELECT FLOOR(123.456);     -- 123
SELECT CEIL(123.456);      -- 124

-- Модуль
SELECT ABS(-100);  -- 100

-- Степень и корень
SELECT POWER(2, 10);   -- 1024
SELECT SQRT(144);      -- 12

-- Случайное число
SELECT RANDOM();  -- от 0 до 1
```

---

## 8. Функции работы с датой

```sql
-- Текущая дата/время
SELECT CURRENT_DATE;
SELECT CURRENT_TIME;
SELECT CURRENT_TIMESTAMP;
SELECT NOW();

-- Извлечение частей даты
SELECT EXTRACT(YEAR FROM order_date) AS year FROM orders;
SELECT EXTRACT(MONTH FROM order_date) AS month FROM orders;
SELECT DATE_PART('year', order_date) AS year FROM orders;

-- Форматирование
SELECT TO_CHAR(order_date, 'DD.MM.YYYY') FROM orders;
SELECT TO_CHAR(order_date, 'Day, DD Month YYYY') FROM orders;

-- Разница дат
SELECT order_date - created_at AS diff FROM orders;
SELECT AGE(CURRENT_DATE, order_date) FROM orders;
```

---

## 9. CASE — условная логика

**Простой CASE:**
```sql
SELECT name,
    CASE status
        WHEN 'pending' THEN 'В обработке'
        WHEN 'completed' THEN 'Выполнен'
        WHEN 'cancelled' THEN 'Отменён'
        ELSE 'Неизвестно'
    END AS status_text
FROM orders;
```

**Поисковый CASE:**
```sql
SELECT name, price,
    CASE 
        WHEN price < 1000 THEN 'Дёшево'
        WHEN price BETWEEN 1000 AND 10000 THEN 'Средне'
        WHEN price > 10000 THEN 'Дорого'
        ELSE 'Цена не указана'
    END AS price_category
FROM products;
```

---

## 10. Приоритет операторов

1. `()` — скобки
2. `*`, `/`, `%` — умножение, деление, остаток
3. `+`, `-` — сложение, вычитание
4. `=`, `<>`, `<`, `>`, `<=`, `>=` — сравнение
5. `NOT`
6. `AND`
7. `OR`

```sql
-- Пример с приоритетом
SELECT * FROM products 
WHERE (price > 1000 AND stock > 50) OR category_id = 1;
```

---

## 11. Шпаргалка по основным командам

| Команда | Описание | Пример |
|---------|----------|--------|
| SELECT | Выборка | `SELECT * FROM table` |
| INSERT | Вставка | `INSERT INTO table VALUES (...)` |
| UPDATE | Обновление | `UPDATE table SET col=val` |
| DELETE | Удаление | `DELETE FROM table WHERE` |
| WHERE | Фильтрация | `WHERE col > 10` |
| ORDER BY | Сортировка | `ORDER BY col DESC` |
| LIMIT | Ограничение | `LIMIT 10 OFFSET 20` |
| DISTINCT | Уникальные | `SELECT DISTINCT col` |
| LIKE | Паттерн | `LIKE '%text%'` |
| IN | Список | `IN (1, 2, 3)` |
| BETWEEN | Диапазон | `BETWEEN 1 AND 100` |
| IS NULL | Проверка NULL | `IS NULL` |
| UNION | Объединение | `UNION ALL` |
| CASE | Условие | `CASE WHEN ... THEN ... END` |
