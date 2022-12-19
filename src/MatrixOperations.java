import java.util.concurrent.ThreadLocalRandom;

public class MatrixOperations {
    public int [][] genMatrix(int N, int min, int max){
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextInt(min, max + 1);
        return matrix;
    }

    public void showMatrix (int [][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j: matrix[i]) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}
