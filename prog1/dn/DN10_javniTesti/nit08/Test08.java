
public class Test08 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("9/3-8/2-7/1-6/5*4+6*5/4");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
