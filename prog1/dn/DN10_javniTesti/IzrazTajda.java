abstract class Izraz {
	public static Izraz zgradi(String niz) {
		if (niz.length() == 1) {
			Stevilo vrni = new Stevilo(niz);
			return vrni;
		}
		// char operator = 'x';
		// String levi = "";
		// String desni = "";
		// return SestavljeniIzraz(new zgradi(levi), operator, new zgradi(desni));
		return null;
	}
	public int steviloOperatorjev() {
		return 0;
	}

	public String postfiksno() {
		return "ni def";
	}

	public int vrednost() {
		return 0;
	}

}

	class Stevilo extends Izraz {
		String n;

		public Stevilo(String n) {
			this.n = n;
		}


		public int steviloOperatorjev() {
			return 0;
		}

		public String postfiksno() {
			return n;
		}

		public int vrednost() {
			return Integer.parseInt(n);
		}
	}


 class SestavljeniIzraz extends Izraz {
	public SestavljeniIzraz(Izraz levi, char operator, Izraz desni) {

	}
 }