package strings_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strings.PalindromeCheck;

@DisplayName("Тесты для PalindromeCheck")
public class PalindromeCheckTest {
    PalindromeCheck palindromeCheck;
    @BeforeEach
    public void setUp(){
        palindromeCheck = new PalindromeCheck();
    }

    @Test
    @DisplayName("Дефолт")
    void palindromeCheck(){
        Boolean actual = palindromeCheck.isPalindrome("A man, a plan, a canal: Panama");
        Boolean expected = true;
        Assertions.assertEquals(expected,actual);
    }
}
