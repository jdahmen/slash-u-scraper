package slashuscraper;

import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Analyze all comments and posts to find statistics
 */

public class AnalyzeComment implements Runnable {

	// Locally stored comments to be analyzed
	private ConcurrentLinkedQueue<Comment> collectedComments;
	// Locally stored comment that have been analyzed
	private ConcurrentLinkedQueue<Comment> processedComments;
	
	// Main constructor
	public AnalyzeComment(ConcurrentLinkedQueue<Comment> comments) {
		this.collectedComments = comments;
		this.processedComments = new ConcurrentLinkedQueue<Comment>();
	}
	
	// Called to analyzed a comment/post
	@Override
	public void run() {
		// Get comment
		Comment comment = null;
		while((comment = collectedComments.poll()) != null) {
			// Process and split comment content
			// Omit punctuation, convert all to lower case, and split by space
			String[] words = comment.getContent().replace("[^A-Za-z'-]", "")
					.toLowerCase().split("\\s+");
			// Traverse words and
			for(String s : words) {
				comment.addWordUsed(s);
			}
			// Add to processed queue
			processedComments.add(comment);
		}
	}
	
	// Return all processed comments
	public ConcurrentLinkedQueue<Comment> getProcessedComments() {
		return this.processedComments;
	}
}
