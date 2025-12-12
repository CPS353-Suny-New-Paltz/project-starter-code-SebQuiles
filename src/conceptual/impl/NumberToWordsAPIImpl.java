package conceptual.impl;

import conceptual.api.NumberToWordsAPI;
import project.annotations.ConceptualAPIPrototype;

/**
 * Number -> Words converter (Conceptual layer).
 *
 * What it does:
 * - 0 => "zero"
 * - negatives => "minus <words>"
 * - supports up to billions (fits int range)
 *
 * Formatting:
 * - uses spaces between groups
 * - uses hyphens for 21-99 when needed (ex: "twenty-five")
 */
public class NumberToWordsAPIImpl implements NumberToWordsAPI {

    // quick lookups so we don't hardcode a million if/else statements
    private static final String[] UNITS_0_TO_19 = {
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    // index is the tens digit (2 => twenty, 3 => thirty, etc.)
    private static final String[] TENS_20_TO_90 = {
            "", "", "twenty", "thirty", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    @Override
    public String toWords(int n) {
        if (n == 0) {
            return "zero";
        }

        // use long so Integer.MIN_VALUE doesn't break when we negate it
        long value = n;
        if (value < 0) {
            return "minus " + convertPositiveNumber(-value);
        }

        return convertPositiveNumber(value);
    }

    /**
     * Converts a positive number (long), split into: billions, millions, thousands, remainder.
     */
    private String convertPositiveNumber(long number) {
        StringBuilder result = new StringBuilder();

        // billions
        appendScale(result, (int) (number / 1_000_000_000L), "billion");
        number %= 1_000_000_000L;

        // millions
        appendScale(result, (int) (number / 1_000_000L), "million");
        number %= 1_000_000L;

        // thousands
        appendScale(result, (int) (number / 1_000L), "thousand");
        number %= 1_000L;

        // last chunk (0..999)
        if (number > 0) {
            appendWithSpace(result, convertUnderThousand((int) number));
        }

        return result.toString();
    }

    /**
     * Adds "<chunk in words> <scaleWord>" to the output if chunk > 0.
     * Example: chunk=12, scaleWord="thousand" => "twelve thousand"
     */
    private void appendScale(StringBuilder out, int chunk, String scaleWord) {
        if (chunk <= 0) return;
        appendWithSpace(out, convertUnderThousand(chunk) + " " + scaleWord);
    }

    /**
     * Appends text with exactly one space if the builder already has content.
     */
    private void appendWithSpace(StringBuilder builder, String text) {
        if (text == null || text.isBlank()) return;

        if (builder.length() > 0) {
            builder.append(' ');
        }
        builder.append(text);
    }

    /**
     * Converts 1..999 into words.
     * Examples:
     * - 7 => "seven"
     * - 42 => "forty-two"
     * - 305 => "three hundred five"
     */
    private String convertUnderThousand(int number) {
        if (number <= 0) return "";

        if (number < 100) {
            return convertUnderHundred(number);
        }

        int hundreds = number / 100;
        int remainder = number % 100;

        String base = UNITS_0_TO_19[hundreds] + " hundred";
        if (remainder == 0) {
            return base;
        }
        return base + " " + convertUnderHundred(remainder);
    }

    /**
     * Converts 1..99 into words.
     * - 1..19 is direct lookup
     * - 20..99 uses tens + optional "-unit"
     */
    private String convertUnderHundred(int number) {
        if (number < 20) {
            return UNITS_0_TO_19[number];
        }

        int tensDigit = number / 10;
        int onesDigit = number % 10;

        String tensWord = TENS_20_TO_90[tensDigit];
        if (onesDigit == 0) {
            return tensWord;
        }
        return tensWord + "-" + UNITS_0_TO_19[onesDigit];
    }
}
