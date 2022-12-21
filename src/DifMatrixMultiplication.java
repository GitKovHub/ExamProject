public class DifMatrixMultiplication
{
    static void mm0(int[][] A, int[][] B, int[][] R) // writes to R
    {
        int N = A.length;
        for(int i = 0;i<N;i++)
            for(int j=0;j<N;j++)
            {
                R[i][j] = 0;
                for(int k=0;k<N;k++)
                    R[i][j] += A[i][k] * B[k][j];
            }
    }

    public int[][] multiply(int[][] A, int[][] B, int cores) throws InterruptedException {
        int n = A.length;
        int[][] R = new int[n][n];

        mm0(A,B,R);
            // create worker threads
            Thread[] Ts = new Thread[cores]; // keep track of threads created.
            int Rs = n/cores; // number of rows to assign to each thread
            for(int w=0;w<cores;w++)
            {
                int Ra = Rs; // rows assigned
                if (w==cores-1) // last thread may get more rows
                    Ra += n%cores;
                workercore wc = new workercore(A,B,R,Rs*w,Ra);
                Ts[w] = new Thread(wc);
                Ts[w].start(); // start right away
            }
            for(int w=0;w<cores;w++) Ts[w].join(); // wait for each to finish
       return R;
    }

}

class workercore implements Runnable
{
    int[][] A;  // pointer to external matrices, AXB = R
    int[][] B;
    int[][] R;
    int start, N; // starting row and number of rows.
    public workercore(int[][] a, int[][] b, int[][] c, int s, int n)
    {A=a; B=b; R=c; start=s; N=n;}

    public void run()
    {
        int Cols = A[0].length;
        for(int i = start;i<N+start && i<Cols;i++)
            for(int j=0;j<Cols;j++)
            {
                R[i][j] = 0;
                for(int k=0;k<Cols;k++)
                    R[i][j] += A[i][k] * B[k][j];
            }
    }
}