package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.ElectionDriver;
import election.InstantRunoffElection;

class MultipleFilesSystemTests {

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
	void testMultFilesOPL() throws FileNotFoundException {
		String[] args = { "/election.files/OPLMultiOne.csv",
						  "/election.files/OPLMultiTwo.csv" };
        ElectionDriver.main(args);
	}
	
	// Should result in successfully run IR election and count ballots
	// from both files
	@Test
	void testMultFilesIR() throws FileNotFoundException {
		String[] args = { "/election.files/comebackIR.csv",
						  "/election.files/comebackIR.csv" };
	    ElectionDriver.main(args);
	}
}