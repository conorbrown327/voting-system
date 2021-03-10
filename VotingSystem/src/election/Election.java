package election;

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
		return null;
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
