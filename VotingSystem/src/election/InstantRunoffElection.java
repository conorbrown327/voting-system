package election;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstantRunoffElection extends Election {

	private Map<Candidate, List<Ballot>> candidatesBallots;
	private List<Candidate> eliminatedCandidates;
	private Candidate winner;

	public InstantRunoffElection(String filePath) {
		determineWinner(filePath);
	}

	@Override
	protected void readBallotFile(String filePath) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeToAuditFile(String line) {
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
	
	//Accomplishes what you did before, but uses the more general determineWinner
	
	protected void determineWinner(String filePath) {
		readBallotFile(filePath);
		winner = getWinner();
		writeMediaFile();
		displayResultsToTerminal();
	}

	private Candidate getWinner() {
		candidates.sort(null); // Should sort using Comparable's compareTo()
		if (existsMajority())
			return candidates.get(0);
		List<Candidate> lastPlaceCandidates = getLastPlaceCandidates();
		Candidate candidateToBeEliminated;
		if (lastPlaceCandidates.size() == 1)
			candidateToBeEliminated = lastPlaceCandidates.get(0);
		else {
			// TODO Fairly eliminate a single candidate
			candidateToBeEliminated = null;
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
		for (int i = candidates.size() - 2; candidates.get(i).getVoteCount() == lastPlaceVoteCount; i--) {
			lastPlaceCandidates.add(candidates.get(i));
		}
		return lastPlaceCandidates;
	}

	private void redistributeBallotVote(Ballot ballot) {
		// TODO If the ballot queue is empty, toss the vote and decrement numBallots

		// Otherwise, add the ballot to the next in line candidate and increment his
		// vote total
	}

	private void eliminateCandidate(Candidate eliminatedCandidate) {
		for (Ballot ballot : candidatesBallots.get(eliminatedCandidate)) {
			redistributeBallotVote(ballot);
		}
		candidates.remove(eliminatedCandidate);
		eliminatedCandidates.add(eliminatedCandidate);
	}

}
