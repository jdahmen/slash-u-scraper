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
		if(this.postRate.contains(day) && this.postRate.get(day) != null) {
			this.postRate.replace(day, this.postRate.get(day) + 1);
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
		// get element
		Sub subObj = this.visitedSubs.get(subreddit);
		// if the key exists, increment value, else initialize to 1
		if(subObj != null) {
			subObj.increment();
			this.visitedSubs.replace(subreddit, subObj);
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
	
	// put words used in a table
	public void addWordUsed(String word, int count) {
		// convert string to lower case for ease of matching
		String wordUsed = word.trim().toLowerCase();
		// get element
		Word wordObj = this.wordFrequency.get(wordUsed);
		// if the key exists, increment the value, else initialize to 1
		if(wordObj != null) {
			wordObj.increment(count);
			this.wordFrequency.replace(wordUsed, wordObj);
		} else {
			this.wordFrequency.put(wordUsed, new Word(wordUsed, count));
		}		
	}
	
	// Print user stats
	public String stats() {
		
		// Remove common words from map
		String[] commonWords = {"a","the","you","to","of","and","i",
				"it","is","your","that","if","for","in","are","this"};
		for(String cw : commonWords) {
			wordFrequency.remove(cw);
		}
		
		// Generate dictionary
		List<Word> words = new ArrayList<Word>(wordFrequency.values());
		
		// Generate dictionary sorted by use
		Collections.sort(words, new Comparator<Word>() {
			@Override
			public int compare(Word w1, Word w2) {
				return ( w2.getCount() - w1.getCount() );
			}
		});
		
		// Generate listing of subreddits
		List<Sub> subs = new ArrayList<Sub>(visitedSubs.values());
		
		// Generate sorted list of subreddits, based on frequency
		Collections.sort(subs, new Comparator<Sub>() {
			@Override
			public int compare(Sub s1, Sub s2) {
				return ( s2.getCount() - s1.getCount() );
			}
			
		});	
		
		// Generate string buffer
		StringBuffer sb = new StringBuffer();
		
		// Append basic user info
		sb.append("User: " + this.username + "\n");
		sb.append("URL:  " + this.userBaseUrl + "\n");
		sb.append("Active since: " + this.joinDate.toString() + "\n");
		
		sb.append("Most frequency used words: \n");
		
		// Append most commonly used words
		for(int i = 0; i < 10 && i < words.size(); i++) {
			sb.append("    " + (i+1) + ". " + words.get(i).getWord() 
					+ " (" + words.get(i).getCount() + ")\n");
		}
		
		sb.append("Most actively participated subreddits: \n");
		
		// Append most visited subreddits
		for(int i = 0; i < 10 && i < subs.size(); i++) {
			sb.append("    " + (i+1) + ". " + subs.get(i).getSub()
					+ " (" + subs.get(i).getCount() + ")\n");
		}
		
		sb.append("\n");
		
//		for(Word w : words) {
//			System.out.println("Word: " + w.getWord() + "(" + w.getCount() + ")\n");
//		}
		
		// Return string
		return sb.toString();
	}
}