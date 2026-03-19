-- ============================================
-- Тестовая база данных для SQL практики
-- ============================================

-- Таблица категорий
CREATE TABLE categories (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id INT REFERENCES categories(id)
);

-- Таблица клиентов
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    city VARCHAR(50),
    created_at DATE DEFAULT CURRENT_DATE
);

-- Таблица товаров
CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category_id INT REFERENCES categories(id),
    stock INT DEFAULT 0
);

-- Таблица заказов
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT REFERENCES customers(id),
    order_date DATE DEFAULT CURRENT_DATE,
    status VARCHAR(20) DEFAULT 'pending',
    total DECIMAL(10, 2)
);

-- Таблица элементов заказа
CREATE TABLE order_items (
    id INT PRIMARY KEY,
    order_id INT REFERENCES orders(id),
    product_id INT REFERENCES products(id),
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Таблица отзывов
CREATE TABLE reviews (
    id INT PRIMARY KEY,
    product_id INT REFERENCES products(id),
    customer_id INT REFERENCES customers(id),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at DATE DEFAULT CURRENT_DATE
);

-- Таблица сотрудников
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    manager_id INT REFERENCES employees(id),
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    hire_date DATE
);

-- ============================================
-- Тестовые данные
-- ============================================

-- Категории
INSERT INTO categories (id, name, parent_id) VALUES
(1, 'Электроника', NULL),
(2, 'Одежда', NULL),
(3, 'Книги', NULL),
(4, 'Смартфоны', 1),
(5, 'Ноутбуки', 1),
(6, 'Мужская одежда', 2),
(7, 'Женская одежда', 2),
(8, 'Фантастика', 3),
(9, 'Научная литература', 3);

-- Клиенты
INSERT INTO customers (id, name, email, city, created_at) VALUES
(1, 'Иван Петров', 'ivan@email.com', 'Москва', '2024-01-15'),
(2, 'Мария Сидорова', 'maria@email.com', 'Санкт-Петербург', '2024-02-20'),
(3, 'Алексей Смирнов', 'alexey@email.com', 'Москва', '2024-03-10'),
(4, 'Елена Козлова', 'elena@email.com', 'Казань', '2024-04-05'),
(5, 'Дмитрий Морозов', 'dmitry@email.com', 'Санкт-Петербург', '2024-05-12'),
(6, 'Ольга Новикова', 'olga@email.com', 'Москва', '2024-06-18'),
(7, 'Андрей Соколов', 'andrey@email.com', 'Екатеринбург', '2024-07-22'),
(8, 'Наталья Лебедева', 'nataly@email.com', 'Москва', '2024-08-30');

-- Товары
INSERT INTO products (id, name, description, price, category_id, stock) VALUES
(1, 'iPhone 15', 'Смартфон от Apple', 99990, 4, 50),
(2, 'Samsung Galaxy S24', 'Смартфон от Samsung', 89990, 4, 30),
(3, 'MacBook Pro 16', 'Ноутбук от Apple', 249990, 5, 20),
(4, 'Dell XPS 15', 'Ноутбук от Dell', 179990, 5, 15),
(5, 'Футболка базовая', 'Хлопковая футболка', 1990, 6, 100),
(6, 'Джинсы классические', 'Джинсы из денима', 4990, 6, 50),
(7, 'Платье вечернее', 'Элегантное платье', 8990, 7, 25),
(8, 'Блузка офисная', 'Блузка для офиса', 3490, 7, 40),
(9, 'Дюна', 'Фантастический роман', 890, 8, 200),
(10, 'Задача трех тел', 'Научная фантастика', 790, 8, 150),
(11, 'Чистая математика', 'Учебник по математике', 1290, 9, 80),
(12, 'Физика для всех', 'Популярная физика', 990, 9, 120);

-- Заказы
INSERT INTO orders (id, customer_id, order_date, status, total) VALUES
(1, 1, '2024-06-01', 'completed', 101980),
(2, 2, '2024-06-05', 'completed', 179990),
(3, 1, '2024-06-10', 'completed', 6980),
(4, 3, '2024-06-15', 'pending', 249990),
(5, 4, '2024-06-20', 'completed', 1780),
(6, 5, '2024-06-25', 'cancelled', 89990),
(7, 2, '2024-07-01', 'completed', 12480),
(8, 6, '2024-07-05', 'pending', 99990),
(9, 7, '2024-07-10', 'completed', 4280),
(10, 1, '2024-07-15', 'completed', 790),
(11, 8, '2024-07-20', 'completed', 1990),
(12, 3, '2024-07-25', 'completed', 3490),
(13, 4, '2024-08-01', 'pending', 8990),
(14, 5, '2024-08-05', 'completed', 1290),
(15, 6, '2024-08-10', 'completed', 990);

-- Элементы заказов
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES
(1, 1, 1, 1, 99990),
(2, 1, 9, 2, 890),
(3, 2, 4, 1, 179990),
(4, 3, 5, 2, 1990),
(5, 3, 6, 1, 4990),
(6, 4, 3, 1, 249990),
(7, 5, 9, 2, 890),
(8, 6, 2, 1, 89990),
(9, 7, 7, 1, 8990),
(10, 7, 8, 1, 3490),
(11, 8, 1, 1, 99990),
(12, 9, 10, 2, 790),
(13, 9, 11, 1, 1290),
(14, 9, 12, 1, 990),
(15, 10, 10, 1, 790),
(16, 11, 5, 1, 1990),
(17, 12, 8, 1, 3490),
(18, 13, 7, 1, 8990),
(19, 14, 11, 1, 1290),
(20, 15, 12, 1, 990);

-- Отзывы
INSERT INTO reviews (id, product_id, customer_id, rating, comment, created_at) VALUES
(1, 1, 1, 5, 'Отличный телефон!', '2024-06-15'),
(2, 1, 3, 4, 'Хороший, но дорогой', '2024-06-20'),
(3, 2, 5, 5, 'Лучший Android!', '2024-07-01'),
(4, 3, 2, 5, 'Мощный ноутбук', '2024-06-25'),
(5, 4, 2, 4, 'Хороший, но шумный', '2024-07-05'),
(6, 5, 1, 3, 'Нормальная за свою цену', '2024-06-20'),
(7, 6, 1, 4, 'Качественные джинсы', '2024-06-22'),
(8, 9, 1, 5, 'Шедевр фантастики', '2024-06-25'),
(9, 9, 4, 5, 'Очень понравилась', '2024-07-01'),
(10, 10, 7, 4, 'Интересная книга', '2024-07-15'),
(11, 11, 7, 5, 'Отличный учебник', '2024-07-20'),
(12, 12, 9, 4, 'Понятно написано', '2024-08-01');

-- Сотрудники
INSERT INTO employees (id, name, manager_id, department, salary, hire_date) VALUES
(1, 'Генеральный директор', NULL, 'Executive', 500000, '2020-01-01'),
(2, 'Технический директор', 1, 'Engineering', 400000, '2020-02-01'),
(3, 'Финансовый директор', 1, 'Finance', 350000, '2020-03-01'),
(4, 'Senior разработчик', 2, 'Engineering', 250000, '2021-01-15'),
(5, 'Senior разработчик', 2, 'Engineering', 260000, '2021-03-01'),
(6, 'Middle разработчик', 4, 'Engineering', 180000, '2022-01-10'),
(7, 'Middle разработчик', 4, 'Engineering', 175000, '2022-03-20'),
(8, 'Junior разработчик', 6, 'Engineering', 100000, '2023-01-15'),
(9, 'Junior разработчик', 6, 'Engineering', 95000, '2023-06-01'),
(10, 'Менеджер по продажам', 3, 'Sales', 150000, '2021-06-01'),
(11, 'Специалист по продажам', 10, 'Sales', 80000, '2022-06-15'),
(12, 'Специалист по продажам', 10, 'Sales', 75000, '2023-02-01');
