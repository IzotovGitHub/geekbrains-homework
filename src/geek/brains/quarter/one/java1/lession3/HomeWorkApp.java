package geek.brains.quarter.one.java1.lession3;

public class HomeWorkApp {

    private static int[] replaceElements(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] == 0 ? 1 : 0;
        }
        return array;
    }
}
