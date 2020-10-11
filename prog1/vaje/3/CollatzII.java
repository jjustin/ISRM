import java.util.Scanner;

public class CollatzII {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int spMeja = sc.nextInt();
		int zgMeja = sc.nextInt();
		
		int najdaljsiStevec = spMeja;
		int najdaljseZaporedje = 0;
		
		// Ponavljamo od spodnje meje pa do zgorenje meje
		for (;spMeja <= zgMeja;spMeja++ ){
			int trenutnaDolzina = dolzinaCollatza(spMeja);
			// preverimo, ce je collatz trenturnega stevca vecji od dosedaj znanega 
			if (najdaljseZaporedje < trenutnaDolzina){ 
				najdaljsiStevec = spMeja;
				najdaljseZaporedje = trenutnaDolzina;
			}
		}

		System.out.printf("%d\n%d", najdaljsiStevec, najdaljseZaporedje);
	}

	static int dolzinaCollatza(int x){
		int counter = 1;
		while (x != 1){
			switch (x % 2) {
				case 1:
					x = x*3 + 1;
					break;

				case 0:
					x = x / 2;
					break;
					
			}
			counter++;
		}
		return counter;
	}
}