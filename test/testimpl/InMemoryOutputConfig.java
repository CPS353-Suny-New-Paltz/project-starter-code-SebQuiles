package testimpl;

import java.util.List;
import java.util.ArrayList;

/**
 * Test-only in-memory output config that holds a list of output strings and an identifier.
 */
public class InMemoryOutputConfig {
    private final String id;
    private final List<String> output;

    public InMemoryOutputConfig(String id) {
        this.id = id;
        this.output = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getOutput() {
        return output;
    }
}
