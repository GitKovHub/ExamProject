import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixOperations {
    ///////////////////////////////// INT /////////////////////////////////////////////
    /**Метод генерації рандомної матриці (з параметрами розміру та мін макс значень)**/
    public int [][] genMatrix(int N, int min, int max){
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextInt(min, max + 1);
        return matrix;
    }

    /**Метод генерації рандомної матриці (з параметрами значанням щільності та максимальним значенням)**/
    public int [][] genMatrix(int N, double density,int min, int max) throws Exception {
        if (density>100 || density<0) throw new Exception("density must be from 0 to 100%");
        if (min<=0) throw new Exception("min must be from 0 to 100%");
        int countZeros = (int)(((N*N)/100) * (100.0 - density));
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int x = 0; x< countZeros; x++)nums.add(0);
        for (int x = 0; x< (N*N)-countZeros; x++)nums.add(ThreadLocalRandom.current().nextInt(min, max + 1));
        Collections.shuffle(nums);
        int counter = 0;
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                matrix[i][j] = nums.get(counter);
                counter++;
            }
        return matrix;
    }

    /**Метод для виводу матриці**/
    public void showMatrix (int [][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j: matrix[i]) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /** Віднімання двох матриць **/
    public int[][] sub(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    /** Додавання  двох матриць  **/
    public int[][] add(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    /** Функція для поділу батьківської матриці на дочірні матриці **/
    public void split(int[][] P, int[][] C, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    /** Функція для об’єднання дочірніх матриць у батьківську матрицю **/
    public void join(int[][] C, int[][] P, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }

    ///////////////////////////////// DOUBLE //////////////////////////////////////////
    /**Метод генерації рандомної матриці (з параметрами розміру та мін макс значень)**/
    public double [][] genMatrix(int N, double min, double max){
        double[][] matrix = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                matrix[i][j] = ThreadLocalRandom.current().nextDouble(min, max+1);
        return matrix;
    }

    /**Метод генерації рандомної матриці (з параметрами значанням щільності та максимальним значенням)**/
    public double [][] genMatrix(int N, double density, double min, double max) throws Exception {
        if (density>100 || density<0) throw new Exception("density must be from 0 to 100%");
        if (min<=0) throw new Exception("min must be from 0 to 100%");
        int countZeros = (int)(((N*N)/100) * (100.0 - density));
        ArrayList<Double> nums = new ArrayList<Double>();
        for (int x = 0; x< countZeros; x++)nums.add(0.0);
        for (int x = 0; x< (N*N)-countZeros; x++)nums.add(ThreadLocalRandom.current().nextDouble(min, max + 1));
        Collections.shuffle(nums);
        int counter = 0;
        double[][] matrix = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                matrix[i][j] = nums.get(counter);
                counter++;
            }
        return matrix;
    }

    /**Метод для виводу матриці**/
    public void showMatrix (double [][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (double j: matrix[i]) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /** Віднімання двох матриць **/
    public double[][] sub(double[][] A, double[][] B)
    {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    /** Додавання  двох матриць  **/
    public double[][] add(double[][] A, double[][] B)
    {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    /** Функція для поділу батьківської матриці на дочірні матриці **/
    public void split(double[][] P, double[][] C, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    /** Функція для об’єднання дочірніх матриць у батьківську матрицю **/
    public void join(double[][] C, double[][] P, int iB, int jB)
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }

}
