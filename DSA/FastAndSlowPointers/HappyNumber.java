package DSA.FastAndSlowPointers;

public class HappyNumber {
    public static void main(String[] args) {
        int number = 19;
        findSqureSum(number);
        System.out.println(happyNumber(number));
    }

    static boolean happyNumber(int num) {
        int slow = findSqureSum(num);
        int fast = findSqureSum(findSqureSum(num));
        while (slow != fast) {
            slow = findSqureSum(slow);
            fast = findSqureSum(findSqureSum(fast));
        }
        return slow == 1;
    }

    static int findSqureSum(int num) {
        int sum = 0;
        while (num > 0) {
            int digit = num % 10;
            sum = sum + (digit * digit);
            num = num / 10;
        }
        return sum;
    }
}
