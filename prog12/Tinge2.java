package prog12;
import java.util.*;

public class Tinge2 implements SearchEngine{
	boolean boo = false;
	HardDisk <PageFile> pageDisk = new HardDisk<PageFile>();
	PageTrie url2index = new PageTrie();
	
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	WordTable word2index = new WordTable();	
	
	
	public Long indexPage(String url) {
		Long index = pageDisk.newFile();
		PageFile pageFile = new PageFile(index, url);
		System.out.println("indexing page " + pageFile);
		pageDisk.put(index, pageFile);
		url2index.put(url, index);
		
		return index;
	}
	
	public Long indexWord(String word) {
		Long index = wordDisk.newFile();
		List<Long> pages = new ArrayList<Long>();
		wordDisk.put(index, pages);
		word2index.put(word, index);

		return index;
	}
	
	
	
	
	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		url2index.read(pageDisk);
		word2index.read(wordDisk);
		
	}
	
	//class to hold a page id and the number of reference to that
	public class PageComparator implements Comparator<Long>{
		public int compare (Long a, Long b) {
			return pageDisk.get(a).getRefCount() - pageDisk.get(b).getRefCount();
		}
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		
		// Iterator into list of page ids for each key word.
	    Iterator<Long>[] wordFileIterators =
	      (Iterator<Long>[]) new Iterator[keyWords.size()];
	    
	    // Current page index in each list, just ``behind'' the iterator.
	    long[] currentPageIndices =new long [keyWords.size()];
	    
	    // LEAST popular page is at top of heap so if heap has numResults
	    // elements and the next match is better than the least popular page
	    // in the queue, the least popular page can be thrown away.

	    PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(numResults, new PageComparator());
		
		//loop to initialize the entries of wordFileIterators if any of the
	    //the words do not have an index, we should index them.
//	    //takes words for key
	    
	    int wrdFlIteratorPos = 0;
	    for(String word: keyWords) {
	    	if(!word2index.containsKey(word)) {
	    		//word has never been seen on the web
	    		return new String[0];
	    	}
	    	else {
	    		long wordIndex = word2index.get(word);
		    	//get an iterator for the list of pages containing this word
		    	wordFileIterators[wrdFlIteratorPos] = wordDisk.get(wordIndex).iterator();
		    	wrdFlIteratorPos++;
	    	}
	    }
	    
//	    for(int i = 0; i<keyWords.size(); i++) {
//	    	String word = keyWords.get(i);
//	    	if(!word2index.containsKey(word)) {
//	    		//word has never been seen on the web
//	    		return new String[0];
//	    	}
//	    	else {
//	    		long wordIndex = word2index.get(word);
//		    	//get an iterator for the list of pages containing this word
//		    	wordFileIterators[i] = wordDisk.get(word2index.get(keyWords.get(i))).iterator();
//	    	}
//	    
//	    	
//	    	
//	    }
	    
	    
	    while(getNextIndices(currentPageIndices, wordFileIterators) == true) {
	    	//point is to move smallest ids forward and update priority queue
	    	if(allEqual(currentPageIndices) == true) {
		    	//long pageIndex = currentPageIndices[0];
		    	
		    	//int bestRef = pageDisk.get(pageIndex).getRefCount();
		    	
		    	
		    	//if(numResults > bestPageIndices.size() || pageDisk.get(pageIndex).getRefCount() > pageDisk.get(bestPageIndices.peek()).getRefCount()) {
//		    		if(numResults == bestPageIndices.size())
//		    			pageDisk.get(bestPageIndices.poll());
		    		bestPageIndices.offer(currentPageIndices[0]);
		    		
	    		
		    	
	    	}
	    	
		    	
	    }

	    	String [] results = new String[bestPageIndices.size()];
	    	for(int i = results.length-1; i >= 0; i--) {
    			results[i] = pageDisk.get(bestPageIndices.poll()).url;
    		}
		
		
		
		return results;
	}
	
	
	/** If all the currentPageIndices are the same (because are just
    starting or just found a match), get the next page index for
    each word: call next() for each word file iterator and put the
    result into current page indices.

    If they are not all the same, only get the next index if the
    current index is smaller than the largest.

    Return false if hasNext() is false for any iterator.

    @param currentPageIndices array of current page indices
    @param wordFileIterators array of iterators with next page indices
    @return true if all minimum page indices updates, false otherwise
*/
	private boolean getNextIndices(long[] currentPageIndices, Iterator<Long>[] wordFileIterators) {		
		long lrgst = currentPageIndices[0];
		 
		if(allEqual(currentPageIndices) == true) {
			for(int i = 0; i < wordFileIterators.length; i++) {
				if(wordFileIterators[i].hasNext()) {
					currentPageIndices[i] = wordFileIterators[i].next();
				}
				else
					return false;
				//currentPageIndices[i] = wordFileIterators[i].next();
			}
		}
		else {
			for(Long ind : currentPageIndices) {
				if(ind > lrgst)
					lrgst = ind;
			}

			for(int i = 0; i < currentPageIndices.length; i++) {
				if(currentPageIndices[i] < lrgst) {
					
					if(wordFileIterators[i].hasNext()) {
						currentPageIndices[i] = wordFileIterators[i].next();
					}
					else
						return false;
				}	
			}	
			return true;
		}
		
				
		
		
		return true;
    }//end getNext Indices
	
	/** Check if all elements in an array are equal.
    @param array an array of numbers
    @return true if all are equal, false otherwise
	 */
	private boolean allEqual (long[] array) {
		long first = array[0];
		for(int i = 0; i < array.length; i++ ) {
			if(first != array[i])
				return false;
		}
		return true;
	}
}






