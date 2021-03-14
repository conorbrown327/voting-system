/**
 * InstantRuboffElection.java
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * InstantRunoffElection process the Instant runoff election type and
 * produces results. determineWinner() is the main function beinging run
 * and will call various helper functions to parse the ballots file and store
 * needed info, create media and audit files and to display results to the
 * terminal.
 */
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

public class InstantRunoffElection extends Election {

	private Map<Candidate, List<Ballot>> candidatesBallots;
	private List<Candidate> eliminatedCandidates;
	private Candidate winner;

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
	 * Initialize election parameters from ballot file. See SRS for file format
	 * details.
	 * 
	 * @param ballotFile: Input file in Scanner form, pointed to second line of
	 *                    data.
	 */
	@Override
	protected void readBallotFile(Scanner ballotFile) {
		numCandidates = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		setCandidatesAndParties(ballotFile.nextLine().replaceAll("\\s+", ""));
		for (Candidate candidate : candidates) {
			candidatesBallots.put(candidate, new LinkedList<Ballot>());
		}
		numBallots = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		
		writeToAuditFile("Initial ballot awarding: \n\n");
		while (ballotFile.hasNextLine()) {
			String ballotLine = ballotFile.nextLine().replaceAll("\\s+", "");
			Ballot ballot = new Ballot(candidates, ballotLine);
			
			// Assign candidate ballots
			Candidate preferredCandidate = ballot.getPreferredCandidate();
			candidatesBallots.get(preferredCandidate).add(ballot);
			writeToAuditFile(ballot.getBallotInfo() + " awarded to " + preferredCandidate.getName() + "\n");
		}

		for (Candidate candidate : candidates) {
			System.out.print(candidate.getName() + " votes: "); //d
			candidate.incrementVoteCount(candidatesBallots.get(candidate).size());
			System.out.println(candidate.getVoteCount()); //d
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
			auditFileWriter.write("\nElection Type: Instant Runoff\n");
			auditFileWriter.write("Number of ballots: " + numBallots + "\n");
			auditFileWriter.write("Number of candidates: " + numCandidates + "\n");

			auditFileWriter.write("Candidates: " + "\n");
			for (Candidate c : candidates) {
				auditFileWriter.write("\t-" + c.getName() + ", Party: " + c.getParty().getPartyName() + "\n");
			}

			auditFileWriter.write("Initial votes per candidate: \n");
			for (Candidate c : candidates) {
				auditFileWriter.write("\t-" + c.toString() + "\n");
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
					+ "--------------------------------\n" + "Candidates " + "(" + numCandidates + "): \n");

			for (Candidate c : candidates) {
				writer.write("-" + c.getName() + "\n");
			}

			writer.write("Elimination Order\n" + "-----------------\n");

			int counter = 1;
			for (Candidate c : eliminatedCandidates) {
				writer.write("(" + counter + ") " + c.getName() + "\n");
				counter++;
			}

			writer.write("\nFinal votes garnered by each candidate\n" + "--------------------------------------\n");
			for (Candidate c : candidates) {
				writer.write("-" + c.toString());
			}

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
		System.out.println("Winner: " + winner.toString());
		System.out.println("Elimination order: ");
		int counter = 1;
		for (Candidate c : eliminatedCandidates) {
			System.out.println("(" + counter + ") " + c.getName());
			counter++;
		}

		System.out.println("An audit file with the name " + auditFileName + ".txt has been produced in "
				+ System.getProperty("user.dir"));
		System.out.println("A media file with the name " + mediaFileName + ".txt has been produced in "
				+ System.getProperty("user.dir"));
	}

	// Accomplishes what you did before, but uses the more general determineWinner

	@Override
	protected void determineWinner(Scanner ballotFile) {
		writeAuditFileHeader();
		winner = getWinner();
		writeToAuditFile("Final Vote Count: \n\n");
		writeToAuditFile("Winner: " + winner.toString() + "\n");
		writeToAuditFile("Runner up(s): \n");
		for(Candidate candidate : candidates)
		{
			if(!candidate.equals(winner))
			{
				writeToAuditFile("\t" + candidate.toString() + "\n");
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
		writeToAuditFile("\nNo majority, redistributing votes...\n\n");
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
		System.out.println(candidates.size()); //d
		for (Candidate candidate : candidates) {
			System.out.print(candidate.getName() + " "); //d
			System.out.println(candidate.getVoteCount()); //d
		}
		List<Candidate> lastPlaceCandidates = new ArrayList<>();
		lastPlaceCandidates.add(candidates.get(candidates.size() - 1));
		int lastPlaceVoteCount = lastPlaceCandidates.get(0).getVoteCount();
		for (int i = candidates.size() - 2; i >= 0 && candidates.get(i).getVoteCount() == lastPlaceVoteCount; i--) {
			lastPlaceCandidates.add(candidates.get(i));
		}
		System.out.println("Last place candidates: " + lastPlaceCandidates.toString()); //d
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
			System.out.println("Next preferred candidate: " + nextPreferredCandidate.getName()); //d
			candidatesBallots.get(nextPreferredCandidate).add(ballot);
			// TODO: Remove comment. Moved to eliminateCandidate()
			// nextPreferredCandidate.incrementVoteCount();
			updateMap(nextPreferredCandidate, newlyAddedVotes);
			writeToAuditFile(ballot.getBallotInfo() + " ballot transfered to " + nextPreferredCandidate.getName() + "\n");
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
			System.out.println("Eliminated candidate " + eliminatedCandidate.getName()); //d
			redistributeBallotVote(ballot, newlyAddedVotes);
		}
		candidates.remove(eliminatedCandidate);
		candidatesBallots.remove(eliminatedCandidate);

		for (Candidate candidate : newlyAddedVotes.keySet()) {
			if(!candidate.equals(eliminatedCandidate))
			{
				writeToAuditFile(candidate.toString() + " + " + newlyAddedVotes.get(candidate) + "\n");
				candidate.incrementVoteCount(newlyAddedVotes.get(candidate));
			}
		}
	}

}
