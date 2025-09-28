import conceptual.imp.JobOrchestratorPrototype;
import conceptual.imp.NumberToWordsAPIPrototype;
import process.imp.StorageEngineAPIPrototype;

public class Checkpoint2DemoRun {
    public static void main(String[] args) throws Exception {
        // Build the pieces of the engine (all prototype stubs)
        StorageEngineAPIPrototype storage = new StorageEngineAPIPrototype();
        NumberToWordsAPIPrototype converter = new NumberToWordsAPIPrototype();
        JobOrchestratorPrototype orchestrator = new JobOrchestratorPrototype(storage, converter);

        // Run with explicit delimiters (pair=';', kv=':')
        String out1 = orchestrator.run("file://fake-input", "file://fake-output", ';', ':');
        System.out.println(out1); // expected: 6:six;12:twelve;21:twenty-one

        // Run with NULL delimiters to test defaults (pair=';', kv=':')
        String out2 = orchestrator.run("file://fake-input", "file://fake-output", null, null);
        System.out.println(out2); // expected same as out1 because defaults kick in
    }
}
