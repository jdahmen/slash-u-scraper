package slashuscraper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Helper {
	
	public static LocalDate stringToDate(String sDate){
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		
//		System.out.println("Full date: " + sDate);
		
		return LocalDate.parse(sDate.substring(0, sDate.length()-6), format);
	}
}
