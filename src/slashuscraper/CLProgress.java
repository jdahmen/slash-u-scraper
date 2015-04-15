package slashuscraper;

/* Simple command line progress bar */

public class CLProgress {

	private int max;		// max progress bar value
	private int current;	// current progress bar value
	
	// constructor
	public CLProgress(int min, int max) {
		this.max = max;
		this.current = min;
		// Set initial state
		this.update();
	}
	
	// increment progress by one unit
	public void increment() {
		// check bounds
		if(current ++ >= max) {
			current = max;
		}
		// increment;
		else {
			current ++;
		}
		// update progress
		this.update();
	}
	
	// increment progress by specified unit
	public void increment(int amount) {
		// check bounds
		if(current + amount >= max) {
			current = max;
		}
		// increment;
		else {
			current += amount;
		}
		// update progress
		this.update();
	}
	
	// updates status of progress counter
	private void update() {
		System.out.print("Progress" +
				(int) (current/max) +
				"\r");
	}
	
	// updates status as complete and returns a newline
	public void done() {
		System.out.print("Complete\n");
	}
	
	// updates status with a user defined completion message and newline
	public void done(String msg) {
		System.out.print(msg + "\n");
	}
}
