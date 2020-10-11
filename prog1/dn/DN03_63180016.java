import java.util.Scanner;

public class DN03_63180016 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        izpisiKoledar(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());    
    }

    public static void izpisiKoledar(int stMesecevVLetu, int stDniVMesecu, int stDniVTednu, 
                                     int nProstiDan, int nPraznik,
                                     int zacetniMesec, int zacetnoLeto, int koncniMesec, int koncnoLeto) {
        int trenutniKoncniMesec = stMesecevVLetu;
        int trenutniZacetniMesec = zacetniMesec;
        for(int leto = zacetnoLeto; leto <=koncnoLeto; leto++){
            // ce je zadnje leto koncaj pri konciMesec
            if(leto == koncnoLeto)
                trenutniKoncniMesec = koncniMesec;

            izpisiLeto(stMesecevVLetu, stDniVMesecu, stDniVTednu, leto, trenutniZacetniMesec, trenutniKoncniMesec, nPraznik, nProstiDan);

            // nastavi zacetni mesec naslednjega leta na 1 - se izvede samo po prvem loopu in potem ostane isto
            trenutniZacetniMesec = 1;
        }
    }

    public static void izpisiLeto(int stMesecevVLetu, int stDniVMesecu, int stDniVTednu, int leto, int zacetniMesec, int koncniMesec, int nPraznik, int nProstiDan) {
        // izracunaj indeks prvega-1 dne, ki se izpise - ce povecamo za 1 dobimo danasnji dan
        int zacetniDan = (leto-1) * stMesecevVLetu*stDniVMesecu + (zacetniMesec-1)*stDniVMesecu;

        // izpisi vse mesece v letu, ki ustezajo podanim kriterijem
        for(int mesec = zacetniMesec; mesec <= koncniMesec; mesec++){
            // header (m/l)
            izpisiGlavoMeseca(leto, mesec);
            //izpis meseca
            izpisiMesec(stDniVTednu, stDniVMesecu, zacetniDan, nPraznik, nProstiDan);

            // pripravi zacetni dan za naslednji mesec
            zacetniDan += stDniVMesecu;
        }
    }

    public static void izpisiMesec(int stDniVTednu, int stDniVMesecu, int zacetniDan, int nPraznik, int nProstiDan) {
        // zacetniDanMeseca predstavlja indeks prvega dneva v tednu. npr. ponedeljek je 0, torek je 1...
        // praznikStevec nam pove koliko dni je preteklo od zadnjega praznika
        // prostiDanStevec nam povek kateri dan v tednu je
        int zacetniDanMeseca = zacetniDan%stDniVTednu;
        int praznikStevec = zacetniDan%nPraznik;
        int prostiDanStevec = zacetniDanMeseca%nProstiDan;

        // Izirsi prazna polja za zacetniDanMeseca dni
        for (int dan = 1; dan<= zacetniDanMeseca;dan++) 
            izpisPraznoMesto();


        // Izpise vse dni v tednu.
        // Ce je trenutni dan zadnji v tednu gre v novo vrsto
        for(int dan = 1; dan <=stDniVMesecu ;dan++){
            praznikStevec++;
            prostiDanStevec++;
            boolean danesJePraznik = (praznikStevec==nPraznik);
            boolean danesJeProstiDan = (prostiDanStevec==nProstiDan);

            // izpisi dan
            izpisDan(dan, danesJePraznik, danesJeProstiDan);
            
            // preveri ce je bil to zadnji dan v tednu
            if((dan+zacetniDanMeseca)%stDniVTednu == 0 && dan != stDniVMesecu){
                izpisiNovaVrsta();
                prostiDanStevec = 0;
            }
            
            // ce je stevec na nProstiDni pomeni da je bil danes prosti dan
            // ponastavi stevec
            if(danesJeProstiDan) prostiDanStevec = 0;

            // ce je stevec na nPraznik pomeni da je bil danes praznik
            // ponastavi stevec
            if(danesJePraznik) praznikStevec = 0;
        }
        // mesec zakljuci s skokom v novo vrsto
        izpisiNovaVrsta();
    }

    public static void izpisDan(int st, boolean jePraznik, boolean jeDelaProst){
        char prisesek ;
        
        // nastavi prisesek na pravilen znak
        //
        //                |praznik| ni praznik
        // Dela prost dan |  '*'  |  '+'
        //           -----+-------+----------
        // Delovni dan    |  'x'  |  '_'
        if (jePraznik)
            prisesek = (jeDelaProst) ? '*' : '+';
        else
            prisesek = (jeDelaProst) ? 'x' : '_';

        System.out.printf("%4d%c",st, prisesek);
    }

    public static void izpisPraznoMesto() {
        System.out.print("     ");
    }

    public static void izpisiNovaVrsta(){
        System.out.println();
    }

    public static void izpisiGlavoMeseca(int leto, int mesec) {
        System.out.printf("%d/%d%n", mesec, leto);
    }
}