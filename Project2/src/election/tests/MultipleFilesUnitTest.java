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

	@Test
	void testSameTypeFiles() throws FileNotFoundException {
		String[] args = { "/election.files/fixedWinnerIR.csv",
						  "/election.files/fixedWinnerIR.csv" };
        ElectionDriver.main(args);
	}
	
	@Test
	void testDifferentTypeFiles() throws FileNotFoundException {
		String[] args = { "/election.files/fixedWinnerIR.csv",
						  "/election.files/OPLTest1.csv" };
        ElectionDriver.main(args);
	}
}