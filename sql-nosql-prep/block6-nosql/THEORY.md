# 📚 Теория: NoSQL базы данных

## 1. Что такое NoSQL?

**NoSQL (Not Only SQL)** — нереляционные базы данных для работы с большими объёмами данных.

**Отличия от SQL:**
| SQL | NoSQL |
|-----|-------|
| Реляционная модель | Разные модели |
| Фиксированная схема | Гибкая схема |
| Вертикальное масштабирование | Горизонтальное масштабирование |
| ACID транзакции | BASE модель |
| JOIN поддерживаются | JOIN ограничены |

---

## 2. CAP теорема

**CAP теорема:** распределённая система может гарантировать только 2 из 3 свойств:

- **Consistency (Согласованность)** — все узлы видят одни данные
- **Availability (Доступность)** — каждый запрос получает ответ
- **Partition Tolerance (Устойчивость к разделению)** — работает при потере связи

**Типы NoSQL по CAP:**

| Тип | CP | AP |
|-----|----|----|
| Пример | MongoDB, Redis | Cassandra, DynamoDB |
| При разделении | Недоступна | Возвращает старые данные |

---

## 3. Типы NoSQL баз данных

### 3.1. Document Stores (Документные)

**Примеры:** MongoDB, CouchDB, RavenDB

**Характеристики:**
- Данные в формате JSON/BSON
- Гибкая схема
- Вложенные документы

**MongoDB пример:**
```javascript
// Вставка документа
db.customers.insertOne({
    name: "Иван Петров",
    email: "ivan@email.com",
    orders: [
        { id: 1, total: 1000, date: "2024-06-01" },
        { id: 2, total: 2000, date: "2024-06-15" }
    ],
    address: {
        city: "Москва",
        street: "Тверская"
    }
});

// Поиск
db.customers.find({ city: "Москва" });
db.customers.findOne({ email: "ivan@email.com" });

// Агрегация
db.orders.aggregate([
    { $group: { _id: "$customer_id", total: { $sum: "$amount" } } },
    { $sort: { total: -1 } },
    { $limit: 10 }
]);
```

**Когда использовать:**
- Контент-менеджмент
- Каталоги товаров
- Профили пользователей

---

### 3.2. Key-Value Stores (Ключ-Значение)

**Примеры:** Redis, DynamoDB, Riak

**Характеристики:**
- Простая модель: ключ → значение
- Очень быстрое чтение/запись
- Кэширование

**Redis пример:**
```bash
# Установка значения
SET user:1:name "Иван Петров"
SET user:1:email "ivan@email.com"

# Получение
GET user:1:name

# Хэши
HSET user:1 name "Иван" email "ivan@email.com"
HGETALL user:1

# Списки
LPUSH orders 1001
LPUSH orders 1002
LRANGE orders 0 -1

# Sorted Sets (рейтинги)
ZADD leaderboard 1000 "player1"
ZADD leaderboard 1500 "player2"
ZREVRANGE leaderboard 0 9

# Истечение (TTL)
SET session:abc123 "data" EX 3600  # 1 час
TTL session:abc123

# Pub/Sub
PUBLISH channel "message"
SUBSCRIBE channel
```

**Когда использовать:**
- Кэширование
- Сессии пользователей
- Рейтинги и лидерборды
- Очереди сообщений

---

### 3.3. Column-Family Stores (Колоночные)

**Примеры:** Cassandra, HBase, ScyllaDB

**Характеристики:**
- Данные хранятся по колонкам
- Оптимизированы для записи
- Горизонтальное масштабирование

**Cassandra пример:**
```sql
-- Создание таблицы
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT,
    email TEXT,
    created_at TIMESTAMP
);

-- Вставка
INSERT INTO users (id, name, email, created_at)
VALUES (uuid(), 'Иван', 'ivan@email.com', NOW());

-- Поиск
SELECT * FROM users WHERE id = ?;

-- Materialized View
CREATE MATERIALIZED VIEW users_by_email AS
SELECT * FROM users
WHERE email IS NOT NULL
PRIMARY KEY (email, id);
```

**Когда использовать:**
- Большие объёмы записи
- Временные ряды
- Логи событий
- IoT данные

---

### 3.4. Graph Databases (Графовые)

**Примеры:** Neo4j, Amazon Neptune, ArangoDB

**Характеристики:**
- Узлы и связи
- Оптимизированы для связей
- Социальные графы

**Neo4j (Cypher) пример:**
```cypher
-- Создание узлов
CREATE (p:Person {name: "Иван", age: 30})
CREATE (p:Person {name: "Мария", age: 25})

-- Создание связи
MATCH (a:Person {name: "Иван"}), (b:Person {name: "Мария"})
CREATE (a)-[:FRIENDS_WITH {since: 2020}]->(b)

-- Поиск друзей
MATCH (p:Person {name: "Иван"})-[:FRIENDS_WITH]->(friend)
RETURN friend.name

-- Поиск путей
MATCH path = shortestPath(
    (a:Person {name: "Иван"})-[*]-(b:Person {name: "Алексей"})
)
RETURN path

-- Рекомендации
MATCH (p:Person {name: "Иван"})-[:FRIENDS_WITH]->()-[:FRIENDS_WITH]->(fof)
WHERE NOT (p)-[:FRIENDS_WITH]->(fof)
RETURN fof.name, COUNT(*) AS mutual_friends
ORDER BY mutual_friends DESC
LIMIT 5
```

**Когда использовать:**
- Социальные сети
- Рекомендательные системы
- Обнаружение мошенничества
- Иерархии и деревья

---

## 4. BASE vs ACID

### ACID (SQL)
- **Atomicity** — атомарность
- **Consistency** — согласованность
- **Isolation** — изоляция
- **Durability** — долговечность

### BASE (NoSQL)
- **Basically Available** — система доступна (может вернуть старые данные)
- **Soft state** — состояние может меняться
- **Eventual consistency** — согласованность в конечном счёте

---

## 5. Шардирование и репликация

### Шардирование

**Разделение данных между серверами:**

```
Shard 1: users A-M
Shard 2: users N-Z

-- Шардирование по ключу
shard_key = hash(user_id) % num_shards
```

**Стратегии:**
- По диапазону (A-M, N-Z)
- По хэшу
- По географии

### Репликация

**Копирование данных на несколько серверов:**

```
Master → Slave 1
       → Slave 2
       → Slave 3
```

**Типы:**
- Master-Slave (чтение из slave)
- Master-Master (запись в любой)
- Multi-Master

---

## 6. SQL vs NoSQL: когда что использовать

### SQL (реляционные)

**Использовать когда:**
- Нужны транзакции (ACID)
- Сложные запросы с JOIN
- Структурированные данные
- Финансовые системы
- Отчётность и аналитика

**Примеры:**
- Банковские системы
- ERP системы
- Бухгалтерия

### NoSQL

**Использовать когда:**
- Большие объёмы данных
- Высокая нагрузка на запись
- Гибкая схема
- Быстрое прототипирование
- Горизонтальное масштабирование

**Примеры:**
- Социальные сети
- IoT платформы
- Кэширование
- Реал-тайм аналитика

---

## 7. Полиглотное хранение

**Использование разных БД для разных задач:**

```
┌─────────────────────────────────────┐
│         Приложение                  │
├──────────┬──────────┬───────────────┤
│   PostgreSQL       │    Redis      │
│   (основные данные)│    (кэш)      │
├──────────┼──────────┼───────────────┤
│  Elasticsearch     │    MongoDB    │
│   (поиск)          │    (логи)     │
└──────────┴──────────┴───────────────┘
```

**Пример архитектуры:**
- PostgreSQL — пользователи, заказы
- Redis — сессии, кэш
- Elasticsearch — поиск
- MongoDB — логи, аналитика

---

## 8. Практические примеры

### Пример 1: Кэширование с Redis

```python
import redis

r = redis.Redis()

# Проверка кэша
cached = r.get('user:1')
if cached:
    return cached

# Запрос к БД
user = db.query('SELECT * FROM users WHERE id = 1')

# Сохранение в кэш
r.setex('user:1', 3600, json.dumps(user))  # 1 час
```

### Пример 2: MongoDB агрегация

```javascript
// Анализ продаж
db.orders.aggregate([
    { $match: { status: "completed" } },
    { $group: { 
        _id: "$customer_id",
        total: { $sum: "$amount" },
        count: { $sum: 1 }
    }},
    { $sort: { total: -1 } },
    { $limit: 100 }
]);
```

### Пример 3: Cassandra для временных рядов

```sql
-- Таблица для метрик
CREATE TABLE metrics (
    sensor_id UUID,
    timestamp TIMESTAMP,
    value DOUBLE,
    PRIMARY KEY (sensor_id, timestamp)
) WITH CLUSTERING ORDER BY (timestamp DESC);

-- Вставка данных
INSERT INTO metrics (sensor_id, timestamp, value)
VALUES (uuid(), NOW(), 23.5);

-- Последние 100 показаний
SELECT * FROM metrics 
WHERE sensor_id = ? 
ORDER BY timestamp DESC 
LIMIT 100;
```

---

## 9. Шпаргалка по NoSQL

| Тип | Примеры | Use Case |
|-----|---------|----------|
| Document | MongoDB | Контент, каталоги |
| Key-Value | Redis | Кэш, сессии |
| Column | Cassandra | Временные ряды, логи |
| Graph | Neo4j | Соцсети, рекомендации |

---

## 10. Выбор базы данных

**Вопросы для выбора:**

1. Какой объём данных?
2. Какая нагрузка (чтение/запись)?
3. Нужны ли транзакции?
4. Нужна ли согласованность?
5. Какое масштабирование нужно?
6. Какая структура данных?

**Решение:**
- Маленькие данные, ACID → SQL
- Большие данные, масштабирование → NoSQL
- Сложные → Полиглотное хранение
