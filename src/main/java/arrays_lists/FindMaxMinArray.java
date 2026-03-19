package arrays_lists;

public class FindMaxMinArray {

    public int findMax(int[] nums) {
        if(nums == null || nums.length == 0){
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        int max = Integer.MIN_VALUE;
        for(int num : nums){
            if (num > max){
                max = num;
            }
        }
        return max;
    }

    public int findMin(int[] nums) {
        if(nums == null||nums.length == 0){
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        int min = Integer.MAX_VALUE;
        for(int num : nums){
            if (num < min){
                min = num;
            }
        }
        return min;
    }
}
