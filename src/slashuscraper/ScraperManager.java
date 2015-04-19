package slashuscraper;

import java.util.ArrayList;

public class ScraperManager {
	// Handling scraping for multiple users should be implemented here
	// Currently ScraperManager is implemented to scrape information from a single user
	// Handling multiple users may relate to how load balancing is implemented, which should also be
	// implemented here

	public static void scrapeUsers(ArrayList<String> usernames) throws InterruptedException {
		// Call Scraper.scrape(String username) for each username listed in usernames
		
		
		// Perhaps wrap this in a for loop to handle multiple users.
		String username = usernames.get(0);
		
		Scraper.scrape(username);
	}
}
