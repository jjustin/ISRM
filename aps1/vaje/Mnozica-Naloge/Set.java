class SetElement {
	Object element;
	SetElement next;

	SetElement() {
		element = null;
		next = null;
	}
}

public class Set {
	private SetElement first;

	public Set() {
		makenull();
	}

	public void makenull() {
		// kazalec first kaze na glavo seznama
		first = new SetElement();
	}

	public SetElement first() {
		return first;
	}

	public SetElement next(SetElement pos) {
		return pos.next;
	}

	public boolean overEnd(SetElement pos) {
		if (pos.next == null)
			return true;
		else
			return false;
	}

	public boolean empty() {
		return first.next == null;
	}

	public Object retrieve(SetElement pos) {
		return pos.next.element;
	}

	public void print() {
		System.out.print("{");
		for (SetElement iter = first(); !overEnd(iter); iter = next(iter)) {
			System.out.print(retrieve(iter));
			if (!overEnd(next(iter)))
				System.out.print(", ");
		}
		System.out.println("}");
	}

	public void insert(Object obj) {
		// nov element vstavimo samo, ce ga ni med obstojecimi elementi mnozice

		if (contains(obj)) {
			return;
		}

		SetElement tmp = first.next;
		first.next = new SetElement();
		first.next.element = obj;
		first.next.next = tmp;
	}

	public void delete(SetElement pos) {
		// odstranimo element na poziciji pos (pozor, zaradi glave seznama so polozaji
		// zamaknjeni!)
		pos.next = next(next(pos));
	}

	public SetElement locate(Object obj) {
		// sprehodimo se cez seznam elementov in preverimo enakost (uporabimo metodo
		// equals)
		//
		// ce element najdemo, vrnemo njegov polozaj (pozor, zaradi glave seznama so
		// polozaji zamaknjeni)
		// sicer vrnemo null
		for (SetElement iter = first(); !overEnd(iter); iter = next(iter)) {
			if (obj.equals(retrieve(iter))) {
				return iter;
			}
		}

		return null;
	}

	public boolean contains(Object obj) {
		for (SetElement iter = first(); !overEnd(iter); iter = next(iter)) {
			if (obj.equals(retrieve(iter))) {
				return true;
			}
		}
		return false;
	}

	public void union(Set a) {
		// brez podvajanja dodaj vse elemente iz mnozice a
		for (SetElement iter = a.first(); !overEnd(iter); iter = next(iter)) {
			insert(retrieve(iter));
		}
	}

	public void intersection(Set a) {
		// odstrani vse elemente, ki se ne nahajajo tudi v mnozici a
		SetElement iter = first();
		while (!overEnd(iter)) {
			if (!a.contains(retrieve(iter))) {
				delete(iter);
			} else {
				iter = next(iter);
			}
		}
	}
}
