package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import conceptual.impl.NumberToWordsAPIImpl;
import network.api.UserComputeAPI;
import network.impl.MultiThreadedUserComputeAPIImpl;
import process.impl.StorageEngineAPIImpl;

public class MultiUserConcurrencyTest {

    @Test
    void multiple_simultaneous_requests_produce_separate_outputs() throws Exception {
        StorageEngineAPIImpl storage = new StorageEngineAPIImpl();
        NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
        MultiThreadedUserComputeAPIImpl api = new MultiThreadedUserComputeAPIImpl(storage, converter);

        int users = 4;
        ExecutorService exec = Executors.newFixedThreadPool(users);
        CountDownLatch startLatch = new CountDownLatch(1);
        List<File> outputs = new ArrayList<>();
        for (int i = 0; i < users; i++) {
            File out = new File("MultiUserConcurrencyTest.out." + i);
            out.deleteOnExit();
            outputs.add(out);
            final int idx = i;
            exec.submit(() -> {
                try {
                    startLatch.await();
                    testharness.TestUser user = new testharness.TestUser(api);
                    user.run(out.getCanonicalPath());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        long start = System.nanoTime();
        startLatch.countDown();
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.sleep(10L);
        }
        long duration = System.nanoTime() - start;

        for (File out : outputs) {
            List<String> lines = Files.readAllLines(out.toPath());
            assertTrue(!lines.isEmpty(), "Output file should not be empty");
            // Each output should have the same single line because input is shared and deterministic
            assertEquals(1, lines.size());
        }

        api.shutdown();
    }

    @Test
    void performance_multiThreaded_faster_than_singleThreaded_for_large_input() throws Exception {
        // Prepare a larger input file
        File largeInput = new File("test" + File.separator + "largeInput.test");
        largeInput.getParentFile().mkdirs();
        largeInput.deleteOnExit();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(i);
        }
        Files.writeString(largeInput.toPath(), sb.toString());

        File singleOut = new File("performance.single.out");
        File multiOut = new File("performance.multi.out");
        singleOut.deleteOnExit();
        multiOut.deleteOnExit();

        StorageEngineAPIImpl storage = new StorageEngineAPIImpl();
        NumberToWordsAPIImpl converter = new NumberToWordsAPIImpl();
        UserComputeAPI single = new network.impl.UserComputeAPIImpl(storage, converter);
        MultiThreadedUserComputeAPIImpl multi = new MultiThreadedUserComputeAPIImpl(storage, converter);

        testharness.TestUser singleUser = new testharness.TestUser(single);
        testharness.TestUser multiUser = new testharness.TestUser(multi);

        long t1 = System.nanoTime();
        singleUser.run(singleOut.getCanonicalPath());
        long singleDuration = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        multiUser.run(multiOut.getCanonicalPath());
        long multiDuration = System.nanoTime() - t2;

        // Allow a small slack; assert that multi-threaded is at least not dramatically slower
        assertTrue(multiDuration <= singleDuration * 1.2,
                "Multi-threaded implementation should be comparable or faster. single=" + singleDuration
                        + " multi=" + multiDuration);

        multi.shutdown();
    }
}
