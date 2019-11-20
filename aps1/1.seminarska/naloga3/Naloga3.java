import java.io.*;

/**
 * Naloga3
 */
public class Naloga3 {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga3 <vhod> <izhod>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));

        List nums = new List();
        oper[] opers = null;
        try {
            // Dobi visino in sirino
            String l = in.readLine();

            String[] lArr = l.split(",");

            for (int i = 0; i < lArr.length; i++) {
                nums.add(Integer.parseInt(lArr[i]));
            }

            l = in.readLine();

            int n = Integer.parseInt(l);

            opers = new oper[n];

            // Preberi mrezo
            for (int i = 0; i < n; i++) {
                oper o = new oper();
                l = in.readLine();
                lArr = l.split(",");
                o.oper = lArr[0].charAt(0);
                o.a = lArr[1].charAt(0);
                if (o.oper != 'z') {
                    o.b = Integer.parseInt(lArr[2]);
                }

                opers[i] = o;
            }
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (in != null)
                in.close();
        }

        for (oper oper : opers) {
            switch (oper.oper) {
            case 'p':
                nums.preslikaj(oper.a, oper.b);
                break;
            case 'o':
                nums.ohrani(oper.a, oper.b);
                break;
            case 'z':
                nums.zdruzi(oper.a);
                break;
            }

            izhod.write(nums.toString());
        }

        izhod.close();
    }

    static class oper {
        char oper;
        char a;
        int b;
    }

    static class List {
        ListElement first;
        ListElement last;

        List() {
            first = new ListElement();
            last = null;
        }

        void add(int i) {
            ListElement e = new ListElement(i);
            if (last == null) {
                last = first;
            }
            last.next = e;
            last = last.next;
        }

        void preslikaj(char oper, int val) {
            switch (oper) {
            case '+':
                for (ListElement c = first.next; c != null; c = c.next) {
                    c.el = c.el + val;
                }
                break;
            case '*':
                for (ListElement c = first.next; c != null; c = c.next) {
                    c.el = c.el * val;
                }
                break;
            }
        }

        void ohrani(char oper, int val) {
            ListElement prev = first;
            switch (oper) {
            case '<':
                for (ListElement c = first.next; c != null; c = c.next) {
                    if (c.el >= val) {
                        prev.next = c.next;
                    } else {
                        prev = prev.next;
                    }
                }
                break;

            case '>':
                for (ListElement c = first.next; c != null; c = c.next) {
                    if (c.el <= val) {
                        prev.next = c.next;
                    } else {
                        prev = prev.next;
                    }
                }

                break;

            case '=':
                for (ListElement c = first.next; c != null; c = c.next) {
                    if (c.el != val) {
                        prev.next = c.next;
                    } else {
                        prev = prev.next;
                    }
                }

                break;
            }
        }

        void zdruzi(char oper) {
            int end = 0;
            switch (oper) {
            case '+':
                for (ListElement c = first.next; c != null; c = c.next) {
                    end += c.el;
                }
                break;

            case '*':
                end = 1;
                for (ListElement c = first.next; c != null; c = c.next) {
                    end *= c.el;
                }

                break;
            }

            ListElement e = new ListElement(end);
            first.next = e;
            last = first;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (ListElement c = first.next; c != null; c = c.next) {
                sb.append(c.el);
                if (c.next != null) {
                    sb.append(",");
                }
            }

            sb.append('\n');
            return sb.toString();
        }

    }

    static class ListElement {
        int el;
        ListElement next;

        ListElement() {
        }

        ListElement(int i) {
            el = i;
        }
    }

}