import java.util.Map;

public class Potenca extends Izraz {
    private Izraz e1;
    private Izraz e2;

    public Potenca(Izraz e1, Izraz e2) {
        super();
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Double eval(Map<String, Double> env) {
        return Math.pow(e1.eval(env), e2.eval(env));
    }

    @Override
    public String toString() {
        return "(^ " + e1 + " " + e2 + ")";
    }

    @Override
    public Izraz simplify() {
        Izraz s1 = e1.simplify();
        Izraz s2 = e2.simplify();
        if (s1 instanceof Konstanta && s2 instanceof Konstanta) {
            return new Konstanta(Math.pow(s1.eval(null), s2.eval(null)));
        } else if (s1 instanceof Konstanta && s1.eval(null) == 1) {
            return new Konstanta(1.0);
        } else if (s2 instanceof Konstanta && s2.eval(null) == 0) {
            return new Konstanta(1.0);
        } else if (s1 instanceof Konstanta && s1.eval(null) == 0) {
            return new Konstanta(0.0);
        }

        return new Potenca(s1, s2);
    }

    @Override
    public String izpis() {
        return "(" + e1.izpis() + ")" + " ^ " + "(" + e2.izpis() + ")";
    }

}
