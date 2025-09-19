package prototype;

import api.NumberToWordsAPI;
import api.StorageEngineAPI;
import java.util.ArrayList;
import java.util.List;

/**
 * Prototype Job Orchestrator.
 * Uses StorageEngineAPI to read integers, calls NumberToWordsAPI to convert,
 * formats the results, and writes them back.
 */
public class JobOrchestratorPrototype {

    private final StorageEngineAPI storage;
    private final NumberToWordsAPI converter;

    public JobOrchestratorPrototype(StorageEngineAPI storage, NumberToWordsAPI converter) {
        this.storage = storage;
        this.converter = converter;
    }

    // Returns something like "6:six;12:twelve;21:twenty-one"
    public String run(String inputSource, String outputDest, Character pairSep, Character kvSep) throws Exception {
        char p = (pairSep == null) ? ';' : pairSep.charValue();
        char k = (kvSep == null) ? ':' : kvSep.charValue();

        List<Integer> ints = storage.readIntegers(inputSource);
        StringBuilder sb = new StringBuilder();
        List<String> pairs = new ArrayList<>();

        boolean first = true;
        for (Integer n : ints) {
            if (!first) {
                sb.append(p);              
            }
            first = false;

            String words = converter.toWords(n);
            String pair = n + String.valueOf(k) + words;
            sb.append(pair);
            pairs.add(pair);
        }

        storage.writeResults(outputDest, pairs);
        return sb.toString();
    }
}
