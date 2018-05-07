package prog05;

import java.util.EmptyStackException;

/** Class to implement interface StackInt<E> as a linked list.
 *   @author vjm
 * */

public class LinkedStack<E> implements StackInt<E> {

	/** A Node is the building block for a single-linked list. */
	private static class Node<E> {
		// Data Fields
		/** The reference to the data. */
		private E data;

		/** The reference to the next node. */
		private Node next;

		// Constructors
		/** Creates a new node with a null next field.
        @param data The data stored
		 */
		private Node (E data) {
			this.data = data;
			next = null; // Necessary in C++ but not in Java.
		}

		/** Creates a new node that references another node.
        @param data The data stored
        @param next The next node referenced by new node.
		 */
		private Node (E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	} //end class Node


	// Data Fields
	/** The reference to the top stack node. */
	private Node<E> top = null;

	/** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
	 */
	public E push (E obj) {
		top = new Node<E>(obj, top);
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
		
		return top.data;
	}

	/** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
	 */
	public E pop() {
		if(empty())
			throw new EmptyStackException();
		
		E last = top.data;
		top = top.next;
		return last;
	}

	/** Returns true if the stack is empty; otherwise, returns false.
      @return true if the stack is empty.
	 */
	public boolean empty() {
		if(top == null)
	  		return true;
		return false;	
	}

}
