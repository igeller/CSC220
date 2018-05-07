package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
	/** Add an entry or change an existing entry.
      @param name The name of the person being added or changed.
      @param number The new number to be assigned.
      @return The old number or, if a new entry, null.
	 */
	public String addOrChangeEntry (String name, String number) {
		String oldNumber = null;
		FindOutput fo = find(name);
		if (fo.found == true) {
			oldNumber = fo.entry.getNumber();
			fo.entry.setNumber(number);
		} else {
			// Create a new entry to insert.
			DLLEntry newEntry = new DLLEntry(name, number);

			// Declare and set the variable next.
			DLLEntry next = fo.entry;

			// Declare the variable previous.
			DLLEntry previous; 


			// Set it.
			// Oops that crashes if next==null.  What should it be then?
			if(next == null) {
				previous = tail;
				tail = newEntry;
				tail.setNext(null);
			}
			else {
				previous = next.getPrevious();
				next.setPrevious(newEntry);
				//previous.setPrevious(newEntry);

			}

			// Set the next and previous of the new entry.
			newEntry.setPrevious(previous);
			newEntry.setNext(next);


			// Set the next of previous to the new entry.
			// Oops that crashes if previous==null.  What should you do then?
			if(previous == null) {
				next = head;
				head = newEntry;
				head.setPrevious(null);
			}
			else
				previous.setNext(newEntry); 



			// Set the previous of next to the new entry.
			// Oops that crashes if next==null.  What should you do then?
			/*next.setPrevious(entry);
    		if(next == null)
    			next = previous;*/




		return null;
		}
		return oldNumber;
	}
/*	/** Find an entry in the directory.
    @param name The name to be found.
    @return A FindOutput object describing the result.
	
	protected FindOutput find (String name) {
		// EXERCISE
		// For each entry in the directory.
		for(DLLEntry entry = head; entry != null; entry = entry.getNext()) {
			// If this is the entry you want
			if(name.equals(entry.getName())) {
				// Return the appropriate FindOutput object.
				return new FindOutput(true, entry);
			}
		}
		//if name is not found
		return new FindOutput(false, null); // Name not found.
	}
*/

	/** Find an entry in the directory.
  @param name The name to be found.
  @return A FindOutput object describing the result.
	*/
	protected FindOutput find (String name) {
		// EXERCISE
		// For each entry in the directory.
		String n1, n2;
		DLLEntry entry;
		for(entry = head; entry != null; entry = entry.getNext()) {
			n1= name;
			n2 = entry.getName();
			int cmp = n1.compareTo(n2);
			// If this is the entry you want
			if(cmp == 0)
				// Return the appropriate FindOutput object.
				return new FindOutput(true, entry);
			else if (cmp < 0)
				//return new FindOutput(false, entry.getNext());
				return new FindOutput(false, entry);
			/*else
				return new FindOutput(false, entry.getPrevious());*/
		}
		//if name is not found//dont want this to return null
		return new FindOutput(false, null); // Name not found.

	}
}
