// src/.../conceptual/impl/StorageEngineAPIDriver.java
package process.impl;

import java.util.Arrays;
import java.util.List;

import process.api.StorageEngineAPI;
import project.annotations.ProcessAPIPrototype;

public class StorageEngineAPIDriver {
	@ProcessAPIPrototype
	public void processAPIPrototype(StorageEngineAPI api) throws Exception {
		List<Integer> ints = api.readIntegers("numbers.txt");
		api.writeResults("output.txt", Arrays.asList("twelve", "twenty-two"));
	}
}
