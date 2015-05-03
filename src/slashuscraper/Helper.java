package slashuscraper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Helper {
	
	public static LocalDate stringToDate(String sDate){
		DateTimeFormatter format = DateTimeFormatter
				.ofPattern("EEE LLL dd HH:mm:ss yyyy zzz");
		return LocalDate.parse(sDate, format);		
	}
}
