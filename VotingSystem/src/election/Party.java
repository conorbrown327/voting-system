package election;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent an individual Party in an election. This class will be
 * used by multiple other methods throughout the course of running an election.
 * 
 * @author Jack Soderwall (soder365)
 *
 */
public class Party {
	private String partyName;
	private int totalPartyVotes;
	private List<Candidate> affiliatedPartyMembers; // new ArrayList<Candidate>();
	private int seatsAllocated;
	private Integer remainder;

	public Party(String partyName) {
		this.partyName = partyName;
		totalPartyVotes = 0;
		affiliatedPartyMembers = new ArrayList<Candidate>();
		seatsAllocated = 0;
		remainder = 0;
	}

	/**
	 * Function that retrieves the party's name.
	 * 
	 * @return partyName: The name of the party.
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * Function that retrieves the party's overall votes.
	 * 
	 * @return totalPartyVotes: The total number of votes a party has received.
	 */
	public int getTotalPartyVote() {
		return totalPartyVotes;
	}

	/**
	 * Function that increments the party's total votes by one.
	 */
	public void incrementTotalPartyVote() {
		totalPartyVotes += 1;
	}

	/**
	 * Function that adds a candidate to the list of affiliated party members.
	 * 
	 * @param c The Candidate object to be added to the party member list.
	 */
	public void addCandidate(Candidate candidate) {
		affiliatedPartyMembers.add(candidate);
	}

	/**
	 * Function that adds a set number of seats to the party.
	 * 
	 * @param seats The number of seats to be added to the party's total seat
	 *              allocation.
	 */
	public void addSeats(int seats) {
		seatsAllocated += seats;
	}

	/**
	 * Function that sets the remainder of votes after initial seat allocation to a
	 * party.
	 * 
	 * @param remainder The remainder left after initial seat allocation.
	 */
	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}

	/**
	 * Function that retrieves the list of all affiliated party members.
	 * 
	 * @return affiliatedPartyMembers: The list of all Candidates in the party.
	 */
	public List<Candidate> getPartyMembers() {
		return affiliatedPartyMembers;
	}

	/**
	 * Function that retrieves the remainder of the party.
	 * 
	 * @return remainder: the remainder of the vote after initial allocation of
	 *         seats.
	 */
	public Integer getRemainder() {
		return remainder;
	}

	/**
	 * Function that retrieves the total number of seats that a party has been
	 * allocated.
	 * 
	 * @return seatsAllocated: the total number of seats that a party has been
	 *         allocated.
	 */
	public int getSeatsAllocated() {
		return seatsAllocated;
	}

	/**
	 * Function that sorts the affiliated party members by votes received.
	 */
	public void sortPartyMembersByVote() {
		Collections.sort(affiliatedPartyMembers, Collections.reverseOrder());
	}
}
