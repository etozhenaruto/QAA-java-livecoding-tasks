# ✅ Ответы: NoSQL

## Задачи 6.1 - 6.3 (MongoDB)

### 6.1
```javascript
db.customers.insertOne({
    name: "Иван Петров",
    email: "ivan@email.com",
    city: "Москва",
    orders: [
        { order_id: 1, total: 1000, date: "2024-06-01" },
        { order_id: 2, total: 2000, date: "2024-06-15" }
    ]
});
```

### 6.2
```javascript
db.customers.find({ city: "Москва" });
```

### 6.3
```javascript
db.orders.aggregate([
    { $group: { _id: "$customer_id", total: { $sum: "$amount" } } },
    { $sort: { total: -1 } }
]);
```

---

## Задачи 6.4 - 6.6 (Redis)

### 6.4
```bash
SET user:1:name "Иван Петров" EX 3600
SET user:1:email "ivan@email.com" EX 3600
# Или хэш:
HSET user:1 name "Иван Петров" email "ivan@email.com"
EXPIRE user:1 3600
```

### 6.5
```bash
ZADD leaderboard 1000 "player1"
ZADD leaderboard 1500 "player2"
ZADD leaderboard 1200 "player3"

# Получить топ-3
ZREVRANGE leaderboard 0 2 WITHSCORES

# Получить ранг игрока
ZREVRANK leaderboard player1
```

### 6.6
```bash
# Добавить в очередь
LPUSH queue item1
LPUSH queue item2
LPUSH queue item3

# Взять из очереди
RPOP queue  # FIFO
# Или LPOP для LIFO
```

---

## Задачи 6.7 - 6.8 (Cassandra)

### 6.7
```sql
CREATE TABLE metrics (
    sensor_id UUID,
    timestamp TIMESTAMP,
    value DOUBLE,
    PRIMARY KEY (sensor_id, timestamp)
) WITH CLUSTERING ORDER BY (timestamp DESC);
```

### 6.8
```sql
SELECT * FROM metrics 
WHERE sensor_id = ? 
ORDER BY timestamp DESC 
LIMIT 100;
```

---

## Задачи 6.9 - 6.10 (Neo4j)

### 6.9
```cypher
CREATE (p1:Person {name: "Иван", age: 30})
CREATE (p2:Person {name: "Мария", age: 25})
CREATE (p1)-[:FRIENDS_WITH {since: 2020}]->(p2)
```

### 6.10
```cypher
MATCH (p:Person {name: "Иван"})-[:FRIENDS_WITH]->()-[:FRIENDS_WITH]->(fof)
WHERE NOT (p)-[:FRIENDS_WITH]->(fof)
RETURN fof.name, COUNT(*) AS mutual_friends
ORDER BY mutual_friends DESC
LIMIT 5;
```
