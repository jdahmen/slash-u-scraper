package slashuscraper.object;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

/* Container for reddit user data and statistics */

public class User {

	private String username;	// reddit username
	private String userBaseUrl;	// user page URL
	
	private LocalDate joinDate;	// date user joined reddit
	
	// Generate average karma = karma / interactions
	private int karma = 0;
	private int interactions = 0;
	
	// sub-reddit hit counts	
	private Hashtable<String, Integer> visitedSubs;
	
	// Store words in a case insensitive format with the word as the
	// key and the integer frequency as the value
	private Hashtable<String, Integer> wordFrequency;
	
	// Store frequency of posting, with respect to days of the week
	// where the day of the week is the key and the rate is the value
	private Hashtable<Integer, Integer> postRate;
	
	// List of user comments to be processed
	private ArrayList<Comment> userComments = null;
	
	/* Add more stats add needed */
	
	// constructor
	public User(String username) {
		this.username = username;
		this.userBaseUrl = ("http://www.reddit.com/user/" + username);
		visitedSubs = new Hashtable<String, Integer>();
		this.wordFrequency = new Hashtable<String, Integer>();
		this.postRate = new Hashtable<Integer, Integer>();
	}

	// get username
	public String getUsername() {
		return username;
	}

	// get user's base URL
	public String getUserBaseUrl() {
		return userBaseUrl;
	}

	// get join date
	public LocalDate getJoinDate() throws Exception {
		if(joinDate == null)
			throw new Exception("Join date not defined");
		return joinDate;
	}

	// set join date 
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	// get link average interaction karma
	public int getAverageKarma() {
		if(this.interactions == 0)
			return 0;
		else
			return (this.karma / this.interactions);
	}

	// add to karma average
	public void addKarmaToAverage(int karma) {
		this.karma += karma;
		this.interactions++;
	}

	// get a hash table of visited subs and hit count
	public Hashtable<String, Integer> getVisitedSubs() {
		return visitedSubs;
	}
	
	// Add a comment to the list of user comments
	public void addComment(Comment comment) {
		this.userComments.add(comment);
	}
	
	// Add multiple comments to the list of user comments
	public void addComments(ArrayList<Comment> comments) {
		this.userComments.addAll(comments);
	}
	
	// Get comment from list and delete it
	public Comment popComment() {
		if(this.userComments.size() != 0) {
			Comment pop = this.userComments.get(0);
			this.userComments.remove(0);
			return pop;
		} else  {
			return null;
		}
	}
	
	// Increment a day of the week per day comment was created
	public void addToPostRate(int day) {
		// if the key exists, increment value, else initialize to 1
		if(this.postRate.contains(day)) {
			this.postRate.put(day, this.postRate.get(day) + 1);
		} else {
			this.postRate.put(day, 1);
		}
	}
	
	// Get the post rate
	public Hashtable<Integer, Integer> getPostRate() {
		return this.postRate;
	}

	// put sub reddit in a table
	public void addVisitedSub(String sub) {
		// convert string to lower case for ease of matching
		String subreddit = sub.trim().toLowerCase();
		// if the key exists, increment value, else initialize to 1
		if(this.visitedSubs.contains(subreddit)) {
			this.visitedSubs.put(subreddit, this.visitedSubs.get(subreddit) + 1);
		} else {
			this.visitedSubs.put(subreddit, 1);
		}
	}

	// get a hash table of used words and hit count
	public Hashtable<String, Integer> getUsedWords() {
		return wordFrequency;
	}

	// put words used in a table
	public void addWordUsed(String word) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// if the key exists, increment the value, else initialize to 1
		if(this.wordFrequency.contains(wordUsed)) {
			this.wordFrequency.put(wordUsed, this.wordFrequency.get(wordUsed) + 1);
		} else {
			this.wordFrequency.put(wordUsed, 1);
		}		
	}
	
	// put words used in a table
	public void addWordUsed(String word, int count) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// if the key exists, increment the value, else initialize to 1
		if(this.wordFrequency.contains(wordUsed)) {
			this.wordFrequency.put(wordUsed, this.wordFrequency.get(wordUsed) + count);
		} else {
			this.wordFrequency.put(wordUsed, count);
		}		
	}
}