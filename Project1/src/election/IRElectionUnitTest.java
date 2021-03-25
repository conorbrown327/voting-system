package election;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class IRElectionUnitTest extends InstantRunoffElection {

	@Test
	void readBallotFileTest() {
		String path = IRElectionUnitTest.class.getResource("/election.files/comebackIR.csv").getPath();
		try {
			Scanner scan = new Scanner(new File(path));
			scan.nextLine().replaceAll("\\s+", "");

			IRElectionUnitTest IR = new IRElectionUnitTest();

			assertEquals(0, IR.candidates.size());
			assertEquals(0, IR.participatingParties.size());

			IR.readBallotFile(scan);

			// Tests for proper base values
			assertEquals(7, IR.numBallots);
			assertEquals(4, IR.candidates.size());
			assertEquals(4, IR.participatingParties.size());

			// Tests for individual candidate stats

			// Rosen stats
			assertEquals("Rosen", IR.candidates.get(0).getName());
			assertEquals(3, IR.candidates.get(0).getVoteCount());
			assertEquals("D", IR.candidates.get(0).getParty().getPartyName());

			// Kleinberg stats
			assertEquals("Kleinberg", IR.candidates.get(1).getName());
			assertEquals(2, IR.candidates.get(1).getVoteCount());
			assertEquals("R", IR.candidates.get(1).getParty().getPartyName());

			// Chou stats
			assertEquals("Chou", IR.candidates.get(2).getName());
			assertEquals(1, IR.candidates.get(2).getVoteCount());
			assertEquals("I", IR.candidates.get(2).getParty().getPartyName());

			// Royce stats
			assertEquals("Royce", IR.candidates.get(3).getName());
			assertEquals(1, IR.candidates.get(3).getVoteCount());
			assertEquals("L", IR.candidates.get(3).getParty().getPartyName());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	void getWinnerTest() {
		IRElectionUnitTest IR = new IRElectionUnitTest();

		Party csci5801 = new Party("CSCI 5801");

		Candidate sean = new Candidate("Sean", csci5801);
		Candidate joe = new Candidate("Joe", csci5801);
		Candidate jack = new Candidate("Jack", csci5801);
		Candidate conor = new Candidate("Conor", csci5801);
		List<Candidate> cList = new ArrayList<>();
		cList.add(sean);
		cList.add(joe);
		cList.add(jack);
		cList.add(conor);

		IR.candidates = cList;
		IR.numBallots = 4;

		// Conor wins by majority
		sean.incrementVoteCount(-1);
		joe.incrementVoteCount(10);
		jack.incrementVoteCount();
		conor.incrementVoteCount(11);

		assertEquals(conor, IR.getWinner());

		// Re-adds eliminated candidates for next test
		IR.candidates = cList;

		joe.incrementVoteCount(2);

		// ensures that no candidate will have a vote majority since majority is decided
		// based on candidate vote divided by the total number of ballots. AKA goes
		// until one candidate remains
		IR.numBallots = 400;

		assertEquals(joe, IR.getWinner());

	}

	@Test
	void existsMajorityTest() {
		IRElectionUnitTest IR = new IRElectionUnitTest();

		Party dems = new Party("Democrats");
		Party republicans = new Party("Republicans");

		Candidate joeBiden = new Candidate("Joe Biden", dems);
		Candidate donaldTrump = new Candidate("Donald Trump", republicans);

		joeBiden.incrementVoteCount(5);
		donaldTrump.incrementVoteCount(5);

		IR.numBallots = 10;

		List<Candidate> cList = new ArrayList<>();
		cList.add(joeBiden);

		IR.candidates = cList;

		// Will be true since only one candidate is in the candidates list
		assertTrue(IR.existsMajority());

		IR.candidates.add(donaldTrump);

		// Will be false since both candidates are tied in votes (no majority)
		assertFalse(IR.existsMajority());

		joeBiden.incrementVoteCount();

		// Will be true since 6 divided by 10 is greater than 50%
		assertTrue(IR.existsMajority());
	}

	@Test
	void getLastPlaceCandidatesTest() {
		IRElectionUnitTest IR = new IRElectionUnitTest();

		Party csci5801 = new Party("CSCI 5801");

		Candidate sean = new Candidate("Sean", csci5801);
		Candidate joe = new Candidate("Joe", csci5801);
		Candidate jack = new Candidate("Jack", csci5801);
		Candidate conor = new Candidate("Conor", csci5801);
		List<Candidate> cList = new ArrayList<>();
		cList.add(sean);
		cList.add(joe);
		cList.add(jack);
		cList.add(conor);

		IR.candidates = cList;

		// Conor wins by majority
		sean.incrementVoteCount();
		joe.incrementVoteCount(10);
		jack.incrementVoteCount();
		conor.incrementVoteCount(11);

		// Sorts the list from highest vote count to lowest
		IR.candidates.sort(null);

		var lastPlaceCandidates = IR.getLastPlaceCandidates();

		// Should be size 2 since two candidates are tied at 1 vote
		assertEquals(2, lastPlaceCandidates.size());
		assertTrue(lastPlaceCandidates.contains(sean));
		assertTrue(lastPlaceCandidates.contains(jack));
	}

}
