//63180019

abstract class Izraz {
	protected String izraz;

	public static Izraz zgradi(String niz) {
		if (niz.length() == 1) {   
			return new Stevilo(niz);
		}
		
		// ne rabis cekirat ce je vecji kot 1, ker ce dobis neki notr in ni enako 1 pol je vecje od 1 (naj ne bi dobila praznega stringa)
		String levi = null;
		String desni = null;
		String operator = null;
		for (int i = niz.length()-1; i >= 0; i--) {
			//brez zunanjih oklepajev
			String zacasniOperator = niz.substring(i, i+1);

			// ce primerjas string z 'x' primerjas string s char tipom -> ne bo nikol isto
			if (zacasniOperator.equals("+") || zacasniOperator.equals("-")) {
				levi = niz.substring(0,i);
				desni = niz.substring(i+1, niz.length());
				operator = niz.substring(i, i+1);
				return new SestavljeniIzraz(levi, operator, desni);
			}
		}
			
			
			/*if (operator.equals("+"))
				skupek = Integer.parseInt(levi) "+" Integer.parseInt(desni);
			else if (operator.equals("-"))
				skupek = Integer.parseInt(levi) "-" Integer.parseInt(desni);
			else if (operator.equals("*"))
				skupek = Integer.parseInt(levi) "*" Integer.parseInt(desni);
			else {
				skupek = Integer.parseInt(levi) "/" Integer.parseInt(desni);
			}
			return skupek;
			//String[] tabela = new tabela[100];
			//for (int i = 0; i < niz.length(); i++) {
			//	tabela[i]
			//}
		}
		*/
		return null;
	}	
	
	public abstract int steviloOperatorjev();
	
	public abstract String postfiksno();
	
	public abstract int vrednost();
	
}

class Stevilo extends Izraz {
	String n;
	
	public Stevilo(String n) {
		this.n = n;
	}
	
	@Override
	public int steviloOperatorjev() {
		return 0;
	}
	
	@Override
	public String postfiksno() {
		return this.n;
	}
	
	@Override
	public int vrednost() {
		return Integer.parseInt(n);
	}
}

class SestavljeniIzraz extends Izraz {
	String levi;
	String operator;
	String desni;
	
	public SestavljeniIzraz(String levi, String operator, String desni) {
		this.levi = levi;
		this.operator = operator;
		this.desni = desni;
	}
	
	@Override
	public int steviloOperatorjev() {
		int stOperatorjev = 0;
		String zacasniOperator1 = null;
		for (int i = 0; i < this.levi.length(); i++) {
			zacasniOperator1 = levi.substring(i, i+1);
			if (zacasniOperator1.equals("+") || zacasniOperator1.equals("-") ||
				zacasniOperator1.equals("*") || zacasniOperator1.equals("/"))
				stOperatorjev++;
		}
		for (int i = 0; i < this.desni.length(); i++) {
			zacasniOperator1 = desni.substring(i, i+1);
			if (zacasniOperator1.equals("+") || zacasniOperator1.equals("-") ||
				zacasniOperator1.equals("*") || zacasniOperator1.equals("/"))
				stOperatorjev++;
		}
		return stOperatorjev;
	}
	
	@Override
	public String postfiksno() {
		String postfiksno = this.levi;
		postfiksno += this.desni;
		postfiksno += this.operator;
		return postfiksno;
	}
	
	@Override
	public int vrednost() {
		int vrednost = 0;
		if (operator.equals("+"))
			vrednost += (Integer.parseInt(levi) + Integer.parseInt(desni));
		else if (operator.equals("-"))
			vrednost += (Integer.parseInt(levi) - Integer.parseInt(desni));
		else if(operator.equals("*"))
			vrednost += (Integer.parseInt(levi) * Integer.parseInt(desni));
		else {
			vrednost += (Integer.parseInt(levi) / Integer.parseInt(desni));
		}
		return vrednost;
	}
	
}
/*boolean odvecniOklepaji = true; 
		int indeksOdvecnega = 0;
		String vmesniNiz = niz;  //na zacetku je to kar niz
		
		//odstranjevanje zunanjih oklepajev
		//while tece, dokler je prevec oklepajev, torej spremenljivka na TRUE
		while (odvecniOklepaji) {
			int globina = 0;  //globina oklepajev pomeni, da ugotovim koliko oklepajev je odvecnih 
			for (int i = 0; i < vmesniNiz.length(); i++) {  //i predstavlja 1 znak (char)
				if (niz.charAt(i) == '(') 
					globina++;
				else if (i == ')') {
					globina--;
				}
				if (i == vmesniNiz.length()-1 && globina == 0) {
					//pomeni, da je nekaj oklepajev odvecnih
					odvecniOklepaji = false;
					indeksOdvecnega = i;
					for (int j = 1; j > vmesniNiz.length(); j++) {
						vmesniNiz += j;
						zgradi(vmesniNiz);
					}
				}
				else {
					
				}
			}
		}
		return vmesniNiz;
		*/
		
