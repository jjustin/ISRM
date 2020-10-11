import java.util.*;

public class Collatz {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int current = sc.nextInt();
		int counter = 1;
		while (current != 1){
			switch (current % 2) {
				case 1:
					current = current*3 + 1;
					break;

				case 0:
					current = current / 2;
					break;
					
			}
			counter++;
		}
		System.out.println(counter);
	}
}