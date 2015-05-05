package slashuscraper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 * Class of potentially helpful functions
 */

public class Helper {
	
	// Generate a date object from the Reddit style UTC date string
	public static LocalDate stringToDate(String sDate){
		// Create date format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		// Return newly generated date object
		return LocalDate.parse(sDate.substring(0, sDate.length()-6), format);
	}
}
