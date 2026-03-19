package com.example.calculator;

/**
 * Простой калькулятор для тестирования.
 * Задание: протестировать все методы.
 */
public class Calculator {
    
    /**
     * Сложение двух чисел.
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Вычитание двух чисел.
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * Умножение двух чисел.
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Деление двух чисел.
     * Бросает ArithmeticException при делении на ноль.
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
    
    /**
     * Возведение в степень.
     */
    public double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }
    
    /**
     * Проверка на чётное число.
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
    
    /**
     * Факториал числа.
     * Бросает IllegalArgumentException для отрицательных чисел.
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
