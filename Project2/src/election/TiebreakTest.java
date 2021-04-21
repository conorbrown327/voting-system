package election;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class TiebreakTest extends OpenPartyListingElection{
	
	@Test
	void tiebreakTest() {
		TiebreakTest OPL = new TiebreakTest();
		Party independent = new Party("Independent");
		Candidate c1 = new Candidate("Jack", independent);
		Candidate c2 = new Candidate("Sean", independent);
		
		var c1Wins = 0;
		var c2Wins = 0;
		Candidate loser;
		
		for(int i = 0; i < 100; i++) {
			loser = OPL.breakTie(c1, c2);
			if (loser.equals(c1)) {
				c2Wins += 1;
			}
			else {
				c1Wins += 1;
			}
		}
		
		assertTrue(Math.abs(c1Wins - c2Wins) <= 20);
	}
}