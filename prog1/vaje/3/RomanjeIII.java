import java.util.Scanner;

public class RomanjeIII {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int pot = sc.nextInt();
		int prehodiDnevno = sc.nextInt();
		int faktorUtrujenosti = sc.nextInt();

		int i = 1;
		while (pot > 0){
			System.out.printf("%d. dan: %d -> %d (prehodil %d)\n", 
				i,
				pot, 
				(pot>=prehodiDnevno) ? pot-prehodiDnevno : 0, 
				(pot>=prehodiDnevno) ? prehodiDnevno:pot);
				
			pot -= prehodiDnevno; // odstej pot
			prehodiDnevno -= faktorUtrujenosti; // Roman se utrudi
			i++;
			if (prehodiDnevno <= 0){
				break;
			}
		}
	}
}