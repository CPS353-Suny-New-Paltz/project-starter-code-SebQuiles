// src/.../process/impl/StorageEngineAPIPrototype.java
package process.impl;

import java.util.Arrays;
import java.util.List;

import process.api.StorageEngineAPI;

public class StorageEngineAPIPrototype implements StorageEngineAPI {
	@Override
	public List<Integer> readIntegers(String path) {
		// stubbed data for demo
		return Arrays.asList(6, 12, 21);
	}

	@Override
	public void writeResults(String path, List<String> data) {
		// no-op for demo
	}
}
