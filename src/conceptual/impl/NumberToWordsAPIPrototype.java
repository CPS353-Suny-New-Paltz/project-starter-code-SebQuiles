package conceptual.impl;

import conceptual.api.NumberToWordsAPI;
import project.annotations.ConceptualAPIPrototype;

/**
 * Prototype converter: returns hard-coded values for demo.
 * This is intentionally NOT the full algorithm for Checkpoint 2.
 */
public class NumberToWordsAPIPrototype implements NumberToWordsAPI {

    @Override
    @ConceptualAPIPrototype
    public String toWords(int n) {
        if (n == 6) {
            return "six";
        }
        if (n == 12) {
            return "twelve";
        }
        if (n == 21) {
            return "twenty-one";
        }
        return "prototype-number";
    }
}
