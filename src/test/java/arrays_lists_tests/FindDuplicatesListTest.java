package arrays_lists_tests;

import arrays_lists.FindDuplicatesList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import strings.PalindromeCheck;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DisplayName("Тесты для FindDuplicatesList")
public class FindDuplicatesListTest {
        FindDuplicatesList findDuplicatesList;

        @BeforeEach
        public void setUp(){
            findDuplicatesList = new FindDuplicatesList();
        }

    static Stream<Arguments> provideListsForDuplicateFinding() {
        return Stream.of(
                Arguments.of("Стандартный случай",List.of(1, 2, 3, 2, 4, 5, 1, 5), Set.of(1, 2, 5)),
                Arguments.of("Список без дубликатов",List.of(1, 2, 3, 4), Set.of()),
                Arguments.of("Список, где все элементы - дубликаты одного числа",List.of(1, 1, 1, 1), Set.of(1)),
                Arguments.of("Список с null значениями и дубликатами (используем Arrays.asList для изменяемого списка с null)",Arrays.asList(1, null, 2, null, 1, 2), Set.of(1, 2)),
                Arguments.of("Список с null значениями без дубликатов чисел",Arrays.asList(1, null, 2, null, 3), Set.of()),
                Arguments.of("Пустой список",Collections.emptyList(), Set.of()),
                Arguments.of("Список с одним элементом",Arrays.asList(null, null, null), Set.of()),
                Arguments.of("Список с одним элементом",List.of(10), Set.of()),
                Arguments.of("Список с несколькими одинаковыми дубликатами",List.of(5, 5, 5, 5, 5), Set.of(5)),
                Arguments.of("Список с разными дубликатами, встречающимися много раз",List.of(1, 2, 1, 2, 1, 2, 3), Set.of(1, 2))
        );
    }
        @ParameterizedTest
        @DisplayName("Стандартные сценарии")
        @MethodSource("provideListsForDuplicateFinding")
        void testFindDuplicates(String name,List<Integer> input,Set<Integer> expected) {
            System.out.println(name + input.toString());
            assertEquals(expected,findDuplicatesList.findDuplicatesStream(input));
        }

    }
