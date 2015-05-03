package slashuscraper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScraperManager {

	private ConcurrentLinkedQueue<Comment> comments = new ConcurrentLinkedQueue<Comment>();

	public static void scrapeUsers(ArrayList<String> usernames)
			throws InterruptedException {

		// Create a list of users
		ArrayList<User> users = new ArrayList<User>();
		
		// Create a queue of comments
		ConcurrentLinkedQueue<Comment> comments = new ConcurrentLinkedQueue<Comment>();

		/**************************************************************************/
		/* Scrape comments                                                        */
		/**************************************************************************/	
		
		// Thread count
		final int NUMTHREADS = 8;
		
		// Create a pool of NUMTHREAD threads that can scrape up to NUMTHREAD
		// users concurrently
		ExecutorService executor = Executors.newFixedThreadPool(NUMTHREADS);

		System.out.println("There are: " + usernames.size()
				+ " users to be scraped.");

		for (int i = 0; i < usernames.size(); i++) {
			String username = usernames.get(i);

			// Add user to users list
			users.add(new User(usernames.get(i)));

			Runnable scraper = new Scraper(username);
			executor.execute(scraper);
		}
		executor.shutdown();

		while (!executor.isTerminated()) {
			;
		}
		
		// TODO: Collect comments into queue (above)

		System.out.println("Done scraping!");
		
		/************************************************************************/
		/* Analyze comments                                                     */
		/************************************************************************/		

		// Begin analyzing posts and comments
		// Use a cached thread pool to expand thread count dynamically
		ExecutorService cachedPool = Executors.newCachedThreadPool();

		// Create a list of future objects
		List<Future<Comment>> analyzedComments = new ArrayList<Future<Comment>>();
		
		Comment toAnalyze = null;
		
		while((toAnalyze = comments.poll()) != null) {
			Callable<Comment> analyzeComments = new AnalyzeComment(toAnalyze);
			Future<Comment> callableFuture = cachedPool.submit(analyzeComments);
			analyzedComments.add(callableFuture);
		}
		
		// shutdown the pool.
		cachedPool.shutdown();
		
		/************************************************************************/
		/* Analyze User Data                                                    */
		/************************************************************************/
		
		// TODO: create 

	}
}
