package process.api;

import project.annotations.ProcessAPI;
import java.util.List;

/**
 * Process API: abstract I/O boundary for reading/writing data.
 */
@ProcessAPI
public interface StorageEngineAPI {
    List<Integer> readIntegers(String inputSourceId);
    List<String> writeResults(String outputDestinationId, List<String> pairs);
}

