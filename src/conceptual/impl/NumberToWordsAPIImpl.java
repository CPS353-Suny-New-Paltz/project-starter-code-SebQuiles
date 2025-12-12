package conceptual.impl;

import conceptual.api.NumberToWordsAPI;

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

    private static final String[] UNITS_0_TO_19 = {
            "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS_20_TO_90 = {
            "", "", "twenty", "thirty", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    @Override
    public String toWords(int n) {
        if (n == 0) {
            return "zero";
        }

        long value = n;
        if (value < 0) {
            return "minus " + convertPositiveNumber(-value);
        }

        return convertPositiveNumber(value);
    }

    private String convertPositiveNumber(long number) {
        StringBuilder result = new StringBuilder();

        appendScale(result, (int) (number / 1_000_000_000L), "billion");
        number %= 1_000_000_000L;

        appendScale(result, (int) (number / 1_000_000L), "million");
        number %= 1_000_000L;

        appendScale(result, (int) (number / 1_000L), "thousand");
        number %= 1_000L;

        if (number > 0) {
            appendWithSpace(result, convertUnderThousand((int) number));
        }

        return result.toString();
    }

    private void appendScale(StringBuilder out, int chunk, String scaleWord) {
        if (chunk <= 0) {
            return;
        }
        appendWithSpace(out, convertUnderThousand(chunk) + " " + scaleWord);
    }

    private void appendWithSpace(StringBuilder builder, String text) {
        if (text == null || text.isBlank()) {
            return;
        }

        if (builder.length() > 0) {
            builder.append(' ');
        }
        builder.append(text);
    }

    private String convertUnderThousand(int number) {
        if (number <= 0) {
            return "";
        }

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
