package election;

/**
 * Class to represent an individual candidate in an election. 
 * @author Jack Soderwall (soder365)
 *
 */
public class Candidate implements Comparable < Candidate >{
	private String name;
	private Party party;
	private Integer voteCount;
	
	/**
	 * The constructor for the Candidate class. Creates a candidate with the given name and party.
	 * @param name: The name of the candidate to be created
	 * @param party: The Party that the candidate is affiliated with
	 */
	
	public Candidate(String name, Party party) {
		this.name = name;
		this.party = party;
		voteCount = 0;
	}
	
	/**
	 * A getter method to retrieve the name of the Candidate object.
	 * @return name: The name of the Candidate.
	 * Used in various processes throughout the course of an election.
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * A getter method to retrieve the Party that the Candidate object is affiliated with.
	 * @return party: The party affiliation of the Candidate object.
	 * Used in various methods throughout the election process.
	 */
	
	public Party getParty() {
		return party;
	}
	
	/**
	 * A getter method to retrieve the voteCount of the Candidate object.
	 * @return voteCount: The number of votes the Candidate has received in the election.
	 * Used in various methods throughout the election process.
	 */
	
	public Integer getVoteCount() {
		return voteCount;
	}
	
	/**
	 * A method to increment the voteCount of the candidate by 1.
	 */
	
	public void incrementVoteCount() {
		voteCount += 1;
	}
	
	/**
	 * A function that somewhat overrides the normal Java compareTo function in order to be able to compare
	 * Candidates based on their vote counts. 
	 */
	public int compareTo(Candidate o) {
		return this.getVoteCount().compareTo(o.getVoteCount());
	}
	
	/**
	 * An override of the normal toString() method in Java that specifies how Candidates should be represented
	 * when they are printed to the screen.
	 */
	
	public String toString() {
		return name + " Votes: " + voteCount;
	}
}
