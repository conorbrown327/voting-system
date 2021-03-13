/**
 * ElectionDriver.java
 * Authors: Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
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
	public static void main(String[] args) {
		String fileName = args[0];
		File ballotsFile = new File(fileName);
		Scanner readNewFile = new Scanner(System.in);

		while(ballotsFile == null)
		{
			if(fileName == "quit")
				System.exit(1);
			System.out.println(args[0] + " not found. Please confirm the file is in "
								+ "the correct directory and the entered name is correct.\n");
			System.out.println("Please enter a valid file name or type quit to terminate the program.\n");
			fileName = readNewFile.nextLine();
			ballotsFile = new File(fileName);
		}

		System.out.println(fileName + " found, tallying votes...");
		
		try{
			Scanner readFirstLine = new Scanner(ballotsFile);
		
			String firstLine = readFirstLine.nextLine();
			Election electionTypeToRun;

			if(firstLine == "IR")
				electionTypeToRun = new InstantRunoffElection(fileName);
			// For some reason it throws error cant find symbol for OpenPartyListingElection
			//else if(firstLine == "OPL")
			//	electionTypeToRun = new OpenPartyListingElection(fileName);
		}
		catch(FileNotFoundException f)
		{
		}
	}
}
