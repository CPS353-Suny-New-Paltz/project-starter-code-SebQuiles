package tests;
import conceptual.impl.NumberToWordsAPIImpl;
import conceptual.api.NumberToWordsAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class NumberToWordsMinValueTest {

    @Test
    void toWords_minValue_doesNotOverflow_andMatchesExpectedWords() {
        // Why this test exists:
        // Integer.MIN_VALUE cannot be negated as an int (-2147483648 overflows),
        // so naive "minus " + toWords(-n) implementations can recurse forever or crash.

        NumberToWordsAPI converter = new NumberToWordsAPIImpl();

        String words = assertTimeoutPreemptively(
                Duration.ofSeconds(1),
                () -> converter.toWords(Integer.MIN_VALUE)
        );

        assertNotNull(words);
        assertFalse(words.isBlank());

        // This checks BOTH: it didn't crash AND it produced a real, correctly formatted result.
        assertEquals(
                "minus two billion one hundred forty-seven million four hundred eighty-three thousand six hundred forty-eight",
                words
        );
    }
}
