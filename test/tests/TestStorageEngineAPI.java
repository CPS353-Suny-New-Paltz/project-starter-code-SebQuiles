package tests;

import org.junit.jupiter.api.Test;

import process.api.StorageEngineAPI;
import process.impl.StorageEngineAPIImpl; // if you want to exercise the real impl

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class TestStorageEngineAPI {
    @Test
    void readIntegers_returnsEmptyByDefaultOrThrows() throws Exception {
        // This mirrors the test you already have — it's a valid smoke check.
        StorageEngineAPI api = new StorageEngineAPIImpl();
        // If implementation doesn't exist or reads a file that isn't present, test may fail — that's expected.
        assertTrue(api.readIntegers("numbers.txt").isEmpty(), "Expected empty list for 'numbers.txt' by smoke test contract");
    }

    @Test
    void writeResults_doesNotThrow() {
        StorageEngineAPI api = new StorageEngineAPIImpl();
        // Smoke: ensure writing doesn't throw for a simple case
        assertDoesNotThrow(() -> api.writeResults("output.txt", List.of("12:twelve")));
    }
}
