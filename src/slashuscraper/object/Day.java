package slashuscraper.object;

/*
 * Container object for holding posts per day frequency
 */

public class Day {
	
	// Final list containing the days of the week with restpect to
	// the ordering used by LocalDate
	private final String[] DAYOFWEEK = {"Monday", "Tuesday", "Wednesday", 
			"Thursday", "Friday", "Saturday", "Sunday"};
	
	private int day;	// Day of the week's value
	private int count;	// Posts per that day of week
	
	// Main constructor
	public Day(int day, int count) {
		if(day > 7 || day < 1) {
			try {
				// Throw error if date is not within bounds
				throw new Exception();
			} catch (Exception e) {
				System.out.println("[ERROR] Not a valid day of week 1-7");
			}
		} else {
			this.day = day;
			this.count = count;
		}
	}
	
	// Get day's int value	
	public int getDay() {
		return this.day;
	}
	
	// Get the name of the date of week
	public String getDayName() {
		return this.DAYOFWEEK[this.day-1];
	}
	
	// Get the count of post per day of week
	public int getCount() {
		return this.count;
	}
	
	// Increment count
	public void increment() {
		this.count++;
	}
	
	// Increment count by N
	public void increment(int amount) {
		this.count += amount;
	}
}
