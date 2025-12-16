package testHarness;

import java.io.File;

import network.api.UserComputeAPI;
import network.api.UserJobRequest;
import network.api.UserJobResponse;

public class TestUser {
	
	// TODO 3: change the type of this variable to the name you're using for your
	// @NetworkAPI interface; also update the parameter passed to the constructor
	private final UserComputeAPI coordinator;

	public TestUser(UserComputeAPI coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";
		
		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter
		UserJobRequest request = new UserJobRequest() {
			@Override
			public String getInputSource() {
				return inputPath;
			}

			@Override
			public String getOutputDestination() {
				return outputPath;
			}

			@Override
			public Character getPairSeparator() {
				return delimiter;
			}

			@Override
			public Character getKvSeparator() {
				return '=';
			}
		};
		UserJobResponse response = coordinator.submitJob(request);
	}

}