import java.util.Scanner;
import java.util.Random;

public class Berac {

    public static void main(String[] args) {
        //Inicializacija scannerja in radnom classa
        Scanner sc = new Scanner(System.in);
        Random random = new Random(sc.nextInt());

        // Branje podatkov
        int funtiVEvro = sc.nextInt();
        int dolarjiVEvro = sc.nextInt();
        int k = sc.nextInt();
        int zgornjaMeja = sc.nextInt();

        // Ustvari klobuk, v katerem se sestavajo donacije
        int klobuk = 0;

        // Izpisi zacetno vrstico
        System.out.printf("%4sDarilo%10sKlobuk%n", "","");

        // Zbiraj donacije
        while(klobuk<zgornjaMeja){

            // Doloci Valuto in donacijo
            int val = random.nextInt(3);
            int denar = random.nextInt(k);

            // Pristej klobuku in izpisi trenutno stanje
            klobuk += vEvre(val, denar, funtiVEvro, dolarjiVEvro);
            System.out.printf("%6d %s%11d mEUR%n",denar,valuta(val),klobuk);
            }
        }

    // valuta vrne valuto glede na to kaksno je stveilo na vhodu
    // 0 = EUR
    // 1 = GPD
    // 2 = USD
    public static String valuta(int n) {
        return (n==0)?"EUR":((n==1)?"GBP":"USD");
    }

    // Pretvori vrednost denarja po tecaju, ki je podan in vrne v mEUR
    public static int vEvre(int n, int denar, int funtiVEvro, int dolarjiVEvro) {
        return (n==0)?denar*1000:((n==1)?denar*funtiVEvro:denar*dolarjiVEvro);
    }
}