package election.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import election.Election;
import election.InstantRunoffElection;

class GeneralElectionTest {

	@Test
	void testConstructor() {
		Election e = new InstantRunoffElection("emptyFile.txt");
		fail("Not yet implemented");
	}

}
