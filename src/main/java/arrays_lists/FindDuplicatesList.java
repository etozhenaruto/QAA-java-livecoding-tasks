package arrays_lists;

import java.util.*;
import java.util.stream.Collectors;

public class FindDuplicatesList {

    public Set<Integer> findDuplicates(List<Integer> list) {
        if (list == null){
            return new HashSet<>();
        }
        Set<Integer> result =  new HashSet<>();
        for (Integer integer : list) {
            int count = 0;
            for(Integer int2 : list) {
                if(int2 == integer) {
                    count++;
                }
            }
            if(count > 1 && integer != null) {
                result.add(integer);
            }
        }
        return result;
    }

    public Set<Integer> findDuplicates2(List<Integer> list) {
        if (list == null){
            return new HashSet<>();
        }
        Set<Integer> input =  new HashSet<>();
        Set<Integer> duplicate =  new HashSet<>();
        for (Integer integer : list) {
            if (integer != null) {
                if (input.contains(integer)) {
                    duplicate.add(integer);
                } else {
                    input.add(integer);
                }
            }
        }
        return duplicate;
    }
    public Set<Integer> findDuplicatesStream(List<Integer> list) {
        if (list == null){
            return new HashSet<>();
        }

        Set<Integer> duplicate =  new HashSet<>();
        return list.stream()
                .filter(Objects::nonNull)
                .filter(n -> !duplicate.add(n))
                .collect(Collectors.toSet());

    }
}
