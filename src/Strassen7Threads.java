import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Strassen7Threads {
    public int[][] multiply(int[][] A, int[][] B) throws ExecutionException, InterruptedException {
        MatrixOperations mo = new MatrixOperations();
        int n = A.length;
        int[][] R = new int[n][n];
        /** base case **/
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

            int [][] M1 = multiply(mo.add(A11, A22), mo.add(B11, B22));
            int [][] M2 = multiply(mo.add(A21, A22), B11);
            int [][] M3 = multiply(A11, mo.sub(B12, B22));
            int [][] M4 = multiply(A22, mo.sub(B21, B11));
            int [][] M5 = multiply(mo.add(A11, A12), B22);
            int [][] M6 = multiply(mo.sub(A21, A11), mo.add(B11, B12));
            int [][] M7 = multiply(mo.sub(A12, A22), mo.add(B21, B22));

            int [][] C11 = mo.add(mo.sub(mo.add(M1, M4), M5), M7);
            int [][] C12 = mo.add(M3, M5);
            int [][] C21 = mo.add(M2, M4);
            int [][] C22 = mo.add(mo.sub(mo.add(M1, M3), M2), M6);

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

            /** Dividing matrix A into 4 halves **/
            mo.split(A, A11, 0 , 0);
            mo.split(A, A12, 0 , n/2);
            mo.split(A, A21, n/2, 0);
            mo.split(A, A22, n/2, n/2);
            /** Dividing matrix B into 4 halves **/
            mo.split(B, B11, 0 , 0);
            mo.split(B, B12, 0 , n/2);
            mo.split(B, B21, n/2, 0);
            mo.split(B, B22, n/2, n/2);

    ExecutorService executor = Executors.newCachedThreadPool();
    List<FutureTask<int[][]>> taskList1 = new ArrayList<FutureTask<int[][]>>();
    // Start thread for the first half of the numbers
    FutureTask<int[][]> futureTask_2 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(mo.add(A11, A22),mo. add(B11, B22));
        }
    });
    FutureTask<int[][]> futureTask_3 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(mo.add(A21, A22), B11);
        }
    });
    FutureTask<int[][]> futureTask_4 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(A11, mo.sub(B12, B22));
        }
    });
    FutureTask<int[][]> futureTask_5 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(A22, mo.sub(B21, B11));
        }
    });
    FutureTask<int[][]> futureTask_6 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(mo.add(A11, A12), B22);
        }
    });
    FutureTask<int[][]> futureTask_7 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(mo.sub(A21, A11), mo.add(B11, B12));
        }
    });
    FutureTask<int[][]> futureTask_8 = new FutureTask<int[][]>(new Callable<int[][]>() {
        @Override
        public int[][] call() throws InterruptedException, ExecutionException {
            return multiply(mo.sub(A12, A22), mo.add(B21, B22));
        }
    });
    taskList1.add(futureTask_2);
    taskList1.add(futureTask_3);
    taskList1.add(futureTask_4);
    taskList1.add(futureTask_5);
    taskList1.add(futureTask_6);
    taskList1.add(futureTask_7);
    taskList1.add(futureTask_8);
    executor.execute(futureTask_2);
    executor.execute(futureTask_3);
    executor.execute(futureTask_4);
    executor.execute(futureTask_5);
    executor.execute(futureTask_6);
    executor.execute(futureTask_7);
    executor.execute(futureTask_8);

    FutureTask<int[][]> ftrTask = taskList1.get(0);
    final int[][] M1 = ftrTask.get();
    FutureTask<int[][]> ftrTask1 = taskList1.get(1);
    final int[][] M2 = ftrTask1.get();
    FutureTask<int[][]> ftrTask2 = taskList1.get(2);
    final int[][] M3 = ftrTask2.get();
    FutureTask<int[][]> ftrTask3 = taskList1.get(3);
    final int[][] M4 = ftrTask3.get();
    FutureTask<int[][]> ftrTask4 = taskList1.get(4);
    final int[][] M5 = ftrTask4.get();
    FutureTask<int[][]> ftrTask5 = taskList1.get(5);
    final int[][] M6 = ftrTask5.get();
    FutureTask<int[][]> ftrTask6 = taskList1.get(6);
    final int[][] M7 = ftrTask6.get();
    executor.shutdown();

            int [][] C11 = mo.add(mo.sub(mo.add(M1, M4), M5), M7);
            int [][] C12 = mo.add(M3, M5);
            int [][] C21 = mo.add(M2, M4);
            int [][] C22 = mo.add(mo.sub(mo.add(M1, M3), M2), M6);

            /** join 4 halves into one result matrix **/
            mo.join(C11, R, 0 , 0);
            mo.join(C12, R, 0 , n/2);
            mo.join(C21, R, n/2, 0);
            mo.join(C22, R, n/2, n/2);
        }
        /** return result **/
        return R;
        }
}
