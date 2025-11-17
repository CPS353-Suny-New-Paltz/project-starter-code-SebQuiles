// src/.../process/impl/StorageEngineAPIPrototype.java
package process.impl;

import java.util.Collections;
import java.util.List;

import process.api.StorageEngineAPI;

public class StorageEngineAPIImpl implements StorageEngineAPI {
	@Override
	public List<Integer> readIntegers(String path) {
		// stubbed data for demo
		return Collections.emptyList();
	}

	@Override
	public void writeResults(String path, List<String> data) {
		// no-op for demo
	}
}
