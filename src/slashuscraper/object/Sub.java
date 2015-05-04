package slashuscraper.object;

/*
 * A container for sort subreddits
 */

public class Sub {
	
	private String sub;	// Word stored
	private int count;		// Times word appears
	
	// Main constructor
	public Sub(String sub, int count) {
		this.sub = sub;
		this.count = count;
	}	
	
	// Get word
	public String getSub() {
		return this.sub;
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
