#Team 21 Voting System

```
Early on, we realized that if the file you are attempting to use is in
a different directory, you might have to pass in the file path to start
the program. For example, we have our test files stored in an election.files directory inside of src, and our test code is inside of src/election/tests, so when we call the tests we use a line like:

String path = IRElectionTest.class.getResource("/election.files/fixedWinnerIR.csv").getPath();

And then pass that path to create our Scanner object that drives the program. Since it says the files will be in the same directory, this likely won't be an issue, but we just wanted to make you aware just in case (we promise the program works, we've put so many hours into this). 
```