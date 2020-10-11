import java.util.*;

public class SteviloStevk {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int stevilo = sc.nextInt();
		int steviloStevk = 0;
		while (stevilo > 0) {
			stevilo = stevilo/10;
			steviloStevk++;
		}
		System.out.println(steviloStevk);
	
	}
}