import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Izziv5
 */
public class Izziv5 {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String l = "";
        try {
            l = br.readLine();
        } catch (Exception x) {
        }

        String[] strA = l.split(" ");
        int[] a = new int[strA.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.parseInt(strA[i]);
        }

        findBest(a, 0, a.length - 1);
    }

    public static int findBest(int[] a, int l, int r) {
        if (l == r) {
            System.out.printf("[%d]: %d%n", a[l], a[l]);
            return a[l];
        }

        int split = (l + r) / 2;
        int left = findBest(a, l, split);
        int right = findBest(a, split + 1, r);
        int sub = findBestSub(a, l, r);
        int max = Math.max(left, Math.max(sub, right));

        System.out.printf("%s: %d%n", Arrays.toString(Arrays.copyOfRange(a, l, r + 1)), max);
        return max;
    }

    public static int findBestSub(int[] a, int l, int r) {
        int split = (l + r) / 2;
        int sub = a[split] + a[split + 1];
        int max = Integer.MIN_VALUE;

        int i = split - 1;
        int j = split + 2;
        while (i >= l || j <= r) {
            max = Math.max(sub, max);
            if (i < l) {
                sub += a[j];
                j++;
                continue;
            } else if (j > r) {
                sub += a[i];
                i--;
                continue;
            }

            if (a[i] > a[j]) {
                sub += a[i];
                i--;
                continue;
            } else {
                sub += a[j];
                j++;
                continue;
            }
        }
        return Math.max(sub, max);
    }
}