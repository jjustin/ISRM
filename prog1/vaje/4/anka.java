import java.util.Scanner;

/**
 * anka
 */
public class anka {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int d = sc.nextInt();
        int kombinacije = 0;
        for (int i = 1;i<=9;i+=2){
            if (i%2 == 1){
                for (int j = m+1; j<=9 ; j++){
                    for (int k = 0; k<=9 ; k++){
                        if (k%d == 0){
                            System.out.printf("%d-%d-%d\n", i,j,k);
                            kombinacije++;
                       }
                    }
                }
            }
        }
        System.out.println(kombinacije);
    }
}