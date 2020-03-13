/**
 * Izziv1 63180016
 */
public class Izziv1 {
    public static void main(String[] args) {

        // Warmup
        for (int i = 1000; i <= 10000; i += 1000) {
            long l = timeLinear(i);
            long b = timeBinary(i);
        }

        // Prepare table
        System.out.printf("%8s | %8s | %8s \n", "n", "linearno", "binarno");
        System.out.printf("---------+----------+----------\n");
        for (int i = 1000; i <= 100000; i += 1000) {
            long l = timeLinear(i);
            long b = timeBinary(i);
            System.out.printf("%8d | %8d | %8d \n", i, l, b);
        }
    }

    static int[] generateTable(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        return a;
    }

    static int findLinear(int[] a, int v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v)
                return i;
        }
        return -1;
    }

    static int findBinary(int[] a, int l, int r, int v) {
        if (l > r)
            return -1;

        int i = ((r - l) / 2) + l; // split index
        if (v > a[i]) {
            return findBinary(a, i + 1, r, v); // iskana vrednost je na desni od i
        } else if (v == a[i]) {
            return (l + r) / 2; // najdeno, vrni trenutni index
        }
        return findBinary(a, l, i - 1, v); // vrednost je na levi od i
    }

    static long timeLinear(int n) {
        int[] a = generateTable(n);

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int rand = (int) (Math.random() * n);
            findLinear(a, rand);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    static long timeBinary(int n) {
        int[] a = generateTable(n);

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            int rand = (int) (Math.random() * n);
            findBinary(a, 0, n - 1, rand);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }
}