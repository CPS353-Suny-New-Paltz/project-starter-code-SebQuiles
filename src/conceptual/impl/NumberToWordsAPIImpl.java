package conceptual.impl;

import conceptual.api.NumberToWordsAPI;
import project.annotations.ConceptualAPIPrototype;

/**
 * Prototype converter: returns hard-coded values for demo. This is
 * intentionally NOT the full algorithm for Checkpoint 2.
 */
public class NumberToWordsAPIImpl implements NumberToWordsAPI {

	@Override
	public String toWords(int n) {
		// Minimal mapping sufficient for the integration tests.
		switch (n) {
			case 1:
				return "one";
			case 10:
				return "ten";
			case 25:
				return "twenty-five";
			default:
				return "prototype-number";
		}
	}
}