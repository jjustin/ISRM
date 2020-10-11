import java.util.Scanner;

/**
 * piramida
 */
public class piramida {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int visina = sc.nextInt();
        int dolzina = 1;
        for (int i = 1; i <=visina;i++){
            for (int j = 1; j <=(visina-i);j++) System.out.print(" ");
            for (int j = 1; j<=dolzina;j++) System.out.print("*");
            System.out.printf("\n");
            dolzina += 2;
        }
    }
}