package com.example.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Простой репозиторий для тестирования Extensions.
 * Задание: создать Extension для логирования и подсчёта тестов.
 */
public class DataRepository {
    
    private final List<String> data = new ArrayList<>();
    
    public void save(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        data.add(item);
    }
    
    public List<String> findAll() {
        return new ArrayList<>(data);
    }
    
    public int count() {
        return data.size();
    }
    
    public void clear() {
        data.clear();
    }
    
    public boolean exists(String item) {
        return data.contains(item);
    }
    
    public void delete(String item) {
        data.remove(item);
    }
}
