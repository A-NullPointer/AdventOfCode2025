package software.aoc.day03.b.domain.model;

import java.util.stream.IntStream;

public record Bank(String batteries) {

    private static final int REQUIRED_DIGITS = 12;

    public Bank {
        if (batteries == null || batteries.isEmpty()) {
            throw new IllegalArgumentException("Batteries string cannot be null or empty");
        }
        if (batteries.length() < 2) {
            throw new IllegalArgumentException("Bank must have at least 2 batteries");
        }
    }

    // SIN NESTED STREAMS
    /**
    public int getLargestJoltage() {
        int maxJoltage = 0;

        for (int i = 0; i < batteries.length() - 1; i++) {
            int firstDigit = batteries.charAt(i) - '0';

            // Buscar el dígito más grande desde i+1 en adelante
            int maxSecondDigit = 0;
            for (int j = i + 1; j < batteries.length(); j++) {
                int digit = batteries.charAt(j) - '0';
                maxSecondDigit = Math.max(maxSecondDigit, digit);
            }

            int joltage = firstDigit * 10 + maxSecondDigit;
            maxJoltage = Math.max(maxJoltage, joltage);
        }

        return maxJoltage;
    }
    */

    public long getLargestJoltage() {
        int n = batteries.length();
        int toRemove = n - REQUIRED_DIGITS;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char current = batteries.charAt(i);

            // Eliminar dígitos menores mientras sea beneficioso
            while (result.length() > 0 &&
                    result.charAt(result.length() - 1) < current &&
                    toRemove > 0) {
                result.deleteCharAt(result.length() - 1);
                toRemove--;
            }

            result.append(current);
        }

        // Eliminar del final si sobran dígitos
        result.setLength(REQUIRED_DIGITS);

        return Long.parseLong(result.toString());
    }
}
