public class Winograd {
    public int[][] multiply(int[][] A, int[][] B)
    {
        MatrixOperations mo = new MatrixOperations();
        int n = A.length;
        int[][] R = new int[n][n];
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
        else
        {
            //System.out.println("rec");
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
        return R;
    }
}
