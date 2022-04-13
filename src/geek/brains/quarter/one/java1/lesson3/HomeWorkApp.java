package geek.brains.quarter.one.java1.lesson3;

public class HomeWorkApp {

    // Задача №1
    private static int[] replaceElements(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] == 0 ? 1 : 0;
        }
        return array;
    }

    // Задача №2
    private static int[] fillArrayWithNumbers(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    // Задача №3
    private static int[] multiplyNumberIf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] < value) {
                array[i] *= 2;
            }
        }
        return array;
    }

    // Задача №5
    private static int[] initArray(int len, int value) {
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = value;
        }
        return array;
    }

    // Задача №6
    private static int findMin(int[] array) {
        int min = array[0];
        for (int value : array) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    // Задача №6
    private static int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
