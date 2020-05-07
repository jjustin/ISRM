import java.util.Scanner;

public class Naloga2Matrike {
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String type = sc.next();
        int blSize = 0;
        if (type.equals("bl") || type.equals("dv") || type.equals("st")) {
            blSize = sc.nextInt();
        }
        int n = sc.nextInt();
        int m = sc.nextInt();
        Matrix a;
        Matrix b;
        int[][] mat = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mat[i][j] = sc.nextInt();
            }
        }
        a = new Matrix(mat);

        n = sc.nextInt();
        m = sc.nextInt();
        mat = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mat[i][j] = sc.nextInt();
            }
        }
        b = new Matrix(mat);

        if (type.equals("dv") || type.equals("st")) {
            int size = (int) Math.pow(2,
                    Math.ceil(Math.log(Math.max(a.n, Math.max(a.m, Math.max(b.n, b.m))) / Math.log(2))));

            a = a.extend(size, size);
            b = b.extend(size, size);
        }

        switch (type) {
            case "os":
                os(a, b);
                break;
            case "bl":
                bl(a, b, blSize);
                break;
            case "dv":
                sb.append(dv(a, b, blSize));
                break;
            case "st":
                sb.append(st(a, b, blSize));
                break;
        }
        System.out.println(sb.toString());
    }

    static void os(Matrix a, Matrix b) {
        sb.append(a.times(b).toString());
    }

    static void bl(Matrix a, Matrix b, int blSize) {
        Matrix c = new Matrix(a.n, b.m);

        int n = a.n / blSize + ((a.n % blSize != 0) ? 1 : 0); // st. blokov v visino v c
        int m = b.m / blSize + ((b.m % blSize != 0) ? 1 : 0); // st. blokov v sirino v c
        int o = a.m / blSize + ((a.m % blSize != 0) ? 1 : 0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Matrix asub = a.subMatrix(0, blSize, i * blSize, (i + 1) * blSize);
                Matrix bsub = b.subMatrix(j * blSize, (j + 1) * blSize, 0, blSize);
                Matrix cij = asub.times(bsub);
                sb.append(cij.elSum());
                sb.append("\n");

                for (int k = 1; k < o; k++) {
                    asub = a.subMatrix(k * blSize, (k + 1) * blSize, i * blSize, (i + 1) * blSize);
                    bsub = b.subMatrix(j * blSize, (j + 1) * blSize, k * blSize, (k + 1) * blSize);

                    Matrix curr = asub.times(bsub);
                    sb.append(curr.elSum());
                    sb.append("\n");
                    cij = cij.add(curr);
                }

                c.setSubmatrix(j * blSize, i * blSize, cij);
            }
        }
        sb.append(c.toString());
    }

    // vhodne matrike kvadratne velikosti 2^n
    static Matrix dv(Matrix a, Matrix b, int cutoff) {
        if (a.n <= cutoff) {
            return a.times(b);
        }

        Matrix a11 = a.subMatrix(0, a.n / 2, 0, a.n / 2);
        Matrix a12 = a.subMatrix(a.n / 2, a.n, 0, a.n / 2);
        Matrix a21 = a.subMatrix(0, a.n / 2, a.n / 2, a.n);
        Matrix a22 = a.subMatrix(a.n / 2, a.n, a.n / 2, a.n);

        Matrix b11 = b.subMatrix(0, b.n / 2, 0, b.n / 2);
        Matrix b12 = b.subMatrix(b.n / 2, b.n, 0, b.n / 2);
        Matrix b21 = b.subMatrix(0, b.n / 2, b.n / 2, b.n);
        Matrix b22 = b.subMatrix(b.n / 2, b.n, b.n / 2, b.n);

        Matrix a11b11 = dv(a11, b11, cutoff);
        sb.append(a11b11.elSum());
        sb.append("\n");
        Matrix a12b21 = dv(a12, b21, cutoff);
        sb.append(a12b21.elSum());
        sb.append("\n");
        Matrix a11b12 = dv(a11, b12, cutoff);
        sb.append(a11b12.elSum());
        sb.append("\n");
        Matrix a12b22 = dv(a12, b22, cutoff);
        sb.append(a12b22.elSum());
        sb.append("\n");
        Matrix a21b11 = dv(a21, b11, cutoff);
        sb.append(a21b11.elSum());
        sb.append("\n");
        Matrix a22b21 = dv(a22, b21, cutoff);
        sb.append(a22b21.elSum());
        sb.append("\n");
        Matrix a21b12 = dv(a21, b12, cutoff);
        sb.append(a21b12.elSum());
        sb.append("\n");
        Matrix a22b22 = dv(a22, b22, cutoff);
        sb.append(a22b22.elSum());
        sb.append("\n");

        Matrix c = new Matrix(a.n, a.n);

        c.setSubmatrix(0, 0, a11b11.add(a12b21));
        c.setSubmatrix(a.n / 2, 0, a11b12.add(a12b22));
        c.setSubmatrix(0, a.n / 2, a21b11.add(a22b21));
        c.setSubmatrix(a.n / 2, a.n / 2, a21b12.add(a22b22));

        return c;
    }

    static Matrix st(Matrix a, Matrix b, int cutoff) {
        if (a.n <= cutoff) {
            return a.times(b);
        }

        Matrix a11 = a.subMatrix(0, a.n / 2, 0, a.n / 2);
        Matrix a12 = a.subMatrix(a.n / 2, a.n, 0, a.n / 2);
        Matrix a21 = a.subMatrix(0, a.n / 2, a.n / 2, a.n);
        Matrix a22 = a.subMatrix(a.n / 2, a.n, a.n / 2, a.n);

        Matrix b11 = b.subMatrix(0, b.n / 2, 0, b.n / 2);
        Matrix b12 = b.subMatrix(b.n / 2, b.n, 0, b.n / 2);
        Matrix b21 = b.subMatrix(0, b.n / 2, b.n / 2, b.n);
        Matrix b22 = b.subMatrix(b.n / 2, b.n, b.n / 2, b.n);

        Matrix m1 = st(a11, b12.sub(b22), cutoff); // a11(b12-b22)
        sb.append(m1.elSum() + "\n");
        Matrix m2 = st(a11.add(a12), b22, cutoff); // (a11+a12)b22
        sb.append(m2.elSum() + "\n");
        Matrix m3 = st(a21.add(a22), b11, cutoff); // (a21+a22)b11
        sb.append(m3.elSum() + "\n");
        Matrix m4 = st(a22, b21.sub(b11), cutoff); // a22(b21-b11)
        sb.append(m4.elSum() + "\n");
        Matrix m5 = st(a11.add(a22), b11.add(b22), cutoff); // (a11+a22)(b11+b22)
        sb.append(m5.elSum() + "\n");
        Matrix m6 = st(a12.sub(a22), b21.add(b22), cutoff); // (a12-a22)(b21+b22)
        sb.append(m6.elSum() + "\n");
        Matrix m7 = st(a11.sub(a21), b11.add(b12), cutoff); // (a21-a11)(b11+b12)
        sb.append(m7.elSum() + "\n");

        Matrix c11 = m5.add(m4).sub(m2).add(m6);
        Matrix c12 = m1.add(m2);
        Matrix c21 = m3.add(m4);
        Matrix c22 = m1.add(m5).sub(m3).sub(m7);
        Matrix c = new Matrix(a.n, a.n);

        c.setSubmatrix(0, 0, c11);
        c.setSubmatrix(a.n / 2, 0, c12);
        c.setSubmatrix(0, a.n / 2, c21);
        c.setSubmatrix(a.n / 2, a.n / 2, c22);

        return c;
    }

}

class Matrix {
    int[][] mat;
    int n;
    int m;

    Matrix(int[][] t) {
        n = t.length;
        m = t[0].length;
        mat = t;
    }

    Matrix(int a, int b) {
        n = a;
        m = b;
        mat = new int[n][m];
    }

    Matrix subMatrix(int x1, int x2, int y1, int y2) {
        int newN = Math.min(y2, n);
        int newM = Math.min(x2, m);
        int[][] newMat = new int[newN - y1][newM - x1];

        for (int i = 0; i < newN - y1; i++) {
            for (int j = 0; j < newM - x1; j++) {
                newMat[i][j] = mat[y1 + i][x1 + j];
            }
        }

        return new Matrix(newMat);
    }

    void setSubmatrix(int x, int y, Matrix a) {
        for (int i = y; i < y + a.n; i++) {
            for (int j = x; j < x + a.m; j++) {
                mat[i][j] = a.mat[i - y][j - x];
            }
        }
    }

    Matrix times(int x) {
        int[][] newMat = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMat[i][j] = mat[i][j] * x;
            }
        }

        return new Matrix(newMat);
    }

    Matrix times(Matrix a) {
        if (a.n != m) {
            System.out.println("times: size mismatch");
        }
        int[][] newMat = new int[n][a.m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < a.m; j++) {
                int sum = 0;
                for (int k = 0; k < m; k++) {
                    sum += mat[i][k] * a.mat[k][j];
                }
                newMat[i][j] = sum;
            }
        }

        return new Matrix(newMat);
    }

    Matrix add(Matrix a) {
        if (a.n != n && a.m != m) {
            System.out.println("add: size mismatch");
        }
        int[][] newMat = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMat[i][j] = mat[i][j] + a.mat[i][j];
            }
        }

        return new Matrix(newMat);
    }

    Matrix sub(Matrix a) {
        if (a.n != n && a.m != m) {
            System.out.println("add: size mismatch");
        }
        int[][] newMat = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMat[i][j] = mat[i][j] - a.mat[i][j];
            }
        }

        return new Matrix(newMat);
    }

    Matrix extend(int a, int b) {
        if (a < n || b < m) {
            System.out.println("extends size mismatch");
        }
        int[][] mm = new int[a][b];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                mm[i][j] = mat[i][j];
            }
        }

        return new Matrix(mm);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DIMS: " + n + "x" + m + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(mat[i][j]);
                sb.append(" ");
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    int elSum() {
        int out = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                out += mat[i][j];
            }
        }
        return out;
    }
}