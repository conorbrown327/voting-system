package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import election.Ballot;
import election.Candidate;
import election.Party;

class BallotTest {
	List<Candidate> candidates;

	private void initCandidates() {
		candidates = new LinkedList<>();
		Party democrat = new Party("Democrat");
		Party republican = new Party("Republican");
		Party federalist = new Party("Federalist");
		Party independent = new Party("Independent");
		Candidate joeBiden = new Candidate("Joe Biden", democrat);
		Candidate donaldTrump = new Candidate("Donald Trump", republican);
		Candidate alexanderHamilton = new Candidate("Alexander Hamilton", federalist);
		Candidate kanyeWest = new Candidate("Kanye West", independent);
		candidates.add(joeBiden);
		candidates.add(donaldTrump);
		candidates.add(alexanderHamilton);
		candidates.add(kanyeWest);
	}

	@Test
	void testBallotPreferences() {
		initCandidates();
		Ballot ballot1 = new Ballot(candidates, "1,2,3,4");
		Ballot ballot2 = new Ballot(candidates, "4,1,3,2");
		Ballot ballot3 = new Ballot(candidates, ",,,1");

		assertNotEquals(ballot1.getPreferredCandidate().getName(), ballot3.getPreferredCandidate().getName());

		assertEquals("Joe Biden", ballot1.getPreferredCandidate().getName());
		ballot1.eliminatePreferredCandidate();
		assertEquals("Donald Trump", ballot1.getPreferredCandidate().getName());

		assertEquals("Donald Trump", ballot2.eliminatePreferredCandidate().getName());
		assertEquals("Kanye West", ballot2.eliminatePreferredCandidate().getName());
		assertEquals("Alexander Hamilton", ballot2.eliminatePreferredCandidate().getName());

		assertEquals("Kanye West", ballot3.eliminatePreferredCandidate().getName());
		assertEquals(null, ballot3.getPreferredCandidate());
	}

	@Test
	void testBallotLine() {
		initCandidates();
		Ballot ballot1 = new Ballot(candidates, "1,2,3,4");
		Ballot ballot2 = new Ballot(candidates, "4,1,3,2");
		Ballot ballot3 = new Ballot(candidates, ",,,1");

		assertEquals("1,2,3,4", ballot1.getBallotInfo());
		assertEquals("4,1,3,2", ballot2.getBallotInfo());
		assertEquals(",,,1", ballot3.getBallotInfo());
	}
}
