package network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import conceptual.api.NumberToWordsAPI;
import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import process.api.StorageEngineAPI;
import shared.stuff.JobStatus;

public class UserComputeAPIImpl implements UserComputeAPI {

    private final StorageEngineAPI storage;
    private final NumberToWordsAPI converter;

    public UserComputeAPIImpl(StorageEngineAPI storage, NumberToWordsAPI converter) {
        if (storage == null) {
            throw new IllegalArgumentException("storage must not be null");
        }
        if (converter == null) {
            throw new IllegalArgumentException("converter must not be null");
        }
        this.storage = storage;
        this.converter = converter;
    }

    @Override
    public UserJobResponse submitJob(UserJobRequest request) {
        // Boundary rule (Checkpoint 5): no exceptions should escape this method.

        if (request == null) {
            return new UserJobResponsePrototype(JobStatus.REJECTED, null, "request must not be null");
        }

        String inputSource = request.getInputSource();
        String outputDestination = request.getOutputDestination();

        if (inputSource == null || inputSource.isBlank()) {
            return new UserJobResponsePrototype(JobStatus.REJECTED, null, "inputSource must not be blank");
        }
        if (outputDestination == null || outputDestination.isBlank()) {
            return new UserJobResponsePrototype(JobStatus.REJECTED, null, "outputDestination must not be blank");
        }

        // For Checkpoint4TestSuite: MUST be comma-separated by default.
        char pairSep = (request.getPairSeparator() != null) ? request.getPairSeparator() : ',';

        // README examples use '=' mostly, so default to that too. may change later.
        char kvSep = (request.getKvSeparator() != null) ? request.getKvSeparator() : '=';

        String jobId = UUID.randomUUID().toString();

        try {
            // 1) Read integers via process API
            List<Integer> inputs = storage.readIntegers(inputSource);

            // 2) Convert + format into key=value pairs
            List<String> pairs = new ArrayList<>();
            for (Integer value : inputs) {
                if (value == null) {
                    continue;
                }

                String words = converter.toWords(value);
                pairs.add(value + String.valueOf(kvSep) + words);
            }

            // 3) Join into ONE line (Checkpoint 4 expects a single comma-separated line)
            String oneLine = String.join(String.valueOf(pairSep), pairs);

            // 4) Write exactly ONE line
            storage.writeResults(outputDestination, List.of(oneLine));

            return new UserJobResponsePrototype(
                    JobStatus.ACCEPTED,
                    jobId,
                    "Wrote " + pairs.size() + " result(s)"
            );

        } catch (Throwable t) {
            // Translate any failure into an explicit FAILED response.
            String msg = t.getMessage();
            if (msg == null || msg.isBlank()) {
                msg = t.getClass().getSimpleName();
            }
            return new UserJobResponsePrototype(JobStatus.FAILED, jobId, "Job failed: " + msg);
        }
    }
}
