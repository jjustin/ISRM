import java.util.Scanner;

/**
 * Izziv3
 */
public class Izziv3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[m];
        int[] out = new int[m + n];

        for (int i = 0; i < n; i++)
            a[i] = sc.nextInt();

        for (int j = 0; j < m; j++)
            b[j] = sc.nextInt();

        int i = 0;
        int j = 0;
        while (i != n || j != m) {
            if (i == n) {
                out[i + j] = b[j];
                System.out.print("b");
                j++;
                continue;
            }
            if (j == m) {
                out[i + j] = a[i];
                System.out.print("a");
                i++;
                continue;
            }

            if (a[i] <= b[j]) {
                out[i + j] = a[i];
                System.out.print("a");
                i++;
                continue;
            } else {
                out[i + j] = b[j];
                System.out.print("b");
                j++;
                continue;
            }
        }
        System.out.println();
        for (i = 0; i < out.length; i++) {
            System.out.print(out[i] + " ");
        }
    }
}