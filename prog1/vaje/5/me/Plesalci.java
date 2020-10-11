import java.util.Scanner;

public class Plesalci {

	/*
     * Vrne GCD pozitivnih celih stevil a in b.
     */
    public static int gcd(int a, int b) {
        while (b > 0) {
            int t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		
		int stMoskih = sc.nextInt();
		int stZensk = sc.nextInt();
		int izbraniGCD = sc.nextInt();
		int stevilkaPara = 1;
		
		for (int i = izbraniGCD; i <= stMoskih; i += izbraniGCD) {
			for (int j = izbraniGCD; j <= stZensk; j += izbraniGCD) {
				if (gcd(i,j) == izbraniGCD) {
					System.out.printf("[%d] %d + %d%n",stevilkaPara,i,j);
					stevilkaPara++;
				}
			}
		}	
    }
}
