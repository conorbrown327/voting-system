package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import election.Candidate;
import election.Party;

class CandidateTest {
	
	@Test
	void testName() {
		Party independent = new Party("Independent");
		Candidate kanyeWest = new Candidate("Kanye West", independent);
		
		// Test to make sure that the constructor and getter method are working properly
		assertEquals("Kanye West", kanyeWest.getName());
	}

	@Test
	void testVoteCount() {
		Party independent = new Party("Independent");
		Candidate kanyeWest = new Candidate("Kanye West", independent);

		// Tests to see if 0 is the initialized vote count
		assertEquals(0, kanyeWest.getVoteCount());

		// Tests if incrementVoteCount() correctly adds 1 to voteCount
		kanyeWest.incrementVoteCount();
		assertEquals(1, kanyeWest.getVoteCount());

		// Ensures that incrementVoteCount() doesn't set to 1
		kanyeWest.incrementVoteCount();
		assertEquals(2, kanyeWest.getVoteCount());

		// Tests that incrementVoteCount with a parameter correctly adds to the total
		// vote count
		kanyeWest.incrementVoteCount(2018);
		assertEquals(2020, kanyeWest.getVoteCount());
	}

	@Test
	void testCompareTo() {
		Party independent = new Party("Independent");
		Candidate kanyeWest = new Candidate("Kanye West", independent);
		Candidate northWest = new Candidate("North West", independent);

		// Should return 1 since comparator sorts those with greater votes with higher
		// priority
		kanyeWest.incrementVoteCount();
		assertEquals(-1, kanyeWest.compareTo(northWest));

		// Should return 0 since the vote count is now equal
		northWest.incrementVoteCount();
		assertEquals(0, kanyeWest.compareTo(northWest));

		// Should return -1 now that North West has the greater vote total
		northWest.incrementVoteCount();
		assertEquals(1, kanyeWest.compareTo(northWest));
	}

	@Test
	void testSetParty() {
		Party patriots = new Party("Patriots");
		Party buccaneers = new Party("Buccaneers");
		Candidate tomBrady = new Candidate("Tom Brady", patriots);

		assertEquals(tomBrady.getParty(), patriots);

		tomBrady.setParty(buccaneers);

		assertEquals(tomBrady.getParty(), buccaneers);
	}

}
