package slashuscraper;

import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperManager {
	// Handling scraping for multiple users should be implemented here
	// Currently ScraperManager is implemented to scrape information from a single user
	// Handling multiple users may relate to how load balancing is implemented, which should also be
	// implemented here

	public static void scrapeUsers(ArrayList<String> usernames) throws InterruptedException {
		// Call Scraper.scrape(String username) for each username listed in usernames		
		
		final int NUMTHREADS = 10;
		
		ExecutorService executor = Executors.newFixedThreadPool(NUMTHREADS);
		
		System.out.println("There are :" + usernames.size() + " users to be scraped.");
		
		// Testing 
		for (int i = 0; i < usernames.size(); i++) {
			String username = usernames.get(i);
			
			Runnable scraper = new Scraper(username);			
			executor.execute(scraper);
		}
		executor.shutdown();
		
		while (!executor.isTerminated()) {
			;
		}
		
		System.out.println("Done!");
	}
}
