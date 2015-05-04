package slashuscraper.object;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/* Container for comments */

public class Comment {

	private String url;				// URL of comment
	private LocalDate datePosted;	// date comment was posted
	private int upvotes;			// votes for comment
	private int downvotes;			// votes against comment
	private boolean gilded;			// gilded status
	private String subreddit;		// sub reddit where posted
	private String author;			// author of post
	private String content;			// content of comment
	
	// Store words in a case insensitive format with the word as the
	// key and the integer frequency as the value
	private Map<String, Word> wordFrequency;

	// constructor
	public Comment(String url, LocalDate datePosted, int upvotes, int downvotes,
			boolean gilded, String subreddit, String author, String content) {
		this.url = url;
		this.datePosted = datePosted;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
		this.gilded = gilded;
		this.subreddit = subreddit;
		this.author = author;
		this.content = content;
		// Init word frequency
		this.wordFrequency = new HashMap<String, Word>();
	}

	// get post URL
	public String getUrl() {
		return this.url;
	}

	// get date post was created
	public LocalDate getDatePosted() {
		return this.datePosted;
	}

	// get post upvotes
	public int getUpvotes() {
		return this.upvotes;
	}
	
	// get post downvotes
	public int getDownvotes() {
		return this.downvotes;
	}
	
	// get gilded status
	public boolean isGilded() {
		return this.gilded;
	}

	// get subreddit where posted
	public String getSubreddit() {
		return this.subreddit;
	}
	
	// get author
	public String getAuthor() {
		return this.author;
	}

	// get post contents
	public String getContent() {
		return content;
	}
	
	// put words used in a table
	public void addWordUsed(String word) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// get element
		Word wordObj = this.wordFrequency.get(wordUsed);
		// if the key exists, increment the value, else initialize to 1
		if(wordObj != null) {
			wordObj.increment();
			this.wordFrequency.replace(wordUsed, wordObj);
		} else {
			this.wordFrequency.put(wordUsed, new Word(wordUsed, 1));
		}		
	}
	
	// get word frequency
	public Map<String, Word> getWordFrequency() {
		return this.wordFrequency;
	}
	
	// overriden toString
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				"URL:           " + this.url.toString() +
				"\nAuthor:      " + this.author.toString() +
				"\nDate posted: " + this.datePosted.toString() +
				"\nUpvotes:     " + this.upvotes +
				"\nDownvotes:   " + this.downvotes +
				"\nGilded:      " + (this.gilded ? "yes" : "no") +
				"\nSub reddit:  " + this.subreddit.toString() +
				"\nContent:     " + this.content.toString());
		return sb.toString();
	}
}
