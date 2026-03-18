package strings_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strings.PalindromeCheck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для PalindromeCheck")
public class PalindromeCheckTest {
    PalindromeCheck palindromeCheck;
    @BeforeEach
    public void setUp(){
        palindromeCheck = new PalindromeCheck();
    }

    @Test
    @DisplayName("Классические палиндромы")
    void classicPalindromes() {
        assertTrue(palindromeCheck.isPalindrome("A man, a plan, a canal: Panama"));
        assertTrue(palindromeCheck.isPalindrome("Was it a car or a cat I saw"));
        assertTrue(palindromeCheck.isPalindrome("No 'x' in Nixon"));
        assertTrue(palindromeCheck.isPalindrome("Madam, I'm Adam"));
    }

    @Test
    @DisplayName("Числовые палиндромы")
    void numericPalindromes() {
        assertTrue(palindromeCheck.isPalindrome("12321"));
        assertTrue(palindromeCheck.isPalindrome("1234321"));
        assertTrue(palindromeCheck.isPalindrome("123 321"));
        assertTrue(palindromeCheck.isPalindrome("1 2 3 2 1"));
    }

    @Test
    @DisplayName("Граничные случаи")
    void boundaryCases() {
        // Пустая строка
        assertTrue(palindromeCheck.isPalindrome(""));

        // Один символ
        assertTrue(palindromeCheck.isPalindrome("a"));
        assertTrue(palindromeCheck.isPalindrome("1"));
        assertTrue(palindromeCheck.isPalindrome("@"));

        // Только пробелы
        assertTrue(palindromeCheck.isPalindrome("   "));

        // Только спецсимволы
        assertTrue(palindromeCheck.isPalindrome("!@#$%"));
    }

    @Test
    @DisplayName("Строки, не являющиеся палиндромами")
    void nonPalindromes() {
        assertFalse(palindromeCheck.isPalindrome("hello"));
        assertFalse(palindromeCheck.isPalindrome("java"));
        assertFalse(palindromeCheck.isPalindrome("testing"));
        assertFalse(palindromeCheck.isPalindrome("12345"));
        assertFalse(palindromeCheck.isPalindrome("not a palindrome"));
    }

    @Test
    @DisplayName("Смешанный регистр и пунктуация")
    void mixedCaseAndPunctuation() {
        assertTrue(palindromeCheck.isPalindrome("Race! Car???"));
        assertTrue(palindromeCheck.isPalindrome("Mr. Owl ate my metal worm"));
        assertTrue(palindromeCheck.isPalindrome("A Santa, at NASA"));
        assertTrue(palindromeCheck.isPalindrome("Eva, can I see bees in a cave?"));
    }

}
