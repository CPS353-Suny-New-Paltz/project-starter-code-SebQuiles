package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import conceptual.api.NumberToWordsAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import network.impl.UserComputeAPIImpl;
import process.api.StorageEngineAPI;
import shared.stuff.JobStatus;

public class TestUserComputeValidation {

    @Test
    public void submitJob_rejectsNullRequest() {
        StorageEngineAPI storage = Mockito.mock(StorageEngineAPI.class);
        NumberToWordsAPI converter = Mockito.mock(NumberToWordsAPI.class);

        UserComputeAPIImpl api = new UserComputeAPIImpl(storage, converter);

        UserJobResponse resp = api.submitJob(null);

        assertNotNull(resp);
        assertEquals(JobStatus.REJECTED, extractStatus(resp));
    }

    @Test
    public void submitJob_rejectsBlankInputSource() {
        StorageEngineAPI storage = Mockito.mock(StorageEngineAPI.class);
        NumberToWordsAPI converter = Mockito.mock(NumberToWordsAPI.class);

        UserComputeAPIImpl api = new UserComputeAPIImpl(storage, converter);

        UserJobRequest req = new UserJobRequest() {
            @Override public String getInputSource() { 
                return "   "; 
            }
            @Override public String getOutputDestination() { 
                return "out.txt"; 
            }
            @Override public Character getPairSeparator() { 
                return ','; 
            }
            @Override public Character getKvSeparator() { 
                return '='; 
            }
        };

        UserJobResponse resp = api.submitJob(req);

        assertNotNull(resp);
        assertEquals(JobStatus.REJECTED, extractStatus(resp));
    }

    // --- helper: supports either getStatus() or getJobStatus() without guessing your interface ---
    private JobStatus extractStatus(UserJobResponse resp) {
        try {
            Method m = resp.getClass().getMethod("getStatus");
            return (JobStatus) m.invoke(resp);
        } catch (Exception ignored) { 
              
        }

        try {
            Method m = resp.getClass().getMethod("getJobStatus");
            return (JobStatus) m.invoke(resp);
        } catch (Exception ignored) { 

        }

        fail("Could not find status getter on UserJobResponse (expected getStatus() or getJobStatus()).");
        return null; // unreachable
    }
}

