package election;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
		try{
		File mediaFile = new File("MediaFile-" + LocalDateTime.now() + ".txt");
		FileWriter writer;
		writer = new FileWriter(mediaFile);

		writer.write(LocalDate.now() + " Instant Runoff Election Results\n" +
										"--------------------------------\n" +
										"Candidates " + "(" + numCandidates + "): \n");
	
		for(Candidate c : candidates)
		{
			writer.write("-" + c.getName() + "\n");
		}

		writer.write("Elimination Order\n" +
					 "-----------------\n");

		int counter = 1;
		for(Candidate c : eliminatedCandidates)
		{
			writer.write("(" + counter + ") " + c.getName() + "\n");
			counter++;
		}

		writer.write("\nFinal votes garnered by each candidate\n" +
						 "--------------------------------------\n");
		for(Candidate c : candidates)
		{
			writer.write("-" + c.toString());
		}
		
		writer.flush();
		writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void displayResultsToTerminal() {
		System.out.println("Program completed successfully\n");
		System.out.println("Election Summary");
		System.out.println("Election Type: Instant Runoff");
		System.out.println("Winner: " + winner.toString());
		System.out.println("Elimination order: ");
		int counter = 1;
		for(Candidate c : eliminatedCandidates)
		{
			System.out.println("(" + counter + ") " + c.getName());
			counter++;
		}
		System.out.println("Total: Ballots Counted: " + numBallots);

		// this will be changed, should pass a LocalDateTime obj to func? when audit file created
		System.out.println("An audit file with the name AuditFile-" + LocalDateTime.now() + ".txt has been produced in " + System.getProperty("user.dir"));
		System.out.println("A media file with the name MediaFile-" + LocalDateTime.now() + ".txt has been produced in " + System.getProperty("user.dir"));
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
