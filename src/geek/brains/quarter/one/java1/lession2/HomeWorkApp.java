package geek.brains.quarter.one.java1.lession2;

public class HomeWorkApp {

    public static void main(String[] args) {

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

    private static boolean isNumberNegative(int number){
        return number < 0;
    }

    private static void printLine(String line, int times){
        for (int i = 0; i < times; i++){
            System.out.println(line);
        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else return year % 100 != 0 && year % 4 == 0;
    }
}
