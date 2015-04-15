package slashuscraper;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* Main menu to query user for usernames to scrape */

public class MainMenu {
	
	// main method
	public static void Main(String[] args) {
		
		String input; // user input
		
		ArrayList<String> usernames; // list of usernames
		
		// user arguments found
		if(args.length > 0) {
			// argument is file parameter
			if(args[0].trim().equals("-f") || args[0].trim().equals("-F")) {
				// TODO: search username(s) via file
			}
			// file is username
			else if (args[0].trim().equals("-u") || args[0].trim().equals("-U")) {
				// TODO: serach username via user input
			}
			// malformed argument, print usage
			else {
				printUsage();
			}
		}
		// no arguments
		else {
			// print welcome
			System.out.println("Slash U Scraper");
			// input scanner
			Scanner sc = new Scanner(new InputStreamReader(System.in));
			// query user for a username(s)
			System.out.print("Please enter username(s): ");
			// get user input
			input = sc.nextLine();
			// split by 'space' into list of username(s)
			usernames = (ArrayList<String>) Arrays.asList(input.split("\\s+"));
			// append newline
			System.out.print("\n");
			// process all usernames in the list
			// TODO: process
		}		
	}
	
	private static void printUsage() {
		System.out.println("sus\n" + 
				"sus -u [username]\n" + 
				"sus -f [filepath]");
	}
}