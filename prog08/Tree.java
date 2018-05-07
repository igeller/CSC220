package prog08;
import java.util.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Tree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
    Node left, right;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  private Node root;
  private int size;

  public int size () { return size; }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K,V> root) {
    // EXERCISE:
	  if (root == null)
			return null;
	  if (key.compareTo(root.key) == 0)
		  return root;
	  if (key.compareTo(root.key) < 0)
		return find(key, root.left);
		else
			return find(key, root.right);
  }    

  public boolean containsKey (Object key) {
    return find((K) key, root) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, root);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K,V> add (K key, V value, Node<K,V> root) {
    // EXERCISE:
	  if(root == null)
		  return new Node<K, V>(key, value);
	  if(key.compareTo(root.key) < 0)
		  root.left = add(key, value, root.left);
	  if(key.compareTo(root.key) > 0)
		  root.right = add(key, value, root.right);

    return root;
  }
  
  int depth (Node root) {
    if (root == null)
      return -1;
    return 1 + Math.max(depth(root.left), depth(root.right));
  }

  public V put (K key, V value) {
    // EXERCISE:
	  Node<K, V> putting = find(key, root);
	  
	  if(putting != null) {
		  return putting.setValue(value);
	  }
	  else {
		  root = add(key, value, root);
		  size++;  
	  }
		  
		  

    return null;
  }      
  
  public V remove (Object keyAsObject) {
    /*
     * // EXERCISE:  Delete these lines and implement remove.
     * The public remove checks if there is a node with that key.  
     * If there is, it calls remove(key, root), decrements size, and return the value of the node.
    if (false)
      return null;*/
	  
	 
	  K key = (K) keyAsObject;
	  
	  Node<K,V> node = find(key, root);
	  if(node == null)
		  return null;
	  

	  root = remove(key, root);
	  size--;
	  return node.value;
}

  private Node<K,V> remove (K key, Node<K,V> root) {
    // EXERCISE:
	  /*
	   * key == root.key? return removeRoot(root)
	   * key < root.key? recursive call on left tree
	   * key > root.key? recursive call on right tree 
	   */
	  if(key.equals(root.key)) {
		  return removeRoot(root);
	  }
	  if(key.compareTo(root.key) < 0) {
		 root.left = remove(key, root.left);
	  }
	  else
		  root.right = remove(key, root.right);
		  
    return root;
  }

  /**
   * Remove root of tree rooted at root.
   * Return root of BST of remaining nodes.
   */
  private Node removeRoot (Node root) {
    // IMPLEMENT
	  /*
	   * left subtree is empty?  return right subtree
	   * right subtree is empty?  return left subtree
	   * Use moveMinToRoot to move the minimum in the right subtree to the root of the right subtree.
	   * Put the left subtree (of the root) to the left of the right subtree.
	   *  Return the right subtree.
	   */
	  if(root.left == null)
		  return root.right;
	  if(root.right == null) {
		  return root.left;
	  }
	  
	  Node<K, V> nRoot = moveMinToRoot(root.right);
	  nRoot.left = root.left;
	  root = nRoot;
	  return root;
  }

  /**
   * Move the minimum key (leftmost) node to the root.
   * Return the new root.
   */
  private Node moveMinToRoot (Node root) {
    // IMPLEMENT
	  /*
	   * left subtree is empty?  return root
	   * Move the minimum node to the root of the left subtree.
	   * Put the right subtree of the minimum to the left of the root.
	   * Put the root the right of the minimum node.
	   * Return the minimum node.
	   */
	  
	  if(root.left == null)
		  return root;
	  
	  Node min = root;
	  Node old = root;
	 
	  while(min.left != null) {
		  min = min.left;
	  }
	  
	  root = min;
	  old.left = min.right;
	  root.right = old;
	  
	  return min;
	}

  public Set<Map.Entry<K, V>> entrySet () { return null; }
  
  public String toString () {
    return toString(root, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + " " + root.value + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    Tree<Character, Integer> tree = new Tree<Character, Integer>();
    String s = "balanced";
    
    for (int i = 0; i < s.length(); i++) {
      tree.put(s.charAt(i), i);
      System.out.print(tree);
      System.out.println("-------------");
    }

    for (int i = 0; i < s.length(); i++) {
      tree.remove(s.charAt(i));
      System.out.print(tree);
      System.out.println("-------------");
    }
  }
}
