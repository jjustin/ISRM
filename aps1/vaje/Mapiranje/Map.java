class MapNode {
	public MapNode(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean equals(Object obj) {
		if (obj instanceof MapNode) {
			MapNode node = (MapNode) obj;
			return key.equals(node.key);
		}

		return false;
	}

	public String toString() {
		return "[" + key + ", " + value + "]";
	}

	private Object key;
	private Object value;
}

public class Map {
	public static final int DEFAULT_SIZE = 7;

	Set[] table;

	public Map() {
		makenull(DEFAULT_SIZE);
	}

	public Map(int size) {
		makenull(size);
	}

	public void makenull() {
		makenull(DEFAULT_SIZE);
	}

	public void makenull(int size) {
		// create an empty table and initialize the linked lists
		table = new Set[size];

		for (int i = 0; i < table.length; i++) {
			table[i] = new Set();
		}
	}

	private int hash(Object d) {
		return Math.abs(d.hashCode()) % table.length;
	}

	public void print() {
		for (int i = 0; i < table.length; i++)
			table[i].print();
	}

	public void assign(Object d, Object r) {
		// Funkcija doda nov par (d, r) v preslikavo M.
		// To pomeni, da velja M(d) = r.
		//
		// V primeru, da v preslikavi M ze obstaja par s kljucem d,
		// se obstojeci shranjeni par spremeni v (d, r).

		// MANJKA KODA
	}

	public Object compute(Object d) {
		// Funkcija vrne vrednost M(d).
		// Ce vrednost M(d) ni definirana, funkcija vrne null.

		Set l = table[hash(d)];
		SetElement pos = l.locate(new MapNode(d, null));

		if (pos != null) {
			return ((MapNode) l.retrieve(pos)).getValue();
		}

		return null;
	}

	public void delete(Object d) {
		// Funkcija zbrise par (d, r) iz preslikave M.
		// To pomeni, da vrednost M(d) ni vec definirana.

		// MANJKA KODA
	}

	public void rehash(int size) {
		// Funkcija zgradi novo zgosceno tabelo podane velikosti.
		// Obstojeci pari (d, r) se prenesejo v novo tabelo.

		// MANJKA KODA
	}
}
