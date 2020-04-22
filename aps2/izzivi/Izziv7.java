import java.util.LinkedList;
import java.util.Scanner;

public class Izziv7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int p = n;
        LinkedList<Integer> roots = new LinkedList<Integer>();
        while (roots.size() == 0) {
            p = nextPrime(p);
            for (int i = 2; i < p; i++) {
                int x = i;
                boolean viable = true;
                for (int j = 2; j <= n; j++) {
                    if (x == 1) {
                        viable = false;
                        break;
                    }
                    x = (x * i) % p;
                }
                if (viable && x == 1) {
                    roots.add(i);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(p);
        sb.append(": ");
        for (Integer i : roots) {
            sb.append(i);
            sb.append(" ");
        }

        int[][] vand = new int[n][n];
        int nthRoot = roots.getFirst();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    vand[i][j] = 1;
                    continue;
                }
                if (i == 1) {
                    vand[i][j] = powerMod(nthRoot, j, p);
                }
                vand[i][j] = (vand[i - 1][j] * vand[1][j]) % p;
            }
        }

        System.out.println(sb.toString());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    System.out.printf("%2d", vand[i][j]);
                } else {
                    System.out.printf("%3d", vand[i][j]);
                }
            }
            System.out.println();
        }
    }

    public static int nextPrime(int n) {
        n++;
        if (n % 2 == 0) {
            n++;
        }
        while (true) {
            if (isPrime(n)) {
                return n;
            }
            n += 2;
        }
    }

    public static boolean isPrime(int number) {
        if (number <= 2) {
            return false;
        }
        double sqrt = Math.ceil(Math.sqrt(number));
        for (int i = 3; i < sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    static int powerMod(int x, int y, int p) {
        int out = 1;

        while (y > 0) {
            if ((y & 1) == 1) // pow 2
                out = (out * x) % p;

            y = y >> 1; // div 2
            x = (x * x) % p;
        }
        return out;
    }
}