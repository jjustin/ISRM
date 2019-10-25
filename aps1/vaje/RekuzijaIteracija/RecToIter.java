class Stack {
	Object[] elements;
	int top;

	public Stack(int size) {
		elements = new Object[size];
		top = -1;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == elements.length - 1;
	}

	public Object top() {
		if (isEmpty()) // error: throw new UnderflowException
			return null;
		else
			return elements[top];
	}

	public void push(Object e) {
		if (isFull()) // error: throw new OverflowException
			;
		else {
			top++;
			elements[top] = e;
		}
	}

	public void pop() {
		if (isEmpty()) // error: throw new UnderflowException
			;
		else
			top--;
	}
}

public class RecToIter {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// NALOGA 1:
	//
	// Podana je (naivna) rekurzivna funkcija za izracun n-tega Fibonaccijevega
	// stevila.
	//
	// Spremeni podano rekurzivno funkcijo v iterativno z uporabo sklada.
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int fibRek(int n) {
		if (n <= 2)
			return 1;
		else
			return fibRek(n - 1) + fibRek(n - 2);
	}

	public static int fibIterStack(int n) {

		class StackElement{
			int n;
			int tmp1;
			int tmp2;
			int address;

			public StackElement(){}
			public StackElement(StackElement e){
				n = e.n;
				tmp1 = e.tmp1;
				tmp2 = e.tmp2;
				address = e.address;
			}
		}

		Stack s = new Stack(100);

		StackElement e = new StackElement();
		e.n = n;
		e.tmp1 = 1;
		e.tmp2 =1;
		e.address = 0;
		s.push(e);

		int result = 0;

		do{
			e = (StackElement)s.top();
			s.pop();
			
			switch (e.address) { 
				case 0:
					if(e.n <= 2){
						result = 1;
					}else{
						e.address = 1;
						s.push(new StackElement(e));

						e.n--;
						e.address = 0;
						s.push(new StackElement(e));
					}
					break;
				case 1:
					e.tmp1 = result;
					e.address= 2;
					s.push(new StackElement(e));

					e.n -= 2;
					e.address =0;
					s.push(new StackElement(e));
					break;

				case 2:
					e.tmp2 = result;
					result = e.tmp1 + e.tmp2;
					break;

			}
		}while(!s.isEmpty());

		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// NALOGA 2:
	//
	// Celi del logaritma nekega stevila se racuna tako, da se presteje, kolikokrat
	// je
	// treba
	// stevilo
	// (celostevilcno)
	// deliti
	// z osnovo logaritma, da stevilo postane manjse od osnove.
	//
	// a) Sestavi iterativno funkcijo za racunanje celega dela logaritma danega
	// stevila
	// n
	// pri
	// osnovi
	// b.
	//
	// b) Sestavi rekurzivno funkcijo za racunanje celega dela logaritma danega
	// stevila
	// n
	// pri
	// osnovi
	// b.
	//
	// c) Spremeni rekurzivno verzijo iz naloge b) v iterativno z uporabo sklada.
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int celiDelLogIter(double n, double b) {
		// Dopolnite kodo
		int i = 0;
		while (n > b) {
			n = n / b;
			i++;
		}

		return i;
	}

	public static int celiDelLogRek(double n, double b) {
		// Dopolnite kodo
		if (n < b) {
			return 0;
		}
		return 1 + celiDelLogRek(n / b, b);
	}

	public static int celiDelLogIterStack(double n, double b) {
		// Dopolnite kodo
		Stack s = new Stack(300);
		return 0;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// NALOGA 3:
	//
	// Spremeni rekurzivno funkcijo "nadaljujIzrazRek" v iterativno z uporabo
	// sklada.
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void nadaljujIzrazRek(int ukl, int zak, char[] izraz, int n) {
		if (zak == 0) {
			System.out.println(izraz);
			return;
		}

		if (ukl > 0) {
			izraz[n] = '(';
			nadaljujIzrazRek(ukl - 1, zak, izraz, n + 1);
		}

		if (zak > ukl) {
			izraz[n] = ')';
			nadaljujIzrazRek(ukl, zak - 1, izraz, n + 1);
		}
	}

	public static void nadaljujIzrazIterStack(int ukl, int zak, char[] izraz, int n) {
		// Dopolnite kodo
		Stack s = new Stack(ukl + zak);

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// NALOGA 4:
	//
	// Spremeni rekurzivno funkcijo "sestaviRek" v iterativno z uporabo sklada.
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean sestaviRek(int[] vrednosti, int index, int znesek) {
		if (znesek == 0)
			return true;

		if (znesek < 0)
			return false;

		if (index >= vrednosti.length)
			return false;

		if (sestaviRek(vrednosti, index + 1, znesek - vrednosti[index])) {
			System.out.print(vrednosti[index] + ", ");
			return true;
		} else
			return sestaviRek(vrednosti, index + 1, znesek);
	}

	public static boolean sestaviIterStack(int[] vrednosti, int index, int znesek) {
		// Dopolnite kodo

		return false;
	}

	public static void main(String[] args) {
		System.out.println("NALOGA 1\n");
		System.out.println("Rekurzivna verzija: osmo Fibonaccievo stevilo je " + fibRek(8));
		System.out.println("Iteracija s skladom: osmo Fibonaccievo stevilo je " + fibIterStack(8));

		double n = 12.876;
		double b = 1.587;

		System.out.println("\n\n\nNALOGA 2\n");
		System.out.println("Iterativna verzija: celi del logaritma stevila " + n + " pri osnovi " + b + " je "
				+ celiDelLogIter(n, b));
		System.out.println("Rekurzivna verzija: celi del logaritma stevila " + n + " pri osnovi " + b + " je "
				+ celiDelLogRek(n, b));
		System.out.println("Iteracija s skladom: celi del logaritma stevila " + n + " pri osnovi " + b + " je "
				+ celiDelLogIterStack(n, b));

		System.out.println("\n\n\nNALOGA 3\n");
		char[] izraz = new char[6];

		System.out.println("Rekurzivna verzija:");
		nadaljujIzrazRek(3, 3, izraz, 0);
		System.out.println("\nIteracija s skladom:");
		nadaljujIzrazIterStack(3, 3, izraz, 0);

		System.out.println("\n\n\nNALOGA 4\n");
		int[] vrednosti = { 8, 5, 1, 3, 4 };
		int znesek = 10;

		System.out.println("Rekurzivna verzija:");
		System.out.print("Znesek " + znesek + " dobimo tako, da sestejemo elemente: ");

		if (!sestaviRek(vrednosti, 0, znesek))
			System.out.println("Zneska ni mogoce sestaviti s podanimi elementi");
		else
			System.out.println();

		System.out.println("\nIteracija s skladom:");
		System.out.print("Znesek " + znesek + " dobimo tako, da sestejemo elemente: ");

		if (!sestaviIterStack(vrednosti, 0, znesek))
			System.out.println("Zneska ni mogoce sestaviti s podanimi elementi");
		else
			System.out.println();
	}

}
