package election;

public class Candidate implements Comparable < Candidate >{
	private String name;
	private Party party;
	private Integer voteCount;
	
	public Candidate(String name, Party party) {
		this.name = name;
		this.party = party;
		voteCount = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public Party getParty() {
		return party;
	}
	
	public Integer getVoteCount() {
		return voteCount;
	}
	
	public void incrementVoteCount() {
		voteCount += 1;
	}
	
	public int compareTo(Candidate o) {
		return this.getVoteCount().compareTo(o.getVoteCount());
	}
	
	public String toString() {
		return name + " Votes: " + voteCount;
	}
}
