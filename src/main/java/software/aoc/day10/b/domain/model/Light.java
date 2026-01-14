package software.aoc.day10.b.domain.model;

public enum Light {
    ON('#'),
    OFF('.');

    private char symbol;
    Light(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public Light toggle() {
        return this == ON ? OFF : ON;
    }

    public static Light fromSymbol(char symbol) {
        return symbol == '#' ? ON : OFF;
    }

    public boolean isOn() {
        return this == ON;
    }
}
