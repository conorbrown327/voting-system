package election;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.junit.jupiter.api.Test;

import election.Candidate;
import election.OpenPartyListingElection;
import election.Party;

public class OPLTest extends OpenPartyListingElection {
	
	@Test
	void gettersAndSetters() throws FileNotFoundException{
		String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);
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

	// Full run of OPL's readBallotFile() under the utmost scrutiny
	@Test
	public void testReadBallotFile() throws FileNotFoundException {
		String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner ballotFile = new Scanner(new File(path));

		ballotFile.nextLine();
		seatedCandidates = new LinkedList<>();
		initializeParameters();
		readBallotFile(ballotFile);

		assertEquals(numCandidates, 4);
		assertEquals(candidates.size(), 4);
		assertEquals(candidates.get(0).getName(), "Dan");
		assertEquals(candidates.get(1).getName(), "Bob");
		assertEquals(candidates.get(2).getName(), "Billy");
		assertEquals(candidates.get(3).getName(), "Jane");

		assertEquals(participatingParties.size(), 3);
		assertEquals(participatingParties.get(0).getPartyName(), "R");
		assertEquals(participatingParties.get(1).getPartyName(), "D");
		assertEquals(participatingParties.get(2).getPartyName(), "I");

		assertSame(candidates.get(0).getParty(), participatingParties.get(0));
		assertSame(candidates.get(1).getParty(), participatingParties.get(0));
		assertSame(candidates.get(2).getParty(), participatingParties.get(1));
		assertSame(candidates.get(3).getParty(), participatingParties.get(2));

		assertSame(participatingParties.get(0).getPartyMembers().get(0), candidates.get(0));
		assertSame(participatingParties.get(0).getPartyMembers().get(1), candidates.get(1));
		assertSame(participatingParties.get(1).getPartyMembers().get(0), candidates.get(2));
		assertSame(participatingParties.get(2).getPartyMembers().get(0), candidates.get(3));

		assertEquals(numBallots, 6);

		assertEquals(candidates.get(0).getVoteCount().intValue(), 1);
		assertEquals(candidates.get(1).getVoteCount().intValue(), 1);
		assertEquals(candidates.get(2).getVoteCount().intValue(), 3);
		assertEquals(candidates.get(3).getVoteCount().intValue(), 1);

		assertEquals(participatingParties.get(0).getTotalPartyVote(), 2);
		assertEquals(participatingParties.get(1).getTotalPartyVote(), 3);
		assertEquals(participatingParties.get(2).getTotalPartyVote(), 1);
	}
	
	@Test
	void OPLTest1() throws FileNotFoundException {
		String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);
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
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);
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
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);

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
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);
		var seatedCandidates = opl.getSeatedCandidates();
		assertEquals(3, seatedCandidates.size());
		seatedCandidates.sort(null);
		assertEquals(4, seatedCandidates.get(0).getParty().getTotalPartyVote());
		assertEquals("Sean", seatedCandidates.get(0).getName());
		assertEquals(1, seatedCandidates.get(1).getParty().getTotalPartyVote());
		assertEquals(1, seatedCandidates.get(2).getParty().getTotalPartyVote());
		assertEquals(seatedCandidates.size(), opl.getSeats());
	}
	
	@Test
	void OPLTest3Way() throws FileNotFoundException{
		String path = OPLTest.class.getResource("/election.files/OPL3Way.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);
		var seatedCandidates = opl.getSeatedCandidates();
		assertEquals(2, seatedCandidates.size());
	}
}
