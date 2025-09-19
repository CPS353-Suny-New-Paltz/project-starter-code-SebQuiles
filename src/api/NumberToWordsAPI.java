package api;

import project.annotations.ConceptualAPI;

/**
 * Conceptual API between the Job Orchestrator and the Number-to-Words Converter.
 * The Orchestrator asks this component to convert an integer into English words.
 */
@ConceptualAPI
public interface NumberToWordsAPI {
    String toWords(int n); // Convert an integer into words
}
