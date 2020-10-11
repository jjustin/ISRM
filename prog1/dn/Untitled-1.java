import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class DN09_63180016 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        VimAdvanced editor = new VimAdvanced(sc);

        boolean nadaljuj = true;

        while(nadaljuj){
            nadaljuj = editor.naslednjiUkaz();
        }

    }

    static class VimAdvanced{
        private ArrayList<String> besedilo;
        private ArrayList<Integer> zgodovina;
        private int zgodovinaPointer;
        private int poraba;
        private int stVrstic;
        private Scanner input;

        public VimAdvanced(Scanner sc){
            input = sc;
            
            besedilo = new ArrayList<String>();
            zgodovina = new ArrayList<Integer>();
        }

        public boolean naslednjiUkaz(){
            String ukaz = input.next();

            if(ukaz.equals("x")){
                return false;
            } else if(ukaz.equals("#")){
                dodajBesediloNaKonec();
            } else if(ukaz.equals("+")){
                dodajBesedilo();
            } else if(ukaz.equals("-")){
                odstraniBesedilo();
            } else if(ukaz.equals("<")){

            } else if(ukaz.equals(">")){

            } else{
                System.out.println("Neznan ukaz: " + ukaz);
                return true;
            }

            izpisiState();
            return true;
        }

        private void dodajBesedilo(){
            int i = input.nextInt();
            dodajBesediloNaIndeks(i);
        }

        private void dodajBesediloNaKonec(){
            dodajBesediloNaIndeks(besedilo.size());
        }

        private void dodajBesediloNaIndeks(int i){
            poraba += 2*(stVrstic - i);

            String niz = input.next();
            besedilo.add(i, niz);
            dodajVZgodovino(i);
            stVrstic++;

            poraba += niz.length();
        }

        private void dodajVZgodovino(int i){
            for(int j = 0; j < zgodovina.size();j++){
                if(Math.abs(zgodovina.get(j)) >= i){
                    if(zgodovina.get(j)>=0) zgodovina.set(j, zgodovina.get(j) + 1);
                    else zgodovina.set(j, zgodovina.get(j) - 1);
                }
            }
            zgodovina.add(zgodovinaPointer, i);
            zgodovinaPointer++;
        }

        private void odstraniBesedilo(){
            int i = input.nextInt();
            zgodovina.add(-i-1);
            System.out.println(besedilo.get(i));
            poraba += 2 * besedilo.get(zgodovina.get(i)).length() + 3 * (stVrstic - i);
            zgodovinaPointer++;
            stVrstic--;
        }

        public void izpisiState(){
            System.out.printf("%d | %s%n", poraba, this.toString());
            // System.out.println(Arrays.toString(zgodovina.toArray()));
        }

        @Override
        public String toString() {
            String out = "";
            String[] output = new String[Collections.max(zgodovina)+1];
            for(int i = 0; i < zgodovinaPointer; i++){
                if(zgodovina.get(i) >= 0){
                    output[zgodovina.get(i)] = besedilo.get(zgodovina.get(i));
                }else{
                    output[-(zgodovina.get(i) + 1)] = "";
                }
            }

            boolean prvaVrstica = true;
            for(int i = 0; i < output.length; i++){
                if(output[i] != ""){
                    out += "/";
                }
                if(prvaVrstica && output[i] != ""){
                    prvaVrstica = false;
                    out = "";
                }
                out += output[i];
            }
            return out;
        }
    }
}
