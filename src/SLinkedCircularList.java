
// TODO: displaying last node in a list of more than one == DONE
// TODO: display solitary node (which is also last) == DONE
// TODO: JUnit Testing

/**
 * This is a singly linked circular list.
 * @author derrick
 *
 */
public class SLinkedCircularList implements ListInterface
{
	
	private Node firstNode;			// reference to the first node
	private Node lastNode;			// reference to the last node
	private int length;				// number of entries in the list
	
	public SLinkedCircularList()
	{
		clear();
	} // end constructor

	@Override
	public boolean add(Object newEntry)
	{
		Node newNode = new Node(newEntry);
		
		if (isEmpty())
		{	
			firstNode = newNode;
			lastNode = newNode;
			firstNode.next = newNode;	// when only 1 node this==this->next
			lastNode.next = newNode;	// when only 1 node this==this->next
		}
		else
		{
			Node currLast = lastNode;	// currLast is the very last node
			currLast.next = newNode;	// makes newNode last by having currLast reference it
			newNode.next = firstNode;	// makes newNode circle back & reference 1st node
			lastNode = newNode;			// makes lastNode the newNode (which is last)
		} // end if/else
		
		length++;	// increment chain's length by one.
		return true;
	} // end add
	
	@Override
	public boolean add(int newPosition, Object newEntry)
	{
		boolean isSuccessful = true;
		
		if ((newPosition >= 1) && (newPosition <= length + 1))
		{
			Node newNode = new Node(newEntry);
			
			if (isEmpty())
			{
				firstNode = newNode;
				lastNode = newNode;
				firstNode.next = newNode;	// when only 1 node this==this->next
				lastNode.next = newNode;	// when only 1 node this==this->next
			}
			else if (newPosition == 1)
			{
				newNode.next = firstNode;	// inserts newNode before firstNode
				firstNode = newNode;		// makes firstNode reference newNode
				lastNode.next = firstNode;	// makes lastNode reference the new newNode
			}
			else if (newPosition == length + 1) // newPosition is the new last node
			{
				lastNode.next = newNode;	 
				lastNode = newNode;
				lastNode.next = firstNode;
			} // end if
			else
			{
				Node nodeBefore = getNodeAt(newPosition -1);
				Node nodeAfter = nodeBefore.next;
				newNode.next = nodeAfter;
				nodeBefore.next = newNode;
			} // end if
			
			length++;
		}
		else
		{
			isSuccessful = false;
		}
		
		return isSuccessful;
	} // end add

	@Override
	public Object remove(int givenPosition)
	{
		Object result = null; 				// return value
		
		if (!isEmpty() && (givenPosition >= 1) && (givenPosition <= length))
		{
			if (givenPosition == 1)
			{
				result = firstNode.data;
				firstNode = firstNode.next;
				lastNode.next = firstNode;
				if (length == 1)
				{
					lastNode = null;		// solitary entry was removed
				} // end if
			} // end if
			else
			{
				Node nodeBefore = getNodeAt(givenPosition -1);
				Node nodeToRemove = nodeBefore.next;
				Node nodeAfter = nodeToRemove.next;
				nodeBefore.next = nodeAfter; 	// disconnect node to be removed
				result = nodeToRemove.data;		// save entry to be removed
				if (givenPosition == length)
				{
					lastNode = nodeBefore;		// last node was removed
					lastNode.next = firstNode;
				} // end if
			} // end else
			
			length--;
		} // end outer if
		
		return result;
	} // end remove

	@Override
	public final void clear()
	{
		firstNode = null;
		lastNode = null;
		length = 0;
	} // end clear

	@Override
	public boolean replace(int givenPosition, Object newEntry)
	{
		boolean isSuccessful = true;
		
		if (!isEmpty() && (givenPosition >=1) && (givenPosition <= length))
		{
			Node desiredNode = getNodeAt(givenPosition);
			desiredNode.data = newEntry;
		}
		else
		{
			isSuccessful = false;
		}
		return isSuccessful;
	} // end replace

	@Override
	public Object getEntry(int givenPosition)
	{
		Object result = null;	// result to return
		
		if (!isEmpty() && (givenPosition >= 1) && (givenPosition <= length))
			result = getNodeAt(givenPosition).data;
		
		return result;
	} // end getEntry

	@Override
	public boolean contains(Object anEntry)
	{
		boolean found = false;
		Node currentNode = firstNode;
		do
		{
			if (anEntry.equals(currentNode.data))
				found = true;
			else
				currentNode = currentNode.next;
		} while (!found && (currentNode != lastNode));// end while
		
		return found;
	} // end contains

	@Override
	public int getLength() {
		return length;
	}

	@Override
	/**
	 * // Length of zero is empty, therefore the list starts at 1 (one).
	 */
	public boolean isEmpty()
	{
		return length == 0;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void display()
	{
		Node currentNode = firstNode;
		
		do
		{
			System.out.println(currentNode.data);
			currentNode = currentNode.next;
		} while (currentNode != firstNode);
	} // end display
	
	/**
	 * ----------- PRIVATE METHODS --------------------
	 */
	
	private Node getNodeAt(int givenPosition)
	{
		Node currentNode = firstNode;
		
		//traverse the list to locate the desired node
		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.next;
		
		return currentNode;
	} // end getNodeAt

	private class Node		// private inner class 
	{
		private Object data;		// data portion
		private Node next;			// link to next node
		
		private Node(Object dataPortion)
		{
			data = dataPortion;
			next = null;
		} // end constructor
		
		private Node(Object dataPortion, Node nextNode)
		{
			data = dataPortion;
			next = nextNode;
		} // end constructor
	} // end Node
	
	public static void main(String[] args)
	{
		SLinkedCircularList CList = new SLinkedCircularList();
		CList.add("apple");
		CList.add("banana");
		CList.add("cantelope");
		CList.add("eggplant");
		CList.add(4, "dandelion");
		CList.add("flower");
		//CList.display();
		CList.remove(5);
		//CList.display();
		CList.clear();
		boolean isItEmpty = CList.isEmpty();
		System.out.println(isItEmpty);
		CList.add("audi");
		CList.add(2, "bmw");
		CList.add("chevy");
		//CList.display();
		CList.replace(2, "benz");
		//CList.display();
		Object string = CList.getEntry(2);
		System.out.println(string);
		isItEmpty = CList.isEmpty();
		System.out.println(isItEmpty);
		CList.display(); // true, benz, false, audi, benz, chevy
	}
}
