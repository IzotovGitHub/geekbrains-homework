package geek.brains.quarter.one.java1.lession3;

import java.util.Arrays;

// Задача №8
public class ElementShift {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(shift(new int[]{1, 2, 3, 4}, 1)));   // [4, 1, 2, 3]
        System.out.println(Arrays.toString(shift(new int[]{1, 2, 3, 4}, -1)));  // [2, 3, 4, 1]
    }

    private static int[] shift(int[] array, int count) {

        if (Math.abs(count) >= array.length) {
            count %= array.length;
        }

        if (count == 0) {
            return array;
        } else if (count > 0) {
            return shift(toRight(array), --count);
        } else {
            return shift(toLeft(array), ++count);
        }
    }

    private static int[] toRight(int[] array) {
        int len = array.length;
        int cache = array[len - 1];

        for (int i = len - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = cache;
        return array;
    }

    private static int[] toLeft(int[] array) {
        int len = array.length;
        int cache = array[0];

        for (int i = 0; i < len - 1; i++) {
            array[i] = array[i + 1];
        }

        array[len - 1] = cache;
        return array;
    }
}
