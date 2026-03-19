package com.example.service;

import java.util.*;

/**
 * Сервис пользователей для тестирования.
 * Задание: протестировать все методы с использованием Nested тестов.
 */
public class UserService {
    
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;
    
    /**
     * Регистрация нового пользователя.
     * @return ID зарегистрированного пользователя
     * @throws IllegalArgumentException если email уже существует
     * @throws IllegalArgumentException если email невалидный
     */
    public int register(String name, String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
        }
        
        User user = new User(nextId, name, email);
        users.put(nextId, user);
        return nextId++;
    }
    
    /**
     * Логин пользователя.
     * @return true если логин успешен
     */
    public boolean login(String email, String password) {
        for (User user : users.values()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                user.setLoggedIn(true);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Выход пользователя.
     */
    public void logout(int userId) {
        User user = users.get(userId);
        if (user != null) {
            user.setLoggedIn(false);
        }
    }
    
    /**
     * Удаление пользователя по ID.
     */
    public void deleteById(int userId) {
        users.remove(userId);
    }
    
    /**
     * Удаление пользователя по email.
     * @return true если пользователь удалён
     */
    public boolean deleteByEmail(String email) {
        for (Iterator<User> iterator = users.values().iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getEmail().equals(email)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Получить пользователя по ID.
     */
    public Optional<User> getById(int userId) {
        return Optional.ofNullable(users.get(userId));
    }
    
    /**
     * Получить всех пользователей.
     */
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
    
    /**
     * Количество зарегистрированных пользователей.
     */
    public int count() {
        return users.size();
    }
    
    /**
     * Обновление имени пользователя.
     * @return true если успешно
     */
    public boolean updateName(int userId, String newName) {
        User user = users.get(userId);
        if (user != null) {
            user.setName(newName);
            return true;
        }
        return false;
    }
}
