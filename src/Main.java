import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MatrixOperations mg = new MatrixOperations();
        Scanner scan = new Scanner(System.in);
        Strassen s = new Strassen();
        Strassen7Threads s7 = new Strassen7Threads();
        int N = scan.nextInt();
        int min = scan.nextInt();
        int max = scan.nextInt();
        int[][] A = mg.genMatrix(N,min,max);
        int[][] B = mg.genMatrix(N,min,max);

        System.out.println("\nA");
        mg.showMatrix(A);

        System.out.println("\nB");
        mg.showMatrix(B);

        /*long time = System.currentTimeMillis();
        int[][] C = s.multiply(A, B);
        System.out.println("\nS");
        mg.showMatrix(C);
        System.out.println(System.currentTimeMillis() - time);*/

        long time = System.currentTimeMillis();
        int[][] C7 = s7.multiply(A, B);
        System.out.println("\nS7");
        mg.showMatrix(C7);
        System.out.println((System.currentTimeMillis() - time)/1000 + "sec");
    }

}