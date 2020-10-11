
import java.util.Scanner;

public class Smreka {

    private static void novaVrsta(){
        System.out.printf("%n");
    }

    private static void znaki(int dolzina, char znak){
        for (int i=1; i <= dolzina;i++){
            System.out.printf("%c", znak);
        }
    }

    /*
     * Nariše piramido vi"sine <visina>, zamaknjeno za <zamik> presledkov v
     * desno.
     */
    private static void piramida(int visina, int zamik) {
        for(int vrstica = 1; vrstica <= visina;vrstica++){
            znaki(zamik+visina-vrstica ,' ');
            znaki((vrstica-1)*2+1, '*');
            novaVrsta();
        }
    }

    private static void smreka(int n) {
        for(int sklop = 1; sklop<=n;sklop++){
            piramida(sklop,n-sklop);
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        smreka(sc.nextInt());
    }

}
