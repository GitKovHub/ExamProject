import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WinogradThread {
    public int[][] multiply(int[][] A, int[][] B) throws ExecutionException, InterruptedException {
        MatrixOperations mo = new MatrixOperations();
        int n = A.length;
        int[][] R = new int[n][n];
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
        else if (n<=64)
        {
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];

            mo.split(A, A11, 0 , 0);
            mo.split(A, A12, 0 , n/2);
            mo.split(A, A21, n/2, 0);
            mo.split(A, A22, n/2, n/2);

            mo.split(B, B11, 0 , 0);
            mo.split(B, B12, 0 , n/2);
            mo.split(B, B21, n/2, 0);
            mo.split(B, B22, n/2, n/2);

            int [][] S1 = mo.add(A21, A22);
            int [][] S2 = mo.sub(S1, A11);
            int [][] S3 = mo.sub(A11, A21);
            int [][] S4 = mo.sub(A12, S2);
            int [][] S5 = mo.sub(B12, B11);
            int [][] S6 = mo.sub(B22, S5);
            int [][] S7 = mo.sub(B22, B12);
            int [][] S8 = mo.sub(S6, B21);

            int [][] P1 = multiply(S2,S6);
            int [][] P2 = multiply(A11,B11);
            int [][] P3 = multiply(A12,B21);
            int [][] P4 = multiply(S3,S7);
            int [][] P5 = multiply(S1,S5);
            int [][] P6 = multiply(S4,B22);
            int [][] P7 = multiply(A22,S8);

            int [][] T1 = mo.add(P1,P2);
            int [][] T2 = mo.add(T1,P4);

            int [][] C11 = mo.add(P2,P3);
            int [][] C12 = mo.add(mo.add(T1, P5),P6);
            int [][] C21 = mo.sub(T2, P7);
            int [][] C22 = mo.add(T2,P5);

            mo.join(C11, R, 0 , 0);
            mo.join(C12, R, 0 , n/2);
            mo.join(C21, R, n/2, 0);
            mo.join(C22, R, n/2, n/2);
        }
        else
        {
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];

            mo.split(A, A11, 0 , 0);
            mo.split(A, A12, 0 , n/2);
            mo.split(A, A21, n/2, 0);
            mo.split(A, A22, n/2, n/2);

            mo.split(B, B11, 0 , 0);
            mo.split(B, B12, 0 , n/2);
            mo.split(B, B21, n/2, 0);
            mo.split(B, B22, n/2, n/2);

            int [][] S1 = mo.add(A21, A22);
            int [][] S2 = mo.sub(S1, A11);
            int [][] S3 = mo.sub(A11, A21);
            int [][] S4 = mo.sub(A12, S2);
            int [][] S5 = mo.sub(B12, B11);
            int [][] S6 = mo.sub(B22, S5);
            int [][] S7 = mo.sub(B22, B12);
            int [][] S8 = mo.sub(S6, B21);

            //int [][] P1 = multiply(S2,S6);
            //int [][] P2 = multiply(A11,B11);
            //int [][] P3 = multiply(A12,B21);
            //int [][] P4 = multiply(S3,S7);
            //int [][] P5 = multiply(S1,S5);
            //int [][] P6 = multiply(S4,B22);
            //int [][] P7 = multiply(A22,S8);



            ExecutorService executor = Executors.newCachedThreadPool();
            List<FutureTask<int[][]>> taskList = new ArrayList<FutureTask<int[][]>>();
            // Start thread for the first half of the numbers
            FutureTask<int[][]> taskP1 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return multiply(S2,S6);
                }
            });
            FutureTask<int[][]> taskP2 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return multiply(A11,B11);
                }
            });
            FutureTask<int[][]> taskP3 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return  multiply(A12,B21);
                }
            });
            FutureTask<int[][]> taskP4 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return  multiply(S3,S7);
                }
            });
            FutureTask<int[][]> taskP5 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return multiply(S1,S5);
                }
            });
            FutureTask<int[][]> taskP6 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return multiply(S4,B22);
                }
            });
            FutureTask<int[][]> taskP7 = new FutureTask<int[][]>(new Callable<int[][]>() {
                @Override
                public int[][] call() throws InterruptedException, ExecutionException {
                    return multiply(A22,S8);
                }
            });

            taskList.add(taskP1);
            taskList.add(taskP2);
            taskList.add(taskP3);
            taskList.add(taskP4);
            taskList.add(taskP5);
            taskList.add(taskP6);
            taskList.add(taskP7);
            executor.execute(taskP1);
            executor.execute(taskP2);
            executor.execute(taskP3);
            executor.execute(taskP4);
            executor.execute(taskP5);
            executor.execute(taskP6);
            executor.execute(taskP7);

            FutureTask<int[][]> ftrTask = taskList.get(0);
            final int[][] P1 = ftrTask.get();
            FutureTask<int[][]> ftrTask1 = taskList.get(1);
            final int[][] P2 = ftrTask1.get();
            FutureTask<int[][]> ftrTask2 = taskList.get(2);
            final int[][] P3 = ftrTask2.get();
            FutureTask<int[][]> ftrTask3 = taskList.get(3);
            final int[][] P4 = ftrTask3.get();
            FutureTask<int[][]> ftrTask4 = taskList.get(4);
            final int[][] P5 = ftrTask4.get();
            FutureTask<int[][]> ftrTask5 = taskList.get(5);
            final int[][] P6 = ftrTask5.get();
            FutureTask<int[][]> ftrTask6 = taskList.get(6);
            final int[][] P7 = ftrTask6.get();
            executor.shutdown();

            int [][] T1 = mo.add(P1,P2);
            int [][] T2 = mo.add(T1,P4);

            int [][] C11 = mo.add(P2,P3);
            int [][] C12 = mo.add(mo.add(T1, P5),P6);
            int [][] C21 = mo.sub(T2, P7);
            int [][] C22 = mo.add(T2,P5);

            mo.join(C11, R, 0 , 0);
            mo.join(C12, R, 0 , n/2);
            mo.join(C21, R, n/2, 0);
            mo.join(C22, R, n/2, n/2);
        }
        return R;
    }

}
