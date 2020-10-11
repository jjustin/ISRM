
import java.util.Scanner;

public class Plesalci {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int stMoskih = sc.nextInt();
        int stZensk = sc.nextInt();
        int g = sc.nextInt();
        int par = 1;

        for(int i = g; i <= stMoskih; i+=g){
            for(int j = g; j <= stZensk; j+=g){
                if (gcd(i, j)==g){
                    System.out.printf("[%d] %d + %d\n", par, i, j);
                    par++;
                }
            }
        }
    }

    /*
     * Vrne GCD pozitivnih celih števil a in b.
     */
    public static int gcd(int a, int b) {
        while (b > 0) {
            int t = a;
            a = b;
            b = t % b;
        }
        return a;
    }
}
