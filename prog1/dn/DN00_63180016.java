import java.util.Scanner;

/**
 * DN00_63180016
 */
public class DN00_63180016 {

    public static void main(String[] args) {
        Scanner tone = new Scanner(System.in);
        int stBonbonov = tone.nextInt();
        int stOtrok = tone.nextInt();

        System.out.printf("%s\n%s", stBonbonov/stOtrok, stBonbonov%stOtrok);
    }
}