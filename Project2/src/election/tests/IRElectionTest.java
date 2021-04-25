package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		InstantRunoffElection ir = new InstantRunoffElection(ballotFiles);
		var winner = ir.getElectionWinner();
		assertEquals("Watters", winner.getName());
		assertEquals("P", winner.getParty().getPartyName());
		assertEquals(3, winner.getVoteCount());

		assertEquals(4, winner.getParty().getPartyMembers().size());
		assertEquals(1, ir.getParticipatingParties().size());
	}

	@Test
	void testLargeIRFile() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/hundredk_ballots_ir.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		InstantRunoffElection ir = new InstantRunoffElection(ballotFiles);
		var winner = ir.getElectionWinner();

		// Ensures that Rosen is the victor of the election
		assertEquals("Rosen", winner.getName());
		assertEquals("D", winner.getParty().getPartyName());

		var eliminatedCandidates = ir.getEliminatedCandidates();
		var candidates = ir.getCandidates();
		// Sorts candidate votes from highest vote to lowest
		candidates.sort(null);

		// Should only have 2 candidates being eliminated since after 2 eliminations
		// Rosen has majority
		assertEquals(2, eliminatedCandidates.size());

		// Order of eliminated candidates should be Royce then Chou
		assertEquals("Royce", eliminatedCandidates.get(0).getName());
		assertEquals("L", eliminatedCandidates.get(0).getParty().getPartyName());
		assertEquals("Chou", eliminatedCandidates.get(1).getName());
		assertEquals("I", eliminatedCandidates.get(1).getParty().getPartyName());

		// End result should be Royce with 52,000 votes and Kleinburg with 48,000
		assertEquals(52000, winner.getVoteCount());
		assertEquals(52000, candidates.get(0).getVoteCount());
		assertEquals("Kleinburg", candidates.get(1).getName());
		assertEquals(48000, candidates.get(1).getVoteCount());

	}

	@Test
	void testComeback() throws FileNotFoundException {
		String path = IRElectionTest.class.getResource("/election.files/comebackIR.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		InstantRunoffElection ir = new InstantRunoffElection(ballotFiles);
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
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		InstantRunoffElection ir = new InstantRunoffElection(ballotFiles);

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
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		InstantRunoffElection ir = new InstantRunoffElection(ballotFiles);
		var winner = ir.getElectionWinner();
		// Addressed in bug list
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
