package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	
	  /** Add an entry or change an existing entry.
    @param name The name of the person being added or changed
    @param number The new number to be assigned
    @return The old number or, if a new entry, null
*/
	public String addOrChangeEntry (String name, String number) {
	  FindOutput fo = find(name);
	  String oldNumber = null;
	  
	  if(!fo.found) {
		  int insertionIndex = fo.index;

		  if(size >= theDirectory.length)
			 reallocate();
		  for(int i = size-1; i >= insertionIndex; i--)
			  theDirectory[i+1] = theDirectory[i];
		  theDirectory[insertionIndex] = new DirectoryEntry(name, number);
		  size++;
		  modified = true;
		  return null;

	  }
	  else {
		    oldNumber = theDirectory[fo.index].getNumber();
		    theDirectory[fo.index].setNumber(number);
		  	modified = true;
		    return oldNumber;
	  }
	}


/** Find an entry in the directory.
	@param name The name to be found
	@return A FindOutput object containing the result of the search.
*/
	protected FindOutput find (String name) {
		
		int first = 0, mid = 0;
		int last = size-1;
		String name1, name2;
		
		while(first <= last) {
			mid = (first + last)/2;
			name1 = name;
			name2 = theDirectory[mid].getName();
			int cmp = name1.compareToIgnoreCase(name2);
			
			if(cmp == 0) 
				return new FindOutput(true, mid);
			else if(cmp < 0)
				last = mid -1;
			else
				first = mid +1;
		}
			return new FindOutput(false, first);
		
	}//end find
	
/** Remove an entry from the directory.
	@param name The name of the person to be removed
	@return The current number. If not in directory, null is
	returned
*/
	
	public String removeEntry (String name) {
		
		FindOutput fo = find(name);
		if (!fo.found)
			return null;
		
		DirectoryEntry entry = theDirectory[fo.index];
		for(int i = fo.index; i < size-1; i++)
		{
			theDirectory[i] = theDirectory[i+1];
		}
		size--;
		modified = true;
		return entry.getNumber();
	}//end remove entry

}
