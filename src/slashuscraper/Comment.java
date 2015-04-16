package slashuscraper;

import java.net.URL;
import java.util.Date;

/* Container for comments */

public class Comment {

	private URL url;			// URL of comment
	private Date datePosted;	// date comment was posted
	private int points;			// points awarded to comment
	private boolean gilded;		// gilded status
	private String subreddit;	// sub reddit where posted
	private String content;		// content of comment

	// constructor
	public Comment(URL url, Date datePosted, int points, boolean gilded, 
			String subreddit, String content) {
		this.url = url;
		this.datePosted = datePosted;
		this.points = points;
		this.gilded = gilded;
		this.subreddit = subreddit;
		this.content = content;
	}

	// get post URL
	public URL getUrl() {
		return url;
	}

	// get date post was created
	public Date getDatePosted() {
		return datePosted;
	}

	// get post points
	public int getPoints() {
		return points;
	}
	
	// get gilded status
	public boolean isGilded() {
		return this.gilded;
	}

	// get subreddit where posted
	public String getSubreddit() {
		return subreddit;
	}

	// get post contents
	public String getContent() {
		return content;
	}
	
	// overriden toString
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				"URL:         " + this.url.toString() +
				"\nDate posted: " + this.datePosted.toString() +
				"\nPoints:      " + this.points +
				"\nGilded:      " + this.gilded +
				"\nSub reddit:  " + this.subreddit.toString() +
				"\nContent:     " + this.content.toString());
		return sb.toString();
	}
}
