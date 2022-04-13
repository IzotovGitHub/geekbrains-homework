package geek.brains.quarter.one.java2.lesson2;

import geek.brains.quarter.one.java2.lesson2.exception.MyArrayDataException;
import geek.brains.quarter.one.java2.lesson2.exception.MyArraySizeException;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.String.format;

public class HomeWorkApp {
    public static void main(String[] args) {

        Stream.of(new String[][]{
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "Не число", "4"},
                {"1", "2", "3", "4"}
        }, new String[][]{
                {"1", "2", "3", "4"},
                {"1", "2", "3"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        }, new String[][]{
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        }).forEach(array -> {
            try {
                System.out.println("Результирующая сумма: " + getSum(array));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private static int getSum(String[][] array) throws Exception {
        int sum = 0;
        String s = "";

        checkArray(array);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    s = array[i][j];
                    sum += Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(format("Не удалось преобразовать строку (%s) в ячейке с координатами i = %s, j = %s", s, i, j), e);
                }
            }
        }

        return sum;
    }

    private static void checkArray(String[][] array) throws MyArraySizeException {
        if (array == null || array.length != 4 || Arrays.stream(array).anyMatch(subArray -> subArray.length != 4)) {
            throw new MyArraySizeException("Передан массив не верного размера");
        }
    }
}
