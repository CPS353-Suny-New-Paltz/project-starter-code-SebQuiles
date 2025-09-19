package prototype;

import api.StorageEngineAPI;
import project.annotations.ProcessAPIPrototype;
import java.util.Arrays;
import java.util.List;

/**
 * Prototype implementation of StorageEngineAPI.
 * Returns hard-coded integers and ignores write requests for now.
 */
public class StorageEngineAPIPrototype implements StorageEngineAPI {

    @Override
    @ProcessAPIPrototype
    public List<Integer> readIntegers(String inputSourceId) {
        // Stub: return a fixed list of numbers instead of reading a real file/db
        return Arrays.asList(6, 12, 21);
    }

    @Override
    @ProcessAPIPrototype
    public void writeResults(String outputDestinationId, List<String> formattedPairs) {
        // Stub: do nothing for now (pretend we wrote to output)
    }
}
