package election;
import java.util.*;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public abstract class Election {

	protected List<Candidate> candidates;
	protected FileWriter auditFile;
	protected int numCandidates;
	protected int numBallots;
	protected Map<String, Party> participatingParties;

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

	protected Candidate determineWinner(String filePath) {
		readBallotFile(filePath);
		return getWinner();
	}

	protected abstract Candidate getWinner();

	protected abstract void readBallotFile(String filePath);

	protected abstract void writeToBallotFile(String line);

	protected abstract void writeMediaFile(Candidate winner);

	protected abstract void displayResultsToTerminal(Candidate winner);
}
