package network.api;

public interface UserJobRequest {
	String getInputSource();        // Where to read input from (e.g., file path, DB)
    String getOutputDestination();  // Where to write the results (e.g., file path, DB)
    Character getPairSeparator();   // Separator between input-output pairs (default = ';')
    Character getKvSeparator();     // Separator between number and words (default = ':')
}
