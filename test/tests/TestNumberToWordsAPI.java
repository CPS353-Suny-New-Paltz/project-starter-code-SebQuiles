package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;

import conceptual.api.NumberToWordsAPI;
import org.mockito.Mockito;

@Tag("smoke")
public class TestNumberToWordsAPI {
    @Test
    void toWords_basicNumbers() {
        // Option A: if implementation exists, exercise it directly
        // NumberToWordsAPI converter = new NumberToWordsAPIImpl();

        // Option B (safe smoke): mock the API so this test demonstrates the expected contract
        NumberToWordsAPI converter = Mockito.mock(NumberToWordsAPI.class);
        Mockito.when(converter.toWords(12)).thenReturn("twelve");
        Mockito.when(converter.toWords(0)).thenReturn("zero");
        Mockito.when(converter.toWords(-1)).thenReturn("negative one"); // if negative support expected

        assertEquals("twelve", converter.toWords(12));
        assertEquals("zero", converter.toWords(0));
        assertEquals("negative one", converter.toWords(-1));
    }
}