import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;

/**
 * Naloga6
 */
public class Naloga6 {
    static StringBuilder output = new StringBuilder();
    static int bele, crne = 0;
    static PrintWriter izhod;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        izhod = new PrintWriter(new FileOutputStream(args[1]));
        try {
            HashMap<String, GrafNode> graf = new HashMap<String, GrafNode>();

            // Dobi visino in sirino
            String l = in.readLine();

            int n = Integer.parseInt(l);

            GrafNode g1 = null;
            // naredi graf povezav
            for (int i = 0; i < n; i++) {
                l = in.readLine();
                String[] lArr = l.split("-");

                // prvi del
                g1 = graf.get(lArr[0]);
                // naredi nov node
                if (g1 == null) {
                    g1 = new GrafNode(lArr[0]);
                    graf.put(lArr[0], g1);
                }
                // drugi del
                GrafNode g2 = graf.get(lArr[1]);
                if (g2 == null) {
                    g2 = new GrafNode(lArr[1]);
                    graf.put(lArr[1], g2);
                }
                g1.add(g2);
                g2.add(g1);
            }

            g1.color(1);

            if (crne == bele) {
                rageQuit();
            }
            int searchFor = crne < bele ? 1 : 2; // vzemi tiste, ki jih je manj

            for (GrafNode gn : graf.values()) {
                if (gn.color == searchFor) {
                    output.append(gn.name);
                    output.append("\n");
                }
            }

            izhod.write(output.toString());

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

    public static void rageQuit() { // invalid input
        output.append("-1");
        izhod.write(output.toString());
        izhod.close();
        System.exit(0);
    }

    public static class GrafNode {
        Set<GrafNode> sosedje;
        int color = 0; // 0 -> nepobarvano, 1 -> 1, 2 -> 2
        String name;

        public GrafNode(String name) {
            sosedje = new HashSet<GrafNode>();
            this.name = name;
        }

        public void add(GrafNode name) {
            sosedje.add(name);
        }

        public void color(int color) {
            this.color = color;

            if (color == 1) { // trenutno vozljisce je belo
                bele++;
                color = 2;
            } else { // vozljisce je crno
                crne++;
                color = 1;
            }

            Iterator<GrafNode> itr = sosedje.iterator();
            while (itr.hasNext()) {
                GrafNode nxt = itr.next();
                if (nxt.color != 0 && nxt.color != color) {
                    rageQuit();
                }
                if (nxt.color == 0) {
                    nxt.color(color); // pobarava vse sosede z neko barvo
                }
            }
        }
    }
}
