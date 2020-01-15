import java.io.*;
import java.util.*;

/**
 * Naloga8
 */
public class Naloga10 {
    static StringBuilder output = new StringBuilder();
    static PrintWriter izhod;
    static int currentNodes = 0;
    static int currentSum = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga10 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        izhod = new PrintWriter(new FileOutputStream(args[1]));
        try {
            String[] lArr = in.readLine().split(",");
            List<Integer> arr = new ArrayList<Integer>();
            for (int i = 0; i < lArr.length; i++) {
                arr.add(Integer.parseInt(lArr[i]));
            }

            ArrayList<Integer> out = new ArrayList<Integer>();
            int maxI = 0;
            Queue<List<Integer>> q = new LinkedList<>();
            q.add(arr);

            while (q.peek() != null) {
                arr = q.remove();
                if (arr.size() == 0) {
                    continue;
                }
                maxI = max(arr);

                out.add(arr.get(maxI));
                q.add(arr.subList(0, maxI));
                q.add(arr.subList(maxI + 1, arr.size()));
            }

            StringBuilder sb = new StringBuilder();
            for (int x : out) {
                sb.append(x);
                sb.append(',');
            }
            sb.setLength(sb.length() - 1);

            izhod.write(sb.toString());
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (in != null)
                in.close();
            if (izhod != null)
                izhod.close();
        }
    }

    static int max(List<Integer> arr) {
        int i = 0;
        int max = 0;
        int maxI = 0;
        for (int x : arr) {
            if (max < x) {
                max = x;
                maxI = i;
            }
            i++;
        }
        return maxI;
    }
}
