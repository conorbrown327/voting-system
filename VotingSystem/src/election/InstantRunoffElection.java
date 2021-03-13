/**
 * InstantRuboffElection.java
 * Authors: Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * InstantRunoffElection process the Instant runoff election type and
 * produces results. determineWinner() is the main function beinging run
 * and will call various helper functions to parse the ballots file and store
 * needed info, create media and audit files and to display results to the
 * terminal.
 */
package election;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.io.FileWriter;

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
	

	/**
	 * Function will create the audit file and audit file writer and write the audit file head after
	 * the ballots file has been processed and initial information is stored. This function takes
	 * no parameters and has no return type.
	 */
	@Override
	protected void writeAuditFileHeader() {
		try{

		auditFile = new File(auditFileName);
		auditFile.createNewFile();
		auditFileWriter = new FileWriter(auditFile);	// would need to close once done writing to audit file

		auditFileWriter.write("Election Type: Instant Runoff\n");
		auditFileWriter.write("Number of ballots: " + numBallots + "\n");
		auditFileWriter.write("Number of candidates: " + numCandidates + "\n");

		auditFileWriter.write("Candidates: "  + "\n");
		for(Candidate c : candidates)
		{
			auditFileWriter.write("\t-" + c.getName() + ", Party: " + c.getParty().getPartyName() + "\n");
		}

		auditFileWriter.write("Initial votes per candidate: \n");
		for(Candidate c : candidates)
		{
			auditFileWriter.write("\t-" + c.toString() + "\n");
		}
		
		auditFileWriter.flush();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create a file with an election summary to provide to the media. This function
	 * take no parameters and has no return type.
	 */
	@Override
	protected void writeMediaFile() {
		try{
		File mediaFile = new File(mediaFileName);
        mediaFile.createNewFile();
		FileWriter writer;
		writer = new FileWriter(mediaFile);

		writer.write(dateTime.format(formatObj) + " Instant Runoff Election Results\n" +
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
	/**
	 * Displays relevant election results to the terminal. Output is formatted so as to fit the results
	 * of an Instant Runoff Election. This function takes no parameters and has no return type.
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
		for(Candidate c : eliminatedCandidates)
		{
			System.out.println("(" + counter + ") " + c.getName());
			counter++;
		}

		System.out.println("An audit file with the name " + auditFileName + ".txt has been produced in " + System.getProperty("user.dir"));
		System.out.println("A media file with the name " + mediaFileName + ".txt has been produced in " + System.getProperty("user.dir"));
	}
	
	//Accomplishes what you did before, but uses the more general determineWinner
	
	protected void determineWinner(String filePath) {
		readBallotFile(filePath);
		writeAuditFileHeader();
		winner = getWinner();
		writeToAuditFile("Winner: " + winner.toString());
		try{
			auditFileWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		writeMediaFile();
		displayResultsToTerminal();
	}

	private Candidate getWinner() {
		candidates.sort(null); // Should sort using Comparable's compareTo()
		if (existsMajority())
		{
			writeToAuditFile("Majority found, Winner declared: \n\n");
			return candidates.get(0);
		}
		writeToAuditFile("No majority\n\n");
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

		writeToAuditFile(eliminatedCandidate.getName() + " eliminated, redistributing votes...\n");
	}

}
