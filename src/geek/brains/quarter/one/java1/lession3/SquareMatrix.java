package geek.brains.quarter.one.java1.lession3;

public class SquareMatrix {

    private static int[][] buildSquareMatrix(int order) {
        int[][] matrix = new int[order][];
        for (int i = 0; i < order; i++) {
            matrix[i] = new int[order];
        }
        return matrix;
    }
}
