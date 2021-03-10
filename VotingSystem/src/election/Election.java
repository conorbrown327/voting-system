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

	protected void determineWinner(String filePath) {
		return;
	}

	protected Candidate getWinner() {
		return null;
	}

	protected abstract void readBallotFile(String filePath);

	protected abstract void writeToBallotFile(String line);

	protected abstract void writeMediaFile();

	protected abstract void writeResultsToTerminal();
}
