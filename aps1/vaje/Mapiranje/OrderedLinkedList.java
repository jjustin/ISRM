class OrderedElement implements Comparable<OrderedElement>
{
	Comparable element;
	OrderedElement next;
	
	OrderedElement(Comparable obj)
	{
		element = obj;
		next = null;
	}
	
	public int compareTo(OrderedElement obj)
	{	
		return element.compareTo(obj.element);
	}
	
	public String toString()
	{
		return element.toString();
	}
}

public class OrderedLinkedList 
{
	private OrderedElement first;
	
	OrderedLinkedList()
	{
		makenull();
	}
	
	public void makenull()
	{
		first = new OrderedElement(null);
	}
	
	public OrderedElement getFirst()
	{
		return first;
	}
	
	public OrderedElement getNext(OrderedElement pos)
	{
		if (pos != null)
			return pos.next;
		else
			return null;
	}
	
	public void write()
	{
		OrderedElement el;
		
		//zaènemo pri elementu za glavo seznama
		el = first.next;
		while (el != null)
		{
			System.out.print(el + ", ");
			el = el.next;
		}
		
		System.out.println();
	}
	
	public void insert(Comparable obj)
	{
		OrderedElement newEl = new OrderedElement(obj);
		
		OrderedElement curEl;
		curEl = first;
		
		while (curEl.next != null)
		{
			int cRes = newEl.compareTo(curEl.next);
			if (cRes == 0)
			{
				curEl.next.element = obj;
				return;
			}
			else if (cRes < 0)
				break;
			else
				curEl = curEl.next;
		}
		
		newEl.next = curEl.next;
		curEl.next = newEl;
	}
	
	public void delete(OrderedElement e)
	{
		e.next = e.next.next;
	}
	
	public OrderedElement first() 
	{
		return first;
	}
	
	public boolean overEnd(OrderedElement e) 
	{
		return e.next == null;
	}
	
	public OrderedElement next(OrderedElement e) 
	{
		return e.next;
	}
	
	public OrderedElement locate(Comparable obj) 
	{
		for(OrderedElement e = first(); !overEnd(e); e = next(e)) 
		{
			int comparisonValue = obj.compareTo(retrieve(e)); 
			if(comparisonValue == 0) 
				return e;
			else if(comparisonValue < 0) 
				break;
		}
		return null;
	}
	
	public Object retrieve(OrderedElement e) 
	{
		return e.next.element;
	}
}

