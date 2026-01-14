package software.aoc.day06.a.domain.model;

import software.aoc.day06.a.domain.control.OperationStrategy;

import java.util.Arrays;

public enum Operator {
    ADD('+', (a, b) -> a + b),
    MULTIPLY('*', (a, b) -> a * b);

    private final char symbol;
    private final OperationStrategy strategy;

    Operator(char symbol, OperationStrategy strategy) {
        this.symbol = symbol;
        this.strategy = strategy;
    }

    public long apply(long a, long b) {
        return strategy.apply(a, b);
    }

    public static Operator fromSymbol(char symbol) {
        return Arrays.stream(values())
                .filter(operator -> operator.symbol == symbol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + symbol));
    }
}
