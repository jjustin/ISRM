import java.util.Scanner;

/**
 * Izziv4
 */
public class Izziv4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] a = new int[n];
        // read
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        // create and fill count array
        int[] count = new int[33];
        for (int i = 0; i < 33; i++) { // make it zero
            count[i] = 0;
        }
        for (int i = 0; i < n; i++) { // count the elements in `a`
            count[Integer.bitCount(a[i])]++;
        }
        for (int i = 1; i < 33; i++) { // comulate
            count[i] += count[i - 1];
        }

        // sort
        int[] sorted = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int x = a[i];
            int ix = --count[Integer.bitCount(x)];
            System.out.printf("(%d,%d)%n", x, ix);
            sorted[ix] = x;
        }
        for (int x : sorted) {
            System.out.printf("%d ", x);
        }
    }
}