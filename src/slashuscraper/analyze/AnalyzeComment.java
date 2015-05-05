package slashuscraper.analyze;

import java.util.concurrent.Callable;

import slashuscraper.object.Comment;

/*
 * Analyze all comments and posts to find statistics
 */

public class AnalyzeComment implements Callable<Comment> {

	// Locally stored comments to be analyzed
	private Comment comment = null;
	
	// Main constructor
	public AnalyzeComment(Comment comment) {
		this.comment = comment;
	}
	
	// Called to analyzed a comment/post
	@Override
	public Comment call() {
		// Get comment
		Comment comment = this.comment;
		// Check comment to make sure not null
		if(comment == null) { 
			System.out.println("ANALYZE_ERROR: Could not process comment");
			return null; 
		}
		// Process and split comment content
		// Omit punctuation, convert all to lower case, and split by space
		String[] words = comment.getContent().toLowerCase().replaceAll(
				"[^A-Za-z\\s+]", "").split("\\s+");		
		// Traverse words and
		for(String s : words) {
			comment.addWordUsed(s);
		}
		
		// Return processed comment
		return comment;
	}
}
