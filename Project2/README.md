# Team 21 Voting System

## Important notes:
```
OUR MAIN CLASS IS: ElectionDriver, located in ElectionDriver.java

In order to run from the command line, one would do:

javac Ballot.java Party.java Election.java Candidate.java ElectionDriver.java OpenPartyListingElection.java InstantRunoffElection.java PopularityOnlyElection.java
from within election directory
then from within the src directory
java election/ElectionDriver {paths to files to run}

EXAMPLE: java election/ElectionDriver /election.files/comebackIR.csv

We also ran this program a lot through Eclipse. In order to do this, you can right click ElectionDriver.java > Run as > Run configurations > Make sure you have ElectionDriver selected, and then at the top there will be an option to insert arguments to the class. You have to pass it the file path, and since we stored our files we used in election.files as well as testing, you can just input ../election.files{fileName} for each file you want to run, if you prefer to do it this way. This is how we have been running our project mostly and it is proven to work.

In order to run our tests, you can simply right click the .java files containing the tests within Eclipse, then go to Run as... > JUnit

IMPORTANT NOTE ABOUT OUR TEST LOG: We felt it pertient to maintain almost all of our tests from the previous project iteration, so we appended the new individual test logs onto the bottom of the testinglogs.pdf file contained within the testing folder. It is clearly marked "Project Portion 2 New Tests" and contains further notes. We apologize for the document being 97 pages long, but we did quite a few tests and had quite a few templates to fill out. 

Also, some testing files are in a separate location from the others. Some tests are in src/election/tests, while some (e.g. OPLTest.java, TiebreakTest.java, IRElectionUnitTest.java, and InputHelperTest.java) are in the src/election folder. There is a method to this madness, but we apologize for any inconvenience nonetheless.

However you decided to run the program, it is already documented that it runs, as evidenced by all of our PBIs being approved in the Sprint Review Meeting with Team 22.
```
