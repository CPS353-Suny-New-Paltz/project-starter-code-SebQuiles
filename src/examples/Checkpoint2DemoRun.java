package examples;

import network.api.UserJobRequestPrototype;
import network.api.UserComputeAPI;
import network.api.UserComputeAPIPrototype;
import network.api.UserJobRequest;
import network.api.UserJobResponse;
import conceptual.impl.JobOrchestratorPrototype;    
import conceptual.impl.NumberToWordsAPIPrototype;   
import process.api.StorageEngineAPIPrototype;      

import shared.stuff.JobStatus;


public class Checkpoint2DemoRun {
    public static void main(String[] args) throws Exception {
        // Network API demo
        UserComputeAPI api = new UserComputeAPIPrototype();
        UserJobRequest req = new UserJobRequestPrototype("file://fake-input", "file://fake-output", ';', ':');
        UserJobResponse resp = api.submitJob(req);
        System.out.println("submitJob: status=" + resp.getStatus()
                + " jobId=" + resp.getJobID()
                + " message=" + resp.getMessage());

        // Orchestrator demo 
        StorageEngineAPIPrototype storage = new StorageEngineAPIPrototype();
        NumberToWordsAPIPrototype converter = new NumberToWordsAPIPrototype();
        JobOrchestratorPrototype orchestrator = new JobOrchestratorPrototype(storage, converter);

        String out = orchestrator.run("file://fake-input", "file://fake-output", ';', ':');
        System.out.println("orchestrator: " + out);
    }
}
