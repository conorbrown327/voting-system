/**
 * ElectionDriver.java
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * ElectionDriver contains the main function. In main the ballots csv
 * file will be opened from the file provided in the cmd args and the
 * first line will be read to gather the election type. Then either an
 * instance of the InstantRunoffElection class or OpenPartyListingElection 
 * class will be creeated and determineWinner() will be called inside the
 * constuctor.
 * 
 */

package election;

import java.util.*;
import java.io.*;
import java.util.Scanner;

public class ElectionDriver {
	public static void main(String[] args) throws FileNotFoundException {
		String fileName = "";
		try{
			fileName = ElectionDriver.class.getResource(args[0]).getPath();
		}
		catch(NullPointerException e)
		{
			System.out.println("Please enter a valid file name.");
			System.exit(1);
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
