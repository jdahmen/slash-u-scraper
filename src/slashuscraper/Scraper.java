package slashuscraper;

import java.io.*;
import java.util.*;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import java.io.File;
import java.io.BufferedWriter;

public class Scraper {
	// Scrapes data for a single user
	// scrape page 1 -> get next page id for comments -> scrape page 2 -> get next page id for comments -> etc
	
	public static void scrape(String username) throws InterruptedException {
		
		String userURL = "http://www.reddit.com/user/" + username;
		// For testing
		//System.out.println("The url is: " + userURL);
	
		boolean hasNextPage = true; int numPagesFetched = 0;
		
		Document userPage = null;
		try {
			
			do {
				System.out.println("Connecting to URL: " + userURL);
				userPage =  Jsoup.connect( userURL ).timeout(50000).get();
				// Maybe consider randomized user agent, assuming reddit doesn't look solely at IP address
				numPagesFetched++;
				// For testing
				System.out.println("Retrieved user: " + username + " page: " + numPagesFetched);
			
				// Perform processing here
			
				// At this point, the data should be stored permanently into objects, as it will not be available
				// after the next page is scraped.
			
				// Selects everything within the siteTable div. This includes user posts and the neccessary id to
				// scrape the next page of comments
				Elements userComments = userPage.select("div#siteTable");
				// For testing
				/*for (Element element : userComments) {
					System.out.println(element);
				  }*/
				
				Elements nextPageLink = userPage.select("span[class=nextprev]");
				// For testing
				/*
				System.out.println("----------------------------------------------------------------------------------------------------");
				for (Element element : nextPageLink) {
					System.out.println(element);
				}
				System.out.println("----------------------------------------------------------------------------------------------------");
				*/
				
				String nextPageLinkString = null;
				if (numPagesFetched == 1)
				{
					// Page 1 has an after link
					nextPageLinkString = nextPageLink.get(0).select("a").first().attr("href");
				}
				else
				{
					// Page 2 ... n - 1 have a before and an after link in the same span
					nextPageLinkString = nextPageLink.get(0).select("a").last().attr("href");
				}
				//For testing
				//System.out.println("The url of nextprev page is: " + nextPageLinkString);
				//System.out.println("----------------------------------------------------------------------------------------------------");
				//System.out.println("----------------------------------------------------------------------------------------------------");
				
				// Check if there is another page of user posts
				// Check that the nextprev url contains "before"
				if (nextPageLinkString.toLowerCase().contains("before")) {
					// Page n has a before link in the spot where there was an after link was for pages 1 .. n - 1
					hasNextPage = false;
				}
				else {
					userURL = nextPageLinkString;
				}
					
				// Random wait every 3 pages processed // Crude rate-limiting
				if (hasNextPage && ((numPagesFetched % 3) == 0) )
				{
					Random rn = new Random();
					int t = rn.nextInt(11) + 5;
					System.out.println("Sleeping: " + t + " seconds.");
					try {
						Thread.sleep(t * 1000);
					} catch (InterruptedException e) {
						System.out.println("Error in thread sleep.");
					}
				}
				
				// Testing
				//hasNextPage = false;
			
			} while (hasNextPage);
		}
		catch (IOException e) {
			System.out.println("Timeout error when scraping user information for user:" + username);
		}
		
	}

}
