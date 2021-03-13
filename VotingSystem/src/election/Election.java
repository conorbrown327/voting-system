/**
 * Election.java
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * Election is the parent class that InstantRunoffElection.java and OpenPartyListingElection.java
 * inherit from. It defines attributes and methods needed in both election scenarios.
 */

package election;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

public abstract class Election {

	protected List<Candidate> candidates;
	protected FileWriter auditFileWriter;
	protected File auditFile;
	protected int numCandidates;
	protected int numBallots;
	protected List<Party> participatingParties;
	// date and time formatting for file names and display to terminal
	protected LocalDateTime dateTime = LocalDateTime.now();
	protected DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
	protected String mediaFileName = "MediaFile-" + dateTime.format(formatObj) + ".txt";
	protected String auditFileName = "AuditFile-" + dateTime.format(formatObj) + ".txt";

	/**
	 * Breaks a tie between two candidates by using a random number generator to
	 * simulate flipping a coin 99 times. This way, there will always be a winner,
	 * as the odd number of tosses removes the possibility of ties.
	 * 
	 * @param candidate1: The first candidate who is tied
	 * @param candidate2: The second candidate who is tied
	 * @return Either candidate1 or candidate2 based on the results of flipping a
	 *         simulated coin 99 times
	 */

	protected Candidate breakTie(Candidate candidate1, Candidate candidate2) {
		int c1Wins = 0;
		int c2Wins = 0;
		Random rand = new Random();

		for (int i = 0; i < 99; i++) {
			int coinFlip = rand.nextInt(2);
			if (coinFlip == 0) {
				c1Wins += 1;
			} else {
				c2Wins += 1;
			}
		}

		if (c1Wins > c2Wins) {
			return candidate2;
		} else {
			return candidate1;
		}
	}
	
	//I changed determineWinner to be void. This matches our UML, and also I feel like allows for the flexibility we need in running the elections
	
	protected abstract void determineWinner(Scanner ballotFile);
	
	// protected abstract Candidate getWinner();

	protected abstract void readBallotFile(Scanner ballotFile);

	/**
	 * Takes in String parameter line and writes that line to the audit file. Has no
	 * return type
	 */
	protected void writeToAuditFile(String line) {
		try {
			auditFileWriter.write(line);
			auditFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// added func to write the audit file header should be called once csv inital
	// info has been read
	protected abstract void writeAuditFileHeader();

	// I took out the arguments for winner and instead made it a private variable in
	// the IR class, since the other class doesn't have a "winner"
	// This way, they are more general, and the private variable can just be used
	// wherever the winner was needed anyways.

	protected abstract void writeMediaFile();

	protected abstract void displayResultsToTerminal();

	// FILE INPUT HELPER METHODS //

	// Definitely can be done better - will get back around to this
	// when I can test the changes
	protected void setCandidatesAndParties(String candidateLine) {
		String nextCandidateName = "";
		String nextCandidateParty = "";
		boolean haveCandidateName = false;
		for (int i = 0; i < candidateLine.length(); ++i) {
			if (Character.isLetter(candidateLine.charAt(i))) {
				if (!haveCandidateName) {
					nextCandidateName += candidateLine.charAt(i);
				} else {
					nextCandidateParty += candidateLine.charAt(i);
				}
			} else if (!nextCandidateName.equals("")) {
				if (!nextCandidateParty.equals("")) {
					participatingParties.add(new Party(nextCandidateParty));
					candidates.add(new Candidate(nextCandidateName, new Party(nextCandidateParty)));
					nextCandidateName = "";
					nextCandidateParty = "";
					haveCandidateName = false;
				} else {
					haveCandidateName = true;
				}
			}
		}
	}
}
