/**
 * Candidate.java

 * @author Conor Brown, Jack Soderwall, Joe Cassidy, Sean Carter
 * 
 * Candidate stores info about each candidate read in from the ballots file.
 * This includes their votes and party affiliation.
 */
 
package election;

public class Candidate implements Comparable<Candidate> {
	private String name;
	private Party party;
	private Integer voteCount;

	/**
	 * The constructor for the Candidate class. Creates a candidate with the given
	 * name and party.
	 * 
	 * @param name:  The name of the candidate to be created
	 * @param party: The Party that the candidate is affiliated with
	 */

	public Candidate(String name, Party party) {
		this.name = name;
		this.party = party;
		voteCount = 0;
	}

	/**
	 * A getter method to retrieve the name of the Candidate object.
	 * 
	 * @return name: The name of the Candidate. Used in various processes throughout
	 *         the course of an election.
	 */

	public String getName() {
		return name;
	}

	/**
	 * A getter method to retrieve the Party that the Candidate object is affiliated
	 * with.
	 * 
	 * @return party: The party affiliation of the Candidate object. Used in various
	 *         methods throughout the election process.
	 */

	public Party getParty() {
		return party;
	}

	/**
	 * A getter method to retrieve the voteCount of the Candidate object.
	 * 
	 * @return voteCount: The number of votes the Candidate has received in the
	 *         election. Used in various methods throughout the election process.
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
	 * Increments the number of votes by numVotes.
	 * 
	 * @param numVotes Number of votes to increment the candidate's vote count by.
	 */
	public void incrementVoteCount(int numVotes) {
		voteCount += numVotes;
	}

	/**
	 * A function that somewhat overrides the normal Java compareTo function in
	 * order to be able to compare Candidates based on their vote counts.
	 */
	@Override
	public int compareTo(Candidate o) {
		return this.getVoteCount().compareTo(o.getVoteCount()) * -1;
	}

	/**
	 * An override of the normal toString() method in Java that specifies how
	 * Candidates should be represented when they are printed to the screen.
	 */

	@Override
	public String toString() {
		return name + " Votes: " + voteCount;
	}

	/**
	 * Function that sets the party for a given Candidate.
	 * 
	 * @param party The party to assign the Candidate to.
	 */
	public void setParty(Party party) {
		this.party = party;
	}
}
