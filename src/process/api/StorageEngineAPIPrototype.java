package process.api;

import project.annotations.ProcessAPIPrototype;

import java.util.List;
import java.util.ArrayList;

public class StorageEngineAPIPrototype {
	@ProcessAPIPrototype
	public void StorageEngineApiPrototype(StorageEngineAPI storageEngineApi) {
		List<Integer> result = storageEngineApi.readIntegers("numbers.txt");
		// turn a comma-separated String into a List<String> to pass to writeResults
		String csv = "twelve, twenty-two";
		List<String> pairs = new ArrayList<>();
		for (String s : csv.split(",")) {
			String trimmed = s.trim();
			if (!trimmed.isEmpty()) {
				pairs.add(trimmed);
			}
		}
		List<String> result2 = storageEngineApi.writeResults("outputDestination.txt", pairs);
	}
}