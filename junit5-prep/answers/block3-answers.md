# ✅ Ответы: Блок 3 — Assumptions

## Задание 3.1: assumeTrue для переменной окружения

```java
package com.example.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayName("Тесты с предположениями для переменных окружения")
class EnvironmentAssumptionsTest {
    
    @Test
    @DisplayName("Тест с переменной окружения TEST_ENV")
    void testWithEnvironmentVariable() {
        String testEnv = System.getenv("TEST_ENV");
        
        // Пропустить тест если TEST_ENV не установлена
        assumeTrue(testEnv != null, "TEST_ENV переменная не установлена");
        
        // Этот код выполнится только если TEST_ENV установлена
        assertFalse(testEnv.isEmpty(), "TEST_ENV не должна быть пустой");
    }
}
```

---

## Задание 3.2: assumeTrue для ОС (Windows)

```java
package com.example.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayName("Тесты с предположениями для ОС")
class OsAssumptionsTest {
    
    @Test
    @DisplayName("Тест только на Windows")
    void testOnlyOnWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        
        // Пропустить тест если не Windows
        assumeTrue(os.contains("win"), "Тест выполняется только на Windows");
        
        // Проверить что os.name содержит "Windows"
        assertTrue(os.contains("windows"), "OS должна быть Windows");
    }
}
```

---

## Задание 3.3: assumeFalse для ОС (не Linux)

```java
package com.example.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class NotLinuxTest {
    
    @Test
    @DisplayName("Тест не на Linux")
    void testNotOnLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        
        // Пропустить тест если Linux
        assumeFalse(os.contains("linux"), "Тест не выполняется на Linux");
        
        // Проверить что os.name не содержит "Linux"
        assertFalse(os.contains("linux"));
    }
}
```

---

## Задание 3.4: assumingThat для блока кода

```java
package com.example.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class ConditionalBlockTest {
    
    @Test
    @DisplayName("Тест с условным блоком кода")
    void testWithConditionalBlock() {
        String os = System.getProperty("os.name").toLowerCase();
        
        // Этот код выполнится всегда
        String userName = System.getProperty("user.name");
        assertNotNull(userName);
        
        // Этот код выполнится только на Windows
        assumingThat(os.contains("win"), () -> {
            // Проверить что user.dir содержит диск (C:\, D:\, и т.д.)
            String userDir = System.getProperty("user.dir");
            assertTrue(userDir.matches("^[A-Z]:\\\\.*"), 
                "Путь должен начинаться с диска Windows");
        });
        
        // Этот код тоже выполнится всегда
        System.out.println("Пользователь: " + userName);
    }
}
```

---

## Задание 3.5: assumeTrue с сообщением

```java
package com.example.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class JavaVersionTest {
    
    @Test
    @DisplayName("Тест с кастомным сообщением при пропуске")
    void testWithCustomSkipMessage() {
        String javaVersion = System.getProperty("java.version");
        
        // Пропустить если Java версия меньше 11
        assumeTrue(
            Integer.parseInt(javaVersion.split("\\.")[0]) >= 11,
            () -> "Тест требует Java 11+, текущая версия: " + javaVersion
        );
        
        // Проверить что версия Java >= 11
        int majorVersion = Integer.parseInt(javaVersion.split("\\.")[0]);
        assertTrue(majorVersion >= 11);
    }
}
```

---

## Задание 3.6: Комбинация Assumptions

```java
package com.example.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class MultipleAssumptionsTest {
    
    @Test
    @DisplayName("Тест с несколькими предположениями")
    void testWithMultipleAssumptions() {
        String os = System.getProperty("os.name").toLowerCase();
        String userName = System.getProperty("user.name");
        String testEnv = System.getenv("TEST_ENV");
        
        // Несколько предположений
        assumeTrue(userName != null, "user.name не установлена");
        assumeTrue(testEnv != null, "TEST_ENV не установлена");
        
        // Тест выполнится только если ВСЕ предположения true
        assertFalse(userName.isEmpty(), "Имя пользователя не должно быть пустым");
    }
}
```

---

## Задание 3.7: EnvironmentChecker тесты

```java
package com.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayName("Тесты для EnvironmentChecker")
class EnvironmentCheckerTest {
    
    private EnvironmentChecker checker;
    
    @BeforeEach
    void setUp() {
        checker = new EnvironmentChecker();
    }
    
    @Nested
    @DisplayName("Тесты переменных окружения")
    class EnvironmentVariables {
        
        @Test
        @DisplayName("Получение переменной окружения")
        void testGetEnvVariable() {
            String testEnv = System.getenv("TEST_ENV");
            assumeTrue(testEnv != null, "TEST_ENV не установлена");
            
            String result = checker.getEnvVariable("TEST_ENV");
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Тесты операционной системы")
    class OperatingSystem {
        
        @Test
        @DisplayName("Проверка Windows")
        void testIsWindows() {
            assumeTrue(checker.isWindows(), "Тест только для Windows");
            assertTrue(checker.isWindows());
            assertTrue(checker.getOsName().toLowerCase().contains("win"));
        }
        
        @Test
        @DisplayName("Проверка Linux")
        void testIsLinux() {
            assumeTrue(checker.isLinux(), "Тест только для Linux");
            assertTrue(checker.isLinux());
            assertTrue(checker.getOsName().toLowerCase().contains("linux"));
        }
        
        @Test
        @DisplayName("Проверка macOS")
        void testIsMac() {
            assumeTrue(checker.isMac(), "Тест только для macOS");
            assertTrue(checker.isMac());
            assertTrue(checker.getOsName().toLowerCase().contains("mac"));
        }
        
        @Test
        @DisplayName("Получение имени ОС")
        void testGetOsName() {
            String osName = checker.getOsName();
            assertNotNull(osName);
            assertFalse(osName.isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Тесты свойств пользователя")
    class UserProperties {
        
        @Test
        @DisplayName("Получение имени пользователя")
        void testGetUserName() {
            String userName = checker.getUserName();
            assertNotNull(userName);
            assertFalse(userName.isEmpty());
        }
        
        @Test
        @DisplayName("Получение рабочей директории")
        void testGetUserDir() {
            String userDir = checker.getUserDir();
            assertNotNull(userDir);
            assertFalse(userDir.isEmpty());
        }
    }
}
```

---

## Вопросы для самопроверки — Ответы

1. **Когда использовать Assumptions вместо Assertions?**
   - Assumptions: когда тест **не применим** в определённых условиях (пропускается)
   - Assertions: когда тест **должен пройти** (проваливается при false)

2. **В чём разница между Assertion failure и Assumption failure?**
   - Assertion failure = тест **ПРОВАЛЕН** ❌ (красный)
   - Assumption failure = тест **ПРОПУЩЕН** ⏭️ (жёлтый/серый)

3. **Как использовать assumeTrue с lambda сообщением?**
   ```java
   assumeTrue(condition, () -> "Дорогостоящее вычисление сообщения");
   ```

4. **Что делает assumingThat?**
   - Выполняет блок кода только если условие true
   - Если условие false — блок пропускается, но тест считается успешным

5. **Можно ли комбинировать несколько assumeTrue?**
   - Да, тест выполнится только если ВСЕ предположения true
