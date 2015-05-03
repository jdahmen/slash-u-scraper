package slashuscraper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/*
 * Analyze all comments and posts to find statistics
 */

public class AnalyzeComment implements Callable<Comment> {

	// Locally stored comments to be analyzed
	private final BlockingQueue<Comment> comments;
	
	// Main constructor
	public AnalyzeComment(BlockingQueue<Comment> comments) {
		this.comments = comments;
	}
	
	// Called to analyzed a comment/post
	@Override
	public Comment call() throws Exception {
		// Get comment
		Comment comment = null;
		while((comment = comments.poll()) != null) {
			// Process and split comment content
			// Omit punctuation, convert all to lower case, and split by space
			String[] words = comment.getContent().replace("[^A-Za-z'-]", "")
					.toLowerCase().split("\\s+");
			// Traverse words and
			for(String s : words) {
				comment.addWordUsed(s);
			}		
			// Return analyzed comment
			return comment;
		}
	}
}
