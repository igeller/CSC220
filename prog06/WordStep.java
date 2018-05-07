package prog06;

import java.util.*;
import java.io.*;

import prog02.GUI;
import prog02.UserInterface;

public class WordStep {
	//variables
	static UserInterface ui = new GUI();


	//constructor
	public WordStep() {}


	private static class Node<String> {
		// Data Fields

		private String word;
		private Node<String> previous;

		// Constructors
		private Node (String word) {
			this.word = word;
			previous = null;
		}


		private Node (String word, Node<String> previous) {
			this.word = word;
			this.previous = previous;
		}
	} //end class Node





	public static void main(String [] args) {
		String start, target;
		int choice = 0;
		WordStep game = new WordStep();


		String dictFile = ui.getInfo("Enter dictionary file");
		if(dictFile == null)
			return;
		game.loadDictionary(dictFile);
		try {
			game.loadDictionary("words");
		} catch (Exception e) {
			System.out.println("test: " + e);
		}


		while(true) {
			start = ui.getInfo("Enter starting word: ");
			if(start == null)
				return;
			if(find(start) == null)
				ui.sendMessage("Word not in dictionary. Try again");
			else
				break;
		}

		while(true) {
			target = ui.getInfo("Enter target word: ");
			if(target == null)
				return;
			if(find(target) == null) 
				ui.sendMessage("Word not in dictionary. Try again");
			else
				break;
		}

		String [] commands = {"Human Plays.", "Computer Plays."};
		while(true) {
			choice = ui.getCommand(commands);
			switch(choice) {
			case 0:	 //human
				game.play(start, target);
				return;
			case 1:	//computer
				game.solve(start, target);
				return;
			}	 	 
		}
	}//end main

	//methods
	public void play(String start, String target) {
		String next;

		while(true) {
			while(true) {
				ui.sendMessage("Current word: " + start + "\nTarget word: " + target);
				next = ui.getInfo("What is your next word?");
				if(next == null)
					return;

				if(find(next) == null)
					ui.sendMessage(next + " not in the dictionary.");
				else
					break;
			}
			if(!oneLetter(start, next))
				ui.sendMessage("Sorry but, " + next + " differs by more than one letter from " + start);
			else
				start = next;

			if(start.equals(target))
				break;
		}	
		ui.sendMessage("You win!");
	}//end play


	

	public static boolean oneLetter(String start, String next) {
		if(start.length() != next.length()) 
			return false;
			
		int length = start.length(), differences = 0;
		char strtChar, tarChar;

		for(int i = 0; i < length; i++) {
			strtChar = start.charAt(i);
			tarChar = next.charAt(i);
			if(strtChar != tarChar)
				differences++;
			if(differences > 1)
				return false;
		}
		return true;
	}//end oneLetter

	
	

	static List<Node> nodes = new ArrayList<Node>();
	
	

	public static void loadDictionary(String name) {
		File fileName = new File(name);
		String word;
		Node<String> wordNode;
		Scanner reader;
		try {
			reader = new Scanner(fileName);
			while(reader.hasNextLine() == true) {
				word = reader.nextLine();
				wordNode = new Node<String>(word);
				nodes.add(wordNode);
			}
		} catch (FileNotFoundException e) {
			ui.sendMessage("Dictionary Not Found");
		}
	}//end loadDictionary

	
	
	public static Node<String> find(String wrd){
		for(Node<String> node: nodes) {
			if((node.word).equals(wrd))
				return node;
		}
		return null;
	}//end find
	
	
	
	public void solve(String start, String target) {
		Queue<Node> solveQueue = new LinkedList<Node>();
		String s = "";
		Node<String> startNode = find(start);
		Node<String>nextNode = null;
		solveQueue.offer(startNode);

		while (!solveQueue.isEmpty()) {
			Node<String> theNode = solveQueue.poll();
			for(Node<String> dictNode : nodes) {
				if(dictNode != startNode && dictNode.previous == null && oneLetter(theNode.word, dictNode.word)) {
					nextNode = dictNode;
					nextNode.previous = theNode;
					
					solveQueue.offer(nextNode);
					
					if((nextNode.word).equals(target)) {
						ui.sendMessage("Computer wins! Target word found!\nGot to " + target + " from " + theNode.word);
						
						while(nextNode != startNode) {
							s = nextNode.word + "\n" + s;
							nextNode = nextNode.previous;
						}
						s = startNode.word + "\n" + s;
						ui.sendMessage(s);
						return;
					}//end small if
				}//end big if
			}//end for
			theNode = solveQueue.peek();
		}//end while
	}//end solve



}//end wordstep class

