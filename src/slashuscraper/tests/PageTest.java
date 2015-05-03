package slashuscraper.tests;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import slashuscraper.*;
import slashuscraper.object.Page;
import slashuscraper.object.PageType;

public class PageTest {

	@Test
	public void emptyConstructor0() {
		Page p = new Page();
		assertEquals(null, p.getUrl());
		assertEquals(null, p.getType());
		assertEquals(null, p.getContent());
	}
	
	@Test
	public void emptyConstructor1() throws MalformedURLException {
		Page p = new Page();
		URL u = new URL("http://www.reddit.com/u/user");
		PageType pt = PageType.OVERVIEW;
		String c = "<html> stuff </html>";
		p.setUrl(u);
		p.setType(pt);
		p.setContent(c);
		assertEquals(u, p.getUrl());
		assertEquals(pt, p.getType());
		assertEquals(c, p.getContent());
	}
	
	@Test
	public void constructor0() throws MalformedURLException {
		URL u = new URL("http://www.reddit.com/u/user");
		PageType pt = PageType.OVERVIEW;
		String c = "<html> stuff </html>";
		Page p = new Page(u, pt, c);
		assertEquals(u, p.getUrl());
		assertEquals(pt, p.getType());
		assertEquals(c, p.getContent());
	}
}
