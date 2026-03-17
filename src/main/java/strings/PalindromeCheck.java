package strings;

public class PalindromeCheck {

    public Boolean isPalindrome(String s){
        char[] value = filterChars(s).toString().toCharArray();
        char [] reverseValue = filterChars(s).reverse().toString().toCharArray();
        for (int i = 0; i < reverseValue.length; i++) {
            if (value[i] != reverseValue[i]) {
                return false;
            }
        }
        return true;
    }

    private StringBuilder filterChars(String s){
        StringBuilder filtered = new StringBuilder(s);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                filtered.append(Character.toLowerCase(c));
            }
        }
        return filtered;
    }
}
