import java.util.Scanner;
import java.util.Random;

public class Dvoboj {

	/**
	 * Simulira partijo Anja-Bojan in vrne 1, ce zmaga Anja, 2, ce zmaga
	 * Bojan, in 0, ce se partija zakljuci z remijem
	 */
	
	private static int simulirajPartijo(Random generator, int verjAnja, int verjBojan) {
		
		int nak = generator.nextInt(100);
		if (nak < verjAnja) {
			// zmaga Anje
			return 1;
		}
		if (nak < verjAnja + verjBojan) {
			// zmaga Bojana
			return 2;
		}
		// remi
		return 0;
	}
	

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		
		int seme = sc.nextInt();
		int ciljnoStZmag = sc.nextInt();
		int verjAnja = sc.nextInt();
		int verjBojan = sc.nextInt();
		
		Random generator = new Random(seme);
		int stZmagAnja = 0;
		int stZmagBojan = 0;
		
		while (stZmagAnja < ciljnoStZmag && stZmagBojan < ciljnoStZmag) {
			int izid = simulirajPartijo(generator, verjAnja, verjBojan);
			
			switch (izid) {
				case 1:
					System.out.print("A");
					stZmagAnja++;
					break;
				case 2:
					System.out.print("B");
					stZmagBojan++;
					break;
				case 0:
					System.out.print("-");
					break;
			}
		}
		System.out.println();
    }
}
