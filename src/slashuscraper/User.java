package slashuscraper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/* Container for reddit user data and statistics */

public class User {

	private String username;	// reddit username
	private String userBaseUrl;	// user page URL
	private Date joinDate;		// date user joined reddit
	
	private int linkKarma;		// link karma count
	private int commentKarma;	// comment karma count
	
	private ArrayList<String> trophies;				// list of trophies
	
	private Hashtable<String, Integer> visitedSubs;	// sub-reddit hit counts
	
	// Store words in a case insensitive format with the word as the
	// key and the integer frequency as the value
	private Hashtable<String, Integer> wordFrequency;
	// Store frequency of posting, with respect to days of the week
	// where the day of the week is the key and the rate is the value
	private Hashtable<Integer, Integer> postRate;
	
	/* Add more stats add needed */
	
	// constructor
	public User(String username) {
		this.username = username;
		this.userBaseUrl = ("http://www.reddit.com/user/" + username);		
		trophies = new ArrayList<String>();
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
	public Date getJoinDate() throws Exception {
		if(joinDate == null)
			throw new Exception("Join date not defined");
		return joinDate;
	}

	// set join date 
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	// get link karma
	public int getLinkKarma() {
		return linkKarma;
	}

	// set link karma
	public void setLinkKarma(int linkKarma) {
		this.linkKarma = linkKarma;
	}

	// get comment karma
	public int getCommentKarma() {
		return commentKarma;
	}

	// set comment karma
	public void setCommentKarma(int commentKarma) {
		this.commentKarma = commentKarma;
	}

	// get trophy list
	public ArrayList<String> getTrophies() {
		return trophies;
	}

	// add a trophy to the list
	public void addTrophy(String trophy) {
		trophies.add(trophy);
	}

	// get a hash table of visited subs and hit count
	public Hashtable<String, Integer> getVisitedSubs() {
		return visitedSubs;
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
}