package slashuscraper.tests;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import slashuscraper.Comment;

public class CommentTest {
	
	@Test
	public void constructorTest0() throws MalformedURLException {
		URL url = new URL("http://www.reddit.com/");
		Date date = new Date();
		int pts = 1;
		boolean gilded = false;
		String sub = "gmu";
		String content = "<html> stuff <\\html>";
		Comment c = new Comment(url, date, pts, gilded, sub, content);
		assertEquals(url, c.getUrl());
		assertEquals(date, c.getDatePosted());
		assertEquals(pts, c.getPoints());
		assertEquals(gilded, c.isGilded());
		assertEquals(sub, c.getSubreddit());
		assertEquals(content, c.getContent());
	}
	
	@Test
	public void toStringTest0() throws ParseException, MalformedURLException {
		URL url = new URL("http://www.reddit.com/");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "16-04-2015 10:20:56";
		Date date = sdf.parse(dateInString);
		int pts = 1;
		boolean gilded = false;
		String sub = "gmu";
		String content = "<html> stuff <\\html>";
		Comment c = new Comment(url, date, pts, gilded, sub, content);
		assertEquals("URL:         http://www.reddit.com/\n" +
				"Date posted: Thu Apr 16 10:20:56 EDT 2015\n" +
				"Points:      1\n" +
				"Gilded:      false\n" +
				"Sub reddit:  gmu\n" +
				"Content:     <html> stuff <\\html>", c.toString());
				
	}
}
