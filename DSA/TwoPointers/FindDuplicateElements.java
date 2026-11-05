package DSA.TwoPointers;

import java.util.HashSet;
import java.util.Set;

public class FindDuplicateElements {
    public static void main(String[] args) {
        int[] arr = {4, 2, 7, 4, 9};
        System.out.println(containsDuplicate(arr));
    }

    static boolean containsDuplicate(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for(int i=0; i< arr.length; i++){
            if(set.contains(arr[i])){
                return true;
            }else{
                set.add(arr[i]);
            }
        }
        return false;
    }
}
