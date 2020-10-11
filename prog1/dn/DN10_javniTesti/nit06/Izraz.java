//63180083	

public abstract class Izraz{
	public static Izraz zgradi(String niz){
		
		//odstranjujes oklepaje dokler ni en od operatorjev izven njih
		//pol ce je niz sam st. je to ze sam izraz
		// ce pa ni pol pa mors pomojm dat new sestavljeni izraz...
		int razlika = 0;
		for(int i = 0; i < niz.length(); i ++){
			if(niz.charAt(i) == '('){
				razlika++;
			}
			else if(niz.charAt(i) == ')'){
				razlika--;
			}
			if(i == niz.length()-1 && niz.length() != 1){
				System.out.println("Odstranjujem: "+niz);
				niz = niz.substring(1, niz.length()-1);
				System.out.println("Odstranjeno: "+niz);
				i = -1;
				razlika = 0;
				continue;
			}
			if(razlika == 0){
				System.out.println("Koncujem z: " + niz);
				break;
			}
			
		}
		
		if(niz.length() == 1){
			int n = Integer.parseInt(niz);
			Stevilo stevilo = new Stevilo(n);
			return stevilo;
		}
		else{
			int indeks = 0;
			razlika = 0;
			for(int i = 0; i < niz.length(); i++){
				if(niz.charAt(i) == '('){
					razlika++;
				}
				else if(niz.charAt(i) == ')'){
					razlika--;
				}
				if(razlika == 0){
					if(niz.charAt(i) == '+' || niz.charAt(i) == '-'){
						indeks = i;
					}
				}	
			}
			
			if(indeks == 0){
				for(int i = 0; i < niz.length(); i++){
					
					if(niz.charAt(i) == '('){
						razlika++;
					}
					else if(niz.charAt(i) == ')'){
						razlika--;
					}
					if(razlika == 0){
						if(niz.charAt(i) == '*' || niz.charAt(i) == '/'){
							indeks = i;
						}
					}
				}	
			}
			
			String l = niz.substring(0, indeks);
			String d = niz.substring(indeks + 1, niz.length());
			System.out.println(l);
			System.out.println(d);
			
			SestavljeniIzraz sestIzraz = new SestavljeniIzraz(zgradi(l), niz.charAt(indeks), zgradi(d));
			return sestIzraz;
		}
	}
	
	public abstract int steviloOperatorjev();
	
	public abstract String postfiksno();
	
	public abstract int vrednost();
	
}



class Stevilo extends Izraz{
	private int n;
	
	public Stevilo(int n ){
		this.n = n;
	}
	
	@Override
	public int steviloOperatorjev(){
		return 0;
	}
	
	@Override
	public int vrednost(){
		return n;
	}
	
	@Override
	public String postfiksno(){
		return Integer.toString(n);
	}
}

class SestavljeniIzraz extends Izraz{
	private Izraz levi;
	private char operator;
	private Izraz desni;
	
	public SestavljeniIzraz(Izraz levi, char operator, Izraz desni){
		this.levi = levi;
		this.operator = operator;
		this.desni = desni;
	}
	@Override
	public int steviloOperatorjev(){
		int stOp = levi.steviloOperatorjev() + desni.steviloOperatorjev() + 1;
		
		
		return stOp;
	}
	
	@Override
	public int vrednost(){
		int rezultat = 0;
		
		switch(this.operator){
			case '+':{
				rezultat = this.levi.vrednost() + this.desni.vrednost();
				break;
			}
			case '-':{
				rezultat = this.levi.vrednost() - this.desni.vrednost();
				break;
			}
			case '*':{
				rezultat = this.levi.vrednost() * this.desni.vrednost();
				break;
			}
			case '/':{
				rezultat = this.levi.vrednost() / this.desni.vrednost();
				break;
			}
		}
		return rezultat;
	}
	
	@Override
	public String postfiksno(){
		String l = this.levi.postfiksno();
		String d = this.desni.postfiksno();
		
		return l + d + this.operator;
	}
}