
import java.util.Scanner;
import java.util.Random;

public class Dvoboj {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random generator = new Random(sc.nextInt());
        int stDobljenihIger = sc.nextInt();
        int a = sc.nextInt();
        int b = sc.nextInt();

        int dobljeneAnja = 0;
        int dobljeneBojan = 0;

        while(!(dobljeneAnja == stDobljenihIger || dobljeneBojan == stDobljenihIger)){
            int nak = generator.nextInt(100);

            if (a > nak){
                System.out.print("A");
                dobljeneAnja++;
            }

            else if (b+a > nak){
                System.out.print("B");
                dobljeneBojan++;
            }
            
            else{
                System.out.print("-");
            }
        }
    }
}
