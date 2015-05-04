package slashuscraper.object;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/* Container for reddit user data and statistics */

public class User {

	private String username;	// reddit username
	private String userBaseUrl;	// user page URL
	
	private LocalDate joinDate;	// date user joined reddit
	
	// Generate average karma = karma / interactions
	private int karma = 0;
	private int interactions = 0;
	
	// sub-reddit hit counts	
	private Hashtable<String, Sub> visitedSubs;
	
	// Store words in a case insensitive format with the word as the
	// key and the integer frequency as the value
	private Hashtable<String, Word> wordFrequency;
	
	// Store frequency of posting, with respect to days of the week
	// where the day of the week is the key and the rate is the value
	private Hashtable<Integer, Integer> postRate;
	
	// List of user comments to be processed
	private ArrayList<Comment> userComments;
	
	/* Add more stats add needed */
	
	// constructor
	public User(String username) {
		this.username = username;
		this.userBaseUrl = ("http://www.reddit.com/user/" + username);
		visitedSubs = new Hashtable<String, Sub>();
		this.wordFrequency = new Hashtable<String, Word>();
		this.postRate = new Hashtable<Integer, Integer>();
		this.userComments = new ArrayList<Comment>();
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
	public Hashtable<String, Sub> getVisitedSubs() {
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
			this.visitedSubs.get(subreddit).increment();
		} else {
			this.visitedSubs.put(subreddit, new Sub(subreddit, 1));
		}
	}

	// get a hash table of used words and hit count
	public Hashtable<String, Word> getUsedWords() {
		return wordFrequency;
	}

	// put words used in a table
	public void addWordUsed(String word) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// if the key exists, increment the value, else initialize to 1
		if(this.wordFrequency.contains(wordUsed)) {
			this.wordFrequency.get(wordUsed).increment();
		} else {
			this.wordFrequency.put(wordUsed, new Word(wordUsed, 1));
		}		
	}
	
	// put words used in a table
	public void addWordUsed(String word, int count) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// if the key exists, increment the value, else initialize to 1
		if(this.wordFrequency.contains(wordUsed)) {
			this.wordFrequency.get(wordUsed).increment(count);
		} else {
			this.wordFrequency.put(wordUsed, new Word(wordUsed, count));
		}		
	}
	
	// Print user stats
	public String stats() {
		
		// Generate dictionary
		List<Word> words = new ArrayList<Word>(wordFrequency.values());
		
		// Generate dictionary sorted by use
		Collections.sort(words, new Comparator<Word>() {
			@Override
			public int compare(Word w1, Word w2) {
				return ( w1.getCount() - w2.getCount() );
			}
		});
		
		// Generate listing of subreddits
		List<Sub> subs = new ArrayList<Sub>(visitedSubs.values());
		
		// Generate sorted list of subreddits, based on frequency
		Collections.sort(subs, new Comparator<Sub>() {
			@Override
			public int compare(Sub s1, Sub s2) {
				return ( s1.getCount() - s2.getCount() );
			}
			
		});	
		
		// Generate string buffer
		StringBuffer sb = new StringBuffer();
		
		// Append basic user info
		sb.append("User: " + this.username + "\n");
		sb.append("URL:  " + this.userBaseUrl + "\n");
		
		sb.append("Most frequency used words: \n");
		
		// Append most commonly used words
		for(int i = 0; i < 3 && i < words.size(); i++) {
			sb.append("    " + (i+1) + ". " + words.get(i).getWord() 
					+ " (" + words.get(i).getCount() + ")\n");
		}
		
		sb.append("Most actively participated subreddits: \n");
		
		// Append most visited subreddits
		for(int i = 0; i < 3 && i < subs.size(); i++) {
			sb.append("    " + (i+1) + ". " + subs.get(i).getSub()
					+ " (" + subs.get(i).getCount() + ")\n");
		}
		
		sb.append("\n");
		
		// Return string
		return sb.toString();
	}
}