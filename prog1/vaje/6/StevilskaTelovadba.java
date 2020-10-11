import java.util.Scanner;

public class StevilskaTelovadba {
	
	
	/**
	 * Vrne najvecjo potenco stevila 10, ki je manjsa ali enaka 
	 * podanemu stevilu <stevilo>. THE SMALLEST EXPONENT
	 */
	

	
	
	/**
	 * Doda podano stevko <stevka> na zacetek stevila <stevilo> 
	 * in vrne rezultat.
	 */
	private static int podaljsajNaZacetku(int stevilo, int stevka){
		int dolzina = 0;
		int a = stevilo;
		while(a!=0){
			dolzina++;
			a /= 10;
		}

		return stevilo+stevka*(int)(Math.pow(10, dolzina));
	}
	 
	 
	 
	/**
	 * Doda podano stevko <stevka> na konec stevila <stevilo> 
	 * in vrne rezultat.
	 */
	private static int podaljsaj(int stevilo, int stevka ){
		return (stevilo*10 + stevka);
	}
	
	 
	/**
	 * Odstrani prvo stevko iz stevila <stevilo> in vrne rezultat.
	 */
	private static int odstraniNaZacetku(int stevilo){
		int dolzina = 0;
		int stevka = stevilo;
		while(stevka>=10){
			dolzina++;
			stevka /= 10;
		}

		return stevilo-stevka*(int)(Math.pow(10, dolzina));
	}

	
	 
	/**
	 * Odstrani zadnjo stevko iz stevila <stevilo> in vrne rezultat.
	 */
	private static int odstrani(int stevilo) {
		return stevilo/10;
	}
	
	 
	 
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		 
		int rezultat = -1;
		switch(sc.nextInt()){
			case 1:
				rezultat = podaljsaj(sc.nextInt(), sc.nextInt());
				break;

			case -1:
				rezultat = odstrani(sc.nextInt());
				break;

			case 2:
				rezultat = podaljsajNaZacetku(sc.nextInt(), sc.nextInt());
				break;

			case -2:
			rezultat = odstraniNaZacetku(sc.nextInt());
			break;


		}
		System.out.println(rezultat);
		
		
	}
}