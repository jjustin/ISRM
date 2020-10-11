import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class DN09_63180016 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        VimAdvanced editor = new VimAdvanced(sc);

        while(editor.naslednjiUkaz());
    }

    static class Change{
        String besedilo;
        int i;
        int type; // 0==removed, 1==added

        public Change(String b, int indeks, int t){
            besedilo = b;
            i = indeks;
            type = t;
        }
        @Override
        public String toString() {
            if(type == 0){
                return String.format("Removed %s @%d", besedilo,i);
            }else{
                return String.format("Added %s @%d", besedilo,i);
            }
        }
    }

    static class VimAdvanced{
        ArrayList<String> besedilo;
        ArrayList<Change> histroy;
        int histroyPointer;
        Scanner sc;
        int poraba;
        boolean inUndo;


        public VimAdvanced(Scanner s){
            sc = s;
            besedilo = new ArrayList<String>();
            histroy = new ArrayList<Change>();
        }

        public boolean naslednjiUkaz(){
            String ukaz = sc.next();

            if(!ukaz.equals(">") && !ukaz.equals("<")){
                endUndo();
            }

            if(ukaz.equals("x")){
                return false;
            } else if(ukaz.equals("#")){
                dodajBesediloNaKonec();
            } else if(ukaz.equals("+")){
                dodajBesedilo();
            } else if(ukaz.equals("-")){
                odstraniBesedilo();
            } else if(ukaz.equals("<")){
                undo();
                inUndo = true;
            } else if(ukaz.equals(">")){
                redo();
                inUndo = true;
            } else{
                System.out.println("Neznan ukaz: " + ukaz);
                return true;
            }

            izpisiState();
            return true;
        }

        private void dodajBesediloNaKonec(){
            dodajBesediloNaIndeks(besedilo.size(), sc.next(), false, false);
        }
        
        private void dodajBesedilo(){
            dodajBesediloNaIndeks(sc.nextInt(), sc.next(), false, false);
        }

        private void dodajBesediloNaIndeks(int i, String niz, boolean undo, boolean redo){            
            int cost = 2*(besedilo.size() - i) + niz.length();
            poraba += cost;

            if(!undo){
                if(!redo) histroy.add(new Change(niz, i, 1));
                histroyPointer++;
            }else histroyPointer--;
 
            besedilo.add(i, niz);
        }

        private void odstraniBesedilo(){
            odstraniBesedilo(sc.nextInt(), false, false);
        }

        private void odstraniBesedilo(int i, boolean undo, boolean redo){
            poraba += 3*(besedilo.size() - i -1) + 2 * besedilo.get(i).length();
            
            if(!undo){
                if(!redo) histroy.add(new Change(besedilo.get(i), i, 0));
                histroyPointer++;
            } else histroyPointer--;

            besedilo.remove(i);
        }
        private void undo(){
            Change c = histroy.get(histroyPointer - 1);
            if(c.type == 0){
                dodajBesediloNaIndeks(c.i, c.besedilo, true, false);
            }else{
                odstraniBesedilo(c.i, true, false);
            }
        }
        
        private void redo(){
            Change c = histroy.get(histroyPointer);
            if(c.type == 0){
                odstraniBesedilo(c.i, false, true);
            }else{
                dodajBesediloNaIndeks(c.i, c.besedilo, false, true);
            }
        }
        
        private void endUndo(){
            inUndo = false;
            histroy.subList(histroyPointer, histroy.size()).clear();
        }

        private void izpisiState(){
            System.out.printf("%d | %s%n", poraba, toString());
            // System.out.println(Arrays.toString(histroy.toArray()));
        }

        @Override
        public String toString() {
            String out = "";
            for(int i=0;i < besedilo.size(); i++){
                if(i!=0){
                    out += "/";
                }
                out+= besedilo.get(i);
            }
            return out;
        }
    }
}
