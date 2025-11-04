package network.impl;

import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import project.annotations.NetworkAPIPrototype;
import shared.stuff.JobStatus;

/**
 * Prototype Network API: always accepts and returns a stubbed response.
 */
public class UserComputeAPIPrototype implements UserComputeAPI {

    @Override
    @NetworkAPIPrototype
    public UserJobResponse submitJob(UserJobRequest request) {
        // Real version would validate request, enqueue work, etc.
        return new ProtoUserJobResponse(
            JobStatus.ACCEPTED,
            "proto-123",
            "Prototype accepted"
        );
    }
}
