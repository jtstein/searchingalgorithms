
public class HashFunction {

	WordList[] theArray;
	int arraySize;
	int itemsInArray = 0;
	int amountInTable = 0;

	int assignments = 0;
	int comparisons = 0;
	

	public int countUniqueWords()
	{
		return amountInTable;
	}	
	public int getAssignments()
	{
		return assignments;
	}
	public int getComparisons()
	{
		return comparisons;
	}

	public int stringHashFunction(String wordToHash)
	{
		int hashKeyValue = 0;

		for (int i = 0; i < wordToHash.length(); i++) {
			// 'a' has the character code of 97 so subtract
			// to make our letters start at 1
			int charCode = wordToHash.charAt(i) - 96;
			int hKVTemp = hashKeyValue;
			
			// Calculate the hash key using the 26 letters
			// plus blank
			hashKeyValue = (hashKeyValue * 27 + charCode) % arraySize;
		}
		return hashKeyValue;
	}

	HashFunction(String[] elementsToAdd)
	{
		arraySize = elementsToAdd.length*2;
		theArray = new WordList[arraySize];

		for (int i = 0; i < arraySize; i++)
		{
			theArray[i] = new WordList();
		}
		addTheArray(elementsToAdd);
	}

	public void insert(Word newWord)
	{
		String wordToHash = newWord.theWord;

		int hashKey = stringHashFunction(wordToHash);
		// Add the new word to the array and set
		// the key for the word
		try{
		theArray[hashKey].insert(newWord, hashKey);
		}
		catch (ArrayIndexOutOfBoundsException e){}
		assignments++;
	}

	public Word find(String wordToFind)
	{
		Word theWord = theArray[stringHashFunction(wordToFind)].find(stringHashFunction(wordToFind), wordToFind);
		return theWord;
	}

	public void addTheArray(String[] elementsToAdd) {
		for (int i = 0; i < elementsToAdd.length; i++) {
			
			String word = elementsToAdd[i];

			try{
			if (theArray[stringHashFunction(word)].find(stringHashFunction(word), word) == null)
			{	
				Word newWord = new Word(word, 1);
				// Add the Word to theArray
				insert(newWord);
				amountInTable++;

			} else theArray[stringHashFunction(word)].find(stringHashFunction(word), word).increment(); // increment occurrence if word is in array
			}catch (ArrayIndexOutOfBoundsException e){}
			comparisons++;
		}

	}

	public void displayTheArray() 
	{
		for (int i = 0; i < arraySize; i++)
		{
			System.out.println("theArray Index " + i);
			theArray[i].displayWordList();
		}
	}

}

class Word {

	public String theWord;
	public int occurrence;
	
	public int key;

	public Word next;

	public Word(String theWord, int occurrence) 
	{
		this.theWord = theWord;
		this.occurrence = 1;
	}

	public String toString() 
	{
		return theWord + " : " + occurrence;
	}
	
	public void increment()
	{
		this.occurrence++;
	}

}

class WordList {

	public Word firstWord = null;
	
	public void insert(Word newWord, int hashKey) 
	{
		Word previous = null;
		Word current = firstWord;
		newWord.key = hashKey;

		while (current != null && newWord.key > current.key) 
		{
			previous = current;
			current = current.next;
		}

		if (previous == null)
			firstWord = newWord;
		else previous.next = newWord;

		newWord.next = current;
	}

	public void displayWordList()
	{
		Word current = firstWord;
		
		while (current != null)
		{
			System.out.println(current);
			current = current.next;
		}
	}

	public Word find(int hashKey, String wordToFind)
	{
		Word current = firstWord;

		// Search for key, but stop searching if
		// the hashKey < what we are searching for
		// Because the list is sorted this allows
		// us to avoid searching the whole list

		while (current != null && current.key <= hashKey)
		{
			if (current.theWord.equals(wordToFind))
				return current;

			current = current.next;
		}
		return null;
	}
}