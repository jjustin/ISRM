import java.util.Random;

public class GlavniProgram 
{	
	public static void testOsnovnihFunkcij()
	{
		Map t = new Map(5);
		t.assign(11, "eleven");
		t.assign(2, "two");
		t.assign(5, "five");
		t.assign(12, "twelve");
		t.assign(934, "nine-hundred-thirty-four");
		t.assign(15, "fifteen");
		t.assign(16, "sixteen");
		t.assign(66, "sixty-six");
		t.assign(41, "forty-one");
		t.assign(38, "osem-in-trideset");
		t.assign(38, "thirty-eight");
		t.assign(48, "forty-eight");
		t.assign(17, "seventeen");
		
		t.print();
		System.out.println();
		
		t.rehash(10);
		t.print();
		System.out.println();
		
		for (int i = 1; i < 20; i++)
		{
			System.out.print("Vrednost elementa s kljucem " + i + ": ");
			Object r = t.compute(i);
			
			if (r != null)
				System.out.println(r);
			else
				System.out.println("elementa s tem kljucem ni!");
		}
		System.out.println();
		
		t.delete(38);
		t.delete(2);
		t.delete(5);
		
		t.print();
		System.out.println();
	}
	
	public static void testPrimerjava()
	{
		final int stVnosov = 1000;
		final int stNakljucnih = 1000;
		final int stPonovitev = 500;
	
		final String[] imena = {"Martin", "Valter", "Josip", "France", "Miha", "Janez", "Andrej",
			"Matej", "Peter", "Igor", "Kaja", "Lara", "Neli", "Marjetka", "Ana", "Eva", "Marija", "Tine", "Tina",
			"Martina", "Hilda", "Ljudmila", "Alenka", "Vanja", "Darko", "Marko", "Stojan", "Milan", "Marcel", "Borut",
			"Vinko", "Karel", "Katarina", "Barbara", "Zvezdana", "Polona", "Bernard", "Tone", "Ciril", "Zlatko", 
			"Polde", "Miro", "Klemen", "Vid", "Stanko", "Bojan", "Mojca", "Tanja", "Valentina", "Iva"};
		
		final String[] priimki = {"Vodopivec", "Novak", "Kranjec", "Vnuk", "Volk", "Kravanja", "Arhar",
			"Brezigar", "Brglez", "Cerar", "Cirman", "Kopitar", "Dostojevski", "Drolc", "Mlakar", "Kos", "Mrak", "Vidmar",
			"Katanec", "Oblak", "Horvat", "Bogataj", "Avsenik", "Osenar", "Debelak", "Cvek", "Domicelj", "Gaber", 
			"Ipavec", "Jager", "Hvala", "Jerin", "Jerman", "Kastelic", "Lombergar", "Mejak", "Planinc", "Ozebek",
			"Pipan", "Ravbar", "Slivnik", "Samec", "Turk", "Ude", "Umek", "Vajgl", "Zrnec", "Zvonar", "Zajc", "Poropat"};
		
		Map tab = new Map(stVnosov);
		OrderedLinkedList list = new OrderedLinkedList();
		
		Oseba[] osebe = new Oseba[stVnosov];
		int[] telefonske = new int[stVnosov];
		
		Random randomizer = new Random();
		
		// nakljucno generiramo osebe in njihove telefonske stevilke.
		// podatke vstavimo v zgosceno tabelo in urejen seznam s kazalci
		for(int i = 0; i < stVnosov; i++) 
		{
			osebe[i] = new Oseba(imena[randomizer.nextInt(imena.length)],  priimki[randomizer.nextInt(priimki.length)]);
			
			telefonske[i] = randomizer.nextInt(888888) + 111111;
			tab.assign(osebe[i], telefonske[i]);
			
			list.insert(new Pair(osebe[i], telefonske[i]));
		}		
		
		// Generiramo se eno mnozico nakljucnih imen in priimkov.
		Oseba noveOsebe[] = new Oseba[stNakljucnih];
		for(int i = 0; i < stNakljucnih; i++) 
			noveOsebe[i] = new Oseba(imena[randomizer.nextInt(imena.length)], priimki[randomizer.nextInt(priimki.length)]);
		
		long startTime;
		long endTime;
		
		// Povprecen cas iskanja osebe, ki je v imeniku		
		System.out.println("Povprecen cas iskanja osebe, ki je v imeniku");
		startTime = System.nanoTime();
		
		for(int p = 0; p < stPonovitev; p++)
			for(int i = 0; i < stVnosov; i++)
				tab.compute(osebe[i]);
				
		endTime = System.nanoTime();
		System.out.println("- z uporabo zgoscene tabele: " + (endTime - startTime) / (stPonovitev * stVnosov) + "ns na iskanje.");
		
		startTime = System.nanoTime();
		
		for(int p = 0; p < stPonovitev; p++)
			for(int i = 0; i < stVnosov; i++)
				list.locate(new Pair(osebe[i], null));
		
		endTime = System.nanoTime();
		System.out.println("- z uporabo urejenega seznama: " + (endTime - startTime) / (stPonovitev * stVnosov) + "ns na iskanje.");		
		System.out.println();
		
		// Povprecen cas iskanje oseb, ki je najbrz ni v imeniku
		System.out.println("Povprecen cas iskanja nakljucno generirane osebe");
		
		startTime = System.nanoTime();
		for(int p = 0; p < stPonovitev; p++)
			for(int i = 0; i < stNakljucnih; i++) 
				tab.compute(noveOsebe[i]);
		
		endTime = System.nanoTime();
		System.out.println("- z uporabo zgoscene tabele: " + (endTime - startTime) / (stPonovitev * stNakljucnih) + "ns na iskanje.");
	
		startTime = System.nanoTime();
		
		for(int p = 0; p < stPonovitev; p++)
			for(int i = 0; i < stNakljucnih; i++) 
				list.locate(new Pair(noveOsebe[i], null));
		
		endTime = System.nanoTime();
		System.out.println("- z uporabo urejenega seznama: " + (endTime - startTime) / (stPonovitev * stNakljucnih) + "ns na iskanje.");
		System.out.println();
		
		
		// Iskanje vseh oseb na doloceno crko
		System.out.println("Iskanje vseh oseb na doloceno crko");
		
		startTime = System.nanoTime();
		
		for (char crka = 'A'; crka <= 'Z'; crka++)
		{
			int n = 0;
			for(int i = 0; i < tab.table.length; i++)
				for(SetElement j = tab.table[i].first(); !tab.table[i].overEnd(j); j = tab.table[i].next(j)) 
				{
					MapNode node = (MapNode)tab.table[i].retrieve(j);
					Oseba oseba = (Oseba)node.getKey();
					if (oseba.priimek.charAt(0) == crka)
						n++;
				}
		}
		endTime = System.nanoTime();
		System.out.println("- z uporabo zgoscene tabele: " + (endTime - startTime) / ('Z' - 'A' + 1));
			
		
		startTime = System.nanoTime();
		
		for (char crka = 'A'; crka <= 'Z'; crka++)
		{
			int n = 0;		
			for(OrderedElement e = list.first(); !list.overEnd(e); e = list.next(e)) 
			{
				Pair par = (Pair)list.retrieve(e); 
				Oseba oseba = (Oseba)par.key;
				if (oseba.priimek.charAt(0) == crka)
					n++;
				else if(oseba.priimek.charAt(0) > crka)
					break;
			}
		}	
		
		endTime = System.nanoTime();
		System.out.println("- z uporabo urejenega seznama: " + (endTime - startTime) / ('Z' - 'A' + 1));
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	//
	// POTREBNO JE IMPLEMENTIRATI NASLEDNJE METODE:
	//
	// assign, compute, delete in rehash v razredu Map,
	//
	// hashCode() v razredu Oseba.
	//
	///////////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) 
	{
		// test osnovnih funkcij nad zgosceno tabelo
		testOsnovnihFunkcij();
		
		// primerjava ucinkovitosti osnovnih operacij
		// zgoscene tabele in urejenega seznama
		testPrimerjava();
	}

}
