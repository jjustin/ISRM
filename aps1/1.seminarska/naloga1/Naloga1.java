import java.io.*;

/**
 * Naloga1
 */
public class Naloga1 {
    static String output = "";

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uporaba: java Naloga1 <mreža> <izhod>");
            System.exit(1);
        }

        BufferedReader mreza = new BufferedReader(new FileReader(args[0]));
        PrintWriter izhod = new PrintWriter(new FileOutputStream(args[1]));

        char[][] mr = null;
        String[] besede = null;
        int h = 0;
        int w = 0;
        int n = 0;
        try {
            // Dobi visino in sirino
            String l = mreza.readLine();

            String[] lArr = l.split(",");
            if (lArr.length != 2) {
                System.out.printf("Napaka pri branju visine/sirine: %s", l);
                return;
            }

            // Parsing
            h = Integer.parseInt(lArr[0]);
            w = Integer.parseInt(lArr[1]);

            // Naredis mrezo
            mr = new char[h][w];

            // Preberi mrezo
            for (int i = 0; i < h; i++) {
                l = mreza.readLine();
                lArr = l.split(",");
                for (int j = 0; j < w; j++) {
                    mr[i][j] = lArr[j].charAt(0);
                }
            }

            n = Integer.parseInt(mreza.readLine());
            besede = new String[n];
            for (int i = 0; i < n; i++) {
                besede[i] = mreza.readLine();
            }

        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        } finally {
            if (mreza != null)
                mreza.close();
        }

        int[] lengths = new int[besede.length];
        for (int i = 0; i < besede.length; i++) {
            lengths[i] = besede[i].length();
        }

        quickSort(lengths, besede, 0, besede.length - 1);

        resi(mr, new boolean[h][w], besede, 0);
        izhod.write(output);
        izhod.close();
    }

    public static void quickSort(int[] arr, String[] besede, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, besede, begin, end);

            quickSort(arr, besede, begin, partitionIndex - 1);
            quickSort(arr, besede, partitionIndex + 1, end);
        }
    }

    private static int partition(int arr[], String[] besede, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] > pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;

                String swapTempStr = besede[i];
                besede[i] = besede[j];
                besede[j] = swapTempStr;
            }
        }

        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        String swapTempStr = besede[i + 1];
        besede[i + 1] = besede[end];
        besede[end] = swapTempStr;

        return i + 1;
    }

    public static boolean resi(char[][] mreza, boolean[][] porabljene, String[] besede, int IxBeseda) {
        if (IxBeseda >= besede.length) {
            return true;
        }
        char prva = besede[IxBeseda].charAt(0);
        boolean out = false;
        int l = besede[IxBeseda].length();
        int sH = 0;
        int sW = 0;
        int eH = 0;
        int eW = 0;
        for (int i = 0; i < mreza.length && !out; i++) { // visina
            for (int j = 0; j < mreza[0].length && !out; j++) { // sirina
                // System.out.printf("Looking for: %s at [%d][%d] %b%n", besede[IxBeseda], i, j,
                // porabljene[i][j]);
                if (!porabljene[i][j] && mreza[i][j] == prva) {
                    sH = i;
                    sW = j;
                    if (l == 1) {
                        eH = i;
                        eW = j;
                        porabljene[i][j] = true;
                        out = resi(mreza, porabljene, besede, IxBeseda + 1);
                        if (!out) {
                            porabljene[i][j] = false;
                        }
                        continue;
                    }

                    for (int deltaW = -1; deltaW <= 1; deltaW++) {
                        for (int deltaH = -1; deltaH <= 1; deltaH++) {
                            if (deltaH == 0 && deltaW == 0) {
                                continue;
                            }
                            if (!out && pravilnaBeseda(mreza, i, j, besede[IxBeseda], deltaH, deltaW, porabljene)) {
                                eH = sH + deltaH * (l - 1);
                                eW = sW + deltaW * (l - 1);
                                out = resi(mreza, porabljene, besede, IxBeseda + 1);
                                if (!out) {
                                    odstraniBesedoIzTabele(porabljene, i, j, deltaH, deltaW, l);
                                }
                            }

                        }
                    }
                }
            }
        }

        if (out) {
            output = String.format("%s,%d,%d,%d,%d%n", besede[IxBeseda], sH, sW, eH, eW) + output;
        }

        return out;
    }

    public static boolean pravilnaBeseda(char[][] mreza, int h, int w, String beseda, int deltaH, int deltaW,
            boolean[][] porabljene) {
        int l = beseda.length();
        int konecH = h + (l - 1) * deltaH;
        int konecW = w + (l - 1) * deltaW;
        if (0 > konecH || mreza.length <= konecH) {
            return false;
        }
        if (0 > konecW || mreza[0].length <= konecW) {
            return false;
        }

        int trenutniH = h;
        int trenutniW = w;
        for (int i = 0; i < l; i++) {
            // System.out.printf("%s[%d]: %c([%d][%d]) =?= %c%n", beseda, i,
            // mreza[trenutniH][trenutniW], trenutniH,
            // trenutniW, beseda.charAt(i));
            if (porabljene[trenutniH][trenutniW] || beseda.charAt(i) != mreza[trenutniH][trenutniW]) {
                return false;
            }
            trenutniH += deltaH;
            trenutniW += deltaW;
        }
        dodajBesedoVTabelo(porabljene, h, w, deltaH, deltaW, l);
        return true;
    }

    public static void dodajBesedoVTabelo(boolean[][] porabljene, int h, int w, int deltaH, int deltaW,
            int dolzinaBesede) {
        for (int i = 0; i < dolzinaBesede; i++) {
            // System.out.printf("adding %d %d\n", h, w);
            porabljene[h][w] = true;
            h += deltaH;
            w += deltaW;
        }
    }

    public static void odstraniBesedoIzTabele(boolean[][] porabljene, int h, int w, int deltaH, int deltaW,
            int dolzinaBesede) {
        for (int i = 0; i < dolzinaBesede; i++) {
            // System.out.printf("removing %d %d\n", h, w);
            porabljene[h][w] = false;
            h += deltaH;
            w += deltaW;
        }
    }
}
