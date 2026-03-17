package algorithms;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {

        public List<String> generateFizzBuzz(int n){
            List<String> list = new ArrayList<String>();
            if (n==0){
                   return list;
               }
            int count = 0;
            while (n>0) {
                count++;
                n--;
                if (count%3==0 && count%5==0){
                    list.add("FizzBuzz");
                    continue;
                }
                if (count%3==0){
                    list.add("Fizz");
                    continue;
                }
                if (count%5==0){
                    list.add("Buzz");
                    continue;
                }
                list.add(String.valueOf(count));
            }
            return list;
        }
}
