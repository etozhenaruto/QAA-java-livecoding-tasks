package maps_sets_tests;

import maps_sets.CountCharOccurrences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Тесты для CountCharOccurrences")
public class CountCharOccurrencesTests {

    private CountCharOccurrences counter;

    @BeforeEach
    void setUp() {
        counter = new CountCharOccurrences();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideTestCases")
    @DisplayName("Тестирование различных строк")
    void testCountCharacters(String testName, String input, Map<Character, Integer> expected) {
        System.out.println("testName: " + testName);
        Map<Character, Integer> result = counter.countCharacters(input);
        assertEquals(expected, result, "Неверный подсчет для строки: " + input);
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("Стандартная строка с пробелами",
                        "hello world",
                         Map.of('h', 1, 'e', 1, 'l', 3, 'o', 2, ' ', 1, 'w', 1, 'r', 1, 'd', 1)),

                Arguments.of("Пустая строка",
                        "",
                        new HashMap<>()),

                Arguments.of("Строка с одним символом",
                        "a",
                         Map.of('a', 1)),

                Arguments.of("Строка с одинаковыми символами",
                        "aaaaa",
                         Map.of('a', 5)),

                Arguments.of("Строка с цифрами",
                        "abc123abc",
                         Map.of('a', 2, 'b', 2, 'c', 2, '1', 1, '2', 1, '3', 1)),

                Arguments.of("Строка с пробелами и табуляцией",
                        "a  b\tc",
                         Map.of('a', 1, ' ', 2, 'b', 1, '\t', 1, 'c', 1)),

                Arguments.of("Строка со спецсимволами",
                        "!@#$%",
                         Map.of('!', 1, '@', 1, '#', 1, '$', 1, '%', 1)),

                Arguments.of("Строка в верхнем регистре",
                        "HELLO",
                         Map.of('H', 1, 'E', 1, 'L', 2, 'O', 1)),

                Arguments.of("Строка в нижнем регистре",
                        "hello",
                         Map.of('h', 1, 'e', 1, 'l', 2, 'o', 1)),

                Arguments.of("Строка с русскими буквами",
                        "Привет мир",
                        Map.of('П', 1, 'р', 2, 'и', 2, 'в', 1, 'е', 1, 'т', 1, ' ', 1, 'м', 1)),

                Arguments.of("Строка со знаками пунктуации",
                        "Hello, World!",
                         Map.of('H', 1, 'e', 1, 'l', 3, 'o', 2, ',', 1, ' ', 1, 'W', 1, 'r', 1, 'd', 1, '!', 1))
        );
    }

}
