// 63180016

public class Bankomat {
    int pet; 
    int dva;
    int ena;
    int topDvig;
    int dvignjenoDanes;
    int topDvignjenoDnevno;
    Datum topDatum; 
    Datum danasnjiDatum; 


    public Bankomat(){
        pet = 0;
        dva = 0;
        ena = 0;
        topDvig = 0;
        topDatum = null;
        dvignjenoDanes = 0;
        topDvignjenoDnevno = 0;
        danasnjiDatum = new Datum(1,1,1);
    }

    public int vrniN5(){
        return pet;
    }
    public int vrniN2(){
        return dva;
    }
    public int vrniN1(){
        return ena;
    }
    public void nalozi(int k5, int k2, int k1){
        pet += k5;
        dva += k2;
        ena += k1;
    }

    public void izpisi(){
        System.out.printf("%d | %d | %d%n", pet, dva, ena);
    }

    public int kolicinaDenarja(){
        return 5*pet + 2*dva + ena;
    }

    public boolean dvigni(int dvig, Datum datum){
        int preostane = dvig;

        // petke
        int s5 = preostane/5; //izracunaj koliko jih lahko placas s 5
        if (pet >= s5)  // ce jih je dovolj v bankomatu
            preostane = preostane%5; //preostanek je to kar se ne da izplacat s petkami
        else{ // ce jih ni dovolj v bankomatu
            s5 = pet; // USE ALL THE FIVES!!!
            preostane -= pet*5; // ostane toliko kot se ne da placat s petkami
        }

        // dvojke
        int s2 = preostane/2; //izracunaj koliko jih lahko placas z 2
        if(dva >= s2) // ce je dvojk dovolj
            preostane =  preostane%2; // ostane se tolk kot se ne more placat z 2
        else { // ce je dvolj premau
            s2 = dva; // porab vse dvojke
            preostane -= dva*2; //od preostanka odstej kar lahko placacmo z dvojkami
        }

        // ce ne gre izpisat se lahko 5 zmanjsa za 1 in se izplaca z vec dvojkami
        // ne da se izpisat, ce je enk pramalo za izplacilo
        int dodanoS2 = 0; 
        while (s5>0 && preostane > ena){
            s5--;
            preostane += 5;

            dodanoS2 = preostane/2;
            s2 += dodanoS2;
            if(s2>dva){ //ce ne more izplacat z dvojkami
                preostane += (s2-dva)*2; //odstej kar je prevec
                s2 = dva; // use all the twos!!!
                break;
            }
            else { // ce je dvojk se dovolj za izplacilo
                preostane -= dodanoS2*2; //odstej od preostanka to kar se sedaj izdaja z dvojkami
            }
        }

        if(preostane > ena){
            return false;
        }

        pet -= s5;
        dva -= s2;
        ena -= preostane;

        if(dvig>topDvig)
            topDvig = dvig;

        if(datum.jeEnakKot(danasnjiDatum))
            dvignjenoDanes += dvig;
        else{
            danasnjiDatum = datum;
            dvignjenoDanes = dvig;
        }
        if(dvignjenoDanes > topDvignjenoDnevno){
            topDvignjenoDnevno = dvignjenoDanes;
            topDatum = danasnjiDatum;
        }
        return true;
    }

    public int najDvig(){
        return topDvig;
    }

    public Datum najDatum(){
        return topDatum;
    }
}