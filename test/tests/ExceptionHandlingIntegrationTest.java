package tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void submitJob_whenStorageWriteWouldFail_doesNotThrowAndReturnsAccepted() throws Exception {
        // real components (integration)
        StorageEngineAPIImpl storage = new StorageEngineAPIImpl();
        NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
        UserComputeAPIImpl api = new UserComputeAPIImpl(storage, converter);

        // input file exists with some integers
        Path inputFile = Files.createTempFile("cp5-input", ".txt");
        Files.writeString(inputFile, "1\n2\n");

        // output is intentionally a DIRECTORY; StorageEngineAPIImpl treats this as a no-op,
        // and by boundary design it does not throw.
        Path outputDir = Files.createTempDirectory("cp5-output-dir");

        UserJobRequest request = new UserJobRequest(){
            @Override
            public String getInputSource(){
                return inputFile.toString();
            }

            @Override
            public String getOutputDestination(){
                return outputDir.toString();
            }

            @Override
            public Character getPairSeparator(){
                return ',';
            }

            @Override
            public Character getKvSeparator(){
                return '=';
            }
        };

        // key assertion: the call should NOT throw, even though the chosen destination
        // cannot be written as a file by this storage implementation.
        UserJobResponse response = assertDoesNotThrow(() -> api.submitJob(request));

        assertNotNull(response);

        // With the current StorageEngineAPIImpl, writeResults silently no-ops for
        // directory destinations, so from the network layer's perspective the job
        // completes successfully.
        assertEquals(JobStatus.ACCEPTED, response.getStatus());
    }
}