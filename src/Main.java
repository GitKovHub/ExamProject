import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("available processors: " + Runtime.getRuntime().availableProcessors());
        MatrixOperations mg = new MatrixOperations();
        Scanner scan = new Scanner(System.in);
        DifMatrixMultiplication dmm = new DifMatrixMultiplication();
        Strassen s = new Strassen();
        StrassenThread st = new StrassenThread();
        Strassen7Threads s7 = new Strassen7Threads();
        Winograd w = new Winograd();
        WinogradThread wt = new WinogradThread();
        int N = scan.nextInt();
        int[][] A = mg.genMatrix(N,1,50);
        int[][] B = mg.genMatrix(N,1,50);

        System.out.println("\nA");
        //mg.showMatrix(A);

        System.out.println("\nB");
        //mg.showMatrix(B);

        long time = System.currentTimeMillis();
        int[][] C = wt.multiply(A,B);
        //System.out.println("\nS");
        //mg.showMatrix(C);
        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        C = w.multiply(A, B);
        //System.out.println("\nS");
        //mg.showMatrix(C);
        System.out.println(System.currentTimeMillis() - time);

        /*long time = System.currentTimeMillis();
        int[][] C7 = s7.multiply(A, B);
        System.out.println("\nS7");
        mg.showMatrix(C7);
        System.out.println((System.currentTimeMillis() - time)/1000 + "sec");*/
    }

}