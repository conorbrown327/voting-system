
package election;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * OpenPartyListingElection.java
 * 
 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 *         OpenPartyListingElection process the Instant runoff election type and
 *         produces results. determineWinner() is the main function beginning
 *         run and will call various helper functions to parse the ballots file
 *         and store needed info, create media and audit files and to display
 *         results to the terminal.
 */

public class OpenPartyListingElection extends Election {

	protected int seatsRemaining; // This will initially be set to equal the seats, but will be decremented as
									// seats are assigned
	protected int seats; // This is where the initial seats will be read in from the file
	protected int quota;
	protected List<Candidate> seatedCandidates;

	/**
	 * Constructor for an OpenPartyListingElection class. Initializes all parameters
	 * and then runs an election. Essentially also doubles as a driver for this
	 * class.
	 * 
	 * @param ballotFile: The Scanner object for the election file that was given to
	 *                    start this election
	 */

	public OpenPartyListingElection(List<Scanner> ballotFiles) {
		try {
			auditFile = new File(auditFileName);
			auditFile.createNewFile();
			auditFileWriter = new FileWriter(auditFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initializeParameters();
		seatedCandidates = new LinkedList<>();
//		if (ballotFiles.size() == 1) {
//			readBallotFile(ballotFiles.get(0));
//			determineWinner();
//		}
		readBallotFileList(ballotFiles);
		determineWinner();
		System.out.println("End of OPL Election");
	}

	/**
	 * The default constructor for an OpenPartyListing election that initializes all
	 * parameters, but does not run the election. This is used for testing purposes.
	 */

	public OpenPartyListingElection() {
		try {
			auditFile = new File(auditFileName);
			auditFile.createNewFile();
			auditFileWriter = new FileWriter(auditFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initializeParameters();
		seatedCandidates = new LinkedList<>();
	}

	/**
	 * A getter function for the candidates who are seated in this OPL election.
	 * 
	 * @return seatedCandiates: The list of seated candidates for this election
	 */
	public List<Candidate> getSeatedCandidates() {
		return seatedCandidates;
	}

	/**
	 * A getter function for the number of seats at the beginning of this election.
	 * 
	 * @return seats: The number of seats up for grabs at the beginning of this
	 *         election.
	 */

	public int getSeats() {
		return seats;
	}

	/**
	 * Initialize election parameters from ballot file. See SRS for file format
	 * details.
	 * 
	 * @param ballotFile: Input file in Scanner form, pointed to second line of
	 *                    data.
	 */
	@Override
	protected void readBallotFile(Scanner ballotFile) {
		// read the ballots file header
		numCandidates = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		setCandidatesAndParties(ballotFile.nextLine());
		seats = Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		numBallots += Integer.parseInt(ballotFile.nextLine().replaceAll("\\s+", ""));
		// determineQuota(); // Get quota set
		// write the audit file header after reading the ballots file header
		writeAuditFileHeader();
		while (ballotFile.hasNextLine()) {
			Ballot ballot = new Ballot(candidates, ballotFile.nextLine().replaceAll("\\s+", ""));
			// get the ballots preferred candidate
			Candidate preferredCandidate = ballot.getPreferredCandidate();
			preferredCandidate.incrementVoteCount();
			// Tally candidate vote count
			preferredCandidate.getParty().incrementTotalPartyVote();
			writeToAuditFile(ballot.getBallotInfo() + " awarded to " + preferredCandidate.getName() + "\n");
		}

		// write the votes summary to the audit final following the conclusion of
		// tallying
		writeToAuditFile("\nVotes summary for party and each candidate: \n");
		for (Party p : participatingParties) {
			writeToAuditFile(p.getPartyName() + ", Total Party Votes: " + p.getTotalPartyVote() + "\n"
					+ "---------------------\n");
			for (Candidate c : p.getPartyMembers()) {
				writeToAuditFile("-" + c.toString() + "\n");
			}
		}
	}

	/**
	 * Function will create the audit file and audit file writer and write the audit
	 * file head after the ballots file has been processed and initial information
	 * is stored. This function takes no parameters and has no return type.
	 */
	@Override
	protected void writeAuditFileHeader() {
		try {
			writeToAuditFile("Election Type: Open Party Listing\n");
			writeToAuditFile("Number of ballots: " + numBallots + "\n");
			writeToAuditFile("Open Seats: " + seats + "\n");
			writeToAuditFile("Quota: " + quota + " votes\n");

			writeToAuditFile("Candidate order as appears on ballots: \n");
			for (Candidate c : candidates) {
				writeToAuditFile("-" + c.getName() + "\n");
			}

			writeToAuditFile("Candidates by party: \n");
			for (Party p : participatingParties) {
				writeToAuditFile(p.getPartyName() + "\n");
				for (Candidate c : p.getPartyMembers()) {
					writeToAuditFile("-" + c.getName() + "\n");
				}
			}

			writeToAuditFile("\nBallots being awarded to each candidate: \n");

			auditFileWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a file with an election summary to provide to the media. This function
	 * take no parameters and has no return type.
	 */
	@Override
	protected void writeMediaFile() {
		try {

			File mediaFile = new File(mediaFileName);
			mediaFile.createNewFile();
			FileWriter writer = new FileWriter(mediaFile);

			writer.write(dateTime.format(formatObj) + " Open Party Listing Election Election Results\n"
					+ "--------------------------------\n");
			writer.write("Total votes cast: " + numBallots + "\n");
			writer.write("Candidates " + "(" + numCandidates + ") and votes by party: \n");

			for (Party p : participatingParties) {
				writer.write(p.getPartyName() + ", Total Party Votes: " + p.getTotalPartyVote() + "\n"
						+ "---------------------\n");
				for (Candidate c : p.getPartyMembers()) {
					writer.write("-" + c.toString() + "\n");
				}
			}

			writer.write("\nAwarded Seats: \n");
			for (Candidate c : seatedCandidates) {
				writer.write("-" + c.getName() + ": " + c.getParty().getPartyName() + ", with " + c.getVoteCount()
						+ " votes\n");
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays relevant election results to the terminal. Output is formatted so as
	 * to fit the results of an Open Party Listing Election. This function takes no
	 * parameters and has no return type.
	 */
	@Override
	protected void displayResultsToTerminal() {
		System.out.println("Program completed successfully\n");
		System.out.println("Election Summary");
		System.out.println("Election Type: Open Party Listing");
		System.out.println("Total Ballots Counted: " + numBallots);
		System.out.println("Participating parties: ");
		for (Party p : participatingParties) {
			System.out.println("-" + p.getPartyName());
		}

		System.out.println("Seats allocated: " + seats);
		System.out.println("Winners: ");
		for (Candidate c : seatedCandidates) {
			System.out.println(
					"-" + c.getName() + ": " + c.getParty().getPartyName() + ", with " + c.getVoteCount() + " votes");
		}

		System.out.println("\nAn audit file with the name " + auditFileName + " has been produced in "
				+ System.getProperty("user.dir"));
		System.out.println("A media file with the name " + mediaFileName + " has been produced in "
				+ System.getProperty("user.dir"));

	}

	/**
	 * This is heart of the OpenPartyListingElection class. This is the main
	 * function that determines which parties get which seats, and which seats go to
	 * which candidates.
	 * 
	 * @param Scanner ballotFile: The Scanner object responsible for reading the
	 *                ballot file that drives the entire election.
	 */
	@Override
	protected void determineWinner() {
		determineQuota();
		seatsRemaining = seats; // seatsRemaining should initially be equal to the seats read in from the file
		List<Party> manipulatedList = new ArrayList<Party>(participatingParties);
		// Initial distribution of seats to all of the parties in the election
		for (Party p : participatingParties) {
			int partySeats = calculateSeats(p);
			int partyRemainder = calculateRemainder(p);
			p.setRemainder(partyRemainder);
			// Handle the case where a party gets more seats than it has candidates here
			if (partySeats > p.getPartyMembers().size()) {
				p.addSeats(p.getPartyMembers().size());
				distributeSeats(p.getPartyMembers().size(), p);
				updateSeatsRemaining(p.getPartyMembers().size());
				manipulatedList.remove(p);
			}
			// All other cases go here
			else {
				p.addSeats(partySeats); // Don't distribute the seats yet, just update the number so that we only
										// distribute once at the end
				updateSeatsRemaining(partySeats);
			}
		}
		// write the initial seats won prior to remainder distribution to the audit file
		writeToAuditFile("\nInitial Seats Awarded: \n\n");
		for (Party p : participatingParties) {
			writeToAuditFile(p.getPartyName() + ": " + p.getSeatsAllocated() + " with " + p.getRemainder()
					+ " votes remaining\n");
		}

		// Checks for the need to distribute seats based on remainders
		if (seatsRemaining > 0) {
			Comparator<Party> compareByRemainder = (Party p1, Party p2) -> p1.getRemainder()
					.compareTo(p2.getRemainder());
			Collections.sort(manipulatedList, compareByRemainder.reversed());
			int distributionCounter = 0; // Counter for keeping track of which party should be distributed to
			while (seatsRemaining > 0) {
				manipulatedList.get(distributionCounter).addSeats(1);
				distributionCounter += 1;
				updateSeatsRemaining(1);
				if (distributionCounter == manipulatedList.size()) {
					distributionCounter = 0;
				}
			}
			// write the updated seats won after remainder distribution to the audit file
			writeToAuditFile("\nUpdated Seats Awarded after remainder distrubution: \n\n");
			for (Party p : participatingParties) {
				writeToAuditFile(p.getPartyName() + ": " + p.getSeatsAllocated() + " seat(s)\n");
			}
		}
		// Now, we can actually distribute the seats, since we will have a total number
		// of seats to distribute, including remainder allocated seats
		for (Party p : manipulatedList) {
			int seatsAllocated = p.getSeatsAllocated();
			distributeSeats(seatsAllocated, p);
		}
		writeToAuditFile("\nSeat winners: \n");
		for (Candidate c : seatedCandidates) {
			writeToAuditFile(
					"-" + c.getName() + ": " + c.getParty().getPartyName() + ", with " + c.getVoteCount() + " votes\n");
		}

		try {
			auditFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		writeMediaFile();
		displayResultsToTerminal();
	}

	// Calculate number of seats to award to party p
	private int calculateSeats(Party p) {
		int partyVotes = p.getTotalPartyVote();
		if (partyVotes < quota) {
			return 0;
		} else {
			return partyVotes / quota;
		}
	}

	// Calculate party votes not going toward a seat
	private int calculateRemainder(Party p) {
		int partyVotes = p.getTotalPartyVote();
		if (partyVotes < quota) {
			return partyVotes;
		} else {
			return partyVotes % quota;
		}
	}

	// Subtract seatsToSubtract seats from seatsRemaining
	private void updateSeatsRemaining(int seatsToSubtract) {
		seatsRemaining -= seatsToSubtract;
	}

	// Calculate election seat quota
	private void determineQuota() {
		quota = numBallots / seats;
	}

	// Distribute numSeats seats to Party p
	private void distributeSeats(int numSeats, Party p) {
		p.sortPartyMembersByVote();
		List<Candidate> sortedList = p.getPartyMembers();
		// If the party wins the same number of seats as candidates, just give everyone
		// a seat
		if (numSeats == p.getPartyMembers().size()) {
			for (int i = 0; i < numSeats; i++) {
				seatedCandidates.add(sortedList.get(i));
			}
		} else {
			// Otherwise, we distribute here
			for (int i = 0; i < numSeats; i++) {
				// Case where there is a tie
				if (sortedList.size() > (i + 1)
						&& sortedList.get(i).getVoteCount() == sortedList.get(i + 1).getVoteCount()) {
					Candidate tieLoser = breakTie(sortedList.get(i), sortedList.get(i + 1));
					// Breaks ties and adds candidates to the list, checking for duplicates whenever
					// they could arise
					if (tieLoser.equals(sortedList.get(i))) {
						if (seatedCandidates.contains(sortedList.get(i + 1))) {
							seatedCandidates.add(tieLoser);
						} else {
							seatedCandidates.add(sortedList.get(i + 1));
						}
					} else {
						if (seatedCandidates.contains(sortedList.get(i))) {
							seatedCandidates.add(sortedList.get(i + 1));
						} else {
							seatedCandidates.add(sortedList.get(i));
						}
					}
				} else {
					seatedCandidates.add(sortedList.get(i));
				}
			}
		}
	}
}

//TODO Test candidate parsing fix, then remove whitespace replacement in readBallotFile