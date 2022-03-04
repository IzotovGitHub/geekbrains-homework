package geek.brains.quarter.one.java1.lession3;

public class ArrayBalance {

    public static void main(String[] args) {
        System.out.println(checkBalance(new int[]{1, 1, 0, 1})); // false
        System.out.println(checkBalance(new int[]{1, 1, 1, 1})); // true
    }

    private static boolean checkBalance(int[] array) {
        int lastIndex = array.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            int leftWeight = arrayRangeSum(array, 0, i);
            int rightWeight = arrayRangeSum(array, i + 1, lastIndex);

            if (leftWeight == rightWeight) {
                return true;
            }
        }
        return false;
    }

    private static int arrayRangeSum(int[] array, int leftBorder, int rightBorder) {
        if (leftBorder >= 0 && leftBorder <= rightBorder && rightBorder < array.length) {
            if (leftBorder == rightBorder) {
                return array[leftBorder];
            }

            int sum = 0;
            while (leftBorder <= rightBorder) {
                sum += array[leftBorder++];
            }
            return sum;
        }
        return 0;
    }
}
