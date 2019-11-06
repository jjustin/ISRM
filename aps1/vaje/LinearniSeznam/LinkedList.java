
public class LinkedList {
	protected LinkedListElement first;
	protected LinkedListElement last;

	LinkedList() {
		makenull();
	}

	// Funkcija makenull inicializira seznam
	public void makenull() {
		// drzimo se implementacije iz knjige:
		// po dogovoru je na zacetku glava seznama (header)
		first = new LinkedListElement(null, null);
		last = null;
	}

	// Funkcija addLast doda nov element na konec seznama
	public void addLast(Object obj) {
		// najprej naredimo nov element
		LinkedListElement el = new LinkedListElement(obj);

		// ustrezno ga povezemo s koncem verige obstojecih elementov
		if (last == null) {
			first.next = el;
			// po potrebi posodobimo kazalca "first" in "last"
			last = first;
		} else {
			last.next.next = el;
			// po potrebi posodobimo kazalca "first" in "last"
			last = last.next;
		}

	}

	// Funkcija write izpise elemente seznama
	public void write() {
		// zacnemo pri elementu za glavo seznama
		// sprehodimo se po elementih do konca seznama
		// in izpisemo vrednost vsakega elementa
		LinkedListElement current = first.next;

		// ni elementov na seznamu
		if (current == null) {
			System.out.println("null");
			return;
		}

		if (current.next != null) {
			while (current.next != null) {
				System.out.printf("%s ", current.element);
				current = current.next;
			}
		}

		// za kontrolo lahko izpisemo tudi vrednosti kazalcev "first" in "last"
		System.out.printf("%s\nFirst: %s, Last: %s\n", current.element, first.next.element, last.next.element);
	}

	// Funkcija addFirst doda nov element na prvo mesto v seznamu (takoj za glavo
	// seznama)
	void addFirst(Object obj) {
		// najprej naredimo nov element
		LinkedListElement el = new LinkedListElement(obj, first.next);
		// ustrezno ga povezemo z glavo seznama
		first.next = el;
		// po potrebi posodobimo kazalca "first" in "last"
		if (last == null) {
			last = first;
		} else if (last == first) {
			last.next = el.next;
		}
	}

	// Funkcija length() vrne dolzino seznama (pri tem ne uposteva glave seznama)
	int length() {
		if (last == null) {
			return 0;
		}
		LinkedListElement current = first;
		int i = 1;
		while (current != last) {
			i++;
			current = current.next;
		}
		return i;
	}

	// Funkcija lengthRek() klice rekurzivno funkcijo za izracun dolzine seznama
	int lengthRek() {
		// pomagajte si z dodatno funkcijo int lengthRek(LinkedListElement el), ki
		// izracuna
		// dolzino seznama za opazovanim elementom ter pristeje 1
		return lengthRek(first.next);
	}

	int lengthRek(LinkedListElement el) {
		if (el == null) {
			return 0;
		}

		return 1 + lengthRek(el.next);
	}

	// Funkcija insertNth vstavi element na n-to mesto v seznamu
	// (prvi element seznama, ki se nahaja takoj za glavo seznama, je na indeksu 0)
	boolean insertNth(Object obj, int n) {
		// zacnemo pri glavi seznama

		// sprehodimo se po elementih dokler ne pridemo do zeljenega mesta

		// ce je polozaj veljaven
		// naredimo nov element
		// ustrezno ga povezemo v verigo elementov
		// po potrebi posodobimo kazalec "last"
		// vrnemo true
		// sicer
		// vrnemo false

		return false;
	}

	// Funkcija deleteNth izbrise element na n-tem mestu v seznamu
	// (prvi element seznama, ki se nahaja takoj za glavo seznama, je na indeksu 0)
	boolean deleteNth(int n) {
		// zacnemo pri glavi seznama

		// sprehodimo se po elementih dokler ne pridemo do zeljenega mesta

		// ce je polozaj veljaven
		// ustrezno prevezemo elemente seznama tako, da ciljni element izlocimo iz
		// verige
		// po potrebi posodobimo kazalec "last"
		// vrnemo true
		// sicer
		// vrnemo false

		return false;
	}

	// Funkcija reverse obrne vrstni red elementov v seznamu (pri tem ignorira glavo
	// seznama)
	void reverse() {
		// ne pozabimo na posodobitev kazalca "last"!
		if (last == null || last == first) {
			return;
		}
		LinkedListElement tmp = first.next;
		while (tmp != last) {

		}
	}

	// Funkcija reverseRek klice rekurzivno funkcijo, ki obrne vrstni red elementov
	// v seznamu
	void reverseRek() {
		// pomagajte si z dodatno funkcijo void reverseRek(LinkedListElement el), ki
		// obrne del seznama za opazovanim elementom, nato ta element doda na konec
		// (obrnjenega) seznama
	}

	// Funkcija removeDuplicates odstrani ponavljajoce se elemente v seznamu
	void removeDuplicates() {
		// ne pozabimo na posodobitev kazalca "last"!
	}
}
