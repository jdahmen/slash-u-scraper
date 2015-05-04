package slashuscraper.object;

/*
 * A container for words, used for sorting purposes
 */

public class Word {

	private String word;	// Word stored
	private int count;		// Times word appears
	
	// Main constructor
	public Word(String word, int count) {
		this.word = word;
		this.count = count;
	}	
	
	// Get word
	public String getWord() {
		return this.word;
	}
	
	// Get word count
	public int getCount() {
		return this.count;
	}

	// Increment word count
	public void increment() {
		this.count++;
	}
	
	// Increment word count by N amount
	public void increment(int amount) {
		this.count += amount;
	}	
}
