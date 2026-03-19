package com.example.validator;

/**
 * Валидатор строк для тестирования.
 * Задание: протестировать все методы.
 */
public class StringValidator {
    
    /**
     * Проверка на пустую строку.
     */
    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * Проверка на null или blank.
     */
    public boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Проверка что строка содержит только цифры.
     */
    public boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Проверка что строка является палиндромом.
     */
    public boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        String normalized = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        String reversed = new StringBuilder(normalized).reverse().toString();
        return normalized.equals(reversed);
    }
    
    /**
     * Обрезать строку до максимальной длины.
     */
    public String truncate(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        if (maxLength < 0) {
            throw new IllegalArgumentException("Max length cannot be negative");
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }
    
    /**
     * Повторить строку n раз.
     */
    public String repeat(String str, int times) {
        if (times < 0) {
            throw new IllegalArgumentException("Times cannot be negative");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < times; i++) {
            result.append(str);
        }
        return result.toString();
    }
}
