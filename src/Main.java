import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("available processors: " + Runtime.getRuntime().availableProcessors());
        MatrixOperations mg = new MatrixOperations();
        Scanner scan = new Scanner(System.in);
        int N = 4096;
       // for(int N = 64; N<32000; N*=2) {
           // System.out.println("N: "+N);
            int[][] A = mg.genMatrix(N, 1, 50);
            int[][] B = mg.genMatrix(N, 1, 50);
            //testWinograd(A, B);
            testWinogradThread(A, B);
            //System.out.println();
        //}

    }

    public static void testWinograd(int[][] A, int[][] B){
        Winograd w = new Winograd();
        double start = System.nanoTime();
        int[][] C = w.multiply(A,B);
        double end = System.nanoTime();
        System.out.println("testWinograd -> taken for n = "+A.length+" is : "+(end-start)/1000000000 + " Seconds");
    }

    public static void testWinogradThread(int[][] A, int[][] B) throws ExecutionException, InterruptedException {
        WinogradThread w = new WinogradThread();
        double start = System.nanoTime();
        int[][] C = w.multiply(A, B);
        double end = System.nanoTime();
        System.out.println("testWinogradThread -> taken for n = "+A.length+" is : "+(end-start)/1000000000 + " Seconds");
    }

    public static void testDefault(int[][] A, int[][] B, int threadCount) throws InterruptedException {
        DifMatrixMultiplication dmm = new DifMatrixMultiplication();
        double start = System.nanoTime();
        int[][] C = dmm.multiply(A, B, threadCount);
        double end = System.nanoTime();
        System.out.println("testDefault -> with " + threadCount + "thread(s) taken for n = "+A.length+" is : "+(end-start)/1000000000 + " Seconds");
    }

}