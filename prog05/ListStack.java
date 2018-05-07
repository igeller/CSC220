package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using a List.
 *   @author vjm
 */

public class ListStack<E> implements StackInt<E> {
	// Data Fields
	/** Storage for stack. */
	List<E> theData;

	/** Initialize theData to an empty List. */
	public ListStack() {
		theData = new ArrayList<E>();
	}

	/** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
	 */
	public E push (E obj) {
		
		
		theData.add(obj);
		return obj;
		
		
	}

	/**** EXERCISE ****/

	/** Returns the object at the top of the stack without removing it.
  post: The stack remains unchanged.
	 * @return 
  @return The object at the top of the stack.
  @throws EmptyStackException if stack is empty.
	 */
	public E peek(){
		if(empty())
			throw new EmptyStackException();
		//int siz
		return theData.get((theData.size())-1);
	}

	/** Returns the object at the top of the stack and removes it.
  post: The stack is one item smaller.
  @return The object at the top of the stack.
  @throws EmptyStackException if stack is empty.
	 */
	public E pop() {
		if(empty())
			throw new EmptyStackException();

		E last = theData.get((theData.size())-1);
		theData.remove((theData.size())-1);
		return last;
	}

	/** Returns true if the stack is empty; otherwise, returns false.
  @return true if the stack is empty.
	 */
	public boolean empty() {
		if(theData.size() == 0)
			return true;
		return false;	
	}
}
