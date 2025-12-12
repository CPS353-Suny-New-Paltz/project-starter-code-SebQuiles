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
 * - integers from a file path (usually one per line)
 * - also supports commas on a line like "1,2,3"
 *
 * Write:
 * - each string becomes its own line
 * - so List.of(oneLine) => output file is exactly one line
 */
public class StorageEngineAPIImpl implements StorageEngineAPI {

    @Override
    public List<Integer> readIntegers(String inputSourceId) throws Exception {
        if (inputSourceId == null || inputSourceId.isBlank()) {
            throw new IllegalArgumentException("Input source path must not be empty");
        }

        File inputFile = new File(inputSourceId);

        // Some tests expect missing input to mean "no data yet".
        if (!inputFile.exists()) {
            return new ArrayList<>();
        }

        if (!inputFile.isFile()) {
            throw new IOException("Input path is not a file: " + inputFile.getAbsolutePath());
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
    }

    @Override
    public void writeResults(String outputDestinationId, List<String> formattedPairs) throws Exception {
        if (outputDestinationId == null || outputDestinationId.isBlank()) {
            throw new IllegalArgumentException("Output destination path must not be empty");
        }
        if (formattedPairs == null) {
            throw new IllegalArgumentException("formattedPairs must not be null");
        }

        File outputFile = new File(outputDestinationId);

        File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Could not create output directory: " + parent.getAbsolutePath());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, false))) {
            for (String line : formattedPairs) {
                if (line == null) {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void addIntegersFromLine(String line, int lineNumber, List<Integer> target) {
        if (line == null) {
            return;
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return;
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
                throw new NumberFormatException("Invalid integer '" + token + "' on line " + lineNumber);
            }
        }
    }
}
