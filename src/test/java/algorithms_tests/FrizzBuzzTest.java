package algorithms_tests;

import algorithms.FizzBuzz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для FrizzBuzz")
public class FrizzBuzzTest {

    private FizzBuzz fizzBuzz;

    @BeforeEach
    void setUp() {
        fizzBuzz = new FizzBuzz();
    }

    @Test
    @DisplayName("Тест стандартного случая FizzBuzz до 15")
    void testFizzBuzzUpTo15() {
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz");
        List<String> actual = fizzBuzz.generateFizzBuzz(15);
        assertEquals(expected, actual, "Последовательность для n=15 должна совпадать с ожидаемой");
    }

    @Test
    @DisplayName("Тест для n=1")
    void testFizzBuzzUpTo1() {
        List<String> expected = List.of("1");
        List<String> actual = fizzBuzz.generateFizzBuzz(1);
        assertEquals(expected, actual, "Последовательность для n=1 должна содержать только '1'");
    }

    @Test
    @DisplayName("Тест для n=3")
    void testFizzBuzzUpTo3() {
        List<String> expected = List.of("1", "2", "Fizz");
        List<String> actual = fizzBuzz.generateFizzBuzz(3);
        assertEquals(expected, actual, "Последовательность для n=3 должна быть '1', '2', 'Fizz'");
    }

    @Test
    @DisplayName("Тест для n=5")
    void testFizzBuzzUpTo5() {
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz");
        List<String> actual = fizzBuzz.generateFizzBuzz(5);
        assertEquals(expected, actual, "Последовательность для n=5 должна включать 'Buzz'");
    }

    @Test
    @DisplayName("Тест для n=0 (некорректный ввод)")
    void testFizzBuzzForZero() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fizzBuzz.generateFizzBuzz(0);
        assertEquals(expected, actual, "Для n=0 должен возвращаться пустой список");
        assertTrue(actual.isEmpty(), "Для n=0 должен возвращаться пустой список (проверка isEmpty)");
    }

    @Test
    @DisplayName("Тест для отрицательного n (некорректный ввод)")
    void testFizzBuzzForNegativeN() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fizzBuzz.generateFizzBuzz(-10);
        assertEquals(expected, actual, "Для отрицательного n должен возвращаться пустой список");
    }

}
