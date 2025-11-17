package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import conceptual.impl.JobOrchestratorPrototype;
import conceptual.impl.NumberToWordsAPIImpl;
import testimpl.InMemoryInputConfig;
import testimpl.InMemoryOutputConfig;
import testimpl.InMemoryStorageEngine;

@Tag("integration")
public class ComputeEngineIntegrationTest {

    @Test
    void endToEnd_computeEngine_writesExpectedFormattedOutput() throws Exception {
        // Prepare in-memory test configs and storage
        InMemoryInputConfig in = new InMemoryInputConfig("in1", Arrays.asList(1, 10, 25));
        InMemoryOutputConfig out = new InMemoryOutputConfig("out1");
        InMemoryStorageEngine storage = new InMemoryStorageEngine(Arrays.asList(in), Arrays.asList(out));

        // Use the empty NumberToWordsAPI implementation (from step 2) and the orchestrator
        NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
        JobOrchestratorPrototype orchestrator = new JobOrchestratorPrototype(storage, converter);

        // Run the orchestrator with no delimiters (null -> defaults should be used)
        String result = orchestrator.run("in1", "out1", null, null);

        // Expected eventual behavior (what the compute engine will compute once implemented)
        List<String> expectedPairs = Arrays.asList(
            "1:one",
            "10:ten",
            "25:twenty-five"
        );
        String expectedCombined = String.join(";", expectedPairs);

        // Validate the string result and the stored output are consistent with expected final behavior.
        // NOTE: Because NumberToWordsAPIImpl is not implemented yet, this assertion is expected to fail until
        // the converter is implemented. That's intentional for this integration test.
        assertEquals(expectedCombined, result, "Combined formatted string should match eventual implementation");

        List<String> written = out.getOutput();
        assertEquals(expectedPairs.size(), written.size(), "Output list size matches expected");
        assertEquals(expectedPairs, written, "Written pairs should match eventual implementation");
    }
}
