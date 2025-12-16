package network.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import conceptual.api.NumberToWordsAPI;
import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import process.api.StorageEngineAPI;
import shared.stuff.JobStatus;

/**
 * Multi-threaded implementation of {@link UserComputeAPI}.
 *
 * This class parallelizes the conceptual NumberToWordsAPI calls using a fixed-size
 * thread pool while preserving input ordering in the final output.
 *
 * Thread count policy:
 * - We cap the pool size to MAX_THREADS to avoid oversubscription.
 * - Effective pool size = min(requestedThreads, MAX_THREADS, availableProcessors).
 */
public class MultiThreadedUserComputeAPIImpl implements UserComputeAPI {

    // Upper bound to avoid creating too many threads on small machines.
    public static final int MAX_THREADS = 8; // Documented in README as required.

    private final StorageEngineAPI storage;
    private final NumberToWordsAPI converter;
    private final ExecutorService executor;

    public MultiThreadedUserComputeAPIImpl(StorageEngineAPI storage,
                                           NumberToWordsAPI converter) {
        this(storage, converter, Runtime.getRuntime().availableProcessors());
    }

    public MultiThreadedUserComputeAPIImpl(StorageEngineAPI storage,
                                           NumberToWordsAPI converter,
                                           int requestedThreads) {
        if (storage == null) {
            throw new IllegalArgumentException("storage must not be null");
        }
        if (converter == null) {
            throw new IllegalArgumentException("converter must not be null");
        }
        int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
        int poolSize = Math.max(1, Math.min(Math.min(requestedThreads, MAX_THREADS), cores));

        this.storage = storage;
        this.converter = converter;
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    /**
     * Allows tests to shut down the underlying pool and prevent thread leaks.
     */
    public void shutdown() {
        executor.shutdownNow();
    }

    @Override
    public UserJobResponse submitJob(UserJobRequest request) {
        // Mirror the validation and boundary behavior of UserComputeAPIImpl,
        // but parallelize the conceptual computation step.
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

        char pairSep = (request.getPairSeparator() != null) ? request.getPairSeparator() : ',';
        char kvSep = (request.getKvSeparator() != null) ? request.getKvSeparator() : '=';

        String jobId = UUID.randomUUID().toString();

        try {
            List<Integer> inputs = storage.readIntegers(inputSource);

            // No data: behave like the single-threaded implementation.
            if (inputs == null || inputs.isEmpty()) {
                storage.writeResults(outputDestination, List.of(""));
                return new UserJobResponsePrototype(JobStatus.ACCEPTED, jobId, "Wrote 0 result(s)");
            }

            // Build tasks per input index to preserve ordering.
            List<Callable<String>> tasks = new ArrayList<>(inputs.size());
            for (Integer value : inputs) {
                tasks.add(() -> {
                    if (value == null) {
                        return null; // skipped later
                    }
                    String words = converter.toWords(value);
                    return value + String.valueOf(kvSep) + words;
                });
            }

            List<Future<String>> futures = executor.invokeAll(tasks);

            List<String> pairs = new ArrayList<>();
            for (Future<String> f : futures) {
                String pair;
                try {
                    pair = f.get();
                } catch (ExecutionException ex) {
                    // Any worker failure => whole job failed; wrap message in a FAILED response.
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    String msg = cause.getMessage();
                    if (msg == null || msg.isBlank()) {
                        msg = cause.getClass().getSimpleName();
                    }
                    return new UserJobResponsePrototype(JobStatus.FAILED, jobId,
                            "Job failed: " + msg);
                }
                if (pair != null) {
                    pairs.add(pair);
                }
            }

            String oneLine = String.join(String.valueOf(pairSep), pairs);
            storage.writeResults(outputDestination, List.of(oneLine));

            return new UserJobResponsePrototype(
                    JobStatus.ACCEPTED,
                    jobId,
                    "Wrote " + pairs.size() + " result(s)");

        } catch (Throwable t) {
            String msg = t.getMessage();
            if (msg == null || msg.isBlank()) {
                msg = t.getClass().getSimpleName();
            }
            return new UserJobResponsePrototype(JobStatus.FAILED, jobId, "Job failed: " + msg);
        }
    }
}
