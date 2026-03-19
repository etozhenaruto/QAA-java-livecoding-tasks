package com.example.util;

/**
 * Утилиты для работы с окружением.
 * Задание: протестировать с использованием Assumptions.
 */
public class EnvironmentChecker {
    
    /**
     * Получить значение переменной окружения.
     */
    public String getEnvVariable(String name) {
        return System.getenv(name);
    }
    
    /**
     * Проверить запущен ли тест на Windows.
     */
    public boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
    
    /**
     * Проверить запущен ли тест на Linux.
     */
    public boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
    
    /**
     * Проверить запущен ли тест на macOS.
     */
    public boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
    
    /**
     * Получить имя операционной системы.
     */
    public String getOsName() {
        return System.getProperty("os.name");
    }
    
    /**
     * Получить имя пользователя.
     */
    public String getUserName() {
        return System.getProperty("user.name");
    }
    
    /**
     * Получить рабочую директорию.
     */
    public String getUserDir() {
        return System.getProperty("user.dir");
    }
}
