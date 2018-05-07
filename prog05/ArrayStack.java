package prog05;

import java.util.EmptyStackException;

import prog02.DirectoryEntry;

/** Implementation of the interface StackInt<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  E[] theData;

  /** Index to top of stack. */
  int top = -1; // initially -1 because there is not top

  private static final int INITIAL_CAPACITY = 2;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack () {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    top++;

    if (top == theData.length)
      reallocate();

    theData[top] = obj;
    return obj;
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop() {
    if (empty())
      throw new EmptyStackException();
    //check the value of top to see if it is empty --> should be negative 1
    
    /**** EXERCISE ****/
    E last = theData[top];
    top--;
    return last;
  }

  /** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E peek () {
    /**** EXERCISE ****/
	  if(empty())
		  throw new EmptyStackException();
	  return theData[top];
  }

  /**** EXERCISE ****/
  /** Returns true if the stack is empty; otherwise, returns false.
  @return true if the stack is empty.
*/
  public boolean empty() {
  	/*if(top == -1)
  		return true;
  	
  	return false;*/
	  return top == -1;
  }
  
  
  /** Allocate a new array to hold the directory. */
  private void reallocate() {
    E[] newData = (E[])new Object[2* theData.length];
    System.arraycopy(theData, 0, newData, 0,
                     theData.length);
    theData = newData;
  }
}
