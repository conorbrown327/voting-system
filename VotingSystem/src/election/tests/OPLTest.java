package election.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import election.Election;
import election.OpenPartyListingElection;
import java.util.Scanner;

class OPLTest {

	@Test
	void test() {
		Election e = new OpenPartyListingElection(new Scanner("EmptyFile.txt"));
		fail("Not yet implemented");
	}

}
