package election;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.Test;

public class InputHelperTest extends Election {

	// Abstract declarations to appease the compiler
	protected void determineWinner(Scanner ballotFile) {}
	protected void readBallotFile(Scanner ballotFile) {}
	protected void writeAuditFileHeader() {}
	protected void writeMediaFile() {}
	protected void displayResultsToTerminal() {}

	// Set candidates when there are none
	@Test
	public void testSetCandidatesEmpty() {
		String candidateLine = "";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 0);
	}

	// Set candidates with typical IR line
	@Test
	public void testSetCandidatesIRStandard() {
		String candidateLine = "Joe (I), Jim (V), Mary (C), Sara (U)";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(2).getName(), "Mary");
		assertEquals(candidates.get(3).getName(), "Sara");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
		assertEquals(candidates.get(2).getParty().getPartyName(), "C");
		assertEquals(candidates.get(3).getParty().getPartyName(), "U");
	}

	// Set IR candidates when there is only one
	@Test
	public void testSetCandidatesIRSingle() {
		String candidateLine = "Joe (I)";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
	}

	// Set IR candidates with more than first name
	@Test
	public void testSetCandidatesIRFullNames() {
		String candidateLine = "Joe Moe (I), Jim Web III (V)";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.get(0).getName(), "Joe Moe");
		assertEquals(candidates.get(1).getName(), "Jim Web III");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
	}

	// Set IR candidates when parties are not abbreviated
	@Test
	public void testSetCandidatesIRPartyFullNames() {
		String candidateLine = "Joe (Independent Democrat), Jim (Vegan Carnivorous Plant)";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(0).getParty().getPartyName(), "Independent Democrat");
		assertEquals(candidates.get(1).getParty().getPartyName(), "Vegan Carnivorous Plant");
	}

	// Set IR candidates when names contain non-ascii characters
	@Test
	public void testSetCandidatesIRNonAscii() {
		String candidateLine = "Joñ (Q), Warszawa (ñ)";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.get(0).getName(), "Joñ");
		assertEquals(candidates.get(1).getName(), "Warszawa");
		assertEquals(candidates.get(0).getParty().getPartyName(), "Q");
		assertEquals(candidates.get(1).getParty().getPartyName(), "ñ");
	}

	// Set IR candidates from line with arbitrary spacing
	@Test
	public void testSetCandidatesIRAbsurdSpacing() {
		String candidateLine = " Joe      (  I)                   ,     Jim (\t\t\t  V  )";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 2);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
	}

	// Set candidates with typical OPL line
	@Test
	public void testSetCandidatesOPLStandard() {
		String candidateLine = "[Joe,I],[Jim,V],[Mary,C],[Sara,U]";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 4);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(2).getName(), "Mary");
		assertEquals(candidates.get(3).getName(), "Sara");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
		assertEquals(candidates.get(2).getParty().getPartyName(), "C");
		assertEquals(candidates.get(3).getParty().getPartyName(), "U");
	}

	// Set OPL candidates when there is only one
	@Test
	public void testSetCandidatesOPLSingle() {
		String candidateLine = "[Joe,I]";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 1);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
	}

	// Set OPL candidates when parties are not abbreviated
	@Test
	public void testSetCandidatesOPLFullNames() {
		String candidateLine = "[Joe Moe,I],[Jim Web III,V]";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 2);
		assertEquals(candidates.get(0).getName(), "Joe Moe");
		assertEquals(candidates.get(1).getName(), "Jim Web III");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
	}

	// Set OPL candidates when parties are not abbreviated
	@Test
	public void testSetCandidatesOPLPartyFullNames() {
		String candidateLine = "[Joe,Independent Democrat],[Jim,Vegan Carnivorous Plant]";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 2);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(0).getParty().getPartyName(), "Independent Democrat");
		assertEquals(candidates.get(1).getParty().getPartyName(), "Vegan Carnivorous Plant");
	}

	// Set OPL candidates when names contain non-ascii characters
	@Test
	public void testSetCandidatesOPLNonAscii() {
		String candidateLine = "[Joñ,Q],[Warszawa,ñ]";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 2);
		assertEquals(candidates.get(0).getName(), "Joñ");
		assertEquals(candidates.get(1).getName(), "Warszawa");
		assertEquals(candidates.get(0).getParty().getPartyName(), "Q");
		assertEquals(candidates.get(1).getParty().getPartyName(), "ñ");
	}

	// Set OPL candidates from line with arbitrary spacing
	@Test
	public void testSetCandidatesOPLAbsurdSpacing() {
		String candidateLine = "[ Joe      ,I]                   ,     [Jim, \t\t\t V]";
		candidates = new LinkedList<>();
		setCandidates(candidateLine);
		assertEquals(candidates.size(), 2);
		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
	}

	// Consolidate parties when there are 4 candidates and 4 parties
	@Test
	public void testConsolidateParties4C4P() {
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
		candidates.add(new Candidate("Joe", new Party("I")));
		candidates.add(new Candidate("Jim", new Party("V")));
		candidates.add(new Candidate("Mary", new Party("C")));
		candidates.add(new Candidate("Sara", new Party("U")));

		consolidateParties();

		assertEquals(candidates.size(), 4);
		assertEquals(participatingParties.size(), 4);

		assertSame(candidates.get(0).getParty(), participatingParties.get(0));
		assertSame(candidates.get(1).getParty(), participatingParties.get(1));
		assertSame(candidates.get(2).getParty(), participatingParties.get(2));
		assertSame(candidates.get(3).getParty(), participatingParties.get(3));

		assertSame(participatingParties.get(0).getPartyMembers().get(0), candidates.get(0));
		assertSame(participatingParties.get(1).getPartyMembers().get(0), candidates.get(1));
		assertSame(participatingParties.get(2).getPartyMembers().get(0), candidates.get(2));
		assertSame(participatingParties.get(3).getPartyMembers().get(0), candidates.get(3));
	}

	// Consolidate parties when there are 3 candidates and 2 parties
	@Test
	public void testConsolidateParties3C2P() {
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
		candidates.add(new Candidate("Joe", new Party("I")));
		candidates.add(new Candidate("Jim", new Party("I")));
		candidates.add(new Candidate("Mary", new Party("C")));

		consolidateParties();

		assertEquals(candidates.size(), 3);
		assertEquals(participatingParties.size(), 2);

		assertSame(candidates.get(0).getParty(), participatingParties.get(0));
		assertSame(candidates.get(1).getParty(), participatingParties.get(0));
		assertSame(candidates.get(2).getParty(), participatingParties.get(1));

		assertSame(participatingParties.get(0).getPartyMembers().get(0), candidates.get(0));
		assertSame(participatingParties.get(0).getPartyMembers().get(1), candidates.get(1));
		assertSame(participatingParties.get(1).getPartyMembers().get(0), candidates.get(2));
	}

	// Consolidate parties when there are 2 candidates and 1 party
	@Test
	public void testConsolidateParties2C1P() {
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
		candidates.add(new Candidate("Joe", new Party("I")));
		candidates.add(new Candidate("Jim", new Party("I")));

		consolidateParties();

		assertEquals(candidates.size(), 2);
		assertEquals(participatingParties.size(), 1);

		assertSame(candidates.get(0).getParty(), participatingParties.get(0));
		assertSame(candidates.get(1).getParty(), participatingParties.get(0));

		assertSame(participatingParties.get(0).getPartyMembers().get(0), candidates.get(0));
		assertSame(participatingParties.get(0).getPartyMembers().get(1), candidates.get(1));
	}

	// Consolidate parties when there are 0 candidates and 0 parties
	@Test
	public void testConsolidateParties0C0P() {
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
		consolidateParties();
		assertEquals(candidates.size(), 0);
		assertEquals(participatingParties.size(), 0);
	}

	@Test
	public void testSetCandidatesAndParties() {

		// Standard input
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
		String candidateLineStandard = "Joe (I), Jim (V), Mary (C), Sara (U)";
		setCandidatesAndParties(candidateLineStandard);

		assertEquals(candidates.size(), 4);
		assertEquals(participatingParties.size(), 4);

		assertEquals(candidates.get(0).getName(), "Joe");
		assertEquals(candidates.get(1).getName(), "Jim");
		assertEquals(candidates.get(2).getName(), "Mary");
		assertEquals(candidates.get(3).getName(), "Sara");
		assertEquals(candidates.get(0).getParty().getPartyName(), "I");
		assertEquals(candidates.get(1).getParty().getPartyName(), "V");
		assertEquals(candidates.get(2).getParty().getPartyName(), "C");
		assertEquals(candidates.get(3).getParty().getPartyName(), "U");

		assertSame(candidates.get(0).getParty(), participatingParties.get(0));
		assertSame(candidates.get(1).getParty(), participatingParties.get(1));
		assertSame(candidates.get(2).getParty(), participatingParties.get(2));
		assertSame(candidates.get(3).getParty(), participatingParties.get(3));



		// Empty input
		candidates = new LinkedList<>();
		participatingParties = new LinkedList<>();
		String candidateLineEmpty = "";
		setCandidatesAndParties(candidateLineEmpty);
		assertEquals(candidates.size(), 0);
		assertEquals(participatingParties.size(), 0);

	}
}