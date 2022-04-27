package geek.brains.quarter.one.java2.lesson5;

import java.util.Arrays;

public class HomeWorkApp {

    private static final int SIZE = 10_000_000;
    private static final int HALF = SIZE / 2;


    public static void main(String[] args) {
        singleThreadedMethod();
        multiThreadedMethod();
    }

    private static void singleThreadedMethod() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = calculateValue(arr[i], i);
        }

        System.out.println("One thread time: " + (System.currentTimeMillis() -
                startTime) + " ms.");
    }

    private static void multiThreadedMethod() {
        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];

        Arrays.fill(leftHalf, 1.0f);
        Arrays.fill(rightHalf, 1.0f);

        long startTime = System.currentTimeMillis();
        Thread t1 = asyncArrayFill(leftHalf);
        Thread t2 = asyncArrayFill(rightHalf);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float[] arr = new float[SIZE];
        System.arraycopy(leftHalf, 0, arr, 0, HALF);
        System.arraycopy(rightHalf, 0, arr, HALF, HALF);

        System.out.println("Multi thread time: " + (System.currentTimeMillis() -
                startTime) + " ms.");

    }

    private static Thread asyncArrayFill(float[] array) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < array.length; i++) {
                array[i] = calculateValue(array[i], i);
            }
        });
        thread.start();
        return thread;
    }

    private static float calculateValue(float value, int i) {
        return (float) (value / Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5) * Math.cos(0.4f + (float) i / 2));
    }

}
