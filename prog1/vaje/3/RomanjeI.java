import java.util.Scanner;

public class RomanjeI {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int pot = sc.nextInt();
		int prehodiDnevno = sc.nextInt();
		
		while (pot > 0){
			System.out.println(pot);
			pot -= prehodiDnevno;
		}
	}
}