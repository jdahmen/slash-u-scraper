package slashuscraper.analyze;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import slashuscraper.object.Comment;
import slashuscraper.object.User;

public class AnalyzeUser implements Callable<User> {

	private User user = null;
	
	public AnalyzeUser(User user) {
		this.user = user;
	}
	
	@Override
	public User call() throws Exception {
		
		// Join date, based on first comment/post
		LocalDate joinDate = LocalDate.now();
		
		// Temporary user comment attributes
		Comment comment = null;
		LocalDate date = null;		
		
		// Process all user comments and posts
		while((comment = user.popComment()) != null) {
			
			// Get date posted
			date = comment.getDatePosted();
			// Replace join date if earlier
			if(date.isBefore(joinDate)) {
				joinDate = date;
			}
			// Increment post per day of the week
			user.addToPostRate(date.getDayOfWeek().getValue());
			
			// Create word rate iterator
			Iterator<Entry<String, Integer>> itr = comment.getWordFrequency()
					.entrySet().iterator();
			Map.Entry<String, Integer> entry = null;
			// Add words and counts to word rate
			while(itr.hasNext()) {
				// Get next entry
				entry = itr.next();
				// Add values to existing word bank
				user.addWordUsed(entry.getKey(), entry.getValue());
			}
			
			// Add karma elemnts to user average (upvotes - downvotes)
			user.addKarmaToAverage(comment.getUpvotes() - comment.getDownvotes());
			
			// Add comment sub to list of subs visited
			user.addVisitedSub(comment.getSubreddit());
		}	
		
		// Set join date
		user.setJoinDate(joinDate);
		
		// Return processed user
		return this.user;
	}

}
