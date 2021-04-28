package election;

import java.util.List;
import java.util.Scanner;

/**
 * PopularityOnlyElection.java
 * 
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 *         Reads in the PO file and stores files information
 */

public class PopularityOnlyElection extends Election {
	
	public PopularityOnlyElection() {
		
	}
	
	public PopularityOnlyElection(List<Scanner> ballotFiles) {
		initializeParameters();
		readBallotFileList(ballotFiles);
	}

	@Override
	protected void determineWinner() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readBallotFile(Scanner ballotFile) {
		while (ballotFile.hasNextLine()) {
			Ballot ballot = new Ballot(candidates, ballotFile.nextLine().replaceAll("\\s+", ""));
			// get the ballots preferred candidate
			Candidate preferredCandidate = ballot.getPreferredCandidate();
			preferredCandidate.incrementVoteCount();
		}

	}

	@Override
	protected void readBallotFileList(List<Scanner> ballotFiles) {
		for (Scanner ballotFile : ballotFiles) {
			readBallotFileHeader(ballotFile);
		}
		
		for (Scanner ballotFile : ballotFiles) {
			readBallotFile(ballotFile);
		}
	}

	@Override
	protected void readBallotFileHeader(Scanner ballotFile) {
		numCandidates = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		if (candidates.isEmpty()) {
			setCandidatesAndParties(ballotFile.nextLine());
		} else {
			ballotFile.nextLine().replaceAll("\\s+", "");
		}
		numBallots += Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
	}

	@Override
	protected void writeAuditFileHeader() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeMediaFile() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void displayResultsToTerminal() {
		// TODO Auto-generated method stub

	}

}
