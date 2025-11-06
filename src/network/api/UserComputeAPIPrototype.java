package network.api;

import project.annotations.NetworkAPIPrototype;
import shared.stuff.JobStatus;
/**
 * Prototype Network API: always accepts and returns a stubbed response.
 */
public class UserComputeAPIPrototype implements UserComputeAPI {

    @Override
    public UserJobResponse submitJob(UserJobRequest request) {
        // Real version would validate request, enqueue work, etc.
        return new UserJobResponsePrototype(
            JobStatus.ACCEPTED,
            "proto-123",
            "Prototype accepted"
        );
    }
    @NetworkAPIPrototype
    public void networkAPIPrototype(UserComputeAPI api) {
        UserJobRequest req = new UserJobRequest(/* minimal valid args */);
        UserJobResponse resp = api.submitJob(req);
        
    }
}
}