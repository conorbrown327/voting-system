
package election;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * InstantRunoffElection.java

 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * InstantRunoffElection process the Instant runoff election type and
 * produces results. determineWinner() is the main function being run
 * and will call various helper functions to parse the ballots file and store
 * needed info, create media and audit files and to display results to the
 * terminal.
 */

public class InstantRunoffElection extends Election {

	private Map<Candidate, List<Ballot>> candidatesBallots;
	private List<Candidate> eliminatedCandidates;
	private Candidate winner;
	
	/**
	 * The constructor for an IR election. Will initialize all of the necessary parameters and then run the election. Essentially doubles as a driver for this class.
	 * @param ballotFile
	 */
	
	public InstantRunoffElection(Scanner ballotFile) {
		try {
			auditFile = new File(auditFileName);
			auditFile.createNewFile();
			auditFileWriter = new FileWriter(auditFile); // would need to close once done writing to audit file
		} catch (IOException e) {
			e.printStackTrace();
		}
		initializeParameters();
		candidatesBallots = new HashMap<>();
		eliminatedCandidates = new LinkedList<>();
		readBallotFile(ballotFile);
		determineWinner(ballotFile);
	}
	
	/**
	 * A getter method for the winner of an Instant Runoff election.
	 * @return winner: The winner of this election
	 */

	public Candidate getElectionWinner() {
		return winner;
	}
	
	/**
	 * A getter method for the candidates who were eliminated throughout the course of this election.
	 * @return eliminatedCandidates: The list of candidates who were eliminated throughout this election
	 */

	public List<Candidate> getEliminatedCandidates() {
		return eliminatedCandidates;
	}

	/**
	 * Initialize election parameters from ballot file. See SRS for file format
	 * details.
	 * 
	 * @param ballotFile: Input file in Scanner form, pointed to second line of
	 *                    data.
	 */
	@Override
	protected void readBallotFile(Scanner ballotFile) {
		// read the ballots file header
		numCandidates = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		setCandidatesAndParties(ballotFile.nextLine().replaceAll("\\s+", ""));
		for (Candidate candidate : candidates) {
			candidatesBallots.put(candidate, new LinkedList<Ballot>());
		}
		numBallots = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		// write the audit file header after reading the ballots file header
		writeAuditFileHeader();
		writeToAuditFile("Initial ballot awarding: \n\n");
		while (ballotFile.hasNextLine()) {
			Ballot ballot = new Ballot(candidates, ballotFile.nextLine().replaceAll("\\s+", ""));

			// get the ballots preferred candidate
			Candidate preferredCandidate = ballot.getPreferredCandidate();
			candidatesBallots.get(preferredCandidate).add(ballot);
			// write each ballot and who it is being awarded to to the audit file
			writeToAuditFile(ballot.getBallotInfo() + " awarded to " + preferredCandidate.getName() + "\n");
		}
		// tally each candidates vote count
		for (Candidate candidate : candidates) {
			candidate.incrementVoteCount(candidatesBallots.get(candidate).size());
		}
		// write each candidates initial vote count after it has been tallied
		writeToAuditFile("\nInitial votes per candidate: \n");
		for (Candidate c : candidates) {
			writeToAuditFile("-" + c.toString() + "\n");
		}
	}

	/**
	 * Function will create the audit file and audit file writer and write the audit
	 * file head after the ballots file has been processed and initial information
	 * is stored. This function takes no parameters and has no return type.
	 */
	@Override
	protected void writeAuditFileHeader() {
		try {
			writeToAuditFile("Election Type: Instant Runoff\n");
			writeToAuditFile("Number of ballots: " + numBallots + "\n");
			writeToAuditFile("Number of candidates: " + numCandidates + "\n");

			writeToAuditFile("Candidates: " + "\n");
			for (Candidate c : candidates) {
				writeToAuditFile("-" + c.getName() + ", Party: " + c.getParty().getPartyName() + "\n");
			}

			auditFileWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a file with an election summary to provide to the media. This function
	 * take no parameters and has no return type.
	 */
	@Override
	protected void writeMediaFile() {
		try {
			File mediaFile = new File(mediaFileName);
			mediaFile.createNewFile();
			FileWriter writer;
			writer = new FileWriter(mediaFile);

			writer.write(dateTime.format(formatObj) + " Instant Runoff Election Results\n"
					+ "--------------------------------\n");

			writer.write("Total Votes Cast: " + numBallots + "\n");
			writer.write("Candidates " + "(" + numCandidates + ")\n");

			for (Candidate c : eliminatedCandidates) {
				writer.write("-" + c.getName() + ": " + c.getParty().getPartyName() + "\n");
			}
			writer.write("-" + winner.getName() + ": " + winner.getParty().getPartyName() + "\n");

			writer.write("Elimination Order\n" + "-----------------\n");
			int counter = 1;
			for (Candidate c : eliminatedCandidates) {
				writer.write("(" + counter + ") " + c.getName() + "\n");
				counter++;
			}

			writer.write("\nFinal votes garnered by each remaining candidate\n"
					+ "--------------------------------------\n");
			for (Candidate c : candidates) {
				writer.write("-" + c.toString() + "\n");
			}

			writer.write("\nWinner: " + winner.toString() + ", "
					+ (((double) winner.getVoteCount() / (double) numBallots) * 100.0) + "% of the vote");

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays relevant election results to the terminal. Output is formatted so as
	 * to fit the results of an Instant Runoff Election. This function takes no
	 * parameters and has no return type.
	 */
	@Override
	protected void displayResultsToTerminal() {
		System.out.println("Program completed successfully\n");
		System.out.println("Election Summary");
		System.out.println("Election Type: Instant Runoff");
		System.out.println("Total Ballots Counted: " + numBallots);
		System.out.println("Winner: " + winner.toString() + ", "
				+ (((double) winner.getVoteCount() / (double) numBallots) * 100.0) + "% of the vote");
		System.out.println("Elimination order: ");
		int counter = 1;
		for (Candidate c : eliminatedCandidates) {
			System.out.println("(" + counter + ") " + c.getName());
			counter++;
		}

		System.out.println("\nAn audit file with the name " + auditFileName + ".txt has been produced in "
				+ System.getProperty("user.dir"));
		System.out.println("A media file with the name " + mediaFileName + ".txt has been produced in "
				+ System.getProperty("user.dir"));
	}

	// Accomplishes what you did before, but uses the more general determineWinner
	
	/**
	 * The main algorithm for an Instant Runoff election. Will determine the winner of the election and then display necessary information to the screen.
	 * @param ballotFile: The scanner for the election file that is passed into the program
	 */

	@Override
	protected void determineWinner(Scanner ballotFile) {
		winner = getWinner();
		writeToAuditFile("Final Vote Count: \n\n");
		writeToAuditFile("Winner: " + winner.toString() + ", "
				+ (((double) winner.getVoteCount() / (double) numBallots) * 100.0) + "% of the vote\n");
		writeToAuditFile("Runner up(s): \n");
		for (Candidate candidate : candidates) {
			if (!candidate.equals(winner)) {
				writeToAuditFile("-" + candidate.toString() + "\n");
			}
		}
		try {
			auditFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeMediaFile();
		displayResultsToTerminal();
	}

	private Candidate getWinner() {
		candidates.sort(null); // Should sort using Comparable's compareTo()
		if (existsMajority()) {
			writeToAuditFile("\nMajority found, Winner declared: \n\n");
			return candidates.get(0);
		}
		writeToAuditFile("\n---------------------------\n");
		writeToAuditFile("\nNo majority, redistributing votes...\n");
		List<Candidate> lastPlaceCandidates = getLastPlaceCandidates();
		Candidate candidateToBeEliminated = lastPlaceCandidates.remove(0);
		if (!lastPlaceCandidates.isEmpty()) {
			for (Candidate otherEliminee : lastPlaceCandidates) {
				candidateToBeEliminated = breakTie(candidateToBeEliminated, otherEliminee);
			}
		}
		eliminateCandidate(candidateToBeEliminated);
		return getWinner();
	}

	private boolean existsMajority() {
		if (candidates.size() < 2)
			return true;
		if (candidates.get(0).getVoteCount() > (numBallots / 2))
			return true;
		return false;
	}

	private List<Candidate> getLastPlaceCandidates() {
		List<Candidate> lastPlaceCandidates = new ArrayList<>();
		lastPlaceCandidates.add(candidates.get(candidates.size() - 1));
		int lastPlaceVoteCount = lastPlaceCandidates.get(0).getVoteCount();
		for (int i = candidates.size() - 2; i >= 0 && candidates.get(i).getVoteCount() == lastPlaceVoteCount; i--) {
			lastPlaceCandidates.add(candidates.get(i));
		}
		return lastPlaceCandidates;
	}

	private Map<Candidate, Integer> initializeMap() {
		Map<Candidate, Integer> newlyAddedVotes = new HashMap<>();
		for (Candidate c : candidates) {
			newlyAddedVotes.put(c, 0);
		}
		return newlyAddedVotes;
	}

	private void updateMap(Candidate c, Map<Candidate, Integer> newlyAddedVotes) {
		newlyAddedVotes.put(c, newlyAddedVotes.get(c) + 1);
	}

	private void redistributeBallotVote(Ballot ballot, Map<Candidate, Integer> newlyAddedVotes) {
		// TODO If the ballot queue is empty, toss the vote and decrement numBallots
		Candidate nextPreferredCandidate = ballot.eliminatePreferredCandidate();
		while (nextPreferredCandidate != null && eliminatedCandidates.contains(nextPreferredCandidate)) {
			nextPreferredCandidate = ballot.eliminatePreferredCandidate();
		}
		if (nextPreferredCandidate != null) {
			System.out.println("Next preferred candidate: " + nextPreferredCandidate.getName()); // d
			candidatesBallots.get(nextPreferredCandidate).add(ballot);
			// TODO: Remove comment. Moved to eliminateCandidate()
			// nextPreferredCandidate.incrementVoteCount();
			updateMap(nextPreferredCandidate, newlyAddedVotes);
			writeToAuditFile(
					ballot.getBallotInfo() + " ballot transfered to " + nextPreferredCandidate.getName() + "\n");
		} else {
			writeToAuditFile(ballot.getBallotInfo() + " no next ranked candidate. ballot tossed\n");
			--numBallots;
		}
		System.out.println("Num ballots: " + numBallots);
	}

	private void eliminateCandidate(Candidate eliminatedCandidate) {
		// A data structure to keep track of how many votes each Candidate receives
		// after ballot redistribution

		writeToAuditFile("\n" + eliminatedCandidate.getName() + " eliminated\n\n");
		eliminatedCandidates.add(eliminatedCandidate);

		Map<Candidate, Integer> newlyAddedVotes = initializeMap();
		for (Ballot ballot : candidatesBallots.get(eliminatedCandidate)) {
			System.out.println("Eliminated candidate " + eliminatedCandidate.getName()); // d
			redistributeBallotVote(ballot, newlyAddedVotes);
		}
		candidates.remove(eliminatedCandidate);
		candidatesBallots.remove(eliminatedCandidate);
		writeToAuditFile("\n");
		for (Candidate candidate : newlyAddedVotes.keySet()) {
			if (!candidate.equals(eliminatedCandidate)) {
				writeToAuditFile(candidate.toString() + " + " + newlyAddedVotes.get(candidate) + "\n");
				candidate.incrementVoteCount(newlyAddedVotes.get(candidate));
			}
		}
	}

}
