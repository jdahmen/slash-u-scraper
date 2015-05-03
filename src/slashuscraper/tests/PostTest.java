package slashuscraper.tests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import slashuscraper.object.Comment;
import slashuscraper.object.Post;

public class PostTest {

	@Test
	public void constructorTest0() throws MalformedURLException {
		URL url = new URL("http://www.reddit.com/");
		String title = "A Unit Test";
		Date date = new Date();
		int pts = 1;
		boolean gilded = false;
		String sub = "gmu";
		String content = "<html> stuff <\\html>";
		Post p = new Post(url, title, date, pts, gilded, sub, content);
		assertEquals(url, p.getUrl());
		assertEquals(date, p.getDatePosted());
		assertEquals(title, p.getTitle());
		assertEquals(pts, p.getPoints());
		assertEquals(gilded, p.isGilded());
		assertEquals(sub, p.getSubreddit());
		assertEquals(content, p.getContent());
	}
	
	@Test
	public void commentListTest0() throws MalformedURLException {
		URL url = new URL("http://www.reddit.com/");
		String title = "A Unit Test";
		Date date = new Date();
		int pts = 1;
		boolean gilded = false;
		String sub = "gmu";
		String content = "<html> stuff <\\html>";
		Comment c = new Comment(url, date, pts, gilded, sub, content);
		Post p = new Post(url, title, date, pts, gilded, sub, content);
		p.addComment(c);
		assertEquals(c, p.getComments().get(0));
	}
	
	@Test
	public void toStringTest0() throws MalformedURLException, ParseException {
		URL url = new URL("http://www.reddit.com/");
		String title = "A Unit Test";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "16-04-2015 10:20:56";
		Date date = sdf.parse(dateInString);
		int pts = 1;
		boolean gilded = false;
		String sub = "gmu";
		String content = "<html> stuff <\\html>";
		Post p = new Post(url, title, date, pts, gilded, sub, content);
		assertEquals("Title:       A Unit Test\n" +
				"URL:         http://www.reddit.com/\n" +
				"Date posted: Thu Apr 16 10:20:56 EDT 2015\n" +
				"Points:      1\n" +
				"Gilded:      false\n" +
				"Sub reddit:  gmu\n" +
				"Content:     <html> stuff <\\html>\n" +
				"Comments:    0", p.toString());
	}
	
	@Test
	public void toStringTest1() throws MalformedURLException, ParseException {
		URL url = new URL("http://www.reddit.com/");
		String title = "A Unit Test";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "16-04-2015 10:20:56";
		Date date = sdf.parse(dateInString);
		int pts = 1;
		boolean gilded = false;
		String sub = "gmu";
		String content = "<html> stuff <\\html>";
		Comment c = new Comment(url, date, pts, gilded, sub, content);
		Post p = new Post(url, title, date, pts, gilded, sub, content);
		p.addComment(c);
		assertEquals("Title:       A Unit Test\n" +
				"URL:         http://www.reddit.com/\n" +
				"Date posted: Thu Apr 16 10:20:56 EDT 2015\n" +
				"Points:      1\n" +
				"Gilded:      false\n" +
				"Sub reddit:  gmu\n" +
				"Content:     <html> stuff <\\html>\n" +
				"Comments:    1", p.toString());
	}

}
