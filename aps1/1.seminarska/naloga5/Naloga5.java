import java.io.*;

public class Naloga5 {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader input = new BufferedReader(new FileReader(args[0]));

        char[][] pasovi = null;
        int n = 0; // stevilo trakov
        int p = 0; // stevilo elementov na traku

        char[][] koncnoStanje = null;

        try {
            // Dobi visino in sirino
            String l = input.readLine();

            String[] lArr = l.split(",");
            if (lArr.length != 2) {
                System.out.printf("Napaka pri branju visine/sirine: %s", l);
                return;
            }

            // Parsing
            n = Integer.parseInt(lArr[0]);
            p = Integer.parseInt(lArr[1]);

            pasovi = new char[n][p];
            koncnoStanje = new char[n][p];

            for (char[] pas : pasovi) {
                l = input.readLine();
                l = l.substring(2);
                lArr = l.split(",");
                for (int i = 0; i < p; i++) {
                    if (lArr.length <= i || lArr[i].length() < 1) {
                        pas[i] = '0';
                    } else {
                        pas[i] = lArr[i].charAt(0);
                    }
                }
            }

            for (char[] pas : koncnoStanje) {
                l = input.readLine();
                l = l.substring(2);
                lArr = l.split(",");
                for (int i = 0; i < p; i++) {
                    if (lArr.length <= i || lArr[i].length() < 1) {
                        pas[i] = '0';
                    } else {
                        pas[i] = lArr[i].charAt(0);
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (input != null)
                input.close();
        }

        Skladisce s = new Skladisce(pasovi, p, n);
        s.solve();

        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));
        izhod.write(s.toString());
        izhod.close();
    }

    static class Skladisce {
        char[][] status;
        int p;
        int n;

        int cart;
        char loaded;
        boolean cartFull;

        String history;

        Skladisce(char[][] s, int p, int n) {
            status = s;
            this.p = p;
            this.n = n;

            cart = 1;
            loaded = '0';
            cartFull = false;

            history = "";
        }

        public void move(int i) {
            cart = i;

            history += "PREMIK " + Integer.toString(i + 1) + "\n";
        }

        public void load() {
            if (cartFull || status[cart][0] == '0') {
                return;
            }

            loaded = status[cart][0];
            status[cart][0] = '0';
            cartFull = true;

            history += "NALOZI \n";
        }

        public void unload() {
            if (!cartFull || status[cart][0] != '0') {
                return;
            }

            status[cart][0] = loaded;
            loaded = '0';
            cartFull = false;

            history += "ODLOZI\n";
        }

        public void up() {
            for (int i = 1; i < status[cart].length; i++) {
                status[cart][i] = status[cart][i - 1];
            }
            status[cart][0] = '0';

            history += "GOR\n";
        }

        public void down() {
            for (int i = 1; i < status[cart].length; i++) {
                status[cart][i - 1] = status[cart][i];
            }
            status[cart][p - 1] = '0';

            history += "DOL\n";
        }

        public void solve() {
            move(1);
            up();
            down();
            move(2);
            load();
            move(0);
            unload();
        }

        public String toString() {
            return history;
        }
    }
}
