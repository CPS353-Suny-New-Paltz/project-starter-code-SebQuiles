package examples;

import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import network.impl.UserComputeAPIImpl;
import network.impl.UserJobRequestPrototype;
import conceptual.api.NumberToWordsAPI;
import conceptual.impl.JobOrchestratorPrototype;    
import conceptual.impl.NumberToWordsAPIImpl;   
import process.api.StorageEngineAPI;
import process.impl.StorageEngineAPIImpl;      

import shared.stuff.JobStatus;


public class Checkpoint2DemoRun {
    public static void main(String[] args) throws Exception {
        
    	//Build dependencies for Network API and Orchestrator
    	StorageEngineAPI storage = new StorageEngineAPIImpl();
    	NumberToWordsAPI converter = new NumberToWordsAPIImpl();
    	
    	// Network API demo
    	UserComputeAPI api = new UserComputeAPIImpl(storage, converter); 
        UserJobRequest req = new UserJobRequestPrototype("file://fake-input", "file://fake-output", ';', ':');
        UserJobResponse resp = api.submitJob(req);
        System.out.println("submitJob: status=" + resp.getStatus()
                + " jobId=" + resp.getJobID()
                + " message=" + resp.getMessage());

        // Orchestrator demo 
        JobOrchestratorPrototype orchestrator = new JobOrchestratorPrototype(storage, converter);

        String out = orchestrator.run("file://fake-input", "file://fake-output", ';', ':');
        System.out.println("orchestrator: " + out);
    }
}