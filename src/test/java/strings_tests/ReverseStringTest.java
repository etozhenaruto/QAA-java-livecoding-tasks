package strings_tests;
import strings.ReverseString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для ReverseString")
public class ReverseStringTest {

    private ReverseString reverseString;
    @BeforeEach
    public void setUp()
    {
        reverseString = new ReverseString();
    }

    @Test
    @DisplayName("Тест стандартного случая для helloworld")
    void testHelloworld() {
        String actual = reverseString.reverseString("HelloWorld");
        String expected = "dlroWolleH";
        assertEquals(expected, actual,"HelloWorld должен вернуться как dlroWolleH");
    }

    @Test
    @DisplayName("Слово с двумя буквами")
    void testTwoChars() {
        String actual = reverseString.reverseString("wr");
        String expected = "rw";
        assertEquals(expected, actual,"wr должен вернуться как rw");
    }

    @Test
    @DisplayName("Слово с одной буквой")
    void testOneChar() {
        String actual = reverseString.reverseString("Е");
        String expected = "Е";
        assertEquals(expected, actual,"Е должен вернуться как Е");
    }

    @Test
    @DisplayName("Пустая строка")
    void testEmptyString() {
        String actual = reverseString.reverseString("");
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Строка из пробела")
    void testBlankString() {
        String actual = reverseString.reverseString(" ");
        String expected = " ";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Фраза с пробелами")
    void testStringWithSpaces() {
        String actual = reverseString.reverseString("  leading and trailing spaces  ");
        String expected = "  secaps gniliart dna gnidael  ";
        assertEquals(expected, actual);
    }
}
