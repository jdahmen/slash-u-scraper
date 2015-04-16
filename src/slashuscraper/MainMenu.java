package slashuscraper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* Main menu to query user for usernames to scrape */

public class MainMenu {
	
	// main method
	public static void main(String[] args) { // HERE main was Main  
		
		String input; 											// user input		
		ArrayList<String> usernames = new ArrayList<String>(); 	// list of usernames
		
		// user arguments found
		if(args.length > 0) {
			// argument is file parameter
			if(args[0].trim().equals("-f") || args[0].trim().equals("-F")) {
				usernames = readNamesFromFile(args[1]);
			}
			// file is username
			else if (args[0].trim().equals("-u") || args[0].trim().equals("-U")) {
				// add username(s) to list
				for(int i = 1; args[i] != null; i++) {
					usernames.add(args[i].trim());
				}
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
			// close scanner
			sc.close();
		}
		// if usernames were entered, and not errors occurred, continue
		if(usernames.size() > 0) {
			// TODO: scrape information...
		}
	}
	
	// read in username(s) from user specified file
	private static ArrayList<String> readNamesFromFile(String file) {
		
		File inFile = new File(file.trim());					// file to read in
		ArrayList<String> usernames = new ArrayList<String>();	// usernames extracted from file
		
		// attempt to open the file and create a buffered reader
		try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
		    // line per each read cycle
			String line;
			// process each line until EOF
		    while ((line = br.readLine()) != null) {
		    	// if more than one 'word' per line, only read first entry
		    	usernames.add(line.split("\\s+")[0].trim());
		    }
		}
		// file could not be opened/found
		catch (FileNotFoundException e) {
			System.out.println("File not found: " + file.toString());
		} 
		// error while reading in file
		catch (IOException e) {
			System.out.println("Error reading file: " + file.toString());
		}
		// return list of usernames
		return usernames;
	}

	// print out a message to show user usages
	private static void printUsage() {
		System.out.println("sus\n" + 
				"sus -u [username]\n" + 
				"sus -f [filepath]");
	}
}