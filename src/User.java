import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/* Container for reddit user data and statistics */

public class User {

	private String username;	// reddit username
	private URL userBaseUrl;	// user page URL
	private Date joinDate;		// date user joined reddit
	
	private int linkKarma;		// link karma count
	private int commentKarma;	// comment karma count
	
	private ArrayList<String> trophies;					// list of trophies
	
	private Hashtable<String, Integer> visitedSubs;	// sub-reddit hit counts
	private Hashtable<String, Integer> wordsUsed;	// work hit counts
	
	/* Add more stats add needed */
	
	// constructor
	User(String username, URL userBaseUrl) {
		this.username = username;
		this.userBaseUrl = userBaseUrl;		
		trophies = new ArrayList<String>();
		visitedSubs = new Hashtable<String, Integer>();
		wordsUsed = new Hashtable<String, Integer>();
	}

	// get username
	public String getUsername() {
		return username;
	}

	// get user's base URL
	public URL getUserBaseUrl() {
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
		return wordsUsed;
	}

	// put words used in a table
	public void addWordUsed(String word) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// if the key exists, increment the value, else initialize to 1
		if(this.wordsUsed.contains(wordUsed)) {
			this.wordsUsed.put(wordUsed, this.wordsUsed.get(wordUsed) + 1);
		} else {
			this.wordsUsed.put(wordUsed, 1);
		}		
	}	
}