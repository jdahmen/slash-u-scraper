package slashuscraper;

import java.util.ArrayList;

public class ScraperManager {
	// Handling scraping for multiple users should be implemented here
	// Currently ScraperManager is implemented to scrape information from a single user
	// Handling multiple users may relate to how load balancing is implemented, which should also be
	// implemented here

	public static void scrapeUsers(ArrayList<String> usernames) throws InterruptedException {
		// Call Scraper.scrape(String username) for each username listed in usernames		
		
		// Create 4 scraper instances
		Scraper si1 = new Scraper();
		Scraper si2 = new Scraper();
		Scraper si3 = new Scraper();
		Scraper si4 = new Scraper();
		
		// Make an array for the scraper instances
		ArrayList<Scraper> scraperInstances = new ArrayList<Scraper>();
		
		// Add all scrapers to the instance list
		scraperInstances.add(si1);
		scraperInstances.add(si2);
		scraperInstances.add(si3);
		scraperInstances.add(si4);
		
		System.out.println("Added all instances to the list");
		
		// Queue list of users into the scraper instances
		for(int i = 0; i < usernames.size(); i++) {
			Scraper s = scraperInstances.get(i % scraperInstances.size());
			s.enqueue(usernames.get(i));
		}
		
		System.out.println("Enqueued all users");
		
		// Start scrapers instances. Only start the instances that are needed
		// i.e. If only two users are supplied, only two instances will be started
		for(int i = 0; i < 1; i++) {
			scraperInstances.get(i).run();
		}
		
		System.out.println("Started all instances");
		
		// Stop each scraper and collect the data
		for(Scraper s : scraperInstances) {
			// s.getData();
			s.stop();
		}		
		
		// Empty scraper instance array
		scraperInstances.clear();
		
		System.out.println("Done!");
	}
}
