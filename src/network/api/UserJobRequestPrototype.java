package network.api;

/**
 * Prototype DTO implementation for UserJobRequest.
 */
public class UserJobRequestPrototype implements UserJobRequest {

    private final String inputSource;
    private final String outputDestination;
    private final Character pairSeparator;
    private final Character kvSeparator;
    
    public UserJobRequestPrototype(String inputSource, String outputDestination,
                               Character pairSeparator, Character kvSeparator) {
        this.inputSource = inputSource;
        this.outputDestination = outputDestination;
        this.pairSeparator = pairSeparator;
        this.kvSeparator = kvSeparator;
    }

    @Override
    public String getInputSource() {
        return inputSource;
    }

    @Override
    public String getOutputDestination() {
        return outputDestination;
    }

    @Override
    public Character getPairSeparator() {
        return pairSeparator;
    }

    @Override
    public Character getKvSeparator() {
        return kvSeparator;
    }
}
