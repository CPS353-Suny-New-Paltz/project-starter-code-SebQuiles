package network.api;

import project.annotations.NetworkAPI;

/**
 * Network API between the User and the Compute Engine.
 * The user submits jobs through this API.
 */
@NetworkAPI
public interface UserComputeAPI {
    UserJobResponse submitJob(UserJobRequest request); // User sends a job request
}
