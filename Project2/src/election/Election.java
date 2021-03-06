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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
	 * Getter function for the participating parties in an election.
	 * 
	 * @return participatingParties: The list of parties participating in the given
	 *         election
	 */

	public List<Party> getParticipatingParties() {
		return participatingParties;
	}

	/**
	 * Getter function for the candidates of a given election.
	 * 
	 * @return candidates: The list of candidates for this election
	 */

	public List<Candidate> getCandidates() {
		return candidates;
	}

	/**
	 * Getter function for the number of total ballots for an election.
	 * 
	 * @return numBallots: The total number of ballots for a given election
	 */

	public int getNumBallots() {
		return numBallots;
	}

	/**
	 * Helper function that initializes the necessary data structures for a given
	 * election.
	 */
	protected void initializeParameters() {
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
	}

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
		writeToAuditFile("\nTie between: " + candidate1.getName() + " and " + candidate2.getName() + "\n");

		for (int i = 0; i < 99; i++) {
			int coinFlip = rand.nextInt(2);
			if (coinFlip == 0) {
				c1Wins += 1;
			} else {
				c2Wins += 1;
			}
		}
		if (c1Wins > c2Wins) {
			writeToAuditFile("Tie broken: " + candidate2.getName() + " eliminated\n");
			return candidate2;
		} else {
			writeToAuditFile("Tie broken: " + candidate1.getName() + " eliminated\n");
			return candidate1;
		}
	}

	/**
	 * Function that handles the main algorithm in determining the election winner.
	 * 
	 */
	protected abstract void determineWinner();

	/**
	 * Function that handles the parsing and storage of the election data contained
	 * by the ballot file.
	 * 
	 * @param ballotFile The file containing all of the election information.
	 */
	protected abstract void readBallotFile(Scanner ballotFile);

	/**
	 * Wrapper function that calls readBallotFile and readBallotFileHeader with each
	 * ballot file in the provided list.
	 * 
	 * @param ballotFiles A list of all ballot files the election will process.
	 */
	protected abstract void readBallotFileList(List<Scanner> ballotFiles);

	/**
	 * Function that handles the reading of the ballot file headers and the storage
	 * of the general election information that it contains.
	 * 
	 * @param ballotFile The file containing all of the election information.
	 */
	protected abstract void readBallotFileHeader(Scanner ballotFile);

	/**
	 * Takes in String and writes that line to the audit file. Has no return type.
	 * 
	 * @param line: A string that is a line to be written to the audit file.
	 */
	protected void writeToAuditFile(String line) {
		try {
			auditFileWriter.write(line);
			auditFileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function that writes the header information for the audit file.
	 */
	protected abstract void writeAuditFileHeader();

	/**
	 * Function that writes the media file with all of the election statistics.
	 */
	protected abstract void writeMediaFile();

	/**
	 * Function that writes the election results and statistics to the terminal.
	 */
	protected abstract void displayResultsToTerminal();

	// FILE INPUT HELPER METHODS //

	/**
	 * A helper function for file input that will set up candidates and parties for
	 * a given election. There is no return type.
	 * 
	 * @param candidateLine: Line from the file containing candidate and party
	 *                       information
	 */

	protected void setCandidatesAndParties(String candidateLine) {
		setCandidates(candidateLine);
		consolidateParties();
	}

	/**
	 * A helper function for file input - merges parties with the same name into one
	 * and adds adds their candidates to the resulting Party object.
	 */

	protected void consolidateParties() {
		for (Candidate candidate : candidates) {

			boolean partyFound = false;

			for (Party party : participatingParties) {
				if (candidate.getParty().getPartyName().equals(party.getPartyName())) { // Candidate party has been
																						// stored
					candidate.setParty(party); // Add party to its candidate
					partyFound = true;
				}
			}

			if (!partyFound) { // Candidate party has not been stored
				participatingParties.add(candidate.getParty()); // Store candidate party
			}

			candidate.getParty().addCandidate(candidate); // Add candidate to its party
		}
	}

	/**
	 * Wrapper class enabling pass-by-reference ints
	 */
	protected class IntRef {
		public IntRef(int val) {
			this.val = val;
		}

		public int val;
	}

	/**
	 * Helper function for file input that parses candidates and parties from the
	 * file line containing them.
	 * 
	 * @param candidateLine: Ballot file line containing candidate information
	 */

	protected void setCandidates(String candidateLine) {
		String nextCandidateName;
		String nextPartyName;
		IntRef i = new IntRef(0);
		while (i.val < candidateLine.length()) {
			nextCandidateName = parseName(candidateLine, i);
			nextPartyName = parseName(candidateLine, i);

			boolean isDuplicate = false;
			for (Candidate candidate : candidates) {
				if (nextCandidateName.equals(candidate.getName())
						&& nextPartyName.equals(candidate.getParty().getPartyName())) { // candidate has already been
																						// stored
					isDuplicate = true;
					break;
				}
			}

			if (!isDuplicate && !(nextCandidateName.length() == 0 || nextPartyName.length() == 0)) { // new candidate
																										// info has been
																										// parsed
				candidates.add(new Candidate(nextCandidateName, new Party(nextPartyName)));
			}
		}
	}

	/**
	 * Helper function for file input that retrieves the first valid candidate or
	 * party name in candidateLine from index curIndex onward; curIndex is
	 * incremented to the index following said name to facilitate repeated calls.
	 * 
	 * @param candidateLine: Ballot file line containing candidate information
	 * @param curIndex:      Index of candidateLine from which to begin
	 *                       left-to-right search
	 */

	protected String parseName(String candidateLine, IntRef curIndex) {
		StringBuilder buf = new StringBuilder();

		while (curIndex.val < candidateLine.length() && !Character.isLetter(candidateLine.charAt(curIndex.val))) { // curIndex
																													// is
																													// not
																													// at
																													// an
																													// alphabetical
																													// character
			++curIndex.val;
		}

		while (curIndex.val < candidateLine.length() && (Character.isLetter(candidateLine.charAt(curIndex.val))
				|| candidateLine.charAt(curIndex.val) == ' ' || candidateLine.charAt(curIndex.val) == '\t')) { // curIndex
																												// is
																												// not
																												// at an
																												// alphabetical/whitespace
																												// character
			buf.append(candidateLine.charAt(curIndex.val));
			++curIndex.val;
		}

		return buf.toString().trim();
	}
}
