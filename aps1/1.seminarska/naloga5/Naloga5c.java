import java.io.*;

public class Naloga5c {
    static int stPasov = 0; // stevilo trakov
    static int dolizinaPasov = 0; // stevilo elementov na traku
    static int stBoxov = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga5 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader input = new BufferedReader(new FileReader(args[0]));

        int[] beginningState = null;
        int[] endState = null;

        AmazonAI solution = null;

        try {
            // Dobi visino in sirino
            String l = input.readLine();

            String[] lArr = l.split(",");
            if (lArr.length != 2) {
                System.out.printf("Napaka pri branju visine/sirine: %s", l);
                return;
            }

            // Parsing
            stPasov = Integer.parseInt(lArr[0]); // n pasov
            dolizinaPasov = Integer.parseInt(lArr[1]); // p elementov na pasu

            char[] startStateAll = new char[stPasov * dolizinaPasov + 1]; // +1 je cart
            startStateAll[0] = '0';
            for (int i = 1; i <= stPasov; i++) {
                l = input.readLine();
                l = l.substring(2);
                lArr = l.split(",");
                for (int j = 0; j < dolizinaPasov; j++) {
                    int x = dolizinaPasov * (i - 1) + j + 1;
                    if (lArr.length > j && lArr[j].length() >= 1) {
                        startStateAll[x] = lArr[j].charAt(0);
                        stBoxov++;
                    } else {
                        startStateAll[x] = '0';
                    }
                }
            }

            beginningState = new int[stBoxov]; // +1 je cart
            endState = new int[stBoxov];

            int endBoxCount = 0;

            for (int i = 0; i < startStateAll.length; i++) {
                if (startStateAll[i] != '0') {
                    beginningState[startStateAll[i] - 'A'] = i;
                }
            }
            for (int i = 1; i <= stPasov; i++) {
                l = input.readLine();
                l = l.substring(2);
                lArr = l.split(",");
                for (int j = 0; j < dolizinaPasov; j++) {
                    if (lArr.length > j && lArr[j].length() >= 1) {
                        int x = dolizinaPasov * (i - 1) + j + 1;
                        endState[lArr[j].charAt(0) - 'A'] = x;
                        endBoxCount++;
                    }
                }
            }
            int[] endBoxes = new int[endBoxCount];
            endBoxCount = 0;
            for (int i = 0; i < endState.length; i++) {
                if (endState[i] != 0) {
                    endBoxes[endBoxCount] = i;
                } else {
                    endState[i] = -1;
                }
            }

            AmazonAI start = new AmazonAI(beginningState, 1, "", 0, -1);
            AmazonAI end = new AmazonAI(endState, 0, "", 0, -1);

            Queue toCheck = new Queue();
            toCheck.push(start);
            Map badAIs = new Map(1000000);

            AmazonAI current;
            do {
                AmazonAI next = toCheck.pop();

                // ce je ze preverjeno
                if (badAIs.exists(next)) {
                    continue;
                }

                // move
                if (next.last != 1) { // da se ne premakne dvakrat zapored
                    for (int i = 1; i <= stPasov; i++) {
                        if (i == next.cartAt) {
                            continue;
                        }
                        toCheck.push(next.move(i));
                    }
                }

                // preveri, ce je trenutni trak prazen
                int startIx = (next.cartAt - 1) * dolizinaPasov + 1;
                int startBox = -2;
                int endIx = next.cartAt * dolizinaPasov;

                boolean cont = false;
                for (int i = 0; i < stBoxov; i++) {
                    if (next.status[i] >= startIx && next.status[i] <= endIx) {
                        cont = true;
                        if (startIx == next.status[i]) {
                            startBox = i;
                        }
                    }
                }

                if (next.last != 2 && next.inCart != -1 // vozicek je poln in prejsnja poteza ni nalozitev
                        && startBox == -2) { // zacetek traku prazen
                    current = next.unload(startIx);
                    if (current.equalsIgnoreCart(end)) {
                        solution = current;
                        break;
                    }
                    toCheck.push(current);
                }

                if (cont) {
                    if (next.last != 3 && next.inCart == -1 // vozicek je prazen in prejsnja poteza ni odlozitev
                            && startBox != -2) { // na koncu traku je element

                        current = next.load(startBox);
                        if (current.equalsIgnoreCart(end)) {
                            solution = current;
                            break;
                        }
                        toCheck.push(current);
                    }

                    current = next.up(startIx, endIx);
                    if (current.destroyed == -1 || !in(current.destroyed, endBoxes)) {
                        if (current.equalsIgnoreCart(end)) {
                            solution = current;
                            break;
                        }
                        toCheck.push(current);
                    }
                    current = next.down(startIx, endIx);
                    if (current.destroyed == -1 || !in(current.destroyed, endBoxes)) {
                        if (current.equalsIgnoreCart(end)) {
                            solution = current;
                            break;
                        }
                        toCheck.push(current);
                    }
                }

                badAIs.push(next);
            } while (!toCheck.empty());

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (input != null)
                input.close();
        }

        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));
        if (solution != null) {
            izhod.write(solution.history());
        } else {
            izhod.write("solution not found");
        }

        izhod.close();
    }

    public static boolean in(int n, int[] in) {
        for (int x : in) {
            if (n == x) {
                return true;
            }
        }
        return false;
    }

    static class AmazonAI {
        // 0 je v cartu
        int[] status;
        // 1 - n
        int cartAt;
        // -1 je prazen
        int inCart;
        // 1 = move
        // 2 = load ...
        int last;
        String history;
        int destroyed;

        AmazonAI(int[] s, int cartAt, String history, int last, int inCart) {
            status = s;
            this.cartAt = cartAt;
            this.last = last;
            this.history = history;
            this.inCart = inCart;
        }

        public AmazonAI move(int i) {
            AmazonAI out = new AmazonAI(copyStatus(), i, history + "PREMIK " + i + "\n", 1, inCart);
            return out;
        }

        void loadB(int boxIX) {
            inCart = boxIX;
            status[boxIX] = 0;
        }

        public AmazonAI load(int boxIX) {
            AmazonAI out = new AmazonAI(copyStatus(), cartAt, history + "NALOZI\n", 2, inCart);
            out.loadB(boxIX);
            return out;
        }

        void unloadB(int to) {
            status[inCart] = to;
            inCart = -1;
        }

        public AmazonAI unload(int to) {
            AmazonAI out = new AmazonAI(copyStatus(), cartAt, history + "ODLOZI\n", 3, inCart);
            out.unloadB(to);
            return out;
        }

        void upB(int start, int stop) {
            destroyed = -1;
            for (int i = 0; i < stBoxov; i++) {
                if (status[i] >= start && status[i] <= stop) {
                    if (status[i] == stop) {
                        destroyed = i;
                        status[i] = -1;
                        continue;
                    }
                    status[i] = status[i] + 1;
                }
            }
        }

        public AmazonAI up(int start, int stop) {
            AmazonAI out = new AmazonAI(copyStatus(), cartAt, history + "GOR\n", 4, inCart);
            out.upB(start, stop);
            return out;
        }

        void downB(int start, int stop) {
            destroyed = -1;
            for (int i = 0; i < stBoxov; i++) {
                if (status[i] >= start && status[i] <= stop) {
                    if (status[i] == start) {
                        destroyed = i;
                        status[i] = -1;
                        continue;
                    }
                    status[i] = status[i] - 1;
                }
            }
        }

        // DOL
        public AmazonAI down(int start, int stop) {
            AmazonAI out = new AmazonAI(copyStatus(), cartAt, history + "DOL\n", 5, inCart);
            out.downB(start, stop);
            return out;
        }

        public int[] copyStatus() {
            int[] out = new int[status.length];
            for (int i = 0; i < out.length; i++) {
                out[i] = status[i];
            }
            return out;
        }

        public boolean equalsIgnoreCart(AmazonAI end) {
            for (int i = 0; i < status.length; i++) {
                if (status[i] == 0 && end.status[i] == -1) {
                    continue;
                }
                if (status[i] != end.status[i]) {
                    return false;
                }
            }
            return true;
        }

        public boolean equals(AmazonAI end) {
            if (cartAt != end.cartAt) {
                return false;
            }
            for (int i = 0; i < status.length; i++) {
                if (status[i] != end.status[i]) {
                    return false;
                }
            }
            return true;
        }

        public String history() {
            return history;
        }

        public int hashCode() {
            int out = 0;
            for (int i = 0; i < status.length; i++) {
                out *= status[i];
            }
            return out * (29 * cartAt + 31 * inCart);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Cart @ " + cartAt + " with " + status[0] + "\n");

            for (int i = 0; i < stPasov; i++) {
                sb.append("[");
                for (int j = 0; j < dolizinaPasov; j++) {
                    int x = dolizinaPasov * i + j + 1;
                    sb.append(status[x]);
                    sb.append(",");
                }
                sb.append("]\n");
            }

            return sb.toString();
        }
    }

    public static class Map {
        Set[] table;

        public Map(int size) {
            table = new Set[size];

            for (int i = 0; i < table.length; i++) {
                table[i] = new Set();
            }
        }

        private int hash(AmazonAI d) {
            return Math.abs(d.hashCode()) % table.length;
        }

        public void push(AmazonAI k) {
            Set l = table[hash(k)];
            Object pos = l.locate(k);
            if (pos == null) {
                l.insert(k);
            }
        }

        public boolean exists(AmazonAI k) {
            Set l = table[hash(k)];

            Object pos = l.locate(k);
            return pos != null;
        }
    }

    public static class Set {
        Set.Element first;

        static class Element {
            AmazonAI element;
            Element next;

            Element() {
                element = null;
                next = null;
            }
        }

        public Set() {
            first = new Set.Element();
        }

        public void insert(AmazonAI obj) {
            if (locate(obj) == null) {
                Set.Element newSE = new Set.Element();
                newSE.element = obj;
                newSE.next = first.next;
                first.next = newSE;
            }
        }

        public void delete(Set.Element pos) {
            pos.next = pos.next.next;
        }

        public AmazonAI retrieve(Set.Element pos) {
            return pos.next.element;
        }

        public Set.Element locate(AmazonAI obj) {
            // check if obj exists in iter
            for (Set.Element iter = first.next; iter != null; iter = iter.next) {
                if (obj.equals(iter.element)) {
                    return iter;
                }
            }

            return null;
        }
    }

    static class Queue {
        class Element {
            AmazonAI element;
            Element next;

            Element() {
                element = null;
                next = null;
            }
        }

        Element front;
        Element rear;

        public Queue() {
            front = null;
            rear = null;
        }

        public boolean empty() {
            return (front == null);
        }

        public void push(AmazonAI obj) {
            Element el = new Element();
            el.element = obj;
            el.next = null;

            if (empty()) {
                front = el;
            } else {
                rear.next = el;
            }

            rear = el;
        }

        public AmazonAI pop() {
            AmazonAI out = front.element;
            if (!empty()) {
                front = front.next;

                if (front == null)
                    rear = null;
            }
            return out;
        }
    }
}