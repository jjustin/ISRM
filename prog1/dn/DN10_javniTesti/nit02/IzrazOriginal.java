// 63180016

import java.util.*;

abstract class Izraz {
    // vrne true, ce je celoten izraz zavit v oklepaje.
    // gre cez celoten izraz in pristeva za vsak '(' in odsteva za vsak ')'.
    // ce je vmes kdaj stevec na 0 pomeni, da je se je prvi oklepaj zakljucil, ce se ni konec izraza
    // to pomeni, da celoten izraz ni ovit v oklepaje
    private static boolean vseVEnemOklepaju(String izraz){
        int stevecUklepajev = 0;
        for(int i = 0; i < izraz.length(); i++){
            if(izraz.charAt(i) == '(') stevecUklepajev++; //pristej
            else if(izraz.charAt(i) == ')') stevecUklepajev--; //odstej
            if(stevecUklepajev == 0 && i != izraz.length() - 1) return false; //klobasica je vecdelna
        }
        return true; //klobasica je enodelna
    }

    // splitpoint je tam, kjer je operator na katerem razdelimo izraz na 2
    private static int poisciSplitPoint(String izraz){
        // predpostavi da je split na zadnjem mestu
        int splitPoint = izraz.length()-2;

        // delimo na delu, kjer je zadnji + ali - oz. kjer je zadnji operator, ce ni + -
        // ne pozabi, da mora biti izraz zunaj oklepajev - stej oklepaje in glej, da je sestevek 0

        // stevec oklepjave
        int stOklepajev = 0;
        // hrani vrednost, ce je bil split v zgodovini ze premaknjen na krat ali mnozeno - ce je bil
        // potem se ne sme premaknit na drug krat ali mnozeno
        boolean kratMnozeno = false;

        // gremo v rikverc, saj iscemo zadnji opeartor, ki usteza pogojem(prednost ima +-, sele nato */)
        // sproti stejemo se okepaje
        for(int i = izraz.length()-1; i > 0; i--){
            char znak = izraz.charAt(i);
            
            // ce najdemo () ukrepamo usterzno s pogoji poslovnja spremenljivke stOklepajev
            if(znak == '(') stOklepajev--;
            else if(znak == ')') stOklepajev++;

            // ce najdemo +- izven oklepaja posljemo indeks nazaj
            if((znak == '+' || znak=='-') && stOklepajev == 0) {
                splitPoint = i;
                break;

            // ce najdemo */ izven oklepaja si to zapomnimo in iscemo naprej, ce mogoce najdemo +-
            } else if(!kratMnozeno && stOklepajev == 0 && ( znak =='/' || znak == '*')){
                splitPoint = i;
                kratMnozeno = true;
            }
        }

        // hohoho, bozicek vraca splitpoint
        return splitPoint;
    }
    public static Izraz zgradi(String izraz){
        //mogoce je da je izraz klobasa "(x)" -> let us remove the uselessness
        while(izraz.charAt(0) == '(' && izraz.charAt(izraz.length()-1) == ')' && vseVEnemOklepaju(izraz)){
            izraz = izraz.substring(1, izraz.length()-1);
        }

        // ce je izraz dolzine 1 je bilo poslano stevilo, to tudi vrni
        if(izraz.length()==1) return new Stevilo(Integer.parseInt(izraz));

        // izkanje tocke delitve
        int splitPoint = poisciSplitPoint(izraz);

        // sestavimo izraz iz dveh podizrazov #rekurzijaLajf
        return new SestavljeniIzraz(zgradi(izraz.substring(0,splitPoint)),
                                    izraz.charAt(splitPoint), 
                                    zgradi(izraz.substring(splitPoint+1)));
    }

    // lol, to ne obstaja
    public int steviloOperatorjev(){
        System.out.println("Izraz.steviloOperatorjev() ni definirana za ta tip Izraza");
        return 0;
    }
    // lol, to ne obstaja
    public String postfiksno(){
        System.out.println("Izraz.postfiskno() ni definirana za ta tip Izraza");
        return "nedefiniran";
    }
    // lol, to ne obstaja
    public int vrednost(){
        System.out.println("Izraz.vrednost() ni definirana za ta tip Izraza");
        return 0;
    };
}

class Stevilo extends Izraz{
    // ejga, tuki notr shranmo stevilo
    int n;

    public Stevilo(int n){
        this.n = n;
    }

    // Stevilo nima operatorjev, vrni 0
    @Override
    public int steviloOperatorjev(){
        return 0;
    }

    // Vrni stevilo, sj je simpl
    @Override
    public String postfiksno(){
        return Integer.toString(n);
    }

    // Hmmmmm, vrednost stevila? a res rabis komentar za to?
    @Override
    public int vrednost(){
        return n;
    };
}

class SestavljeniIzraz extends Izraz{
    Izraz levi;
    char operator;
    Izraz desni;

    public SestavljeniIzraz(Izraz levi, char operator, Izraz desni) {
        this.levi = levi;
        this.operator = operator;
        this.desni = desni;
    }

    // sestejes st. operatorjev v levem in desnem izrazu ter pristejes enga za trenuten izraz, simpl
    @Override
    public int steviloOperatorjev(){
        return levi.steviloOperatorjev() + desni.steviloOperatorjev() + 1;
    }

    // postfiskni obliki levega in desnega dela dodas opeator
    @Override
    public String postfiksno(){
        return levi.postfiksno()+desni.postfiksno()+operator;
    }
    
    // dobra stara matematika drugega razred :O
    @Override
    public int vrednost(){
        switch (operator) {
            case '+':
                return levi.vrednost() + desni.vrednost();
            case '-':
                return levi.vrednost() - desni.vrednost();
            case '*':
                return levi.vrednost() * desni.vrednost();
            case '/':
                return levi.vrednost() / desni.vrednost();
            }

        return 0;
    }
}