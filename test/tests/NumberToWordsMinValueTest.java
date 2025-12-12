package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import conceptual.api.NumberToWordsAPI;
import conceptual.impl.NumberToWordsAPIImpl;

public class NumberToWordsMinValueTest {

    @Test
    void toWords_minValue_doesNotOverflow_andMatchesExpectedWords() {
        NumberToWordsAPI converter = new NumberToWordsAPIImpl();

        String words = assertTimeoutPreemptively(
                Duration.ofSeconds(1),
                () -> converter.toWords(Integer.MIN_VALUE)
        );

        assertNotNull(words);
        assertFalse(words.isBlank());

        assertEquals(
                "minus two billion one hundred forty-seven million four hundred eighty-three thousand six hundred forty-eight",
                words
        );
    }
}
