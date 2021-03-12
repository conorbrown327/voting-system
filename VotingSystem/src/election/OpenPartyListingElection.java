package election;

import java.util.*;
import java.time.LocalDateTime; // import the LocalDateTime class

public class OpenPartyListingElection extends Election {
	
	private int seatsRemaining; //This will initially be set to equal the seats, but will be decremented as seats are assigned
	private int seats; //This is where the initial seats will be read in from the file
	private int quota;
	private List<Candidate> seatedCandidates;
	
	public OpenPartyListingElection(String filePath) {
		determineWinner(filePath);
	}

	@Override
	protected void readBallotFile(String filePath) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeToAuditFile(String line) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeMediaFile() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void displayResultsToTerminal() {
		System.out.println("Program completed successfully\n");
		System.out.println("Election Summary");
		System.out.println("Election Type: Open Party Listing");
		System.out.println("Participating parties: ")
		for(Paty p : participatingParties)
		{
			System.out.println("\t-" + p.getPartyName);
		}

		System.out.println("Seats allocated: " + seats);
		System.out.println("Winners: ");
		for(Candidate c : seatedCandidates)
		{
			System.out.println(c.getName() + ": " + c.getParty().getPartyName());
		}
		System.out.println("Total: Ballots Counted: " + numBallots);

		// this will be changed, should pass a LocalDateTime obj to func? when audit file created
		System.out.println("An audit file with the name " + LocalDateTime.now() + " has been produced in " + System.getProperty("user.dir"));

	}

	@Override
	protected void determineWinner(String filePath) {
		readBallotFile(filePath); //Read all of the necessary information/data into the proper locations
		determineQuota(); //Get quota set
		seatsRemaining = seats; //seatsRemaining should initially be equal to the seats read in from the file
		//Initial distribution of seats to all of the parties in the election
		for (Party p : participatingParties) {
			int partySeats = calculateSeats(p);
			int partyRemainder = calculateRemainder(p);
			p.setRemainder(partyRemainder);
			p.addSeats(partySeats); //Don't distribute the seats yet, just update the number so that we only distribute once at the end
			updateSeatsRemaining(partySeats);
		}
		//Checks for the need to distribute seats based on remainders
		if (seatsRemaining > 0) {
			Comparator<Party> compareByRemainder = (Party p1, Party p2) -> p1.getRemainder().compareTo(p2.getRemainder());
			Collections.sort(participatingParties, compareByRemainder);
			int distributionCounter = 0; //Counter for keeping track of which party should be distributed to
			while (seatsRemaining > 0) {
				participatingParties.get(distributionCounter).addSeats(1);
				distributionCounter += 1;
				updateSeatsRemaining(1);
			}
		}
		//Now, we can actually distribute the seats, since we will have a total number of seats to distribute, including remainder allocated seats
		for (Party p: participatingParties) {
			int seatsAllocated = p.getSeatsAllocated();
			distributeSeats(seatsAllocated, p);
		}
		
		writeMediaFile();
		displayResultsToTerminal();
	}
	
	private int calculateSeats(Party p) {
		int partyVotes = p.getTotalPartyVote();
		if (partyVotes <  quota) {
			return 0;
		}
		else {
			return partyVotes / quota;
		}
	}
	
	private int calculateRemainder(Party p) {
		int partyVotes = p.getTotalPartyVote();
		if (partyVotes < quota) {
			return partyVotes;
		}
		else {
			return partyVotes % quota;
		}
	}
	
	private void updateSeatsRemaining(int seatsToSubtract) {
		seatsRemaining -= seatsToSubtract;
	}
	
	private void determineQuota() {
		quota = numBallots / seats;
	}
	
	private void distributeSeats(int numSeats, Party p) {
		p.sortPartyMembersByVote();
		List<Candidate> sortedList = p.getPartyMembers();
		for (int i = 0; i < numSeats; i++) {
			if(sortedList.size() > (i + 1) && sortedList.get(i).getVoteCount() == sortedList.get(i + 1).getVoteCount()) {
				Candidate tieWinner = breakTie(sortedList.get(i), sortedList.get(i+1));
				seatedCandidates.add(tieWinner);
			}
			else {
				seatedCandidates.add(sortedList.get(i));
			}
		}
		p.addSeats(numSeats);
	}
}
