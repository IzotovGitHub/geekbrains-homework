package geek.brains.quarter.one.java1.lession4;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String USER = "user";
    private static final String AI = "ai";

    private static final int SIZE = 5;
    private static final int WIN_SIZE = 4;
    private static final int MAX_STEPS = SIZE * SIZE;
    private static final char[][] MAP = new char[SIZE][SIZE];
    private static final char DOT_X = 'X';
    private static final char DOT_0 = '0';
    private static final char DOT_EMPTY = 183;

    private static int userStepX = -1;
    private static int userStepY = -1;
    private static int steps = 0;
    private static boolean isUserMove;
    public static Random rand = new Random();
    private static Map<String, Character> dotBindings;


    public static void main(String[] args) {
        initMap();
        isUserMove = new Random().nextBoolean();
        setDotBindings();
        while (!isWin()){
            if (isUserMove ^= true) {
                userMove();
            } else {
                aiMove();
            }
            steps++;
            drawMap();

            if(isMapFull()){
                System.out.println("Ничья");
               break;
            }
        }

        if (isUserMove) {
            System.out.println("Победило человечество!");
        } else {
            System.out.println("Роботы торжествуют!");
        }
        scanner.close();
    }

    private static void initMap() {
        for (char[] row : MAP) {
            Arrays.fill(row, DOT_EMPTY);
        }
    }

    private static void setDotBindings() {
        if (isUserMove) {
            dotBindings = Map.of(
                    USER, DOT_X,
                    AI, DOT_0);
        } else {
            dotBindings = Map.of(
                    USER, DOT_0,
                    AI, DOT_X);
        }
    }

    private static void drawMap() {
        System.out.print("   ");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + "  ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(MAP[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void userMove() {
        do {
            System.out.println("Введите координаты в формате X Y");
            userStepX = scanner.nextInt();
            userStepY = scanner.nextInt();
        } while (!isCellValid(userStepX - 1, userStepY - 1));
        MAP[--userStepY][--userStepX] = getSymbol();
    }

    private static void aiMove() {
        boolean flag = false;
        int x = userStepX, y = userStepY;
        char userSymbol = dotBindings.get(USER);
        int userSequenceRow = 0, userSequenceColumn = 0;
        if (x != -1) {
            userSequenceRow = getMaxHorizontalSequence(MAP[y], userSymbol);
            userSequenceColumn = getMaxVerticalSequence(x, userSymbol);
        }
        do {
            if (!flag && userSequenceRow > 1 && userSequenceRow >= userSequenceColumn) {
                x = getRowStep();
                flag = true;
            } else if (!flag  && userSequenceColumn > userSequenceRow) {
                y = getColumnStep();
                flag = true;
            } else {
                x = getRandomStep();
                y = getRandomStep();
            }
        } while (!isCellValid(x, y));

        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
        MAP[y][x] = getSymbol();
    }

    private static int getRowStep() {
        for (int i = 0; i < SIZE; i++) {
            if (MAP[userStepY][i] == DOT_EMPTY) {
                return i;
            }
        }
        return -1;
    }

    private static int getColumnStep() {
        for (int i = 0; i < SIZE; i++) {
            if (MAP[i][userStepX] == DOT_EMPTY) {
                return i;
            }
        }
        return -1;
    }

    private static int getRandomStep() {
        return rand.nextInt(SIZE);
    }

    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < SIZE
                && y >= 0 && y < SIZE
                && MAP[y][x] == DOT_EMPTY;
    }

    private static boolean isWin() {
        char symbol = getSymbol();
        if (getMaxDiagonalSequence(symbol) >= WIN_SIZE || getMaxReverseDiagonalSequence(symbol) >= WIN_SIZE) {
            return true;
        }

        for (int i = 0; i < SIZE; i++) {
            if (getMaxHorizontalSequence(MAP[i], symbol) >= WIN_SIZE
                    || getMaxVerticalSequence(i, symbol) >= WIN_SIZE) {
                return true;
            }
        }
        return false;
    }

    private static int getMaxHorizontalSequence(char[] row, char symbol) {
        int maxSeq = 0;
        int counter = 0;
        for (char sym : row) {
            if (symbol == sym) {
                counter++;
            } else {
                counter = 0;
            }

            if (maxSeq < counter) {
                maxSeq = counter;
            }
        }
        return maxSeq;
    }

    private static int getMaxVerticalSequence(int columnNum, char symbol) {
        int maxSeq = 0;
        int counter = 0;
        if (columnNum >= 0 && columnNum < SIZE) {
            for (char[] row : MAP) {
                char sym = row[columnNum];
                if (symbol == sym) {
                    counter++;
                } else {
                    counter = 0;
                }

                if (maxSeq < counter) {
                    maxSeq = counter;
                }
            }
        }
        return maxSeq;
    }

    private static int getMaxDiagonalSequence(char symbol) {
        int maxSeq = 0;
        int n = 0;
        while (SIZE - n >= WIN_SIZE) {
            int j = 0;
            int rightCounter = 0;
            int leftCounter = 0;

            for (int i = n; i < SIZE; i++) {
                char rightSeq = MAP[i][j];
                char leftSeq = MAP[j][i];
                if (rightSeq == symbol) {
                    rightCounter++;
                } else {
                    rightCounter = 0;
                }

                if (leftSeq == symbol) {
                    leftCounter++;
                } else {
                    leftCounter = 0;
                }

                if (maxSeq < Math.max(rightCounter, leftCounter)) {
                    maxSeq = Math.max(rightCounter, leftCounter);
                }
                j++;
            }
            n++;
        }
        return maxSeq;
    }

    private static int getMaxReverseDiagonalSequence(char symbol) {
        int maxSeq = 0;
        int n = 0;
        while (SIZE - n >= WIN_SIZE) {
            int j = SIZE - 1;
            int rightCounter = 0;
            int leftCounter = 0;

            for (int i = n; i < SIZE; i++) {
                char rightSeq = MAP[i][j];
                char leftSeq = MAP[SIZE - 1 - j][SIZE - 1 - i];
                if (rightSeq == symbol) {
                    rightCounter++;
                } else {
                    rightCounter = 0;
                }

                if (leftSeq == symbol) {
                    leftCounter++;
                } else {
                    leftCounter = 0;
                }

                if (maxSeq < Math.max(rightCounter, leftCounter)) {
                    maxSeq = Math.max(rightCounter, leftCounter);
                }
                j--;
            }
            n++;
        }
        return maxSeq;
    }

    private static boolean isMapFull() {
        return steps == MAX_STEPS;
    }

    private static char getSymbol() {
        if (isUserMove) {
            return dotBindings.get(USER);
        }
        return dotBindings.get(AI);
    }
}
