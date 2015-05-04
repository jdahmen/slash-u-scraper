package slashuscraper;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import slashuscraper.object.Comment;
import slashuscraper.object.Post;

//public class Scraper implements Runnable {
public class Scraper implements Callable<ConcurrentLinkedQueue<Comment>> {
	// Scrapes data for a single user
	// scrape page 1 -> get next page id for comments -> scrape page 2 -> get next page id for comments -> etc
	
	public final String username;
	private ConcurrentLinkedQueue<Comment> comments = new ConcurrentLinkedQueue<Comment>();
	
	Scraper(String username) {
		this.username = username;
	}
	
	@Override
	public ConcurrentLinkedQueue<Comment> call() {
		
		String userURL = "http://www.reddit.com/user/" + username;
		// Testing
		//System.out.println("The url is: " + userURL);
	
		boolean hasNextPage = true; 
		int numPagesFetched = 0;
		
		Document userPage = null;
		try {
			
			do {
				System.out.println("Connecting to URL: " + userURL);
				userPage =  Jsoup.connect( userURL ).timeout(50000).get();
				// Maybe consider randomized user agent, assuming reddit doesn't look solely at IP address
				numPagesFetched++;
				// Testing
				System.out.println("Retrieved user: " + username + " page: " + numPagesFetched);
				
				// The user being scraped created the thread with this post
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
					titleLink = title.attr("href");
					System.out.println(titleLink);
					
					titleDescription = element.select("a[href]").get(1).text();
					System.out.println(titleDescription);
					
					//datetime = element.select("time[title]").first().text();
					datetime = element.select("time[title]").first().attr("datetime");
					System.out.println(datetime);
					
					author = username;
					System.out.println(author);
					
					inSubReddit = element.select("a[class~=(subreddit hover).*").get(0).text();
					System.out.println(inSubReddit);
					
					// Testing
					//System.out.println(element);
					System.out.println("----------------------------------------------------------------------------------------------------");
					
					// Process data here -->
					comments.add(new Post(titleLink, titleDescription, Helper.stringToDate(datetime), score_likes,
					                      score_dislikes, false, inSubReddit, author, ""));
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
					
					score_dislikesStr = element.select("span[class~=(score dislikes)]").get(0).text();
					System.out.println(score_dislikesStr);
					
					score_unvotedStr = element.select("span[class~=(score unvoted)]").get(0).text();
					System.out.println(score_unvotedStr);
					
					score_likesStr = element.select("span[class~=(score likes)]").get(0).text();
					System.out.println(score_likesStr);
					
					Element title = element.select("a[href]").get(0);
					titleLink = title.attr("href");
					System.out.println(titleLink);
					
					titleDescription = element.select("a[href]").get(0).text();
					System.out.println(titleDescription);
					
					//datetime = element.select("time[title]").first().text();
					datetime = element.select("time[title]").first().attr("datetime");
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
					
					// Add comment to list to be returned
					comments.add(new Comment(titleLink, Helper.stringToDate(datetime), score_likes, score_dislikes, 
							                 false, inSubReddit, this.username, userComment));
					
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
				
				if (nextPageLinkString == null)
				{
					hasNextPage = false;
				}
				//For testing
				//System.out.println("The url of nextprev page is: " + nextPageLinkString);
				//System.out.println("----------------------------------------------------------------------------------------------------");
				//System.out.println("----------------------------------------------------------------------------------------------------");
				
				// Check if there is another page of user posts
				// Check that the nextprev url contains "before"
				if (hasNextPage && nextPageLinkString.toLowerCase().contains("before")) {
					// Page n has a before link in the spot where there was an after link was for pages 1 .. n - 1
					hasNextPage = false;
				}
				else {
					if (hasNextPage) {
						userURL = nextPageLinkString;
					}
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
				hasNextPage = false;
			
			} while (hasNextPage);
		}
		catch (IOException e) {
			// It is possible to get a timeout exception when scraping
			// When running on the GMU network, it is much more likely to get an HTTP 429 response (rate limiting from Reddit),
			// which causes an error to occur. Reddit appears to be rate limiting the entire GMU reddit userbase, as all the 
			// requests appear to come from the same IP or the same limited number of IPs. It should work fine on a VPS / etc. 
			System.out.println("Error when scraping user information for user:" + username + " on page: " + numPagesFetched);
			e.printStackTrace();
		}
		
		// For callable
		return comments;		
	}
}
