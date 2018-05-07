package prog08;

import prog02.GUI;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;

public class WordStep {
	private static class Node {
		String word;
		Node previous;
	

		Node (String word) {
			this.word = word;
		}

		public int distance() {			
				if(previous == null) 
					return 0;
				else
					return previous.distance()+1;
		}
	}
	

/*
 * Add a class NodeComparator that implements Comparator<Node>.

    a. Its constructor NodeComparator(target) should take the target as
       an argument and store it inside NodeComparator.

    b. A value method value(node) returns the distance to the start
       plus the difference from the target.

    c. A compare method compare(node1, node2) should return < 0 if
       node1 has smaller value, = 0 if they have the same value, and >
       0 otherwise.

    Switch to using a PriorityQueue<Node> using your Comparator.
    (Check the documentation, this constructor also requires an
    initial capacity.  Use 1000.)  How many words does it dequeue now?

 */
	private static class NodeComparator implements Comparator<Node>{
		
		String target;
		
		NodeComparator(String target){
			this.target = target;
		}
		
		public int value(Node n) {
			return n.distance() + differenceLetters(n.word, target);
		}
		
		@Override
		public int compare(Node o1, Node o2) {
			return value(o1)-value(o2);
		}
	}
	
	
	
	
	
	

	static GUI ui = new GUI();

	public static void main (String[] args) {
		WordStep game = new WordStep();
		String fn = null;
		do {
			fn = ui.getInfo("Enter dictionary file:");
			if (fn == null)
				return;
		} while (!game.loadDictionary(fn));

		String start = ui.getInfo("Enter starting word:");
		if (start == null)
			return;
		while (game.find(start) == null) {
			ui.sendMessage(start + " is not a word.");
			start = ui.getInfo("Enter starting word:");
			if (start == null)
				return;
		}
		String target = ui.getInfo("Enter target word:");
		if (target == null)
			return;
		while (game.find(target) == null) {
			ui.sendMessage(target + " is not a word.");
			target = ui.getInfo("Enter target word:");
			if (target == null)
				return;
		}

		String[] commands = { "Human plays.", "Computer plays." };
		int c = ui.getCommand(commands);

		if (c == 0)
			game.play(start, target);
		else
			game.solve(start, target);
	}

	void play (String start, String target) {
		while (true) {
			ui.sendMessage("Current word: " + start + "\n" +
					"Target word: " + target);
			String word = ui.getInfo("What is your next word?");
			if (word == null)
				return;
			if (find(word) == null)
				ui.sendMessage(word + " is not in the dictionary.");
			else if (!oneLetter(start, word))
				//adding differenceLetters in here exercise 10
				ui.sendMessage("Sorry, but " + word +
						" differs by " + differenceLetters(start, word) + " letters from " + start);
			
			else if (word.equals(target)) {
				ui.sendMessage("You win!");
				return;
			}
			else
				start = word;
		}
	}    

	static boolean oneLetter (String snow, String slow) {
		if (snow.length() != slow.length())
			return false;
		int count = 0;
		for (int i = 0; i < snow.length(); i++)
			if (snow.charAt(i) != slow.charAt(i))
				count++;
		return count == 1;
	}
	

	//exercise 10
	public static int differenceLetters(String start, String target){
		int count = 0;
		if (start.length() != target.length())
			return -1;
		for (int i = 0; i < start.length(); i++) {
			if (start.charAt(i) != target.charAt(i))
				count++;
		}
		return count;
	}

	List<Node> nodes = new ArrayList<Node>();

	boolean loadDictionary (String file) {
		try {
			Scanner in = new Scanner(new File(file));
			while (in.hasNextLine()) {
				String word = in.nextLine();
				Node node = new Node(word);
				nodes.add(node);
			}
		} catch (Exception e) {
			ui.sendMessage("Uh oh: " + e);
			return false;
		}
		return true;
	}

	Node find (String word) {
		for (int i = 0; i < nodes.size(); i++)
			if (word.equals(nodes.get(i).word))
				return nodes.get(i);
		return null;
	}

	void clearAllPrevious () {
		for (int i = 0; i < nodes.size(); i++)
			nodes.get(i).previous = null;
	}

	void solve (String start, String target) {
		clearAllPrevious();

		//Queue<Node> queue = new LinkedList<Node>();
		//exercise 11
		Queue<Node> queue = new PriorityQueue<Node>(1000, new NodeComparator(target));
		//Queue<Node> queue= new Heap<Node>(new NodeComparator(target));

		int pollCounter = 0;

		Node startNode = find(start);
		queue.offer(startNode);
		while (!queue.isEmpty()) {
			Node node = queue.poll();

			pollCounter++;

			System.out.println("DEQUEUE: " + node.word);
			System.out.print("ENQUEUE:");
			for (int i = 0; i < nodes.size(); i++) {
				Node next = nodes.get(i);
				//if (next != startNode && next.previous == null && oneLetter(node.word, next.word)) {
				if (next != startNode && oneLetter(node.word, next.word)) {
					if(next.previous == null ) {
					next.previous = node;
					queue.offer(next);
					System.out.print(" " + next.word);
					}
					
					if (next.word.equals(target)) {
						//exercise 9
						ui.sendMessage("Steps between target word and start word: " + next.distance());
						ui.sendMessage("Got to " + target + " from " + node.word);
						//step 8
						ui.sendMessage("Calls made to the poll method:" + pollCounter);

						String s = node.word + "\n" + target;
						while (node != startNode) {
							node = node.previous;
							s = node.word + "\n" + s;
						}
						ui.sendMessage(s);
						

						return;
					}
					
					
					else if(node.distance()+1 < next.distance()) {
						queue.remove(next);
						next.previous = node;
						queue.offer(next);
					}	
				}
			}
			System.out.println();
		}
	}
}
