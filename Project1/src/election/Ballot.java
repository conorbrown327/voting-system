/**
 * Ballot.java
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * Ballot.java is used to store individual ballots read in from the 
 * ballots csv file. It keeps track of each ballots rankings of candidates.
 */

package election;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Ballot {

	private Queue<Candidate> votePreferences;
	private String ballotInfo;
	
	/**
	 * Ballot constructor. See SRS for details regarding file format.
	 * 
	 * @param candidates: List of candidates in order of appearance on-file
	 * @param ballotLine: Comma-delimited vote rankings for one ballot
	 * @return Corresponding ballot object
	 */
	public Ballot(List<Candidate> candidates, String ballotLine) {
		ballotInfo = ballotLine;
		votePreferences = new LinkedList<>();
		List<int[]> rankings = getRankings(ballotLine);
		rankings.sort((a, b) -> a[1] - b[1]);
		for (int[] ranking : rankings) {
			votePreferences.add(candidates.get(ranking[0]));
		}
	}

	/**
	 * Function that gets the Candidate that is currently the most preferred
	 * eligible according to the voter preferences.
	 * 
	 * @return This ballot's highest-rated remaining candidate
	 */
	public Candidate getPreferredCandidate() {
		return votePreferences.peek();
	}

	/**
	 * Function that returns the ballot info as written in the initial audit file.
	 * 
	 * @return ballotInfo: the ballot information as written in the initial audit
	 *         file.
	 */
	public String getBallotInfo() {
		return ballotInfo;
	}

	/**
	 * Eliminates this ballot's highest-rated remaining candidate.
	 * @return The candidate to be eliminated. Gathered using votePreferences.poll()
	 */
	public Candidate eliminatePreferredCandidate() {
		return votePreferences.poll();
	}

	// Parses line of ballot data into list of rankings by order of candidate appearance
	private List<int[]> getRankings(String ballotLine) {
		List<int[]> result = new ArrayList<int[]>();

		int candidateIndex = 0;
		String strRanking = "";
		for (int i = 0; i < ballotLine.length(); ++i) {
			if (ballotLine.charAt(i) == ',') { // Ranking has been fully stored in strRanking
				if (strRanking != "") { // Ranking is non-empty
					int[] nextRanking = { candidateIndex, Integer.parseInt(strRanking) };
					result.add(nextRanking); // Store ranking as int[2]
					strRanking = ""; // Reset strRanking
				}
				++candidateIndex;
			} else {
				strRanking += ballotLine.charAt(i);
			}
		}

		// Store last ranking  (it's a kludge but it works)
		if (strRanking != "") {
			int[] nextRanking = { candidateIndex, Integer.parseInt(strRanking) };
			result.add(nextRanking);
			strRanking = "";
		}

		return result;
	}
}
