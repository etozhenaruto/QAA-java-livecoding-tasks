package strings;

public class ReverseString {

    public String reverseString(String s){
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        for(int i = chars.length - 1; i >= 0; i--){
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    public String reverseString2(String s){
        return new StringBuilder(s).reverse().toString();
    }
}
