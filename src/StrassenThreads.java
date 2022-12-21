class StrassenThread {
    public static int[][] strassen(int[][]A, int[][]B) {
        MatrixOperations mo = new MatrixOperations();
        int n = A.length;
        int[][] R = new int[n][n];
        if(n==1)
            R[0][0] = A[0][0]*B[0][0];
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
            class Mul implements Runnable {
                private Thread t;
                private String threadName;
                Mul(String name)
                {
                    threadName=name;
                }
                public void run()
                {
                    if(threadName.equals("thread1"))
                    {
                        mo.split(A,A11,0,0);
                    }
                    else if(threadName.equals("thread2"))
                    {
                        mo.split(A,A12,0,n/2);
                    }

                    else if(threadName.equals("thread3"))
                    {
                        mo.split(A,A21,n/2,0);
                    }

                    else if(threadName.equals("thread4"))
                    {
                        mo.split(A,A22,n/2,n/2);
                    }

                    else if(threadName.equals("thread5"))
                    {
                        mo.split(B,B11,0,0);
                    }

                    else if(threadName.equals("thread6"))
                    {
                        mo.split(B,B12,0,n/2);
                    }

                    else if(threadName.equals("thread7"))
                    {
                        mo.split(B,B21,n/2,0);
                    }

                    else if(threadName.equals("thread8"))
                    {
                        mo.split(B,B22,n/2,n/2);
                    }
                }

                public void start() {
                    if(t==null) {
                        t=new Thread(this,"Thread1");
                        t.start();
                    }
                }
            }
            Mul m1=new Mul("thread1");
            m1.start();
            Mul m2=new Mul("thread2");
            m2.start();
            Mul m3=new Mul("thread3");
            m3.start();
            Mul m4=new Mul("thread4");
            m4.start();
            Mul m5=new Mul("thread5");
            m5.start();
            Mul m6=new Mul("thread6");
            m6.start();
            Mul m7=new Mul("thread7");
            m7.start();
            Mul m8=new Mul("thread8");
            m8.start();
            for (Thread t : new Thread[] { m1.t,m2.t,m3.t,m4.t,m5.t,m6.t,m7.t,m8.t }) {
                try {
                    t.join();
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            }


            int [][] M1 = strassen(mo.add(A11, A22), mo.add(B11, B22));
            int [][] M2 = strassen(mo.add(A21, A22), B11);
            int [][] M3 = strassen(B11, mo.sub(B12, B22));
            int [][] M4 = strassen(A22, mo.sub(B21, B11));
            int [][] M5 = strassen(mo.add(A11, A12), B22);
            int [][] M6 = strassen(mo.sub(A21, A11), mo.add(B11, B12));
            int [][] M7 = strassen(mo.sub(A12, A22), mo.add(B21, B22));

            int [][] C11 = mo.add(mo.sub(mo.add(M1, M4), M5), M7);
            int [][] C12 = mo.add(M3, M5);
            int [][] C21 = mo.add(M2, M4);
            int [][] C22 = mo.add(mo.sub(mo.add(M1, M3), M2), M6);

            class Mul2 implements Runnable {
                private Thread t;
                private String threadName;
                Mul2(String name) {
                    threadName=name;
                }

                public void run() {
                    if(threadName.equals("thread1")) {
                        mo.join(C11, R, 0 , 0);
                    }
                    else if(threadName.equals("thread2")) {
                        mo.join(C12, R, 0 , n/2);
                    }

                    else if(threadName.equals("thread3")) {
                        mo.join(C21, R, n/2, 0);

                    }

                    else if(threadName.equals("thread4")) {
                        mo.join(C22, R, n/2, n/2);
                    }

                }

                public void start() {
                    if(t==null) {
                        t=new Thread(this,"Thread1");
                        t.start();
                    }
                }
            }
            Mul2 m11=new Mul2("thread1");
            m11.start();
            Mul2 m22=new Mul2("thread2");
            m22.start();
            Mul2 m33=new Mul2("thread3");
            m33.start();
            Mul2 m44=new Mul2("thread4");
            m44.start();
            for (Thread t : new Thread[] { m11.t,m22.t,m33.t,m44.t }) {
                try {
                    t.join();
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            }
        }
        return R;
    }

}