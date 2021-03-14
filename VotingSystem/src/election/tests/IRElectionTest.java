package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.ElectionDriver;
import election.InstantRunoffElection;

class IRElectionTest {

	@Test
	void testFixedFile() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/fixedWinnerIR.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		InstantRunoffElection ir = new InstantRunoffElection(scan);
		var winner = ir.getElectionWinner();
		assertEquals("Watters", winner.getName());
		assertEquals("P", winner.getParty().getPartyName());
		assertEquals(3, winner.getVoteCount());

		assertEquals(4, winner.getParty().getPartyMembers().size());
		assertEquals(1, ir.getParticipatingParties().size());
	}

	@Test
	void testComeback() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/comebackIR.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		InstantRunoffElection ir = new InstantRunoffElection(scan);
		var winner = ir.getElectionWinner();

		assertEquals("Kleinberg", winner.getName());
		assertEquals("R", winner.getParty().getPartyName());
		assertEquals(4, winner.getVoteCount());
	}

	@Test
	void testFourWayTie() throws FileNotFoundException {
		// A file where each candidate only has one vote with no other preferences
		String path = IRElectionTest.class.getResource("/election.files/fourWayTie.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		InstantRunoffElection ir = new InstantRunoffElection(scan);

		// There should always be 3 eliminated candidates by the end
		assertEquals(3, ir.getEliminatedCandidates().size());

		// 4 parties participated in this election
		assertEquals(4, ir.getParticipatingParties().size());

		// There should only be one candidate and one ballot remaining
		assertEquals(1, ir.getNumBallots());
		assertEquals(1, ir.getCandidates().size());
	}

	@Test
	void testCandidatesWithLastNames() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/lastNameCandidatesIR.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		InstantRunoffElection ir = new InstantRunoffElection(scan);
		var winner = ir.getElectionWinner();
		// Add to bug list or fix
		assertEquals("Mike Douglas", winner.getName());
		assertEquals("D", winner.getParty().getPartyName());
	}

	@Test
	void fileNotFound() throws FileNotFoundException {
		String path = "/election.files/noSuchFile.csv";
		String[] args = { path };
		assertThrows(IllegalArgumentException.class, () -> {
			ElectionDriver.main(args);
		});
	}
}
