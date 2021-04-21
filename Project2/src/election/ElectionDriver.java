/**
 * ElectionDriver.java
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * ElectionDriver contains the main function. In main the ballots csv
 * file will be opened from the file provided in the cmd args and the
 * first line will be read to gather the election type. Then either an
 * instance of the InstantRunoffElection class or OpenPartyListingElection 
 * class will be created and determineWinner() will be called inside the
 * constructor.
 * 
 */

package election;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ElectionDriver {
	public static void main(String[] args) throws FileNotFoundException {
		String[] fileNames = new String[args.length];
		// Attempt to find the file path if not found throw exception and terminate
		try {
			for (int i = 0; i < args.length; i++) {
				fileNames[i] = ElectionDriver.class.getResource(args[i]).getPath();
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Please enter a valid file name.\n");
		}
		// File found open file and scanner to read file
		List<Scanner> ballotFiles = new ArrayList<>();
		System.out.println("Ballots file(s) found, tallying votes...");
		for (int i = 0; i < fileNames.length; i++) {
			Scanner ballotFile = new Scanner(new File(fileNames[i]));
			ballotFiles.add(ballotFile);
		}
//		File file = new File(fileNames[0]);
//		Scanner ballotFile = new Scanner(file);

		// Dispatch to the appropriate election type
		try {
			String electionType = "";
			for (Scanner ballotFile : ballotFiles) {
				electionType = ballotFile.nextLine().replaceAll("\\s+", "");
			}
			if (electionType.equals("IR")) {
				new InstantRunoffElection(ballotFiles);
			} else if (electionType.equals("OPL")) {
				new OpenPartyListingElection(ballotFiles);
			} else {
				System.out.println("Invalid election type " + electionType);
				System.exit(1);
			}

			// Catch when file is empty
		} catch (NoSuchElementException e) {
			System.out.println("Error: file " + fileNames + " are empty");
			System.exit(1);
		}
	}
}
