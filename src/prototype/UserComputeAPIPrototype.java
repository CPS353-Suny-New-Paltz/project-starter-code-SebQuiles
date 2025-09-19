package prototype;

import api.UserComputeAPI;
import api.UserJobRequest;
import api.UserJobResponse;
import project.annotations.NetworkAPIPrototype;

/**
 * Prototype implementation of the UserComputeAPI.
 * Always accepts jobs and returns a stubbed response.
 */
public class UserComputeAPIPrototype implements UserComputeAPI {

    @Override
    @NetworkAPIPrototype
    public UserJobResponse submitJob(UserJobRequest request) {
        // For now, just accept everything and return a fake jobId
        return new ProtoUserJobResponse(true, "proto-123", "Prototype accepted");
    }

    /**
     * Simple prototype version of a JobRequest.
     */
    public static class ProtoUserJobRequest implements UserJobRequest {
        private final String inputSource;
        private final String outputDestination;
        private final Character pairSeparator;
        private final Character kvSeparator;

        public ProtoUserJobRequest(String inputSource, String outputDestination,
                Character pairSeparator, Character kvSeparator) {
            this.inputSource = inputSource;
            this.outputDestination = outputDestination;
            this.pairSeparator = pairSeparator;
            this.kvSeparator = kvSeparator;
        }

        @Override
        public String getInputSource() {
            return inputSource;
        }

        @Override
        public String getOutputDestination() {
            return outputDestination;
        }

        @Override
        public Character getPairSeparator() {
            return pairSeparator;
        }

        @Override
        public Character getKvSeparator() {
            return kvSeparator;
        }
    }

    /**
     * Simple prototype version of a JobResponse.
     */
    public static class ProtoUserJobResponse implements UserJobResponse {
        private final boolean accepted;
        private final String jobId;
        private final String message;

        public ProtoUserJobResponse(boolean accepted, String jobId, String message) {
            this.accepted = accepted;
            this.jobId = jobId;
            this.message = message;
        }

        @Override
        public boolean isAccepted() {
            return accepted;
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
}
