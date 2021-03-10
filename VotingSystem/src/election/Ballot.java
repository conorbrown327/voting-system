package election;

import java.util.PriorityQueue;
import java.util.Queue;

public class Ballot {

	private PriorityQueue<Candidate> votePreferences;

	public Candidate findNextPreferredCandidate() {

		return null;
	}

	public Queue<Candidate> getBallotQueue() {
		return this.votePreferences;
	}

	public void addToQueue(Candidate c) {
		this.votePreferences.add(c);
	}

}
