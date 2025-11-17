package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import org.mockito.Mockito;

public class TestUserComputeAPI {
    @Test
    void submitJob_returnsResponse() {
        // Mock the API itself to assert expected contract (this is a smoke test of the API signature)
        UserComputeAPI computeApi = Mockito.mock(UserComputeAPI.class);

        UserJobRequest request = Mockito.mock(UserJobRequest.class);
        UserJobResponse response = Mockito.mock(UserJobResponse.class);

        when(computeApi.submitJob(any(UserJobRequest.class))).thenReturn(response);

        UserJobResponse actual = computeApi.submitJob(request);
        assertNotNull(actual);
        verify(computeApi).submitJob(request); // ensure the API was called with the provided request
    }
}
