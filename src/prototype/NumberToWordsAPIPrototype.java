package prototype;

import api.NumberToWordsAPI;
import project.annotations.ConceptualAPIPrototype;

/**
 * Prototype implementation of NumberToWordsAPI.
 * Returns hard-coded words for a few integers, otherwise returns "prototype-number".
 */
public class NumberToWordsAPIPrototype implements NumberToWordsAPI {

    @Override
    @ConceptualAPIPrototype
    public String toWords(int n) {
        // Simple stubbed logic, not a full converter yet
        if (n == 6)  return "six";
        if (n == 12) return "twelve";
        if (n == 21) return "twenty-one";
        return "prototype-number"; // default placeholder
    }
}
