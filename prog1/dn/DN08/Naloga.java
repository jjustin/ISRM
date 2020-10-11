// 63180016
import java.util.*;

public class Naloga {

    public static class Predmet {
        private int sifra;
        private String naziv;
        private int kt;
        private int tip;
        private Modul modul;
        private Izvajalec[] izvajalci;

        public Predmet(int sifra, String naziv, int kt, int tip, Modul modul, Izvajalec[] izvajalci) {
            this.sifra = sifra;
            this.naziv = naziv;
            this.kt = kt;
            this.tip = tip;
            this.modul = modul;
            this.izvajalci = izvajalci;
        }

        // vrne sifro
        public int sifra() {
            return sifra;
        }

        // vrne naziv
        public String naziv(){
            return naziv;
        }

        // vrne kreditne tocke
        public int kt(){
            return kt;
        }

        // vrne tip predmeta
        public int tip() {
            return tip;
        }

        // vrne seznam vseh izvajalcev
        public Izvajalec[] izvajalci() {
            return izvajalci;
        }

        // vrne stevilo izvajalcev
        public int stIzvajalcev(){
            return izvajalci.length;
        }

        // pove ce je predmet del modula
        public boolean jeDelModula(){
            return tip == 2;
        }

        // vrne sifro modula
        public int sifraModula(){
            if(jeDelModula()){
                return modul.sifra();
            }else{
                return -1;
            }
        }

        @Override
        public String toString() {
            return String.format("%d (%s)", this.sifra, this.naziv);
        }
    }

    public static class Modul {
        private int sifra;
        private String naziv;

        public Modul(int sifra, String naziv) {
            this.sifra = sifra;
            this.naziv = naziv;
        }

        // vrne sifro modula
        public int sifra() {
            return sifra;
        }

        @Override
        public String toString() {
            return String.format("%d (%s)", this.sifra, this.naziv);
        }
    }

    public static class Izvajalec {
        private int sifra;
        private String ip;

        public Izvajalec(int sifra, String ip) {
            this.sifra = sifra;
            this.ip = ip;
        }

        @Override
        public String toString() {
            return String.format("%d (%s)", this.sifra, this.ip);
        }
    }

    public static class Predmetnik {
        private Predmet[] predmeti;

        public Predmetnik(Predmet[] predmeti) {
            this.predmeti = predmeti;
        }

        // izpise skupno stevilo kreditnih tock
        //
        // naredi range cez celotno tabelo in sesteva tocke
        public int steviloKT() {
            int out = 0;
            for(Predmet p : predmeti) out += p.kt();
            return out;
        }

        // vrne tabelo [stObveznih, stIzbirnh, stModulov]
        //
        // naredi tabelo v katero steje st zeljenih modulov
        public int[] tipiPredmetov() {
            int[] out = new int[3];
            for(Predmet p : predmeti) out[p.tip]++;
            return out; 
        }

        // vrne predmet z najvec izvajalci
        //
        // Najprej preveri, ce obstajajo predmeti v predmetniku
        // nato gre cez tabelo in primerja koliko izvajalcev imajo predmeti
        // ce ima i-ti predmet vec kot dosedeaj najvecji si shrani indeks i
        // ce imata predmeta enako stevilo izvajalcev primerja njuno stevilko in ohrani manjso
        // vrne predmet na iMax-tem indeksu
        public Predmet predmetZNajvecIzvajalci() {
            if(predmeti.length == 0) return null;
            int iMax = 0;
            for(int i = 0; i < predmeti.length;i++) {
                if(predmeti[i].stIzvajalcev() > predmeti[iMax].stIzvajalcev()) iMax = i; 
                else if(predmeti[i].stIzvajalcev() == predmeti[iMax].stIzvajalcev() &&
                        predmeti[i].sifra() < predmeti[iMax].sifra()) iMax = i;
            }
            return predmeti[iMax];
        }

        // gre cez predmetnik in gleda, ce je predmet del modula ter, ce se sifri ujemata
        // ce so pogoji izpolnjeni potem predmet shrani v nazivi tabelo
        public int predmetiModula(int sifra, String[] nazivi) {
            int i = 0;
            for(Predmet p : predmeti){
                if(p.jeDelModula() && p.sifraModula() == sifra){
                    nazivi[i] = p.naziv();
                    i++;
                }
            }
            return i;
        }

        // v Set zmece vse izvajalce pri predmetih in pogleda veliksost seta
        public int steviloIzvajalcev() {
            Set<Izvajalec> izvajalci = new HashSet<Izvajalec>();
            for(Predmet p : predmeti) izvajalci.addAll(Arrays.asList(p.izvajalci()));
            return izvajalci.size(); 
        }

        // Naredi 2 mapa, oblike map[sifraModula] = stPredmetov, ki so v predmetniku in modulu
        // range cez oba predmetnika
        // ce je predmet del modula poglej, ce ima map[sifraModula] ze vrednost 
        // ce jo ima jo povecaj za 1
        // ce je nima jo nastavi na 1
        // mapKey-e spravi v sete in vzemi presek setov - tako dobimo vse sifreModulov, ki so v obeh predmetnikih
        // pojdi cez set in sestej kolikokrat sta v obeh mapih vrednosti enaki
        public int steviloEnakoMocnihModulov(Predmetnik drugi) {
            Map<Integer, Integer> moduliThis = new HashMap<Integer, Integer>();
            Map<Integer, Integer> moduliDrugi = new HashMap<Integer, Integer>();

            for(Predmet p : predmeti) 
                if(p.jeDelModula()) 
                    // ce ze imamo vnos za sifroModula v mapu ga povecaj za 1, drugace zapisi 1
                    moduliThis.put(p.sifraModula(), (moduliThis.containsKey(p.sifraModula()) ? moduliThis.get(p.sifraModula())+1 : 1));

            for(Predmet p : drugi.predmeti) 
                if(p.jeDelModula()) 
                    // ce ze imamo vnos za sifroModula v mapu ga povecaj za 1, drugace zapisi 1
                    moduliDrugi.put(p.sifraModula(), (moduliDrugi.containsKey(p.sifraModula()) ? moduliDrugi.get(p.sifraModula())+1 : 1));
    
            Set<Integer> range = moduliDrugi.keySet();
            range.retainAll(moduliThis.keySet());

            int out = 0;
            for(Integer i : range){
                if(moduliDrugi.get(i) == moduliThis.get(i)){
                    out++;
                }
            }

            return out;
        }
    }
}
