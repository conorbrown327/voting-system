package election.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import election.Election;
import election.InstantRunoffElection;
import java.util.Scanner;

class GeneralElectionTest {

	@Test
	void testConstructor() {
		Election e = new InstantRunoffElection(new Scanner("emptyFile.txt"));
		fail("Not yet implemented");
	}

}
