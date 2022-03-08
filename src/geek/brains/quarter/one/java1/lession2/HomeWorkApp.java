package geek.brains.quarter.one.java1.lession2;

public class HomeWorkApp {

    public static void main(String[] args) {
        System.out.println("10 + 15 в диапазоне от 10 до 20? " + isSumBetweenTenAndTwenty(10, 15));
        System.out.println();

        System.out.println("-10:");
        signCheck(-10);
        System.out.println();

        System.out.println("10 - меньше нуля? " + isNumberNegative(10));
        System.out.println();

        printLine("Меня напечатали 3 раза", 3);
        System.out.println();

        System.out.println("1-ый год високосный? " + isLeapYear(1));
        System.out.println();

        System.out.println("4-ый год високосный? " + isLeapYear(4));
        System.out.println();

        System.out.println("100-ый год високосный? " + isLeapYear(100));
        System.out.println();

        System.out.println("400-ый год високосный? " + isLeapYear(400));
        System.out.println();
    }

    private static boolean isSumBetweenTenAndTwenty(int first, int second) {
        int sum = first + second;
        return sum >= 10 && sum <= 20;
    }

    private static void signCheck(int number) {
        if (number >= 0) {
            System.out.println("Положительное число");
        } else {
            System.out.println("Отрицательное число");
        }
    }

    private static boolean isNumberNegative(int number) {
        return number < 0;
    }

    private static void printLine(String line, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(line);
        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }
        return year % 100 != 0 && year % 4 == 0;
    }
}
