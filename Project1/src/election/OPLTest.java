package election;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.Candidate;
import election.OpenPartyListingElection;
import election.Party;

class OPLTest {
	
	@Test
	void gettersAndSetters() throws FileNotFoundException{
		String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);
		var participatingParties = opl.getParticipatingParties();
		var candidates = opl.getCandidates();
		var numBallots = opl.getNumBallots();
		var seats = opl.getSeats();
		assertEquals(3, participatingParties.size());
		assertEquals(4, candidates.size());
		assertEquals(6, numBallots);
		assertEquals(2, seats);
		assertEquals("R", participatingParties.get(0).getPartyName());
		assertEquals("D", participatingParties.get(1).getPartyName());
		assertEquals("I", participatingParties.get(2).getPartyName());
	}
	
	@Test
	void tibreakTest() throws FileNotFoundException{
		String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);
		var independent = new Party("I");
		var c1 = new Candidate("Jack", independent);
		var c2 = new Candidate("Sean", independent);
		
		var c1Wins = 0;
		var c2Wins = 0;
		
		for(int i = 0; i < 100; i++) {
			Candidate winner = opl.breakTie(c1, c2);
			if (winner.equals(c1)) {
				c1Wins += 1;
			}
			else {
				c2Wins += 1;
			}
		}
		
		assertTrue(Math.abs(c1Wins - c2Wins) < 10);
		
	}

	@Test
	void OPLTest1() throws FileNotFoundException {
		String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);
		var seatedCandidates = opl.getSeatedCandidates();
		assertEquals(2, seatedCandidates.size());
		seatedCandidates.sort(null);
		assertEquals("Billy", seatedCandidates.get(0).getName());
		assertEquals("D", seatedCandidates.get(0).getParty().getPartyName());
		assertEquals(1, seatedCandidates.get(0).getParty().getSeatsAllocated());
		assertEquals(1, seatedCandidates.get(1).getParty().getSeatsAllocated());
		assertEquals(seatedCandidates.size(), opl.getSeats());
	}

	@Test
	void OPLTest2() throws FileNotFoundException {
		String path = OPLTest.class.getResource("/election.files/OPLTest2.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);
		var seatedCandidates = opl.getSeatedCandidates();
		assertEquals(3, seatedCandidates.size());
		seatedCandidates.sort(null);
		assertEquals(5, seatedCandidates.get(0).getParty().getTotalPartyVote());
		assertEquals("Pike", seatedCandidates.get(0).getName());
		assertEquals(3, seatedCandidates.get(1).getParty().getTotalPartyVote());
		assertEquals("Jones", seatedCandidates.get(1).getName());
		assertEquals("Foster", seatedCandidates.get(2).getName());
	}

	@Test
	void testLargeOPLFile() throws FileNotFoundException {
		String path = OPLTest.class.getResource("/election.files/hundredk_ballots_opl.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);

		var seatedCandidates = opl.getSeatedCandidates();
		assertEquals(seatedCandidates.size(), 3);
		assertEquals(99999, seatedCandidates.get(0).getParty().getTotalPartyVote());
		assertEquals(1, seatedCandidates.get(2).getParty().getTotalPartyVote());

		assertEquals("Pike", seatedCandidates.get(0).getName());
		assertEquals("D", seatedCandidates.get(0).getParty().getPartyName());
		assertEquals("Foster", seatedCandidates.get(1).getName());
		assertEquals("D", seatedCandidates.get(1).getParty().getPartyName());
		assertEquals("Smith", seatedCandidates.get(2).getName());
		assertEquals("I", seatedCandidates.get(2).getParty().getPartyName());

	}

	@Test
	void OPLTestSpecial() throws FileNotFoundException {
		String path = OPLTest.class.getResource("/election.files/OPLTestSpecial.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);
		var seatedCandidates = opl.getSeatedCandidates();
		assertEquals(3, seatedCandidates.size());
		seatedCandidates.sort(null);
		assertEquals(4, seatedCandidates.get(0).getParty().getTotalPartyVote());
		assertEquals("Sean", seatedCandidates.get(0).getName());
		assertEquals(1, seatedCandidates.get(1).getParty().getTotalPartyVote());
		assertEquals(1, seatedCandidates.get(2).getParty().getTotalPartyVote());
		assertEquals(seatedCandidates.size(), opl.getSeats());
	}
}
