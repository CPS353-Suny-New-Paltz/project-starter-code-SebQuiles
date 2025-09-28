package process.impl;

import process.api.StorageEngineAPI;
import project.annotations.ProcessAPIPrototype;
import java.util.Arrays;
import java.util.List;

/**
 * Prototype storage: returns a fixed set of integers and ignores writes.
 */
public class StorageEngineAPIPrototype implements StorageEngineAPI {

    @Override
    @ProcessAPIPrototype
    public List<Integer> readIntegers(String inputSourceId) {
        return Arrays.asList(6, 12, 21);
    }

    @Override
    @ProcessAPIPrototype
    public void writeResults(String outputDestinationId, List<String> formattedPairs) {
        // Prototype: no-op
    }
}
