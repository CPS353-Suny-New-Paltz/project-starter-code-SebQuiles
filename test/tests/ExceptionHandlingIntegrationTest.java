package tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import conceptual.impl.NumberToWordsAPIImpl;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import network.impl.UserComputeAPIImpl;
import process.impl.StorageEngineAPIImpl;
import shared.stuff.JobStatus;

public class ExceptionHandlingIntegrationTest {

    @Test
    public void submitJob_whenStorageWriteThrows_returnsErrorResponseInsteadOfThrowing() throws Exception {
        // real components (integration)
        StorageEngineAPIImpl storage = new StorageEngineAPIImpl();
        NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
        UserComputeAPIImpl api = new UserComputeAPIImpl(storage, converter);

        // input file exists with some integers
        Path inputFile = Files.createTempFile("cp5-input", ".txt");
        Files.writeString(inputFile, "1\n2\n");

        // output is intentionally a DIRECTORY to trigger IOException in writeResults(...)
        Path outputDir = Files.createTempDirectory("cp5-output-dir");

        UserJobRequest request = new UserJobRequest() {
            @Override public String getInputSource() { return inputFile.toString(); }
            @Override public String getOutputDestination() { return outputDir.toString(); }
            @Override public Character getPairSeparator() { return ','; }
            @Override public Character getKvSeparator() { return '='; }
        };

        // key assertion: the call should NOT throw, even though storage fails
        UserJobResponse response = assertDoesNotThrow(() -> api.submitJob(request));

        assertNotNull(response);

        // These method names might be getStatus()/getMessage() depending on your interface.
        // If yours differ, just rename these two lines to match.
        assertEquals(JobStatus.REJECTED, response.getStatus());
        assertTrue(response.getMessage().toLowerCase().contains("failed"));
    }
}
