package geek.brains.quarter.one.java1.lession3;

public class ArrayBalance {

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
