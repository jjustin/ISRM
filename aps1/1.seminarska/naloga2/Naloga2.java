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

        String[] cards = null;
        String[][] shuffles = null;
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
            cards = l.split(",");

            shuffles = new String[m][3];
            for (int i = 0; i < m; i++) {
                shuffles[i] = in.readLine().split(",");
            }

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (in != null)
                in.close();
        }

        List k2 = new List();
        for (String card : cards) {
            k2.add(card);
        }

        for (int i = 0; i < m; i++) {
            k2 = shuffle(k2, shuffles[i][0], shuffles[i][1],
                    (int) Integer.parseInt(shuffles[i][2].replaceAll("\\s+", "")));
        }

        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));
        izhod.write(k2.toString());
        izhod.close();
    }

    public static List shuffle(List k2, String split, String after, int len) {
        List k1 = k2;
        k2 = k1.split(split);

        k1.moveFrom(k2, len, after);

        return k1;
    }

    public static class List {
        ListElement zero;
        ListElement last;

        public List() {
            zero = new ListElement("");
            last = null;
        }

        public List(ListElement le) {
            zero = new ListElement("");
            zero.next = le;
            last = le;

            // in case le is null we dont have last.next
            if (le == null) {
                return;
            }
            // update last to the last element
            while (last.next != null) {
                last = last.next;
            }
        }

        // gets first element on list
        public ListElement first() {
            return zero.next;
        }

        // add new element
        public void add(String str) {
            if (last == null) {
                last = new ListElement(str);
                zero.next = last;
                return;
            }
            last.next = new ListElement(str);
            last = last.next;
        }

        // Splits on card `on`
        public List split(String on) {
            ListElement o = first();

            // find the element where on occurs
            while (o != null && !o.el.equals(on)) {
                o = o.next;
            }
            // if nothing was found move everything to new list an make this one blank
            if (o == null) {
                List out = new List(zero.next);
                zero.next = null;
                last = null;
                return out;
            }

            // move everything after o to new list
            List out = new List(o.next);
            // make o last element
            o.next = null;
            last = o;
            return out;
        }

        // moves in packets of `n` from `l` to `this` and puts every packet after
        // `after`
        public void moveFrom(List l, int n, String after) {
            ListElement o = first();

            // find element to place after
            while (o != null && !o.el.equals(after)) {
                o = o.next;
            }

            // if no element was found use root as add point
            if (o == null) {
                o = zero;
            }
            // we have to update last element
            if (o.next == null) {
                // TODO
            }

            while (l.zero.next != null) {
                ListElement toRelink = o.next;
                o.next = l.first();
                ListElement add = l.first();
                for (int i = 1; i < n && add.next != null; i++) {
                    add = add.next;
                }
                l.zero.next = add.next;
                add.next = toRelink;
            }
        }

        // string output
        public String toString() {
            if (zero.next == null) {
                return "";
            }
            String out = first().el;
            for (ListElement o = first().next; o != null; o = o.next) {
                out += "," + o.el;
            }
            return out;
        }
    }

    static class ListElement {
        String el;
        ListElement next;

        ListElement(String str) {
            el = str;
        }

        public String toString() {
            return el;
        }
    }
}