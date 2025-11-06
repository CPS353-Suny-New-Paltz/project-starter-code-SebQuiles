// src/.../conceptual/impl/StorageEngineAPIDriver.java
package conceptual.impl;

import project.annotations.ProcessAPIPrototype;
import process.api.StorageEngineAPI;
import java.util.*;

public class StorageEngineAPIDriver {
    @ProcessAPIPrototype
    public void processAPIPrototype(StorageEngineAPI api) throws Exception {
        List<Integer> ints = api.readIntegers("numbers.txt");
        api.writeResults("output.txt", Arrays.asList("twelve", "twenty-two"));
    }
}
