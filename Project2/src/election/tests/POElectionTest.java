package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.*;

public class POElectionTest {
	
	// Test that info from the run election is stored and is correct
	@Test
	void testPOFile1() throws FileNotFoundException {
		String path = POElectionTest.class.getResource("/election.files/POFile1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		PopularityOnlyElection po = new PopularityOnlyElection(ballotFiles);
		int numBallots = po.getNumBallots();
		List<Candidate> numCands = po.getCandidates();
		
		assertEquals(numBallots, 9);
		assertEquals(numCands.size(), 6);
		
		assertEquals(numCands.get(0).getName(), "Pike");
		assertEquals(numCands.get(1).getName(), "Foster");
		assertEquals(numCands.get(2).getName(), "Deutsch");
		assertEquals(numCands.get(3).getName(), "Borg");
		assertEquals(numCands.get(4).getName(), "Jones");
		assertEquals(numCands.get(5).getName(), "Smith");
		
		assertEquals(numCands.get(0).getParty().getPartyName(), "D");
		assertEquals(numCands.get(1).getParty().getPartyName(), "D");
		assertEquals(numCands.get(2).getParty().getPartyName(), "R");
		assertEquals(numCands.get(3).getParty().getPartyName(), "R");
		assertEquals(numCands.get(4).getParty().getPartyName(), "R");
		assertEquals(numCands.get(5).getParty().getPartyName(), "I");
		
		assertEquals(numCands.get(0).getVoteCount(), 3);
		assertEquals(numCands.get(1).getVoteCount(), 2);
		assertEquals(numCands.get(2).getVoteCount(), 0);
		assertEquals(numCands.get(3).getVoteCount(), 2);
		assertEquals(numCands.get(4).getVoteCount(), 1);
		assertEquals(numCands.get(5).getVoteCount(), 1);
	}
	
	// Test that info from the run election is stored and is correct
	@Test
	void testPOFile2() throws FileNotFoundException {
		String path = POElectionTest.class.getResource("/election.files/POFile2.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		PopularityOnlyElection po = new PopularityOnlyElection(ballotFiles);
		int numBallots = po.getNumBallots();
		List<Candidate> numCands = po.getCandidates();
		
		assertEquals(numBallots, 5);
		assertEquals(numCands.size(), 4);
		
		assertEquals(numCands.get(0).getName(), "Jack");
		assertEquals(numCands.get(1).getName(), "Sean");
		assertEquals(numCands.get(2).getName(), "Joe");
		assertEquals(numCands.get(3).getName(), "Conor");
		
		assertEquals(numCands.get(0).getParty().getPartyName(), "I");
		assertEquals(numCands.get(1).getParty().getPartyName(), "I");
		assertEquals(numCands.get(2).getParty().getPartyName(), "I");
		assertEquals(numCands.get(3).getParty().getPartyName(), "I");
		
		assertEquals(numCands.get(0).getVoteCount(), 3);
		assertEquals(numCands.get(1).getVoteCount(), 1);
		assertEquals(numCands.get(2).getVoteCount(), 1);
		assertEquals(numCands.get(3).getVoteCount(), 0);
	}
	
	// Test that PO can read and store the correct info from multiple files
	@Test
	void testPOMultipleFiles() throws FileNotFoundException {
		String path1 = POElectionTest.class.getResource("/election.files/POFile2.csv").getPath();
		String path2 = POElectionTest.class.getResource("/election.files/POFile2.csv").getPath();
		Scanner scan1 = new Scanner(new File(path1));
		Scanner scan2 = new Scanner(new File(path2));
		scan1.nextLine().replaceAll("\\s+", "");
		scan2.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan1);
		ballotFiles.add(scan2);
		PopularityOnlyElection po = new PopularityOnlyElection(ballotFiles);
		int numBallots = po.getNumBallots();
		List<Candidate> numCands = po.getCandidates();
		
		assertEquals(numBallots, 10);
		assertEquals(numCands.size(), 4);
		
		assertEquals(numCands.get(0).getName(), "Jack");
		assertEquals(numCands.get(1).getName(), "Sean");
		assertEquals(numCands.get(2).getName(), "Joe");
		assertEquals(numCands.get(3).getName(), "Conor");
		
		assertEquals(numCands.get(0).getParty().getPartyName(), "I");
		assertEquals(numCands.get(1).getParty().getPartyName(), "I");
		assertEquals(numCands.get(2).getParty().getPartyName(), "I");
		assertEquals(numCands.get(3).getParty().getPartyName(), "I");
		
		assertEquals(numCands.get(0).getVoteCount(), 6);
		assertEquals(numCands.get(1).getVoteCount(), 2);
		assertEquals(numCands.get(2).getVoteCount(), 2);
		assertEquals(numCands.get(3).getVoteCount(), 0);
	}
}
