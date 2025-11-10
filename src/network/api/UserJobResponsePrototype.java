package network.api;

import shared.stuff.JobStatus;

/**
 * Prototype DTO implementation for UserJobResponse.
 */
public class UserJobResponsePrototype implements UserJobResponse {

    private final JobStatus status;
    private final String jobId;
    private final String message;
    
    public UserJobResponsePrototype(JobStatus status, String jobId, String message) {
        this.status = status;
        this.jobId = jobId;
        this.message = message;
    }

    @Override
    public JobStatus getStatus() {
        return status;
    }

    @Override
    public String getJobID() {
        return jobId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
