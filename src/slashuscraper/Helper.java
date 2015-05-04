package slashuscraper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Helper {
	
	public static LocalDate stringToDate(String sDate){
//		DateTimeFormatter format = DateTimeFormatter
//				.ofPattern("EEE LLL dd HH:mm:ss yyyy zzz");
//				.ofPattern("EEE' 'LLL' 'dd' 'HH:mm:ssp' 'yyyy' 'zzz");
		
		// 2014-11-24T13:46:58-08:00
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		
		// Testing
		System.out.println("Full date: " + sDate);
		
		return LocalDate.parse(sDate.substring(0, sDate.length()-6), format);
		
		
	}
}
