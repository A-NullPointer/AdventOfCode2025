package software.aoc.day03.a.domain.model;

import java.util.Objects;
import java.util.stream.IntStream;

public record Bank(String batteries) {

    private static final int REQUIRED_DIGITS = 2;

    public Bank {
        if (batteries == null || batteries.isEmpty()) {
            throw new IllegalArgumentException("Batteries string cannot be null or empty");
        }
        if (batteries.length() < 2) {
            throw new IllegalArgumentException("Bank must have at least 2 batteries");
        }
    }

    public long getLargestJoltage() {
        int n = batteries.length();
        int toRemove = n - REQUIRED_DIGITS;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char current = batteries.charAt(i);

            while (result.length() > 0 &&
                    result.charAt(result.length() - 1) < current &&
                    toRemove > 0) {
                result.deleteCharAt(result.length() - 1);
                toRemove--;
            }

            result.append(current);
        }

        result.setLength(REQUIRED_DIGITS);

        return Long.parseLong(result.toString());
    }
    /**
    public int getLargestJoltage(){
        // Encontrar el valor máximo considerando:
        // 1. El primer dígito (más grande posible)
        // 2. El segundo dígito (mejor después del primero)
        return IntStream.range(0, batteries.length() - 1)
                .map(i -> {
                    int firstDigit = batteries.charAt(i) - '0';
                    // Buscar el mejor segundo dígito desde i+1 en adelante
                    int secondDigit = IntStream.range(i + 1, batteries.length())
                            .map(j -> batteries.charAt(j) - '0')
                            .max()
                            .orElse(0);
                    return firstDigit * 10 + secondDigit;
                })
                .max()
                .orElse(0);
    }
     */

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
}
