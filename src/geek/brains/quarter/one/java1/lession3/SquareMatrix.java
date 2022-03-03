package geek.brains.quarter.one.java1.lession3;

public class SquareMatrix {

    private static int[][] fillInInverseDiagonalOfSquareMatrix(int[][] matrix, int value) {
        if (isSquareMatrix(matrix)) {
            int len = matrix.length;
            for (int i = 0; i < len; i++){
                for(int j = 0; j < len; j++){
                    if(i == Math.abs(len - j - 1)){
                        matrix[i][j] = value;
                        break;
                    }
                }
            }
        }
        return matrix;
    }

    private static boolean isSquareMatrix(int[][] matrix) {
        int len = matrix.length;
        for (int[] row : matrix) {
            if (row.length != len) {
                return false;
            }
        }
        return true;
    }

    private static int[][] buildIdentitySquareMatrix(int order) {
        int[][] matrix = buildSquareMatrix(order);
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }

    private static int[][] buildSquareMatrix(int order) {
        int[][] matrix = new int[order][];
        for (int i = 0; i < order; i++) {
            matrix[i] = new int[order];
        }
        return matrix;
    }
}
