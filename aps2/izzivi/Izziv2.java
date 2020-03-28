import java.util.Scanner;

/**
 * Izziv2
 */
public class Izziv2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Heap heap = new Heap(n);
        for (int i = 0; i < n; i++) {
            heap.add(sc.nextInt());
        }
        heap.makeStartingHeap();

        System.out.println(heap.toString());
        for (int i = 0; i < n; i++) {
            heap.pop();
            System.out.println(heap.toString());
        }
    }

    static class Heap {
        int[] storage;
        int len;
        int n;

        Heap(int n) {
            storage = new int[n];
            len = 0;
            this.n = n;
        }

        void add(int a) {
            storage[len] = a;
            len++;
        }

        void makeStartingHeap() {
            for (int i = (n / 2) - 1; i >= 0; i--) {
                fixHeap(i);
            }
        }

        void fixHeap(int root) {
            if (root > (n / 2) - 1) {
                return;
            }
            int firstIx = 2 * root + 1;
            int secondIx = 2 * root + 2;

            if (secondIx >= len) {
                if (storage[root] < storage[firstIx]) {
                    switchIx(root, firstIx);
                    fixHeap(firstIx);
                }
                return;
            }

            if (storage[root] < storage[firstIx]) { // levi vecji
                if (storage[firstIx] < storage[secondIx]) { // desni najvecji
                    switchIx(root, secondIx);
                    fixHeap(secondIx);
                } else { // levi najvecji
                    switchIx(root, firstIx);
                    fixHeap(firstIx);
                }
            } else if (storage[root] < storage[secondIx]) { // desni vecji in levi manjsi == desni najvecji
                switchIx(root, secondIx);
                fixHeap(secondIx);
            }

        }

        int pop() {
            int out = storage[0];

            storage[0] = storage[len - 1];
            len--;
            int aIx = 0;
            int secondIx = 2 * aIx + 1;
            while ((secondIx < len && storage[secondIx] > storage[aIx])
                    || (secondIx + 1 < len && storage[secondIx + 1] > storage[aIx])) {
                int replaceIx = secondIx;
                if (storage[secondIx + 1] > storage[secondIx]) { // vzemi vecjo
                    replaceIx++;
                }

                // zamenjaj in posodobi inekse
                switchIx(replaceIx, aIx);
                aIx = replaceIx;
                secondIx = 2 * aIx + 1;
            }

            return out;
        }

        private void switchIx(int a, int b) {
            int tmp = storage[a];
            storage[a] = storage[b];
            storage[b] = tmp;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < len; i++) {
                sb.append(storage[i]);
                sb.append(" ");
                if (len - 1 != i && ((i + 2) & (i + 1)) == 0) {
                    sb.append("| ");
                }
            }

            return sb.toString();
        }

    }
}