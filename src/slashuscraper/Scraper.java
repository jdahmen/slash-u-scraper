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
	
	@SuppressWarnings("unused")
	@Override
	public ConcurrentLinkedQueue<Comment> call() {
		
		String userURL = "http://www.reddit.com/user/" + username;
		
		//System.out.println("The url is: " + userURL);
		
		// Random generator
		Random rand = new Random();
	
		boolean hasNextPage = true; 
		int numPagesFetched = 0;
		
		Document userPage = null;
		try {
			
			
			
			do {			
				System.out.println("Connecting to URL: " + userURL);
				// Set random port to help diminish 429 errors
				System.setProperty("http.proxyPort", String.valueOf((rand.nextInt() % 1000) + 5000));
				userPage =  Jsoup.connect( userURL )
						  .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
					      .referrer("http://www.google.com").timeout(50000).get();
				// Maybe consider randomized user agent, assuming reddit doesn't look solely at IP address
				numPagesFetched++;
				// Testing
				System.out.println("Retrieved user: " + username + " page: " + numPagesFetched);
				
				// The user being scraped created the thread with this post
				// System.out.println("User post is OP");
				
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
					score_dislikes = Integer.parseInt(element.select("div[class~=(score dislikes)]").get(0).text());					
					score_unvoted = Integer.parseInt(element.select("div[class~=(score unvoted)]").get(0).text());					
					score_likes = Integer.parseInt(element.select("div[class~=(score likes)]").get(0).text());			
					
					Element title = element.select("a").first();
					titleLink = title.attr("href");					
					titleDescription = element.select("a[href]").get(1).text();
					
					//datetime = element.select("time[title]").first().text();
					datetime = element.select("time[title]").first().attr("datetime");					
					author = username;					
					inSubReddit = element.select("a[class~=(subreddit hover).*").get(0).text();									
					comments.add(new Post(titleLink, titleDescription, Helper.stringToDate(datetime), score_likes,
					                      score_dislikes, false, inSubReddit, author, ""));
				}
								
				// The user being queried commented on a thread created by another user				
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
					threadAuthor = element.select("a[class~=(author ).*").get(0).text();					
					inSubReddit = element.select("a[class~=(subreddit hover).*").get(0).text();					
					score_dislikesStr = element.select("span[class~=(score dislikes)]").get(0).text();					
					score_unvotedStr = element.select("span[class~=(score unvoted)]").get(0).text();					
					score_likesStr = element.select("span[class~=(score likes)]").get(0).text();
					
					Element title = element.select("a[href]").get(0);
					titleLink = title.attr("href");					
					titleDescription = element.select("a[href]").get(0).text();
					
					//datetime = element.select("time[title]").first().text();
					datetime = element.select("time[title]").first().attr("datetime");					
					author = username;					
					userComment = element.select("div.md").text();					
					subRedditTo = inSubReddit;
				
					// score_likes = Integer.parseInt(score_likesStr.replaceAll("[\\D]", ""));
					// score_dislikes = Integer.parseInt(score_dislikesStr.replaceAll("[\\D]", ""))
					
					// Add comment to list to be returned
					comments.add(new Comment(titleLink, Helper.stringToDate(datetime), score_likes, score_dislikes, 
							                 false, inSubReddit, this.username, userComment));					
				}
				

				Elements nextPageLink = userPage.select("span[class=nextprev]");
				
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
				if (hasNextPage && ((numPagesFetched % 1) == 0) )
				{
					Random rn = new Random();
					int t = rn.nextInt(7) + 3;
					System.out.println("Sleeping: " + t + " seconds.");
					try {
						Thread.sleep(t * 1000);
					} catch (InterruptedException e) {
						System.out.println("Error in thread sleep.");
					}
				}
				
				// Testing
				// hasNextPage = false;
			
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
