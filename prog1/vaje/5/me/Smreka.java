import java.util.Scanner;

public class Smreka {

	/**
	 * Napise zaporedje <koliko> znakov <znak>.
	 */
	 
	private static void znaki(int length, char znak) {
		for (int j=0; j<length; j++) {
			System.out.print(znak);
		}	
	}

    /**
     * Narise piramido visine <visina>, zamaknjeno za <zamik> 
     * presledkov v desno.
     */
	 
	private static void piramida(int visina, int odmik) {
		int stPresledkov = visina - 1;
		int stZvezdic = 1;
		for (int i = 0; i < visina; i++) {
			znaki(odmik, ' ');
			znaki(stPresledkov, ' ');
			znaki(stZvezdic, '*');
			
			System.out.println();
			stZvezdic += 2;
			stPresledkov--;
		}
	}

	/**
	 * Narise smreko visine <n>, t.j. z <n> vejami
	 */
	 
	private static void smreka(int visina) {
		for (int i = 1; i <= visina; i++) {
			piramida(i, visina - i);
		}
	}
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		
		int visina = sc.nextInt();
		smreka(visina);
    }	
}
