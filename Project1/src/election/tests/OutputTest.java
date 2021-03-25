package election.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import election.ElectionDriver;
import election.InstantRunoffElection;

class OutputTest
{
    // test that file names are being made correctly
    @Test
    void testFileNames() throws FileNotFoundException
    {
        // might have to do instance of ir or opl not sure if this will work
		Election elec;
        String correctMediaFileName = "MediaFile-" + elec.dateTime.format(elec.formatObj) + ".txt";
        String correctAuditFileName = "AuditFile-" + elec.dateTime.format(elec.formatObj) + ".txt";
        assertEquals(correctMediaFileName, elec.mediaFileName);
        assertEquals(correctAuditFileName, elec.auditFileName);
    } // testFileNames

    // run an IR election to test media file, audit and display to screen 
    // test will be done by inspection
    @Test
    void testIRWriteFunctions() throws FileNotFoundException
    {
        String path = IRElectionTest.class.getResource("/election.files/fixedWinnerIR.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		InstantRunoffElection ir = new InstantRunoffElection(scan);
    } // testWriteToAuditFile

    // run an OPL election to test media file, audit and display to screen 
    // test will be done by inspection
    @Test
    void testOPLWriteFunctions() throws FileNotFoundException
    {
        String path = OPLTest.class.getResource("/election.files/OPLTest1.csv").getPath();
		Scanner scan = new Scanner(new File(path));
		scan.nextLine().replaceAll("\\s+", "");
		OpenPartyListingElection opl = new OpenPartyListingElection(scan);
    } // testWriteToAuditFile
} // OutputTest