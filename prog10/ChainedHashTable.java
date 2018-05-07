package prog10;

import java.util.*;

import prog02.DirectoryEntry;

public class ChainedHashTable<K, V> extends AbstractMap<K, V> {
  private static class Entry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;
    Entry<K,V> next;

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V value) { return this.value = value; }

    Entry (K key, V value, Entry<K,V> next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }

  private final static int DEFAULT_CAPACITY = 5;
  private Entry<K,V>[] table = new Entry[DEFAULT_CAPACITY];
  private int size;

  private int hashIndex (Object key) {
    int index = key.hashCode() % table.length;
    if (index < 0)
      index += table.length;
    return index;
  }

  private Entry<K,V> find (Object key) {
    int index = hashIndex(key);
    for (Entry<K,V> node = table[index]; node != null; node = node.next)
      if (key.equals(node.key))
        return node;
    return null;
  }

  public boolean containsKey (Object key) {
    return find(key) != null;
  }

  public V get (Object key) {
    Entry<K,V> node = find(key);
    if (node == null)
      return null;
    return node.value;
  }

  public V put (K key, V value) {
    Entry<K,V> node = find(key);
    if (node != null) {
      V old = node.value;
      node.value = value;
      return old;
    }
    if (size == table.length)
      rehash(2 * table.length);
    int index = hashIndex(key);
    table[index] = new Entry<K,V>(key, value, table[index]);
    size++;
    return null;
  }
	
  public V remove (Object key) {
    // IMPLEMENT
    // Get the index for the key.
	  int index = hashIndex(key);
 
    // What do you do if the linked list at that index is empty?
	  if(table[index] == null) 
		  return null;
	  


    // What do you do if the first element of the list has this key?
	  if(key.equals(table[index].key)) {
		  V val = table[index].value;
		  table[index] = table[index].next;
		  size--;
		  return val;
	  }






    // If the key is further down the list, make sure you keep track
    // of the pointer to the previous entry, because you will need to
    // change its next variable.
	  for(Entry<K, V> node = table[index]; node.next != null; node = node.next) {
		  if(key.equals(node.next.key)) {
			  V val = node.next.value;
			  node.next = node.next.next;
			  size--;
			  return val;
		  }
	  }
	 








    // Return null otherwise.
    return null;
  }

  private void rehash (int newCapacity) {
    // IMPLEMENT
	  Entry<K, V>[] prevTable = table;	
	  table = new Entry[newCapacity];
	  size = 0;
	  for(int i = 0; i < prevTable.length; i++) {
	      for (Entry<K,V> node = prevTable[i]; node != null; node = node.next){
			  put(node.key, node.value);
		  }
	  }
  }


private Iterator<Map.Entry<K, V>> entryIterator () {
    return new EntryIterator();
  }

  private class EntryIterator implements Iterator<Map.Entry<K, V>> {
    // EXERCISE
	  boolean head = true;
	  int index = 0;
	  Entry <K, V>entry = table[index];

	/* public EntryIterator(){
		 for(int k = 0; k < table.length; k++) {
			 if(table[k] == null)
				 entry = table[index];
			return;
		 }
	 }*/


    public boolean hasNext () {
      // EXERCISE
    	
    	for(int k = index; k < table.length; k++) {
			 if(table[k] != null)
				 return true;
		 }
    	return false;
    	/*if(entry == null)
    		return false;
    	else
    		return false;*/
    	
    	
    }

    public Map.Entry<K, V> next () {
    	if(head)
    		entry = table[index];
    	else
    		entry = entry.next;
    	
    	while(entry == null) {
    		index++;
    		entry = table[index];
    		head = true;
    	}
    	
    	if(entry.next == null) {
    		index++;
    		head = true;
    		return entry;
    	}
    	else
    		head = false;
    	
    	return entry;
    	
    	
    	
    	
      // EXERCISE
    	/*while(index != table.length) {
    		if(table[index] == null)
    			index++;
    		else if(hasNext() != false)
        		return entry;
    	}*/
    	
    	
  
    	/*Entry<K, V> save = entry;
    	// Go to the next entry in the list
    		entry = entry.next;
        	if(entry == null) {
    	// If there isn't one, start at the next list
        		index++;
    	// Go forward until you run off the end or find a non-empty list
        		while(index < table.length && table[index] == null) {
        			index++;
        		}
    	//If you found a non-empty one, set entry
        		if(index < table.length) {
        			entry = table[index];
        		}
        		
        	}
        	return save;*/




     // return null;
    }

    public void remove () {}
  }

  public Set<Map.Entry<K,V>> entrySet() { return new EntrySet(); }

  private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    public int size() { return size; }

    public Iterator<Map.Entry<K, V>> iterator () {
      return entryIterator();
    }

    public void remove () {}
  }

  public String toString () {
    String ret = "------------------------------\n";
    for (int i = 0; i < table.length; i++) {
      ret = ret + i + ":";
      for (Entry<K,V> node = table[i]; node != null; node = node.next)
        ret = ret + " " + node.key + " " + node.value;
      ret = ret + "\n";
    }
    return ret;
  }

  public static void main (String[] args) {
    ChainedHashTable<String, Integer> table =
      new ChainedHashTable<String, Integer>();

    table.put("Brad", 46);
    System.out.println(table);
    table.put("Hal", 10);
    System.out.println(table);
    table.put("Kyle", 6);
    System.out.println(table);
    table.put("Lisa", 43);
    System.out.println(table);
    table.put("Lynne", 43);
    System.out.println(table);
    table.put("Victor", 46);
    System.out.println(table);
    table.put("Zoe", 6);
    System.out.println(table);
    table.put("Zoran", 76);
    System.out.println(table);

    for (String key : table.keySet())
      System.out.print(key + " ");
    System.out.println();

    table.remove("Zoe");
    System.out.println(table);
    table.remove("Kyle");
    System.out.println(table);
    table.remove("Brad");
    System.out.println(table);
    table.remove("Zoran");
    System.out.println(table);
    table.remove("Lisa");
    System.out.println(table);
  }
}
			     
