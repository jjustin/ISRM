import java.util.*;

public abstract class Izraz {
	public abstract Double eval(Map<String, Double> env);

	public abstract String toString();

	public Izraz simplify() {
		return this;
	}

	public abstract String izpis();

	public static Map<Class<? extends Izraz>, Integer> prioritete;

	static {
		prioritete = new HashMap<Class<? extends Izraz>, Integer>();
		prioritete.put(Konstanta.class, 0);
		prioritete.put(Spremenljivka.class, 0);
		prioritete.put(Potenca.class, 1);
		prioritete.put(UnarniMinus.class, 2);
		prioritete.put(Krat.class, 3);
		prioritete.put(Deljenje.class, 3);
		prioritete.put(Plus.class, 4);
		prioritete.put(Minus.class, 4);
	}
}
