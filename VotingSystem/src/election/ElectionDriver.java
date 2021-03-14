

package election;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

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

public class ElectionDriver {
	public static void main(String[] args) throws FileNotFoundException {
		String fileName = "";
		try {
			fileName = ElectionDriver.class.getResource(args[0]).getPath();
		} catch (NullPointerException e) {
			System.out.println("Please enter a valid file name.");
			throw new IllegalArgumentException("Invalid file name");
		}
		System.out.println("Ballots file found, tallying votes...");
		File file = new File(fileName);
		Scanner ballotFile = new Scanner(file);

		// Dispatch to the appropriate election type
		try {
			String electionType = ballotFile.nextLine().replaceAll("\\s+", "");

			if (electionType.equals("IR")) {
				new InstantRunoffElection(ballotFile);
			} else if (electionType.equals("OPL")) {
				new OpenPartyListingElection(ballotFile);
			} else {
				System.out.println(String.format("Invalid election type '%s'", electionType));
				System.exit(1);
			}

		} catch (NoSuchElementException e) {
			System.out.println(String.format("Error: file '%s' is empty", fileName));
			System.exit(1);
		}
	}
}
