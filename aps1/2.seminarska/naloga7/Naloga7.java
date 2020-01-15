import java.io.*;
import java.util.*;

/**
 * Naloga8
 */
public class Naloga7 {
    static PrintWriter izhod;
    static HashSet<BitField> checked = new HashSet<BitField>();
    static Node start;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        izhod = new PrintWriter(new FileOutputStream(args[1]));
        try {

            int n = Integer.parseInt(in.readLine());

            HashMap<Integer, Node> nodes = new HashMap<>();
            for (int i = 0; i < n; i++) {
                String[] lArr = in.readLine().split(",");
                int a = Integer.parseInt(lArr[0]);
                int b = Integer.parseInt(lArr[1]);
                double c = Double.parseDouble(lArr[2]);

                // prepare first
                Node node1 = nodes.get(a);
                if (node1 == null) {
                    node1 = new Node(a);
                    nodes.put(a, node1);
                }
                // prepare second
                Node node2 = nodes.get(b);
                if (node2 == null) {
                    node2 = new Node(b);
                    nodes.put(b, node2);
                }

                // connect them
                node1.add(node2, c);
                node2.add(node1, c);
            }

            String[] lArr = in.readLine().split(",");
            int a = Integer.parseInt(lArr[0]);
            double b = Double.parseDouble(lArr[1]);

            Stack<Node> s = new Stack<>(); // history of events
            BitField hs = new BitField(nodes.size()); // contents of history
            start = nodes.get(a);

            int out = 0;
            for (Conn c : start.sosedje) {
                s.push(c.sosed);
                hs.add(c.sosed);
                out += lookIn(s, hs, c.len, b);
                hs.remove(c.sosed);
                s.pop();
            }

            izhod.write(Integer.toString(out));

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

    static int lookIn(Stack<Node> history, BitField content, double len, double limit) {
        if (len > limit) {
            return 0;
        }
        Node node = history.peek();
        if (node == start && !checked.contains(content)) {
            checked.add(content.clone());
            return 1;
        }

        int out = 0;
        for (Conn c : node.sosedje) {
            if (content.contains(c.sosed)) {
                continue;
            }

            history.add(c.sosed);
            content.add(c.sosed);
            out += lookIn(history, content, len + c.len, limit);
            content.remove(c.sosed);
            history.pop();
        }
        return out;
    }

    static class Node {
        Set<Conn> sosedje;
        int n;

        Node(int n) {
            sosedje = new HashSet<Conn>();
            this.n = n;
        }

        void add(Node who, double len) {
            sosedje.add(new Conn(len, who));
        }

        public String toString() {
            return Integer.toString(n);
        }
    }

    static class Conn {
        double len;
        Node sosed;

        Conn(double l, Node s) {
            len = l;
            sosed = s;
        }
    }

    static class BitField {
        byte[] field;

        BitField() {
        }

        BitField(int n) {
            field = new byte[n];
        }

        public BitField clone() {
            BitField bf = new BitField();
            bf.field = Arrays.copyOf(field, field.length);
            return bf;
        }

        public boolean contains(Node c) {
            return field[c.n - 1] == 1;
        }

        void add(Node c) {
            field[c.n - 1] = 1;
        }

        void remove(Node c) {
            field[c.n - 1] = 0;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(field);
        }

        @Override
        public boolean equals(Object x) {
            if (x instanceof BitField) {
                BitField bf = (BitField) x;
                Arrays.equals(field, bf.field);
            }
            return true;
        }
    }
}
