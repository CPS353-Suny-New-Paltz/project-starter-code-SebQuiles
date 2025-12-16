package testharness;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import conceptual.impl.NumberToWordsAPIImpl;
import network.api.UserComputeAPI;
import network.impl.MultiThreadedUserComputeAPIImpl;
import network.impl.UserComputeAPIImpl;
import process.impl.StorageEngineAPIImpl;

public class TestMultiUser {
	
	// TODO 1: change the type of this variable to the name you're using for your @NetworkAPI
	// interface
	private UserComputeAPI coordinator;
	private MultiThreadedUserComputeAPIImpl networkAPI;
	
	@BeforeEach
	public void initializeComputeEngine() {
		StorageEngineAPIImpl storage = new StorageEngineAPIImpl();
		NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
		// multi-threaded implementation used by this test
		networkAPI = new MultiThreadedUserComputeAPIImpl(storage, converter);
		//TODO 2: create an instance of the implementation of your @NetworkAPI; this is the component
		// that the user will make requests to
		// Store it in the 'coordinator' instance variable
		coordinator = networkAPI;
	}
	
	@AfterEach
	public void cleanup() {
        if (networkAPI != null) {
            networkAPI.shutdown();
        }
    }
	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int numThreads = 4;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}
		
		// Run single threaded using the existing single-threaded implementation
		String singleThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.singleThreadOut.tmp";
		StorageEngineAPIImpl storage = new StorageEngineAPIImpl();
		NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
		UserComputeAPI singleThreadApi = new UserComputeAPIImpl(storage, converter);
		for (int i = 0; i < numThreads; i++) {
			File singleThreadedOut = 
					new File(singleThreadFilePrefix + i);
			singleThreadedOut.deleteOnExit();
			TestUser user = new TestUser(singleThreadApi);
			user.run(singleThreadedOut.getCanonicalPath());
		}
		
		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.multiThreadOut.tmp";
		for (int i = 0; i < numThreads; i++) {
			File multiThreadedOut = 
					new File(multiThreadFilePrefix + i);
			multiThreadedOut.deleteOnExit();
			String multiThreadOutputPath = multiThreadedOut.getCanonicalPath();
			TestUser testUser = testUsers.get(i);
			results.add(threadPool.submit(() -> testUser.run(multiThreadOutputPath)));
		}
		
		for (Future<?> future : results) {
			future.get();
		}
		threadPool.shutdownNow();
		
		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, numThreads);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, numThreads);
		Assertions.assertEquals(singleThreaded, multiThreaded);
	}

	private List<String> loadAllOutput(String prefix, int numThreads) throws IOException {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < nThreads; i++) {
			File multiThreadedOut = 
					new File(prefix + i);
			result.addAll(Files.readAllLines(multiThreadedOut.toPath()));
		}
		return result;
	}

}