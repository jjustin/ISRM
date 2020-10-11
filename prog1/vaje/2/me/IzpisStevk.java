import java.util.*;

public class IzpisStevk {
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);

		long a = sc.nextLong();
		while (a != 0){
			System.out.println(a%10);
			a = a/10;
		}
	}
}