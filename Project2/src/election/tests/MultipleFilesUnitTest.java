package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.ElectionDriver;
import election.InstantRunoffElection;

class MultipleFilesUnitTest {

	// System should exit saying no file was provided
	@Test
	void testNoFile() throws FileNotFoundException {
		String[] args = {};
        ElectionDriver.main(args);
	}
	
	
	// Should run a successful election with only the first file's ballots
	@Test
	void testOneFile() throws FileNotFoundException {
		String[] args = { "/election.files/OPLMultiOne.csv" };
        ElectionDriver.main(args);
	}
	
	
	// Should result in successfully run OPL election and count ballots
	// from both files
	@Test
	void testTwoFiles() throws FileNotFoundException {
		String[] args = { "/election.files/OPLMultiOne.csv",
						  "/election.files/OPLMultiTwo.csv" };
        ElectionDriver.main(args);
	}
	
	// Should result in an error must be the same election type
	@Test
	void testDifferentElectionTypes() throws FileNotFoundException {
		String[] args = { "/election.files/fixedWinnerIR.csv",
						  "/election.files/OPLTest1.csv" };
        ElectionDriver.main(args);
	}
}