package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import testimpl.InMemoryInputConfig;
import testimpl.InMemoryOutputConfig;
import testimpl.InMemoryStorageEngine;

@Tag("integration")
public class InMemoryStorageEngineIT {

    @Test
    void readAndWrite_roundTrip() throws Exception {
        InMemoryInputConfig in = new InMemoryInputConfig("in1", Arrays.asList(10, 20, 30));
        InMemoryOutputConfig out = new InMemoryOutputConfig("out1");

        InMemoryStorageEngine engine = new InMemoryStorageEngine(Arrays.asList(in), Arrays.asList(out));

        List<Integer> read = engine.readIntegers("in1");
        assertEquals(3, read.size());
        assertEquals(Arrays.asList(10, 20, 30), read);

        engine.writeResults("out1", Arrays.asList("ten","twenty","thirty"));

        List<String> written = out.getOutput();
        assertEquals(3, written.size());
        assertEquals("ten", written.get(0));
        assertEquals("twenty", written.get(1));
        assertEquals("thirty", written.get(2));
    }
}
