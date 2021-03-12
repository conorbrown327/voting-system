package election;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class Ballot {

	public Ballot(List<Candidate> candidates, String ballotLine) {
		List<int[]> rankings = getRankings(ballotLine);
		rankings.sort((a, b) -> a[1] - b[1]);
		for (int[] ranking : rankings) {
			votePreferences.add(candidates.get(ranking[0]));
		}
	}

	public Candidate getPreferredCandidate() {
		return votePreferences.peek();
	}

	public void eliminatePreferredCandidate() {
		votePreferences.poll();
	}

	private List<int[]> getRankings(String ballotLine) {
		List<int[]> result = new ArrayList<int[]>();

		int candidateIndex = 0;
		String strRanking = "";
		for (int i = 0; i < ballotLine.length(); ++i) {
			if (ballotLine.charAt(i) == ',') {
				++candidateIndex;
				if (strRanking != "") {
					int[] nextRanking = {candidateIndex, Integer.parseInt(strRanking)};
					result.add(nextRanking);
				}
			} else {
				strRanking += ballotLine.charAt(i);
			}
		}

		return result;
	}

	private Queue<Candidate> votePreferences;
}
