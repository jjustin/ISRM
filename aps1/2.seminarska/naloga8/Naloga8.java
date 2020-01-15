import java.io.*;
import java.util.Set;
import java.util.HashSet;

/**
 * Naloga8
 */
public class Naloga8 {
    static StringBuilder output = new StringBuilder();
    static PrintWriter izhod;
    static int currentNodes = 0;
    static int currentSum = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        izhod = new PrintWriter(new FileOutputStream(args[1]));
        try {

            int n = Integer.parseInt(in.readLine());

            GrafNode[][] map = new GrafNode[n][n];
            for (int i = 0; i < n; i++) {
                String[] lArr = in.readLine().split(",");
                for (int j = 0; j < lArr.length; j++) {
                    map[i][j] = new GrafNode(Integer.parseInt(lArr[j]), 0);
                }
            }

            for (int i = 1; i <= log2(n); i++) {
                int pow = (int) Math.pow(2, i - 1);
                for (int y = 0; y < n / pow; y += 2) {
                    for (int x = 0; x < n / pow; x += 2) {
                        GrafNode gn = new GrafNode(i);
                        gn.add(map[x][y]);
                        gn.add(map[x + 1][y]);
                        gn.add(map[x][y + 1]);
                        gn.add(map[x + 1][y + 1]);

                        map[x / 2][y / 2] = gn;
                    }
                }
            }

            int l = Integer.parseInt(in.readLine());

            for (int i = 0; i < l; i++) {
                currentSum = 0;
                currentNodes = 0;
                map[0][0].execute(Integer.parseInt(in.readLine()));
                izhod.println(currentSum + "," + currentNodes);
            }

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

    public static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    public static class GrafNode {
        Set<GrafNode> otroci;
        int min;
        int max;
        int globina;

        public GrafNode(int g) {
            otroci = new HashSet<GrafNode>();
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            globina = g;
        }

        // public GrafNode(int min, int max) {
        // otroci = new HashSet<GrafNode>();
        // this.max = max;
        // this.min = min;
        // }

        public GrafNode(int n, int g) {
            otroci = new HashSet<GrafNode>();
            this.max = n;
            this.min = n;
            globina = g;
        }

        public void add(GrafNode gn) {
            otroci.add(gn);
            if (gn.min < min) {
                min = gn.min;
            }
            if (gn.max > max) {
                max = gn.max;
            }
        }

        public void execute(int n) {
            currentNodes++;
            if (min == max || max <= n) {
                if (min <= n) {
                    currentSum += (int) Math.pow(4, globina);
                }
                return;
            }

            if (min <= n) {
                for (GrafNode gn : otroci) {
                    gn.execute(n);
                }
            }
        }
    }
}
