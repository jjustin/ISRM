import java.util.Scanner;

/**
 * kvadrati
 */
public class Kvadrati {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        for (int i = a; i <= b ; i++){
            System.out.println(i*i);
        }
    }
}