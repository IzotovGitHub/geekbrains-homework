package geek.brains.quarter.one.java1.lession3;

public class ElementShift {

    private static int[] shift(int[] array, int count) {
        if (count == 0) {
            return array;
        } else if (count > 0) {
            return shift(toRight(array), --count);
        } else {
            return shift(toLeft(array), ++count);
        }
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

    private static int[] toRight(int[] array) {
        int len = array.length;
        int cache = array[len - 1];

        for (int i = len - 1; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = cache;
        return array;
    }
}
