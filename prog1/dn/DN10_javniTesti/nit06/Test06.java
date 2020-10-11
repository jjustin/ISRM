
public class Test06 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("3*7*2*1*5*6");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
