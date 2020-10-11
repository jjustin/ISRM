
import java.util.Scanner;

/**
 * DN02_63180016
 */
public class DN02_63180016 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int out=-1;
        switch (sc.nextInt()){
            case 1:
                out = prviClen(sc);
                break;
            case 2:
                out = zadnjiClen(sc);
                break;
            case 3:
                out = vsotaZadnjihDvehClenov(sc);
                break;
            case 4:
                out = dolzinaCete(sc);
                break;
            case 5:
                out = najvecUniqueElementovVCeti(sc);
                break;
        }
        System.out.println(out); 
    }

    public static int prviClen(Scanner sc) {
        return sc.nextInt();
    }

    public static int zadnjiClen(Scanner sc) {
        int out = 0; 
        while (sc.hasNextInt()) out = sc.nextInt();
        return out;
    }

    public static int vsotaZadnjihDvehClenov(Scanner sc) {
        int zadnja = 0;
        int predzadnja = 0;
        while (sc.hasNextInt()){
            predzadnja = zadnja;
            zadnja = sc.nextInt();
        }
        return predzadnja+zadnja;
    }

    public static int dolzinaCete(Scanner sc) {
        int dolzina = 0;
        int predzadnji = 0;
        int zadnji = 0;
        while(sc.hasNextInt()){
            predzadnji = zadnji;
            zadnji = sc.nextInt();
            if (predzadnji <= zadnji) dolzina++; 
            else break;
        }
        return dolzina;
    }

    public static int najvecUniqueElementovVCeti(Scanner sc) {
        int predzadnji = 0;
        int zadnji = 0;
        int skupniMax = 0;
        int max = 0;

        while(sc.hasNextInt()){
            predzadnji = zadnji;
            zadnji = sc.nextInt();

            if(zadnji > predzadnji) max++;
            else if(zadnji == predzadnji) continue;
            else{
                if (skupniMax < max)  skupniMax = max;

                zadnji = sc.nextInt();
                max = 1;
            }
        }
        return (skupniMax < max) ? max : skupniMax;
    }
}