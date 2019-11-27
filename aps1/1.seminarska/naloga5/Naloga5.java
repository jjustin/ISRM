import java.io.*;

public class Naloga5 {
    static int stPasov = 0; // stevilo trakov
    static int dolizinaPasov = 0; // stevilo elementov na traku
    static int stBoxov = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader input = new BufferedReader(new FileReader(args[0]));

        char[] beginningState = null;
        char[] endState = null;

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

            beginningState = new char[stPasov * dolizinaPasov + 1]; // +1 je cart
            endState = new char[stPasov * dolizinaPasov + 1];
            beginningState[0] = '0';
            endState[0] = '0';

            for (int i = 1; i <= stPasov; i++) {
                l = input.readLine();
                l = l.substring(2);
                lArr = l.split(",");
                for (int j = 0; j < dolizinaPasov; j++) {
                    int x = dolizinaPasov * (i - 1) + j + 1;
                    if (lArr.length <= j || lArr[j].length() < 1) {
                        beginningState[x] = '0';
                    } else {
                        beginningState[x] = lArr[j].charAt(0);
                        stBoxov++;
                    }
                }
            }

            int endBoxesCount = 0;
            for (int i = 1; i <= stPasov; i++) {
                l = input.readLine();
                l = l.substring(2);
                lArr = l.split(",");
                for (int j = 0; j < dolizinaPasov; j++) {
                    int x = dolizinaPasov * (i - 1) + j + 1;
                    if (lArr.length <= j || lArr[j].length() < 1) {
                        endState[x] = '0';
                    } else {
                        endState[x] = lArr[j].charAt(0);
                        endBoxesCount++;
                    }
                }
            }

            char[] endBoxes = new char[endBoxesCount];
            endBoxesCount = 0;
            for (char x : endState) {
                if (x != '0') {
                    endBoxes[endBoxesCount] = x;
                    endBoxesCount++;
                }
            }

            AmazonAI start = new AmazonAI(beginningState, 1, "", 0);
            AmazonAI end = new AmazonAI(endState, 0, "", 0);

            Queue toCheck = new Queue();

            Map badAIs = new Map(10);

            AmazonAI next = start;
            do {
                // ce je ze preverjeno
                if (badAIs.exists(next)) {
                    next = toCheck.pop();
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
                int endIx = next.cartAt * dolizinaPasov;
                boolean cont = false;
                for (int i = startIx; i <= endIx; i++) {
                    if (next.status[i] != '0') {
                        cont = true;
                        continue;
                    }
                }

                if (next.last != 2 && next.status[0] != '0' // vozicek je poln in prejsnja poteza ni nalozitev
                        && next.status[startIx] == '0') { // zacetek traku prazen
                    toCheck.push(next.unload());
                }

                if (cont) {
                    if (next.last != 3 && next.status[0] == '0' // vozicek je prazen in prejsnja poteza ni odlozitev
                            && next.status[startIx] != '0') { // na koncu traku je element
                        toCheck.push(next.load());
                    }

                    AmazonAI newState = next.up();
                    if (newState.containsBoxes(endBoxes)) {
                        toCheck.push(newState);
                    }

                    newState = next.down();
                    if (newState.containsBoxes(endBoxes)) {
                        toCheck.push(newState);
                    }
                }

                badAIs.push(next);
                next = toCheck.pop();
            } while (!toCheck.empty() && !next.equalsIgnoreCart(end));

            solution = next;

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

    static class AmazonAI {
        char[] status;
        int cartAt;
        // 1 = move
        // 2 = load ...
        int last;
        String history;

        AmazonAI(char[] s, int cartAt, String history, int last) {
            status = s;
            this.cartAt = cartAt;
            this.last = last;
            this.history = history;
        }

        public AmazonAI move(int i) {
            AmazonAI out = new AmazonAI(status, i, history + "PREMIK " + i + "\n", 1);
            return out;
        }

        public AmazonAI load() {
            int i = (cartAt - 1) * dolizinaPasov + 1;
            char[] working = copyStatus();

            char moving = working[i];
            working[i] = '0';
            working[0] = moving;

            return new AmazonAI(working, cartAt, history + "NALOZI\n", 2);
        }

        public AmazonAI unload() {
            int i = (cartAt - 1) * dolizinaPasov + 1;
            char[] working = copyStatus();

            char moving = working[0];
            working[0] = '0';
            working[i] = moving;

            return new AmazonAI(working, cartAt, history + "ODLOZI\n", 3);
        }

        public AmazonAI up() {
            int start = (cartAt - 1) * dolizinaPasov + 1;
            int end = cartAt * dolizinaPasov;
            char[] working = copyStatus();

            working[start] = '0';
            for (int i = start + 1; i <= end; i++) {
                working[i] = status[i - 1];
            }

            return new AmazonAI(working, cartAt, history + "GOR\n", 4);
        }

        // DOL
        public AmazonAI down() {
            int start = (cartAt - 1) * dolizinaPasov + 1;
            int end = cartAt * dolizinaPasov;
            char[] working = copyStatus();

            for (int i = start; i < end; i++) {
                working[i] = status[i + 1];
            }
            working[end] = '0';

            return new AmazonAI(working, cartAt, history + "DOL\n", 5);
        }

        public char[] copyStatus() {
            char[] out = new char[status.length];
            for (int i = 0; i < out.length; i++) {
                out[i] = status[i];
            }
            return out;
        }

        public boolean containsBoxes(char[] wantedBoxes) {
            char[] hasLetters = new char[stBoxov + 1];
            int i = 0;
            for (char x : status) {
                if (x != '0') {
                    hasLetters[i] = x;
                    i++;
                }
            }

            for (i = 0; i < wantedBoxes.length; i++) {
                for (int j = 0; j < hasLetters.length; j++) {
                    if (hasLetters[j] == 0) { // if end is hit
                        return false;
                    }
                    if (hasLetters[j] == wantedBoxes[i]) {
                        break;
                    }
                }
            }

            return true;
        }

        public boolean equalsIgnoreCart(AmazonAI end) {
            for (int i = 1; i < status.length; i++) {
                if (end.status[i] != status[i]) {
                    return false;
                }
            }
            return true;
        }

        public boolean equals(AmazonAI end) {
            if (equalsIgnoreCart(end)) {
                if (end.status[0] == status[0] && cartAt == end.cartAt) {
                    return true;
                }
            }
            return false;
        }

        public String history() {
            return history;
        }

        public int hashCode() {
            int out = 0;
            for (int i = 0; i < status.length; i++) {
                out += i * status[i];
            }
            return out;
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
            for (Set.Element iter = first; iter.next != null; iter = iter.next) {
                if (obj.equals(retrieve(iter))) {
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