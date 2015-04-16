package slashuscraper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/* Container class for posts */

public class Post extends Comment{
	
	private String title;	// Post title
	
	private ArrayList<Comment> comments;	// List of comments in post

	// constructor
	public Post(URL url, String title, Date datePosted, int points,
			boolean gilded, String subreddit, String content) {
		super(url, datePosted, points, gilded, subreddit, content);
		
		this.title = title;
		this.comments = new ArrayList<Comment>();
	}

	// get post title
	public String getTitle() {
		return title;
	}
	
	// get list of comments
	public ArrayList<Comment> getComments() {
		return this.comments;
	}
	
	// add comment to list
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	// overriden toString
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				"Title:       " + this.title.toString() +
				"\n" + super.toString() +
				"\nComments:    " + this.comments.size());
		return sb.toString();
	}
}
