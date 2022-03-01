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
}
