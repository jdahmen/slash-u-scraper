package slashuscraper.object;

import java.net.URL;

/* Container class for web pages */

public class Page {
	
	private URL url;		// page URL
	private PageType type;	// enumerated page type
	private String content;	// content
	
	// empty constructor
	public Page() {

	}
	
	// full constructor
	public Page(URL url, PageType type, String content) {
		this.url = url;
		this.type = type;
		this.content = content;
	}

	// get URL
	public URL getUrl() {
		return url;
	}

	// set URL
	public void setUrl(URL url) {
		this.url = url;
	}

	// get page type
	public PageType getType() {
		return type;
	}

	// set page type
	public void setType(PageType type) {
		this.type = type;
	}

	// get content
	public String getContent() {
		return content;
	}

	// set content
	public void setContent(String content) {
		this.content = content;
	}
	
	// overriden toString
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
				"URL:     " + this.url.toString() +
				"Type:    " + this.type.toString() +
				"Content: " + this.content.toString());
		return sb.toString();
	}
}