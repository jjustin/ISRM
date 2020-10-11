import java.util.Scanner;

public class DN05_63180016 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int stElementov = sc.nextInt(); 
        int[] cloveki = new int[stElementov];
        long out=1;//prvi clen je ze vstet

        for(int i = 0; i < stElementov; i++){
            cloveki[i] = sc.nextInt();
        }

        int indeksNajvecjega = 0;
        int najvecji = cloveki[0];

        for(int i = 1; i < stElementov; i++){
            int add = 0;
            if(cloveki[i] >= najvecji){
                najvecji = cloveki[i];
                indeksNajvecjega = i;

                add = i+1;
            }else{
                for(int j = i; j >= indeksNajvecjega && add <= 0; j--){
                    if (cloveki[i] < cloveki[j]){
                        add = i-j;
                        break;
                    }
                }
            }
            out += add;
        }
        System.out.println(out);
    }
}