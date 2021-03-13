package election;
import java.util.*;

import java.io.*;
import java.io.FileWriter;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.util.Map;

public abstract class Election {

	protected List<Candidate> candidates;
	protected FileWriter auditFileWriter;
	// audit file File type
	protected File auditFile;
	protected int numCandidates;
	protected int numBallots;
	protected List<Party> participatingParties;
	// date and time formatting for file creating and display
	protected LocalDateTime dateTime = LocalDateTime.now();
    protected DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
	
	/**
	 * Breaks a tie between two candidates by using a random number generator to simulate flipping a coin 99 times.
	 * This way, there will always be a winner, as the odd number of tosses removes the possibility of ties.
	 * @param candidate1: The first candidate who is tied
	 * @param candidate2: The second candidate who is tied
	 * @return Either candidate1 or candidate2 based on the results of flipping a simulated coin 99 times
	 */

	protected Candidate breakTie(Candidate candidate1, Candidate candidate2) {
		int c1Wins = 0;
		int c2Wins = 0;
		Random rand = new Random();
		
		for (int i = 0; i < 99; i++) {
			int coinFlip = rand.nextInt(2);
			if (coinFlip == 0) {
				c1Wins += 1;
			}
			else {
				c2Wins += 1;
			}
		}
		
		if (c1Wins > c2Wins) {
			return candidate1;
		}
		else {
			return candidate2;
		}	
	}
	
	//I changed determineWinner to be void. This matches our UML, and also I feel like allows for the flexibility we need in running the elections
	
	protected abstract void determineWinner(String filePath);
	
	// protected abstract Candidate getWinner();

	protected abstract void readBallotFile(String filePath);

	/**
	 * Takes in String parameter line and writes that line to the audit file.
	 * Has no return type
	 */
	protected void writeToAuditFile(String line) {
		try
		{
			auditFileWriter.write(line);
			auditFileWriter.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	// added func to write the audit file header should be called once csv inital info has been read
	protected abstract void writeAuditFileHeader();
	
	//I took out the arguments for winner and instead made it a private variable in the  IR class, since the other class doesn't have a "winner"
	//This way, they are more general, and the private variable can just be used wherever the winner was needed anyways.
	
	protected abstract void writeMediaFile();

	protected abstract void displayResultsToTerminal();
}
