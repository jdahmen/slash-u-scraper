package slashuscraper;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	// Scrapes data for a single user
	// scrape page 1 -> get next page id for comments -> scrape page 2 -> get next page id for comments -> etc
	
	private ConcurrentLinkedQueue<String> usernames;	// List of usernames to be scraped
	private boolean scrape;								// Status of scraping <T> scrape or <F> stopped
	
	// Constructor
	public Scraper() {
		// Initialize class variables
		this.scrape = false;
		this.usernames = new ConcurrentLinkedQueue<String>();
	}
	
	// Add a single user to the queue
	public void enqueue(String username) {
		usernames.add(username);
	}
	
	// Add a list of users to the queue
	public void enqueue(ArrayList<String> usernames) {
		usernames.addAll(usernames);
	}
	
	// Return a username or empty
	public String dequeue() {
		return usernames.poll();
	}
	
	// Start the scrape cycler
	public void start() {
		this.scrape = true;
		try {
			this.scrapeCycle();
		} catch (InterruptedException e) {
			System.out.println("CRITICAL ERROR: Scraper thread could no execute wait " 
					+ "and has now crashed");			
		}
	}
	
	// Stop the scrape cycler
	public void stop() {
		this.scrape = false;		
	}
	
	// Do scraping while class is active
	private void scrapeCycle() throws InterruptedException {
		// Username being evaluated
		String username = "None";
		// While active
		while(scrape == true) {
			// Check if queue is occupied
			if(usernames.size() > 0) {
				// Attempt to scrape user
				try {
					// Get head of queue
					username = usernames.poll();
					// Scrape username
					scrape(username);
				} catch (InterruptedException e) {
					System.out.println("ERROR: Could not scrape user: " + username
							+ ". Moving to next user.");
				}
				// Remove user from queue when finished
				usernames.remove(0);
			} else {
				// Wait 50 milliseconds if the queue is empty
				TimeUnit.MILLISECONDS.wait(50);
			}
		}
		// Finished
		return;
	}
	
	private void scrape(String username) throws InterruptedException {
		
		String userURL = "http://www.reddit.com/user/" + username;
		// For testing
		//System.out.println("The url is: " + userURL);
	
		boolean hasNextPage = true; int numPagesFetched = 0;
		
		Document userPage = null;
		try {
			
			do {
				System.out.println("Connecting to URL: " + userURL);
				userPage =  Jsoup.connect( userURL ).timeout(50000).get(); 						// ERROR: GETTING TIMEOUT EVERYTIME
				// Maybe consider randomized user agent, assuming reddit doesn't look solely at IP address
				numPagesFetched++;
				// For testing
				System.out.println("Retrieved user: " + username + " page: " + numPagesFetched);
				
				/*
				Elements userCommentsV2 = userPage.select("div[class~=( thing id-)[a-z0-9]+");
				// For testing
				System.out.println("Comments ...");
				for (Element element : userCommentsV2) {
					System.out.println(element);
					System.out.println("----------------------------------------------------------------------------------------------------");
					
				}
				System.out.println("----------------------------------------------------------------------------------------------------");
				*/
				
				// The user being queried created the thread with this post
				System.out.println("User post is OP");
				
				String data_fullname = "";
				int score_dislikes = 0; 
				int score_unvoted = 0; 
				int score_likes  = 0;
				String titleLink = ""; 
				String titleDescription = "";
				String datetime = "";
				String author = "";
				String inSubReddit = "";
				
				Elements userCreatedThreads = userPage.select("div[class~=( thing id-).*( self)$]");
				
				for (Element element : userCreatedThreads) {
					
					data_fullname = element.attr("data-fullname");
					System.out.println(data_fullname);
					
					score_dislikes = Integer.parseInt(element.select("div[class~=(score dislikes)]").get(0).text());
					System.out.println(score_dislikes);
					
					score_unvoted = Integer.parseInt(element.select("div[class~=(score unvoted)]").get(0).text());
					System.out.println(score_unvoted);
					
					score_likes = Integer.parseInt(element.select("div[class~=(score likes)]").get(0).text());
					System.out.println(score_likes);
					
					
					Element title = element.select("a").first();
					//titleLink = element.select("a").first().attr("href");
					titleLink = title.attr("href");
					System.out.println(titleLink);
					
					//titleDescription = element.select("a").first().text();
					//titleDescription = title.outerHtml();
					titleDescription = element.select("a[href]").get(1).text();
					System.out.println(titleDescription);
					
					// Retrieving the time is currently not working correctly
					datetime = element.select("time[title]").first().text();
					System.out.println(datetime);
					
					author = username;
					System.out.println(author);
					
					inSubReddit = element.select("a[class~=(subreddit hover).*").get(0).text();
					System.out.println(inSubReddit);
					
					// For testing
					//System.out.println(element);
					System.out.println("----------------------------------------------------------------------------------------------------");
					
					// Process data here -->
				}
				
				System.out.println("----------------------------------------------------------------------------------------------------");
				System.out.println("|                                                                                                  |");
				System.out.println("|                                                                                                  |");
				System.out.println("|                                                                                                  |");
				System.out.println("----------------------------------------------------------------------------------------------------");
				
				
				// The user being queried commented on a thread created by another user
				System.out.println("User post is in another user's thread");
				
				data_fullname = "";
				titleLink = "";
				titleDescription = "";
				String threadAuthor = "";
				inSubReddit = "";
				String score_dislikesStr = "";
				String score_unvotedStr = "";
				String score_likesStr = "";
				datetime = "";
				author = "";
				String userComment = "";
				String subRedditTo = "";
				
				Elements userPostedInThreads = userPage.select("div[class~=( thing id-).*( comment )$]");
				
				for (Element element : userPostedInThreads) {
					
					data_fullname = element.attr("data-fullname");
					System.out.println(data_fullname);
					
					threadAuthor = element.select("a[class~=(author ).*").get(0).text();
					System.out.println(threadAuthor);
					
					inSubReddit = element.select("a[class~=(subreddit hover).*").get(0).text();
					System.out.println(inSubReddit);
					
					// score_* is returning odd results, will need to be fixed
					score_dislikesStr = element.select("span[class~=(score dislikes)]").get(0).text();
					System.out.println(score_dislikes);
					
					score_unvotedStr = element.select("span[class~=(score unvoted)]").get(0).text();
					System.out.println(score_unvoted);
					
					score_likesStr = element.select("span[class~=(score likes)]").get(0).text();
					System.out.println(score_likes);
					
					Element title = element.select("a[href]").get(0);
					titleLink = title.attr("href");
					System.out.println(titleLink);
					
					titleDescription = element.select("a[href]").get(0).text();
					System.out.println(titleDescription);
					
					// Retrieving the time is currently not working correctly
					datetime = element.select("time[title]").first().text();
					System.out.println(datetime);
					
					author = username;
					System.out.println(author);
					
					userComment = element.select("div.md").text();
					System.out.println(userComment);
					
					subRedditTo = inSubReddit;
					System.out.println(subRedditTo);
					
					// For testing
					//System.out.println(element);
					System.out.println("----------------------------------------------------------------------------------------------------");
					
					// Process data here -->
				}
				
				System.out.println("----------------------------------------------------------------------------------------------------");
				System.out.println("|                                                                                                  |");
				System.out.println("|                                                                                                  |");
				System.out.println("|                                                                                                  |");
				System.out.println("----------------------------------------------------------------------------------------------------");
				
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
				
				// For testing
				hasNextPage = false;
			
			} while (hasNextPage);
		}
		catch (IOException e) {
			System.out.println("Timeout error when scraping user information for user:" + username);
		}
		
	}

}
