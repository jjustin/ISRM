
import java.util.Scanner;

/**
 * DN01_63180016
 */
public class DN01_63180016 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        byte n = sc.nextByte(); //stevilo valov
        byte dolzina = sc.nextByte();
        byte amplituda = sc.nextByte();
        byte zamik = sc.nextByte();

        // range cez vse vrste
        for (byte vrsta = 0; vrsta <= amplituda; vrsta++){
            /* output za vsako vrsto se generira med 
            izvajanjem for-a, shranjuje se v out 
            variablo in se izpise na koncu for-a */
            String out = ""; 

            // prva vrsta
            if(vrsta == 0){
                for (int i = 1+zamik; i<=dolzina*n + zamik; i++){
                    if (i%dolzina <= dolzina/2){
                        out += (i%((dolzina)/2)==0) ? "+":"-";
                    }
                    else out += " ";
                }
            }

            // zadnia vrsta
            else if(vrsta == amplituda){
                for (int i = 1+zamik; i<=dolzina*n + zamik; i++){
                    if (i%dolzina >= dolzina/2 || i%dolzina == 0){
                        out += (i%((dolzina)/2)==0) ? "+":"-";
                    }
                    else out += " ";
                }
            }

            //ostalo
            else{
                for (int i = 1+zamik; i<=dolzina*n + zamik; i++){
                    out += (i%((dolzina)/2)==0) ? "|":" ";
                }
            }
            System.out.println(out);
        }
    }
}