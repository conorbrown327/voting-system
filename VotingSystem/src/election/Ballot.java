/**
 * Ballot.java
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * Ballot.java is used to store individual ballots read in from the 
 * ballots csv file. It keeps track of each ballots rankings of candidates.
 */

package election;

import java.util.Queue;

public class Ballot {

	private Queue<Candidate> votePreferences;

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
