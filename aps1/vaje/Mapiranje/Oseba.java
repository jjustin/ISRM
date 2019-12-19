
class Oseba implements Comparable<Oseba>
{
	String ime;
	String priimek;
	
	Oseba(String ime, String priimek)
	{
		this.ime = ime;
		this.priimek = priimek;
	}
	
	public int compareTo(Oseba o)
	{
		int r = priimek.compareTo(o.priimek);
		
		if (r == 0)
			return ime.compareTo(o.ime);
		else
			return r;
	}
	
	public boolean equals(Oseba o)
	{
		return compareTo(o) == 0;
	}
	
	public String toString()
	{
		return priimek + ", " + ime;
	}
	
	public int hashCode()
	{
		// MANJKA KODA
			
		return 0;
	}
}