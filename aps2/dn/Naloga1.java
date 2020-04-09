import java.util.Scanner;

/**
 * Naloga1
 */
public class Naloga1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String type = sc.next();
        String alg = sc.next();
        String way = sc.next();
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        Sorter s = null;
        switch (alg) {
            case "bs":
                s = new BubbleSort(arr, way);
                break;
            case "ss":
                s = new SelectionSort(arr, way);
                break;
            case "is":
                s = new InsertionSort(arr, way);
                break;
            case "hs":
                s = new HeapSort(arr, way);
                break;
            case "qs":
                s = new QuickSort(arr, way);
                break;
            case "ms":
                s = new MergeSort(arr, way);
                break;
            case "cs":
                s = new CountingSort(arr, way);
                break;
            case "rs":
                s = new RadixSort(arr, way);
                break;
        }

        switch (type) {
            case "trace":
                s.trace();
                break;
            case "count":
                s.count();
                break;
        }
        sc.close();
    }

    static class BubbleSort extends Sorter {
        BubbleSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            int j = 0;
            while (j != table.length) {
                if (print) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < j; i++) {
                        sb.append(table[i]);
                        sb.append(" ");
                    }
                    sb.append("|");
                    for (int i = j; i < table.length; i++) {
                        sb.append(" ");
                        sb.append(table[i]);
                    }
                    System.out.println(sb.toString());
                }

                for (int i = table.length - 1; i > j; i--) {
                    if (isAGreater(table[i - 1], table[i])) {
                        switchVals(i, i - 1);
                    }
                }
                j++;

            }
        }
    }

    static class SelectionSort extends Sorter {
        SelectionSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            int j = 0;
            while (j != table.length) {
                if (print) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < j; i++) {
                        sb.append(table[i]);
                        sb.append(" ");
                    }
                    sb.append("|");
                    for (int i = j; i < table.length; i++) {
                        sb.append(" ");
                        sb.append(table[i]);
                    }
                    System.out.println(sb.toString());
                }

                // zdanje ne rabis
                if (j == table.length - 1) {
                    return;
                }

                int best = table[j];
                int ixBest = j;
                for (int i = j + 1; i < table.length; i++) {
                    if (isAGreater(best, table[i])) {
                        best = table[i];
                        ixBest = i;
                    }
                }
                switchVals(ixBest, j);
                j++;
            }
        }
    }

    static class InsertionSort extends Sorter {
        InsertionSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            int j = 1;
            while (j != table.length + 1) {
                if (print) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < j; i++) {
                        sb.append(table[i]);
                        sb.append(" ");
                    }
                    sb.append("|");
                    for (int i = j; i < table.length; i++) {
                        sb.append(" ");
                        sb.append(table[i]);
                    }
                    System.out.println(sb.toString());
                }

                if (j == table.length) {
                    return;
                }

                for (int i = j; i > 0 && isAGreater(table[i - 1], table[i]); i--) {
                    switchVals(i - 1, i);
                }
                j++;
            }
        }
    }

    static class HeapSort extends Sorter {
        int len;

        HeapSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            len = table.length;
            makeStartingHeap();
            if (print) {
                System.out.println(heapToString());
            }

            for (int i = len - 1; i > 0; i--) {
                switchVals(0, i);
                len--;
                fixHeap(0);
                if (print) {
                    System.out.println(heapToString());
                }
            }
        }

        void makeStartingHeap() {
            for (int i = (len / 2) - 1; i >= 0; i--) {
                fixHeap(i);
            }
        }

        void fixHeap(int root) {
            if (root > (len / 2) - 1) {
                return;
            }
            int firstIx = 2 * root + 1;
            int secondIx = 2 * root + 2;

            if (secondIx >= len) {
                if (isAGreater(table[firstIx], table[root])) {
                    switchVals(root, firstIx);
                    fixHeap(firstIx);
                }
                return;
            }

            if (isAGreater(table[firstIx], table[root])) { // levi vecji
                if (isAGreater(table[secondIx], table[firstIx])) { // desni najvecji
                    switchVals(root, secondIx);
                    fixHeap(secondIx);
                } else { // levi najvecji
                    switchVals(root, firstIx);
                    fixHeap(firstIx);
                }
            } else if (isAGreater(table[secondIx], table[root])) { // desni vecji in levi manjsi == desni najvecji
                switchVals(root, secondIx);
                fixHeap(secondIx);
            }
        }

        String heapToString() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < len; i++) {
                sb.append(table[i]);
                sb.append(" ");
                if (len - 1 != i && ((i + 2) & (i + 1)) == 0) {
                    sb.append("| ");
                }
            }

            return sb.toString();

        }

    }

    static class QuickSort extends Sorter {
        QuickSort(int[] t, String w) {
            super(t, w);
        }

        void trace(boolean print) {
            qs(0, table.length - 1, print);
        }

        void qs(int l, int r, boolean print) {
            if (r - l < 1)
                return;

            int i = l;
            int j = r;
            int pivot = table[i + (j - i) / 2];
            moveCount++;

            while (i <= j) {
                while (isAGreater(pivot, table[i]))
                    i++;
                while (isAGreater(table[j], pivot))
                    j--;

                if (i <= j) {
                    switchVals(i, j);
                    i++;
                    j--;
                }
            }

            if (print) {
                StringBuilder sb = new StringBuilder();
                for (int k = l; k <= j; k++) {
                    sb.append(table[k]);
                    sb.append(" ");
                }
                sb.append("| ");
                for (int k = j + 1; k < i; k++) {
                    sb.append(table[k]);
                    sb.append(" ");
                }
                sb.append("|");
                for (int k = i; k <= r; k++) {
                    sb.append(" ");
                    sb.append(table[k]);
                }
                System.out.println(sb.toString());
            }

            qs(l, j, print);
            qs(j + 1, i - 1, print);
            qs(i, r, print);
        }
    }

    static class MergeSort extends Sorter {
        MergeSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            table = ms(0, table.length - 1, print);
        }

        int[] ms(int l, int r, boolean print) {
            if (l == r) {
                moveCount++;
                return new int[] { table[l] };
            }
            // last in left half
            int splitIx = l + (r - l) / 2;

            if (print) {
                StringBuilder sb = new StringBuilder();
                for (int i = l; i <= splitIx; i++) {
                    sb.append(table[i]);
                    sb.append(" ");
                }
                sb.append("|");
                for (int i = splitIx + 1; i <= r; i++) {
                    sb.append(" ");
                    sb.append(table[i]);
                }
                System.out.println(sb.toString());
            }

            int[] left = ms(l, splitIx, print);
            int[] right = ms(splitIx + 1, r, print);

            int i = 0;
            int j = 0;
            int[] newT = new int[left.length + right.length];

            for (int k = 0; k < newT.length; k++) {
                if (i == left.length) {
                    newT[k] = right[j];
                    j++;
                } else if (j == right.length) {
                    newT[k] = left[i];
                    i++;
                } else {
                    if (isAGreater(left[i], right[j])) {
                        newT[k] = right[j];
                        j++;
                    } else {
                        newT[k] = left[i];
                        i++;
                    }
                }
                moveCount++;
            }

            if (print) {
                StringBuilder sb = new StringBuilder();
                for (i = 0; i < newT.length; i++) {
                    sb.append(newT[i]);
                    sb.append(" ");
                }
                System.out.println(sb.toString());
            }

            return newT;
        }
    }

    static class CountingSort extends Sorter {
        CountingSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            cs(0, print);
        }

        void cs(int whichByte, boolean print) {
            int[] countTable = new int[256];
            for (int i = 0; i < table.length; i++) {
                countTable[nthByte(table[i], whichByte)]++;
            }

            for (int i = 1; i < countTable.length; i++) {
                countTable[i] += countTable[i - 1];
            }

            if (print) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < countTable.length; i++) {
                    sb.append(countTable[i]);
                    sb.append(" ");
                }
                System.out.println(sb.toString());
            }

            // sort
            int[] sorted = new int[table.length];
            if (sortUp) {
                for (int i = table.length - 1; i >= 0; i--) {
                    int x = nthByte(table[i], whichByte);
                    int ix;
                    ix = --countTable[x];
                    sorted[ix] = table[i];
                    if (print) {
                        System.out.printf("%d ", ix);
                    }
                }
            } else {
                for (int i = 0; i < table.length - 1; i++) {
                    int x = nthByte(table[i], whichByte);
                    int ix;
                    ix = sorted.length - countTable[x]--;
                    sorted[ix] = table[i];
                    if (print) {
                        System.out.printf("%d ", ix);
                    }
                }
            }
            table = sorted;

            if (print) {
                System.out.println();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < table.length; i++) {
                    sb.append(table[i]);
                    sb.append(" ");
                }
                System.out.println(sb.toString());
            }
        }

        int nthByte(int a, int n) {
            return (a >> 8 * n) & 0xff;
        }
    }

    static class RadixSort extends CountingSort {
        RadixSort(int[] t, String w) {
            super(t, w);
        }

        @Override
        void trace(boolean print) {
            for (int i = 0; i < 4; i++) {
                cs(i, print);
            }
        }
    }

    static class Sorter {
        int[] table;
        boolean sortUp;
        int compareCount = 0;
        int moveCount = 0;

        Sorter(int[] t, String w) {
            table = t;
            sortUp = w.equals("up");
        }

        void trace() {
            trace(true);
        };

        void trace(boolean print) {
        };

        // true -> switch
        // false -> don't switch
        boolean isAGreater(int a, int b) {
            compareCount++;
            if (sortUp) {
                return a > b;
            } else {
                return a < b;
            }
        }

        void switchVals(int i, int j) {
            int tmp = table[i];
            table[i] = table[j];
            table[j] = tmp;
            moveCount += 3;
        }

        void count() {
            trace(false);
            printData();
            resetCountData();
            trace(false);
            printData();
            resetCountData();
            sortUp = !sortUp;
            trace(false);
            printData();
        }

        void printData() {
            System.out.printf("%d %d%n", compareCount, moveCount);
        }

        void resetCountData() {
            compareCount = 0;
            moveCount = 0;
        }
    }
}