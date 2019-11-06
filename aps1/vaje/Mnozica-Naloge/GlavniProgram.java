
public class GlavniProgram {

	// Funkcija prejme stavek ter izpise (brez ponavljanja)
	// - crke, ki nastopajo v stavku
	// - crke, ki nastopajo v vsaki besedi stavka
	public static void crke(String stavek) {
		// stavek spremenimo v male crke in ga razdelimo na besede
		String[] besede = stavek.toLowerCase().split(" ");

		// mnozica za hranjenje vseh crk v stavku
		Set vse = new Set();

		for (char ch : stavek.toLowerCase().replace(" ", "").toCharArray()) {
			vse.insert(ch);
		}

		// mnozico za hranjenje crk, ki nastopajo v vsaki besedi stavka
		Set povsod = new Set();
		povsod.union(vse);

		for (String str : besede) {
			Set thisWord = new Set();
			for (char ch : str.toCharArray()) {
				thisWord.insert(ch);
			}
			vse.intersection(thisWord);
		}

		// sprehodimo se cez vse besede in zgradimo zahtevani mnozici

		System.out.println("V stavku se pojavljajo crke: ");
		vse.print();
		System.out.println("V vsaki besedi se pojavljajo crke: ");
		povsod.print();
	}

	public static Set createPowerSet(Set s) {
		// rezultat je mnozica mnozic
		Set result = new Set();

		// prvi element v rezultatu je prazna mnozica
		result.insert(new Set());

		// zaporedno uporabimo elemente iz izhodiscne mnozice za razsiritev rezultata
		// tako,
		// da za vsako mnozico v rezultatu dodamo se mnozico razsirjeno s trenutnim
		// elementom

		return result;
	}

	public static void printPowerSet(Set p) {
		for (SetElement iter = p.first(); !p.overEnd(iter); iter = p.next(iter)) {
			Set s = (Set) p.retrieve(iter);
			s.print();
		}
	}

	public static void main(String[] args) {
		Set a = new Set();
		a.insert(1);
		a.print();

		a.delete(a.locate(1));
		a.print();

		a.insert(1);
		a.insert(2);
		a.insert(3);
		a.insert(2);
		a.insert(3);
		a.insert(4);
		a.print();

		Set c = new Set();
		c.insert(5);
		c.insert(3);
		c.print();

		a.union(c);
		a.print();

		Set d = new Set();
		d.insert(3);
		d.insert(6);
		d.print();

		a.print();
		a.intersection(d);
		a.print();

		crke("Abstraktni podatkovni tip");

		Set b = new Set();

		b.insert(10);
		b.insert(5);
		b.insert(3);

		System.out.print("Potencna mnozica mnozice ");
		b.print();

		Set p = createPowerSet(b);
		printPowerSet(p);
	}

}
