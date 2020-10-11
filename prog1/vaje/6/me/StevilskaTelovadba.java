import java.util.Scanner;

public class StevilskaTelovadba {
	
	
	/**
	 * Vrne najvecjo potenco stevila 10, ki je manjsa ali enaka 
	 * podanemu stevilu <stevilo>. THE SMALLEST EXPONENT
	 */
	 private static int navzdolDoPotence10(int stevilo) {
		int potenca = 1;
		
		while (stevilo >= 10) {
			stevilo /= 10;
			potenca *= 10;
		}
		
		return potenca;
	 }
	

	
	
	/**
	 * Doda podano stevko <stevka> na zacetek stevila <stevilo> 
	 * in vrne rezultat.
	 */
	 private static int dodajZ(int stevilo, int stevka) {
		int potenca = navzdolDoPotence10(stevilo);
		int rezultat = stevilo + stevka * potenca * 10;
		
		return rezultat;
	 }

	 
	 
	 
	/**
	 * Doda podano stevko <stevka> na konec stevila <stevilo> 
	 * in vrne rezultat.
	 */
	private static int dodajK (int stevilo, int stevka) {
		int rezultat = stevilo*10+stevka;
		
		return rezultat;
	}
	
	
	 
	/**
	 * Odstrani prvo stevko iz stevila <stevilo> in vrne rezultat.
	 */
	private static int odstraniZ(int stevilo) {
		return (stevilo % navzdolDoPotence10(stevilo));
	}
	
	 
	/**
	 * Odstrani zadnjo stevko iz stevila <stevilo> in vrne rezultat.
	 */
	private static int odstraniK(int stevilo) {
		stevilo = stevilo/10;
		return stevilo;
	}
	
	 
	 
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		 
		int ukaz = sc.nextInt();
		
		int stevilo = sc.nextInt();
		
		
		switch (ukaz) {
			case 1:
				// dodajK
				int stevka = sc.nextInt();
				System.out.println(dodajK(stevilo, stevka));
				break;
			case -1:
				// odstraniK
				System.out.println(odstraniK(stevilo));
				break;
			case 2:
				// dodajZ
				//int stevka = sc.nextInt();
				System.out.println(dodajZ(stevilo, sc.nextInt()));
				break;
			case -2:
				// odstraniZ
				System.out.println(odstraniZ(stevilo));
				break;
				
		}
		
	}
}