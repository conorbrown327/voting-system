package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.OpenPartyListingElection;

class OPLTest {

	@Test
	void OPLTest1() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/OPLTest1.csv").getPath();
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
		String path = IRElectionTest.class.getResource("/election.files/OPLTest2.csv").getPath();
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
	void OPLTestSpecial() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/OPLTestSpecial.csv").getPath();
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
