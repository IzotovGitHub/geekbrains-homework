package geek.brains.quarter.one.java1.lession3;

public class ElementShift {

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
