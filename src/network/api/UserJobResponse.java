package network.api;

import shared.stuff.JobStatus;

/**
 * Response contract for job submission over the Network API.
 */
public interface UserJobResponse {
    JobStatus getStatus();  // ACCEPTED/REJECTED/PENDING/...
    String getJobID();
    String getMessage();
}
