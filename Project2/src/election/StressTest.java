package election;

import java.io.FileNotFoundException;
import org.junit.Test;

public class StressTest {
    @Test
    public void OPLStressTest() throws FileNotFoundException {
        String path = "/election.files/hundredk_ballots_opl.csv";
        String[] args = new String[10];
        for (int i = 0; i < 10; ++i) {
            args[i] = path;
        }

        ElectionDriver.main(args);
    }

    @Test
    public void IRStressTest() throws FileNotFoundException {
        String path = "/election.files/hundredk_ballots_ir.csv";
        String[] args = new String[10];
        for (int i = 0; i < 10; ++i) {
            args[i] = path;
        }

        ElectionDriver.main(args);
    }
}

