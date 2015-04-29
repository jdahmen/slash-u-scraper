package slashuscraper;

import java.util.ArrayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScraperManager {

	public static void scrapeUsers(ArrayList<String> usernames) throws InterruptedException {
		// Run a scraper for each user to be scraped		
		
		final int NUMTHREADS = 10;
		
		// Create a pool of NUMTHREAD threads that can scrape up to NUMTHREAD users concurrently
		ExecutorService executor = Executors.newFixedThreadPool(NUMTHREADS);
		
		System.out.println("There are: " + usernames.size() + " users to be scraped.");
		
		for (int i = 0; i < usernames.size(); i++) {
			String username = usernames.get(i);
			
			Runnable scraper = new Scraper(username);			
			executor.execute(scraper);
		}
		executor.shutdown();
		
		while (!executor.isTerminated()) {
			;
		}
		
		System.out.println("Done scraping!");
	}
}
