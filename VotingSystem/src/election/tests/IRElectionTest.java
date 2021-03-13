package election.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import election.Election;
import election.InstantRunoffElection;
import java.util.Scanner;

class IRElectionTest {

	@Test
	void test() {
		Election ir = new InstantRunoffElection(new Scanner("emptyfile.txt"));
		fail("Not yet implemented");
	}

}
