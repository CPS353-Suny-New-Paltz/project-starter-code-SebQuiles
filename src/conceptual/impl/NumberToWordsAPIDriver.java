package conceptual.impl;

import conceptual.api.NumberToWordsAPI;
import project.annotations.ConceptualAPIPrototype;

public class NumberToWordsAPIDriver {
	@ConceptualAPIPrototype
	public void conceptualAPIPrototype(NumberToWordsAPI numberToWords) {
		String word = numberToWords.toWords(12);
	}
}
