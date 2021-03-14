package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import election.Candidate;
import election.Party;

class PartyTest {

	@Test
	void testInitValues() {
		Party presidential = new Party("Presidential");

		assertEquals("Presidential", presidential.getPartyName());

		assertEquals(0, presidential.getTotalPartyVote());

		assertEquals(0, presidential.getPartyMembers().size());

		assertEquals(0, presidential.getSeatsAllocated());

		assertEquals(0, presidential.getRemainder());

	}

	@Test
	void testPartyMembers() {
		Party presidential = new Party("Presidential");

		Candidate georgeWashington = new Candidate("George Washington", presidential);
		Candidate thomasJefferson = new Candidate("Thomas Jefferson", presidential);
		Candidate teddyRoosevelt = new Candidate("Theodore Roosevelt", presidential);
		Candidate abeLincoln = new Candidate("Abraham Lincoln", presidential);

		presidential.addCandidate(georgeWashington);
		presidential.addCandidate(thomasJefferson);
		presidential.addCandidate(teddyRoosevelt);
		presidential.addCandidate(abeLincoln);

		assertEquals(4, presidential.getPartyMembers().size());

		abeLincoln.incrementVoteCount(50);
		teddyRoosevelt.incrementVoteCount(2);
		thomasJefferson.incrementVoteCount(10);
		georgeWashington.incrementVoteCount(100);

		assertEquals(abeLincoln, presidential.getPartyMembers().get(3));

		presidential.sortPartyMembersByVote();

		assertEquals(teddyRoosevelt, presidential.getPartyMembers().get(3));
	}

}
