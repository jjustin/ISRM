
public class Test10 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("((2-((0-(3+9/(7/(3)/2)*(4-((8-3))*9))*7)-6)*3))*((3-4)-5)");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
