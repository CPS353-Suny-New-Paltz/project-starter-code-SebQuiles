package testimpl;

import process.api.StorageEngineAPI;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Test-only in-memory StorageEngine implementation. It maps input/output ids to the
 * in-memory configs created in tests.
 */
public class InMemoryStorageEngine implements StorageEngineAPI {
    private final Map<String, List<Integer>> inputs = new HashMap<>();
    private final Map<String, List<String>> outputs = new HashMap<>();

    public InMemoryStorageEngine(List<InMemoryInputConfig> inputConfigs, List<InMemoryOutputConfig> outputConfigs) {
        if (inputConfigs != null) {
            for (InMemoryInputConfig c : inputConfigs) {
                inputs.put(c.getId(), new ArrayList<>(c.getInput()));
            }
        }
        if (outputConfigs != null) {
            for (InMemoryOutputConfig c : outputConfigs) {
                outputs.put(c.getId(), c.getOutput());
            }
        }
    }

    @Override
    public List<Integer> readIntegers(String inputSourceId) throws Exception {
        List<Integer> list = inputs.get(inputSourceId);
        if (list == null) {
            throw new Exception("No input found for id: " + inputSourceId);
        }
        return new ArrayList<>(list); // return a copy
    }

    @Override
    public void writeResults(String outputDestinationId, List<String> formattedPairs) throws Exception {
        List<String> out = outputs.get(outputDestinationId);
        if (out == null) {
            throw new Exception("No output found for id: " + outputDestinationId);
        }
        out.addAll(formattedPairs);
    }
}
