package testimpl;

import java.util.List;
import java.util.ArrayList;

/**
 * Test-only in-memory input config that holds a list of integers and an identifier.
 */
public class InMemoryInputConfig {
    private final String id;
    private final List<Integer> input;

    public InMemoryInputConfig(String id, List<Integer> input) {
        this.id = id;
        this.input = (input == null) ? new ArrayList<>() : new ArrayList<>(input);
    }

    public String getId() {
        return id;
    }

    public List<Integer> getInput() {
        return input;
    }
}
