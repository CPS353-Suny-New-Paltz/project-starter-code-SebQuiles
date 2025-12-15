package project.checkpointtests;

import conceptual.api.NumberToWordsAPI;
import conceptual.impl.NumberToWordsAPIImpl;
import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import process.api.StorageEngineAPI;
import process.impl.StorageEngineAPIImpl;
import network.impl.UserComputeAPIImpl;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {
        try {
            // TODO 1: instantiate real implementations of all 3 APIs
            StorageEngineAPI storage = new StorageEngineAPIImpl();
            NumberToWordsAPI converter = new NumberToWordsAPIImpl();
            UserComputeAPI api = new UserComputeAPIImpl(storage, converter);

            // TODO 2: run computation using INPUT/OUTPUT and delimiter ','
            UserJobRequest request = new UserJobRequest() {
                @Override
                public String getInputSource() {
                    return INPUT;
                }

                @Override
                public String getOutputDestination() {
                    return OUTPUT;
                }

                @Override
                public Character getPairSeparator() {
                    return ','; // Checkpoint4TestSuite splits by comma
                }

                @Override
                public Character getKvSeparator() {
                    return '='; // matches your README examples
                }
            };

            api.submitJob(request);

        } catch (Throwable t) {
            // don't rethrow; just report
            String msg = t.getMessage();
            if (msg == null || msg.isBlank()) {
                msg = t.getClass().getSimpleName();
            }
            System.err.println("ManualTestingFramework failed: " + msg);
        }
    }
}
