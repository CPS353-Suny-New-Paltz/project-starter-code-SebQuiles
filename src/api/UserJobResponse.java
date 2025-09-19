package api;

public interface UserJobResponse {
	boolean isAccepted(); // true if the job request was accepted 
	String getJobID(); // ID for tracking this job
	String getMessage(); //Extra info about acceptance or error
}
