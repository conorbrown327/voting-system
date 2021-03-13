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
		String fileName = ElectionDriver.class.getResource(args[0]).getPath();
		Scanner ballotFile = new Scanner(new File(fileName));
		Scanner readNewFile = new Scanner(System.in);

		// if the file is not found prompt the user to enter a new file or "quit"
		while(ballotFile == null)
		{
			if(fileName == "quit")
				System.exit(1);
			System.out.println(fileName + " not found. Please confirm the file is in "
								+ "the correct directory and the entered name is correct.\n");
			System.out.println("Please enter a valid file name or type \"quit\" to terminate the program.\n");
			fileName = readNewFile.nextLine();
			ballotFile = new Scanner(fileName);
		}

		// Dispatch to the appropriate election type
		Election heldElection;
		String electionType = ballotFile.nextLine().replaceAll("\\s+", "");
		if (electionType.equals("IR")) {
			heldElection = new InstantRunoffElection(ballotFile);
			heldElection.determineWinner(ballotFile);
		} else if (electionType.equals("OPL")) {
			heldElection = new OpenPartyListingElection(ballotFile);
			heldElection.determineWinner(ballotFile);
		} else {
			System.out.println(String.format("Invalid election type '%s'", electionType));
			System.exit(1);
		}
	}
}
