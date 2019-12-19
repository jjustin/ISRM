
public class Pair implements Comparable<Pair>
{
	Comparable key;
	Object value;
	
	Pair(Comparable k, Object v)
	{
		key = k;
		value = v;
	}
	
	public int compareTo(Pair p)
	{
		return key.compareTo(p.key);
	}
	
	public String toString()
	{
		return "[" + key + ", " + value + "]";
	}
}
