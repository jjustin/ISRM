import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DN07_63180016 {
    public static void main(String[] args) {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));        
        
        Proga proga = new Proga(sc);
 
        System.out.println(proga.izracunaj());
    }
}

class Proga{
    BufferedReader sc; //scanner, nabit s predpostavljenimi podatki
    int stPostaj; // stevilo postaj na progi
    int[] potovalniCasi; // potovalni casi med postajami
    int stVozenj; // st parov vlakov
    int[] casiA; // cas, ki ga vlak A potrebuje da pride do i-te postaje
    int[] casiB;    // cas, ki ga vlak A potrebuje da pride do i-te postaje

    public Proga(BufferedReader scanner){
        sc = scanner;
        
        // preberi koliko postaj je
        try {
            stPostaj = Integer.parseInt(sc.readLine());
        } catch(Exception e){}
        
        // ustvari tabelo, v katero se shranijo casi potovanj
        String[] potovalniCasiString = new String[stPostaj];
        try {
            potovalniCasiString = sc.readLine().split(" ");
        } catch (Exception e) {}

        // definiraj
        potovalniCasi = new int[stPostaj-1];
        casiA = new int[stPostaj];
        casiB = new int[stPostaj];
        
        // pretvori potovalniCasiString v int-e
        for(int i = 0; i < potovalniCasi.length; i++){
            potovalniCasi[i] = Integer.parseInt(potovalniCasiString[i]);
        }
        
        // izracunaj koliko casa rabi A do i-te postaje brez zamika
        for(int i = 1; i < stPostaj;i++){
            casiA[i] = casiA[i-1]+potovalniCasi[i-1];
        }
        
        // izracunaj koliko casa rabi B do i-te postaje brez zamika
        for(int i = stPostaj-2; i >= 0;i--){
            casiB[i] = casiB[i+1]+potovalniCasi[i];
        }
        
        try {
            stVozenj = Integer.parseInt(sc.readLine());
        } catch (Exception e) {}
    }

    /**
     * izracunaj() izracuna koliko casa najmanj bo potrebno
     * cakati na trenutni progi za vse primere
     * @return  najkrajsi cakalni cas.
     */
    public long izracunaj(){
        long out = 0;
        for(int i = 0; i < stVozenj;i++){
            out += izracunajZaNaslednjiPar();
        }
        return out;
    }

    /**
     * izracunajZaNaslednjiPar() izracuna za naslednji par vlakov najbolj optimalno
     * postajo za postanek in cakalni cas na tej postaji 
     * @return  cakalni cas za naslednji par
     */
    private int izracunajZaNaslednjiPar(){
        /* cakalni cas v odvisnosti od stevilke postaje izrise graf,ki od zacetka pada in potem narasca. 
        Torej ima nekje minimum.
        Ta minimum je iskana vrednost. Poisce se z bisekcijo

        Ideja bisekcije: 
        Imamo zgornjo, spodnjo mejo in center.
        ce je vrednost desno od centra vecja kot levo, potem je minimum na levi - desno stran lahko pozabimo
        podobno velja, ce je vrednost levo od centra vecja - lebo stran lahko pozabimo
        ce to ponavljamo dovolj dolgo dobimo zelo majhen interval, iz katerega izberemo najmanjso vrednost.
        ta najmanjsa vrednost je vrzena v return
        */

        // pridobi podatke o vlakih
        String[] vlak = new String[2];
        try {
            vlak = sc.readLine().split(" ");
        } catch (Exception e) {}

        int a = Integer.parseInt(vlak[0]);
        int b = Integer.parseInt(vlak[1]);

        // preveri, ce se vlaka sploh srecata
        if(casiA[stPostaj-1] <= Math.abs(a-b)) return 0;

        // postavi zgornje, spodnje meje in center
        int spodnjaMeja = 0;
        int zgornjaMeja = stPostaj-1;
        int center = (zgornjaMeja+spodnjaMeja)/2;
        int vrednostSpodaj = casZaNaslednjoPostajo(center - 1, a, b);
        int vrednostZgoraj = casZaNaslednjoPostajo(center + 1, a, b);
        int vrednostCenter = casZaNaslednjoPostajo(center, a, b);

        // bisekticirej to zlo!
        while(Math.abs(zgornjaMeja - spodnjaMeja) >2){
            // ce je minimum najden
            if(vrednostSpodaj > vrednostCenter && vrednostZgoraj > vrednostCenter){
                break;
            }
            // ce je leva stran manjsa
            else if(vrednostSpodaj < vrednostCenter){
                zgornjaMeja = center;
            }
            // ce je desna stran manjsa
            else{
                spodnjaMeja = center;
            }
            // prestavi center na sredino med zgornjo in spodnjo mejo ter izracunaj vrednosti centra in okolice
            center = (spodnjaMeja+zgornjaMeja)/2; 
            vrednostSpodaj = casZaNaslednjoPostajo(center - 1, a, b);
            vrednostZgoraj = casZaNaslednjoPostajo(center + 1, a, b);
            vrednostCenter = casZaNaslednjoPostajo(center, a, b);
        }

        // izmed centra in okolice izberi najmanjsega
        return Math.min(Math.min(vrednostSpodaj, vrednostZgoraj), vrednostCenter);
    }

    /**
     * casZaNaslednjoPostajo() izracuna koliksen je cakalni cas na podani
     * postaji ob podanem zamiku
     * @param postajaIX ineks postaje, na kateri se eden od vlakov ustavi
     * @param a         zacetni cas vlaka A
     * @param b         zacetni cas vlaka B
     * @return          cakalni cas
     */ 
    private int casZaNaslednjoPostajo(int postajaIX, int a, int b){
        // mogoce je, da dobis za indeks postaje cifro manjso od 0 ali pa vecjo od stevila postaj
        // za vsak slucaj, modeeel
        if(postajaIX < 0 || postajaIX >= stPostaj){
            if (postajaIX == -1) postajaIX = 0;
            else postajaIX = stPostaj -1;
        }

        // ze izracunani vrednosti casa, ki ga vlaka potrebujeta do postaje pristej se zacetni cas
        a += casiA[postajaIX];
        b += casiB[postajaIX];

        return Math.abs(a-b);
    }
}