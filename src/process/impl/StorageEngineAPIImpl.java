package process.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import process.api.StorageEngineAPI;

/**
 * Storage (Process layer) implementation.
 *
 * Read:
 * - expects integers from a file path (usually one per line)
 * - also supports commas on a line like "1,2,3"
 *
 * Write:
 * - writes each string in the list as its own line
 * - so if you pass List.of(oneLine), the output file is exactly one line
 *
 * Checkpoint 5 Part 2:
 * - This is a boundary. No exceptions should escape.
 * - On error, return sentinel values (empty list / no-op).
 */
public class StorageEngineAPIImpl implements StorageEngineAPI {

	@Override
	public List<Integer> readIntegers(String inputSourceId) {
		try {
			// Validation note: we don't require the file to exist here.
			// For this project/tests, "missing input file" should behave like "no data yet".
			if (inputSourceId == null || inputSourceId.isBlank()) {
				return new ArrayList<>();
			}

			File inputFile = new File(inputSourceId);

			// Missing file => "no data yet"
			if (!inputFile.exists()) {
				return new ArrayList<>();
			}

			// Bad input path => sentinel (don't throw)
			if (!inputFile.isFile()) {
				return new ArrayList<>();
			}

			List<Integer> values = new ArrayList<>();

			try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
				String line;
				int lineNumber = 0;

				while ((line = reader.readLine()) != null) {
					lineNumber++;
					addIntegersFromLine(line, lineNumber, values);
				}
			}

			return values;

		} catch (Exception e) {
			// Sentinel value: empty list on any unexpected failure
			return new ArrayList<>();
		}
	}

	@Override
	public void writeResults(String outputDestinationId, List<String> formattedPairs) {
		try {
			// Validation note: output can be any path string. We create parent dirs and overwrite.
			if (outputDestinationId == null || outputDestinationId.isBlank()) {
				return;
			}
			if (formattedPairs == null) {
				return;
			}

			File outputFile = new File(outputDestinationId);

			// If destination is a directory, just no-op (boundary should not throw)
			if (outputFile.exists() && outputFile.isDirectory()) {
				return;
			}

			// make parent dirs if needed
			File parent = outputFile.getParentFile();
			if (parent != null && !parent.exists()) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					return;
				}
			}

			// overwrite file each time
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, false))) {
				for (String line : formattedPairs) {
					if (line == null) {
						continue; // skip nulls just in case
					}
					writer.write(line);
					writer.newLine();
				}
			}

		} catch (Exception e) {
			// Sentinel behavior: no-op on any unexpected failure
			return;
		}
	}

	// Takes a line like "42" or "1, 2, 3" and pushes ints into target.
	private void addIntegersFromLine(String line, int lineNumber, List<Integer> target) {
		if (line == null) {
			return;
		}

		String trimmed = line.trim();
		if (trimmed.isEmpty()) {
			return; // skip blank lines
		}

		String[] tokens = trimmed.split(",");
		for (String raw : tokens) {
			String token = raw.trim();
			if (token.isEmpty()) {
				continue;
			}

			try {
				target.add(Integer.parseInt(token));
			} catch (NumberFormatException ex) {
				// For Part 2: parsing errors should not crash the boundary.
				// Easiest approach is to just ignore bad tokens.
				continue;
			}
		}
	}
}
