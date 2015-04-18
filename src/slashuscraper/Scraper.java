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
	
	// A loop needs to be implemented to scrape all pages for that user.
	
	// scrape page 1 -> get next page id for comments -> scrape page 2 -> get next page id for comments -> etc
	
	public static void scrape(String username) {
		
		String userURL = "http://www.reddit.com/user/" + username;
		
		System.out.println("The url is: " + userURL);
	
		boolean hasNextPage = true;
		
		Document userPage = null;
		try {
			userPage =  Jsoup.connect( userURL ).timeout(20000).get();
			
			System.out.println("Retrieved user" + username);
			
			// Perform processing here
			
			// At this point, the data should be stored permanently into objects, as it will not be available
			// after the next page is scraped.
			
			// Selects everything within the siteTable div. This includes user posts and the neccessary id to
			// scrape the next page of comments
			Elements userComments = userPage.select("div#siteTable");
			
			for (Element element : userComments) {
		        System.out.println(element);
		    }
		}
		catch (IOException e) {
			System.out.println("Timeout error when scraping user information for user:" + username);
		}
		
	}

}
