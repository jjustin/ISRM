
public class Test09 {

    public static void main(String[] args) {
        Izraz izraz = Izraz.zgradi("(3+4)*5/6");
        System.out.println(izraz.steviloOperatorjev());
        System.out.println(izraz.postfiksno());
        System.out.println(izraz.vrednost());
    }
}
