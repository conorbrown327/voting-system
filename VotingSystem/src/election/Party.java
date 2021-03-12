package election;
import java.util.*;

/**
 * A class to represent an individual Party in an election. This class will be used by multiple other methods
 * throughout the course of running an election.
 * 
 * @author Jack Soderwall (soder365)
 *
 */
public class Party {
	private String partyName;
	private int totalPartyVotes;
	private List<Candidate> affiliatedPartyMembers; //new ArrayList<Candidate>();
	private int seatsAllocated;
	private Integer remainder;
	
	public Party(String partyName) {
		this.partyName = partyName;
		totalPartyVotes = 0;
		affiliatedPartyMembers = new ArrayList<Candidate>();
		seatsAllocated = 0;
		remainder = 0;
	}
	
	public String getPartyName() {
		return partyName;
	}
	
	public int getTotalPartyVote() {
		return totalPartyVotes;
	}
	
	public void incrementTotalPartyVote() {
		totalPartyVotes += 1;
	}
	
	public void addCandidate(Candidate c) {
		affiliatedPartyMembers.add(c);
	}
	
	public void addSeats(int seats) {
		seatsAllocated += seats;
	}
	
	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}
	
	public List<Candidate> getPartyMembers(){
		return affiliatedPartyMembers;
	}
	
	public Integer getRemainder() {
		return remainder;
	}
	
	public int getSeatsAllocated() {
		return seatsAllocated;
	}
	
	public void sortPartyMembersByVote() {
		Collections.sort(affiliatedPartyMembers, Collections.reverseOrder());
	}
}
