package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.ElectionDriver;
import election.InstantRunoffElection;
import election.OpenPartyListingElection;

class OutputTest
{	
    // run an IR election to test media file, audit and display to screen 
    // test will be done by inspection
    @Test
    void testIRWriteFunctions() throws FileNotFoundException
    {
        String path = OutputTest.class.getResource("/election.files/fixedWinnerIR.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		InstantRunoffElection ir = new InstantRunoffElection(ballotFiles);
    } // testIRWriteFunctions

    // run an OPL election to test media file, audit and display to screen 
    // test will be done by inspection
    @Test
    void testOPLWriteFunctions() throws FileNotFoundException
    {
        String path = OutputTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		List<Scanner> ballotFiles = new ArrayList<>();
		ballotFiles.add(scan);
		OpenPartyListingElection opl = new OpenPartyListingElection(ballotFiles);
    } // testOPLWriteFunctions
} // OutputTest