public class Strassen
{
    /** Function to multiply matrices **/
    public int[][] multiply(int[][] A, int[][] B)
    {
        MatrixOperations mo = new MatrixOperations();
        int n = A.length;
        int[][] R = new int[n][n];
        /** base case **/
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
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

            /**
             M1 = (A11 + A22)(B11 + B22)
             M2 = (A21 + A22) B11
             M3 = A11 (B12 - B22)
             M4 = A22 (B21 - B11)
             M5 = (A11 + A12) B22
             M6 = (A21 - A11) (B11 + B12)
             M7 = (A12 - A22) (B21 + B22)
             **/

            int [][] M1 = multiply(mo.add(A11, A22), mo.add(B11, B22));
            int [][] M2 = multiply(mo.add(A21, A22), B11);
            int [][] M3 = multiply(A11, mo.sub(B12, B22));
            int [][] M4 = multiply(A22, mo.sub(B21, B11));
            int [][] M5 = multiply(mo.add(A11, A12), B22);
            int [][] M6 = multiply(mo.sub(A21, A11), mo.add(B11, B12));
            int [][] M7 = multiply(mo.sub(A12, A22), mo.add(B21, B22));

            /**
             C11 = M1 + M4 - M5 + M7
             C12 = M3 + M5
             C21 = M2 + M4
             C22 = M1 - M2 + M3 + M6
             **/
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