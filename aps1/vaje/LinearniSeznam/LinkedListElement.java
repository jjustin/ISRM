
public class LinkedListElement 
{
	Object element;
	LinkedListElement next;
	
	LinkedListElement(Object obj)
	{
		element = obj;
		next = null;
	}
	
	LinkedListElement(Object obj, LinkedListElement nxt)
	{
		element = obj;
		next = nxt;
	}
}
