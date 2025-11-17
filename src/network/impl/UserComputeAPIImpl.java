package network.impl;

import conceptual.api.NumberToWordsAPI;
import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import process.api.StorageEngineAPI;
import shared.stuff.JobStatus;
/**
 * Prototype Network API: always accepts and returns a stubbed response.
 */
public class UserComputeAPIImpl implements UserComputeAPI {
	
	private final StorageEngineAPI storage; 
	private final NumberToWordsAPI converter; 
	
	public UserComputeAPIImpl(StorageEngineAPI storage, NumberToWordsAPI converter) {
		this.storage = storage;
		this.converter = converter;
	}
	
    @Override
    public UserJobResponse submitJob(UserJobRequest request) {
        // Real version would validate request, enqueue work, etc.
        return new UserJobResponsePrototype(
            JobStatus.ACCEPTED,
            "proto-123",
            "Prototype accepted"
        );
    }
}