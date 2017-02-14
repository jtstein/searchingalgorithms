/* Jordan Stein
 * CSCI 3412 - Algorithms - FALL 2015
 * Program 2 - Searching Algorithms
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;

public class SearchingAlgorithms {
	
	static long assignments = 0, comparisons = 0;

	public static void main(String args[])
	{
		String fileName = "wordlist.txt";
		
		Vector<String> readWords = new Vector<String>(); // vector that will store all words from file
		readWords = readFileToVector(fileName);  // reads all words into words vector (without punctuation lines)
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Which algorithm would you like to use?");
		System.out.println("1. unsorted vector\n2. sorted vector\n3. binary search tree\n4. hash table");
		int choice = sc.nextInt();
		sc.close();
		
		int uniqueWords = 0;
		
		switch(choice)
		{
			case(1): uniqueWords = unsortedUniqueCount(readWords);
					break;
			case(2): uniqueWords = sortedUniqueCount(readWords);
					break;
			case(3): uniqueWords = binarySearchTree(readWords);
					break;
			case(4): uniqueWords = hashTable(readWords);
					break;
		}
		
		System.out.println("Number of unique words = " + uniqueWords);
		System.out.println("Number of assignments = " + assignments);
		System.out.println("Number of comparisons = " + comparisons);
		
	}
	
	public static int countStr(String str, Vector<String> readWords)
	{
		int count = 0;
		for (int i=0; i < readWords.size(); i++)
		{
			if (readWords.get(i).equals(str))
				count ++; // counts how many times the word appears
			comparisons++;
		}
		return count;
	}
	
	
	public static int hashTable(Vector<String> readWords)
	{
		String[] wordArr = new String[readWords.size()];
		for (int i=0; i < readWords.size(); i++)
			wordArr[i] = readWords.get(i);
		
		HashFunction wordHashTable = new HashFunction(wordArr);
	
		assignments = wordHashTable.getAssignments();
		comparisons = wordHashTable.getComparisons();
		return wordHashTable.countUniqueWords();
	}

	
	public static int binarySearchTree(Vector<String> readWords)
	{
		BinarySearchTree tree = new BinarySearchTree();

		for (int i=0; i < readWords.size(); i++)
		{
			if (!tree.find(readWords.get(i))) // if we didn't find the word in the tree, insert it
				tree.insert(readWords.get(i));
		}
		
		assignments = tree.getAssignments();
		comparisons = tree.getComparisons();
		
		return tree.size;
	}
	

	public static int unsortedUniqueCount(Vector<String> readWords)
	{
		System.out.println("Running unsortedUniqueCount. Please wait.");
		Vector<String> uniqueWords = new Vector<String>(); // stores all unique words
		Vector<Integer> uniqueCount = new Vector<Integer>();
		
		for (int i=0; i < readWords.size(); i++)
		{		
			if (!uniqueWords.contains(readWords.get(i)))
			{
				uniqueWords.add(readWords.get(i));
				uniqueCount.add(countStr(readWords.get(i), readWords));
				assignments++; 
			}
			comparisons += uniqueWords.size(); //.contains compares all words against eachother 

	 
			if (i % 10000 == 1 ) // only used to tell me how far along this algorithm is running during execution
			{ 
				double d = (double) i;
				double r = (double) readWords.size();
				System.out.printf("%.2f",(d/r)*100); 
				System.out.println("%");
			} 
			
		}
		return uniqueWords.size();
	}
	
	public static int sortedUniqueCount(Vector<String> readWords)
	{
		Vector<String> uniqueWords = new Vector<String>();
		Vector<Integer> uniqueCount = new Vector<Integer>();

		Collections.sort(readWords);

		int count = 0; // index for counting same words.
		for (int i=0; i < readWords.size()-1; i++)
		{
			if (!readWords.get(i).equals(readWords.get(i+1)))
			{
				count++; // increment count
				uniqueWords.add(readWords.get(i)); // add that unique word to the vector
				uniqueCount.add(count); //add the count of that word to the count vector
				count = 0; // reset count to zero for next unique word
				assignments++;
			}
			else count++; // add to count if the word is the same.
			comparisons++;
		}

		if (!uniqueWords.contains(readWords.get(readWords.size()-1))) //adds the last element if it is unique (*it is*)
		{
			uniqueWords.add(readWords.get(readWords.size()-1));
			uniqueCount.add(Collections.frequency(readWords,  readWords.get(readWords.size()-1)));
			assignments++;
		}
				
		return uniqueWords.size();
	}
	


	
	public static Vector<String> readFileToVector(String fileName)
	{
		Vector<String> words = new Vector<String>();
		File file = new File(fileName);
		
		try{
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine())
			{
				String data = sc.nextLine().toLowerCase();
				char ch=' ';
				if (!data.equals(""))
				ch = data.charAt(0); // get first character of string
				if (ch >= 'a' && ch <= 'z')
					words.add(data); // adds values to words vector if character is alphabetical.
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return words;
	}
}