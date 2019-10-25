import java.io.*;

/**
 * Naloga2
 */
public class Naloga2 {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga2 <vhod> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));

        String[] karte = null;
        String[][] mesanja = null;
        int n = 0;
        int m = 0;
        try {
            String l = in.readLine();

            String[] lArr = l.split(",");
            if (lArr.length != 2) {
                System.out.printf("Napaka pri branju prvih podatkov: %s", l);
                return;
            }

            n = Integer.parseInt(lArr[0]);
            m = Integer.parseInt(lArr[1]);

            // Preberi mrezo
            l = in.readLine();
            karte = l.split(",");

            mesanja = new String[m][3];
            for (int i = 0; i < m; i++) {
                mesanja[i] = in.readLine().split(",");
            }

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (in != null)
                in.close();
        }

        List k2 = new List();
        for (int i = 0; i < n; i++) {
            k2.set(i, karte[i]);
        }

        for (int i = 0; i < m; i++) {
            k2 = shuffle(k2, mesanja[i][0], mesanja[i][1],
                    (int) Integer.parseInt(mesanja[i][2].replaceAll("\\s+", "")));
        }

        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));
        izhod.write(k2.toString());
        izhod.close();
    }

    public static List shuffle(List k2, String split, String after, int len) {
        List k1 = new List();
        // find split index
        int splitIx = k2.length() - 1;
        List o = k2;
        for (int i = 0; i < k2.length(); i++) {
            if (o.el.equals(split)) {
                splitIx = i;
                break;
            }
            o = o.next;
        }
        // split k2
        k1 = k2;
        k2 = k1.split(splitIx);

        List insertAfter = new List();

        while (k2 != null) {
            // make new list with items to insert into insertAfter
            List k3 = k2;
            k2 = k3.split(len - 1);

            List nxt = insertAfter.next;
            insertAfter.next = k3;
            k3.last().next = nxt;
        }

        for (o = k1; o != null; o = o.next) {
            if (o.el.equals(after)) {
                insertAfter.last().next = o.next;
                o.next = insertAfter.next;
                break;
            }
        }
        if (o == null) {
            insertAfter.last().next = k1;
            k1 = insertAfter.next;
        }

        return k1;
    }

    public static class List {
        String el;
        List next;

        public List() {
            el = "";
        }

        public List(List o) {
            this.el = "";
            this.next = o;
        }

        public void set(int i, String el) {
            List o = this;
            for (int j = 0; j < i; j++) {
                if (o.next == null) {
                    o.setNext(new List());
                }
                o = o.next;
            }
            o.el = el;
        }

        public List last() {
            List o = this;
            while (o.next != null) {
                o = o.next;
            }
            return o;
        }

        public void setNext(List n) {
            next = n;
        }

        public boolean hasNext() {
            return next != null;
        }

        public List nth(int n) {
            List o = this;
            for (int i = 0; i < n; i++) {
                if (next == null) {
                    return null;
                }
                o = o.next;
            }
            return o;
        }

        public int length() {
            int i = 1;
            List o = this;
            for (i = 0; o.next != null || o.next == this; i++) {
                o = o.next;
            }
            return i;
        }

        public List split(int i) {
            List o = this.nth(i);
            if (o == null) {
                return null;
            }

            List out = o.next;
            o.next = null;
            return out;
        }

        public String toString() {
            String out = this.el;
            for (List o = this.next; o != null; o = o.next) {
                out += "," + o.el;
            }
            return out;
        }
    }
}