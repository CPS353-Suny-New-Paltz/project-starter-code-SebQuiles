// src/.../process/impl/StorageEngineAPIPrototype.java
package process.impl;

import process.api.StorageEngineAPI;
import java.util.*;

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
