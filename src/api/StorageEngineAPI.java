package api;

import java.util.List;
import project.annotations.ProcessAPI;

/**
 * Process API between the Compute Engine and the Data Storage system.
 * Handles reading input integers and writing formatted results.
 */
@ProcessAPI
public interface StorageEngineAPI {
    List<Integer> readIntegers(String inputSourceId) throws Exception; // Read integers from a source
    void writeResults(String outputDestinationId, List<String> formattedPairs) throws Exception; // Write results
}
